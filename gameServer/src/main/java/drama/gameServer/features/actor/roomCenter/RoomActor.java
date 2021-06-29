package drama.gameServer.features.actor.roomCenter;

import akka.actor.ActorRef;
import akka.actor.Kill;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.base.msg.room.In_PlayerDisconnectedQuitRoomMsg;
import dm.relationship.base.msg.room.In_PlayerQuitRoomMsg;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Result_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.roomCenter.ctrl.RoomCtrl;
import drama.gameServer.features.actor.roomCenter.ctrl.RoomPlayerCtrl;
import drama.gameServer.features.actor.roomCenter.msg.In_CheckPlayerAllReadyMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerChooseRoleRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerCreateRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerJoinRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerOnCanSearchRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerOnReadyRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerOnSwitchStateRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerSearchRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerSyncClueRoomMsg;
import drama.gameServer.features.actor.roomCenter.msg.In_playerOnOpenDubRoomMsg;
import drama.gameServer.features.actor.roomCenter.pojo.Room;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import drama.gameServer.features.actor.roomCenter.utils.RoomContainer;
import drama.gameServer.system.actor.DmActorSystem;
import drama.protos.CodesProtos;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.RoomProtos;
import drama.protos.RoomProtos.Cm_Room;
import drama.protos.RoomProtos.Cm_Room.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.message.interfaces.InnerMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static drama.gameServer.features.actor.playerIO.utils.RoomProtoUtils.createSmRoomByActionWithoutRoomPlayer;
import static drama.gameServer.features.actor.playerIO.utils.RoomProtoUtils.setAction;


public class RoomActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomActor.class);
    private final RoomCtrl roomCtrl;
    private final String roomId;
    private final String masterId;

    public RoomActor(RoomCtrl roomCtrl, String roomId, String masterId) {
        this.roomId = roomId;
        this.masterId = masterId;
        this.roomCtrl = roomCtrl;
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof RoomNetWorkMsg) {
            onCmRoomMsg((RoomNetWorkMsg) msg);
        } else if (msg instanceof InnerMsg) {
            onInnerMsg((InnerMsg) msg);
        }
    }

    private void onInnerMsg(InnerMsg msg) {
        if (msg instanceof In_PlayerDisconnectedQuitRoomMsg) {
            onPlayerDisconnectedQuitRoom((In_PlayerDisconnectedQuitRoomMsg) msg);
        } else if (msg instanceof In_CheckPlayerAllReadyMsg) {
            onCheckPlayerAllReadyMsg((In_CheckPlayerAllReadyMsg) msg);
        }

    }


    private void onCheckPlayerAllReadyMsg(In_CheckPlayerAllReadyMsg msg) {
        if (roomCtrl.checkAllPlayerReady() && roomCtrl.hasNextStateAndTimes()) {
            //所有人都已经举手准备好了,并且配置里还有下一个环节
            roomCtrl.setNextStateAndTimes();
            //TODO 通知玩家房间状态已经更新成播放剧本状态
            _tellAllRoomPlayer(new In_PlayerOnSwitchStateRoomMsg(roomCtrl.getTarget()), ActorRef.noSender());
        }
    }

    private void onPlayerDisconnectedQuitRoom(In_PlayerDisconnectedQuitRoomMsg msg) {
        String playerId = msg.getPlayerId();
        if (roomCtrl.containsPlayer(playerId)) {
            roomCtrl.removePlayer(playerId);
            //TODO 玩家已经掉线了就不用回消息了通知一下房间里其他人
        } else {
            LOGGER.debug("玩家不在房间中,退出房间异常 并且玩家已经掉线了,忽略: playerId={}, roomId={}", playerId, roomId);
        }
    }

    private void onCmRoomMsg(RoomNetWorkMsg msg) {
        if (msg.getMessage() instanceof Cm_Room) {
            Cm_Room cm_room = (Cm_Room) msg.getMessage();
            RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
            b = setAction(b, cm_room);
            try {
                switch (cm_room.getAction().getNumber()) {
                    case Action.CREAT_VALUE:
                        onCreateRoomMsg(cm_room, msg.getConnection(), msg.getPlayer());
                        break;
                    case Action.JION_VALUE:
                        onJoinRoomMsg(cm_room, msg.getPlayer());
                        break;
                    case Action.QUIT_VALUE:
                        onQuitRoomMsg(cm_room, msg.getPlayer());
                        break;
                    case Action.ANSWER_VALUE:
                        onAnswer(cm_room, msg.getPlayer());
                        break;
                    case Action.READY_VALUE:
                        onReady(msg.getPlayer(), cm_room.getState(), cm_room.getStateTimes());
                        break;
                    case Action.IS_DUB_VALUE:
                        onOpenDub(msg.getPlayer());
                        break;
                    case Action.SEARCH_VALUE:
                        onSearch(msg.getPlayer(), cm_room.getTypeName());
                        break;
                    case Action.SYNC_ROOM_CLUE_VALUE:
                        onSyncRoomClue(msg.getPlayer());
                        break;
                    case Action.SYNC_PLAYER_CLUE_VALUE:
                        onSyncPlayerClue(msg.getPlayer());
                        break;
                    case Action.SYNC_CAN_SEARCH_VALUE:
                        onCanSearch(msg.getPlayer());
                        break;
                    default:
                        break;
                }
            } catch (BusinessLogicMismatchConditionException e) {
                MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, b.getAction(), e.getErrorCodeEnum());
                msg.getConnection().send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
            }
        }
    }


    private void onSyncPlayerClue(Player player) {
        _checkRoomContainsPlayer(player);
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        _tellPlayerSyncClueMsg(player.getPlayerId(), roomPlayerCtrl.getClueIds(), RoomProtos.Sm_Room.Action.RESP_SYNC_PLAYER_CLUE);
    }

    private void onSyncRoomClue(Player player) {
        _checkRoomContainsPlayer(player);
        _tellPlayerSyncClueMsg(player.getPlayerId(), roomCtrl.getClueIds(), RoomProtos.Sm_Room.Action.RESP_SYNC_ROOM_CLUE);
    }


    private void onSearch(Player player, String typeName) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SEARCH) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            throw new BusinessLogicMismatchConditionException("房间状态不匹配", EnumsProtos.ErrorCodeEnum.WRONG_ROOM_STATE);
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        //TODO 读配置 判断剩余次数
        List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(roomCtrl.getDramaId());
        if (acterRowList == null) {
            String msg = String.format("剧本ID不存在....那房间咋能创建成功呢,dramaId=%s", roomCtrl.getDramaId());
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        int times = Table_Acter_Row.getSrchTimes(acterRowList, roomPlayerCtrl.getRoleId(), roomCtrl.getRoomStateTimes());
        if (roomPlayerCtrl.getSrchTimes() == MagicNumbers.DEFAULT_ZERO) {
            String msg = String.format("搜证次数已经用完了,这个请求应该被客户端挡住的! playerId=%s", player.getPlayerId());
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        // 获取线索配置 by typeId
        List<Table_Search_Row> srchRowList = Table_Search_Row.getSearchByTypeNameAndStateTimes(typeName, roomCtrl.getRoomStateTimes());
        int id = MagicNumbers.DEFAULT_ZERO;
        for (Table_Search_Row row : srchRowList) {
            //房间线索和玩家的线索都没有这条线索才可以搜取
            if (!roomCtrl.containsClueId(row.getId()) && !roomPlayerCtrl.containsClueId(row.getId())) {
                roomPlayerCtrl.addClueId(row.getId());
                roomCtrl.addClueId(row.getId());
                //一次只能搜一条线索,搜到就break;
                id = row.getId();
                roomPlayerCtrl.subSrchTimes();
                break;
            }
            //如果没进上面的判断代表关于这个typeId的线索都搜索完了
        }
        //发往playerActor回消息
        String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", player.getPlayerId());
        DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerSearchRoomMsg(id, roomPlayerCtrl.getTarget()), ActorRef.noSender());
        checkPlayerFinishSearchShowHideClueMsg();
    }

    private void checkPlayerFinishSearchShowHideClueMsg() {
        if (roomCtrl.checkPlayerFinishSearch()) {
            //所有人都已经用完了搜证次数,并且有隐藏配置,放进房间里,然后同步给玩家
            List<Integer> allHideClueIds = Table_Search_Row.getAllHideClueIds(roomCtrl.getRoomStateTimes());
            if (allHideClueIds.size() != MagicNumbers.DEFAULT_ZERO) {
                if (!roomCtrl.containsClueIds(allHideClueIds)) {
                    roomCtrl.addClueIds(allHideClueIds);
                    _tellAllRoomPlayer(new In_PlayerSyncClueRoomMsg(allHideClueIds, RoomProtos.Sm_Room.Action.RESP_SYNC_ROOM_CLUE), ActorRef.noSender());
                }
            }
        }
    }


    private void onOpenDub(Player player) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() == EnumsProtos.RoomStateEnum.READ) {
            RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
            // 设置成使用配音状态
            if (!roomPlayerCtrl.isDub()) {
                roomPlayerCtrl.setDub(true);
                _tellAllRoomPlayer(new In_playerOnOpenDubRoomMsg(roomPlayerCtrl.getTarget()), ActorRef.noSender());
            }
        }

    }

    private void onReady(Player player, EnumsProtos.RoomStateEnum state, int stateTimes) {
        _checkRoomContainsPlayer(player);
        //暂时选角阶段强制判断是否已经选定角色,可能有个别的剧本没有强制选定角色
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomCtrl.getRoomState() == state && roomCtrl.getRoomStateTimes() == stateTimes) {
            //TODO 根据状态获取配置,应该有一些限制举手准备的条件
            if (roomCtrl.hasChooseRole(roomPlayerCtrl.getRoleId(), player.getPlayerId())) {
                roomPlayerCtrl.setReady(!roomPlayerCtrl.isReady());
                //通知房间内所有玩家
                _tellAllRoomPlayer(new In_PlayerOnReadyRoomMsg(roomPlayerCtrl.getTarget()), ActorRef.noSender());
            } else {
                LOGGER.debug("玩家还没有选定角色,不能举手准备 playerId={}", player.getPlayerId());
                throw new BusinessLogicMismatchConditionException("玩家还没有选定角色,不能举手准备");
            }
        } else {
            LOGGER.debug("请求准备的状态和房间状态不匹配 playerId={}, RequestStateEnum={},RoomStateEnum={},RequestStateTimes={},RoomStateTimes={}", //
                    player.getPlayerId(), state, roomCtrl.getRoomState().toString(), stateTimes, roomCtrl.getRoomStateTimes());//
            throw new BusinessLogicMismatchConditionException("请求准备的状态和房间状态不匹配", EnumsProtos.ErrorCodeEnum.WRONG_ROOM_STATE);
        }

    }


    private void onAnswer(Cm_Room cm_room, Player player) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.ANSWER) {
            LOGGER.debug("房间状态不是选角状态,非法的请求 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            throw new BusinessLogicMismatchConditionException("房间状态不是选角状态,非法的请求", EnumsProtos.ErrorCodeEnum.WRONG_ROOM_STATE);
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        EnumsProtos.SexEnum sex = player.getBase().getSex();
        List<String> optionsList = cm_room.getOptionsList();
        List<Integer> rightAnswerIdx = getRightAnswerIdx(optionsList, roomCtrl.getDramaId(), sex);
        int roleIdx = roomCtrl.getRoleIdx(rightAnswerIdx);
        if (roleIdx != MagicNumbers.DEFAULT_ZERO && !roomCtrl.hasChooseRole(roleIdx, roomPlayerCtrl.getPlayerId())) {
            roomPlayerCtrl.setRoleId(roleIdx);
            roomCtrl.chooseRole(roomPlayerCtrl.getTarget());
            //通知房间内所有玩家
            _tellAllRoomPlayer(new In_PlayerChooseRoleRoomMsg(roomPlayerCtrl.getTarget()), ActorRef.noSender());
        } else {
            String answer = "";
            for (String s : cm_room.getOptionsList()) {
                answer = answer + s;
            }
            String msg = String.format("没有选出角色 playerId=%s, answer=%s", player.getPlayerId(), answer);
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg);
        }
    }


    /**
     * 根据答题答案获取对应的角色优先级列表
     *
     * @param optionsList
     * @param dramaId
     * @param sex
     * @return
     */
    private List<Integer> getRightAnswerIdx(List<String> optionsList, int dramaId, EnumsProtos.SexEnum sex) {
        List<Integer> resIdx = new ArrayList<>();
        List<Table_Result_Row> rows = Table_Result_Row.getTableResultRowByDramaIdAndSex(dramaId, sex);
        for (Table_Result_Row row : rows) {
            if (optionsList.size() == row.getAnswer().size()) {
                long count = row.getAnswer().stream().filter(it -> !optionsList.contains(it)).count();
                if (count == MagicNumbers.DEFAULT_ZERO) {
                    resIdx = row.getResult();
                    break;
                }
            }
        }
        return resIdx;
    }

    private void onCanSearch(Player player) {
        _checkRoomContainsPlayer(player);
        List<Table_Search_Row> values = RootTc.get(Table_Search_Row.class).values();
        List<Table_Search_Row> result = values.stream().filter(it -> !roomCtrl.getClueIds().contains(it.getId()) && roomCtrl.getRoomStateTimes() == it.getSrchNum()).collect(Collectors.toList());
        List<Integer> typeIds = new ArrayList<>();
        for (Table_Search_Row row : result) {
            if (!typeIds.contains(row.getTypeid())) {
                typeIds.add(row.getTypeid());
            }
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", player.getPlayerId());
        DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerOnCanSearchRoomMsg(typeIds, roomPlayerCtrl.getTarget()), ActorRef.noSender());
    }

    private void onJoinRoomMsg(Cm_Room cm_room, Player player) {
        Room room = roomCtrl.getTarget();
        if (roomCtrl.checkRoomIsFull()) {
            LOGGER.debug("房间已满,playerId={},roomId={}", player.getPlayerId(), roomId);
            throw new BusinessLogicMismatchConditionException("房间已满 roomId=" + roomId + ",playerId=" + player.getPlayerId(), EnumsProtos.ErrorCodeEnum.ROOM_FULL);
        }
        if (roomCtrl.containsPlayer(player.getPlayerId())) {
            LOGGER.debug("玩家已经在房间内 playerId={},roomId ={}", player.getPlayerId(), roomId);
            for (String s : room.getIdToRoomPlayer().keySet()) {
                LOGGER.debug("onJoinRoomMsg 房间内玩家 playerId={}", s);
            }
        } else {
            RoomPlayer roomPlayer = new RoomPlayer(player.getPlayerId(), roomId);
            RoomPlayerCtrl roomPlayerCtrl = GlobalInjector.getInstance(RoomPlayerCtrl.class);
            roomPlayerCtrl.setTarget(roomPlayer);
            roomCtrl.addPlayer(roomPlayer, roomPlayerCtrl);
            String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", player.getPlayerId());
            DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerJoinRoomMsg(room), ActorRef.noSender());
        }
    }

    private void onQuitRoomMsg(Cm_Room cm_room, Player player) {
        if (roomCtrl.containsPlayer(player.getPlayerId())) {
            if (_isMaster(player)) {
                // 玩家是房间主人要把所有房间内玩家请出去,通知所有玩家
                LOGGER.debug("房间主人退出了房间,所有玩家清出房间");
                _tellAllRoomPlayer(new In_PlayerQuitRoomMsg(roomId, masterId), ActorRef.noSender());
                RoomContainer.remove(roomId, player.getPlayerId());
                self().tell(Kill.getInstance(), ActorRef.noSender());
            } else {
                // 非房间主人,直接退出就行
                roomCtrl.removePlayer(player.getPlayerId());
                for (String s : roomCtrl.getTarget().getIdToRoomPlayer().keySet()) {
                    LOGGER.debug("onQuitRoomMsg 房间内玩家 playerId={}", s);
                }
                String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", player.getPlayerId());
                DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerQuitRoomMsg(roomId, masterId), ActorRef.noSender());
            }
        } else {
            LOGGER.debug("玩家不在房间中,退出房间异常 忽略: playerId={}, roomId={}", player.getPlayerId(), roomId);
        }
    }


    private void onCreateRoomMsg(Cm_Room cm_room, Connection connection, Player player) {
        LOGGER.debug("RoomActor收到消息: onCreateRoom playerId = {}, roomId ={}, dramaName={}", masterId, roomId, cm_room.getDramaId());
        roomCtrl.createRoom(roomId, player.getPlayerId(), cm_room.getDramaId());
        RoomContainer.add(roomCtrl.getTarget(), masterId);
        MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, RoomProtos.Sm_Room.Action.RESP_CREATE);
        br.setResult(true);
        RoomProtos.Sm_Room b = createSmRoomByActionWithoutRoomPlayer(roomCtrl.getTarget(), RoomProtos.Sm_Room.Action.RESP_CREATE);
        br.setSmRoom(b);
        //TODO 转回playerActor回消息
        String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", masterId);
        DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerCreateRoomMsg(roomCtrl.getTarget()), ActorRef.noSender());//TODO 通
        LOGGER.debug("房间创建成功 roomId={},dramaId={},MasterId={}", roomId, cm_room.getDramaId(), masterId);
    }

    private void _checkRoomContainsPlayer(Player player) {
        if (!roomCtrl.containsPlayer(player.getPlayerId())) {
            LOGGER.debug("玩家不在房间中, playerId={}, roomId={}", player.getPlayerId(), roomId);
            throw new BusinessLogicMismatchConditionException("玩家不在房间中 playerId=" + player.getPlayerId() + ",roomId=" + roomId, EnumsProtos.ErrorCodeEnum.NOT_IN_ROOM);
        }
    }

    private void _tellPlayerSyncClueMsg(String playerId, List<Integer> clueIds, RoomProtos.Sm_Room.Action action) {
        String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", playerId);
        DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerSyncClueRoomMsg(roomCtrl.getClueIds(), action), ActorRef.noSender());
    }

    private void _tellAllRoomPlayer(InnerMsg msg, ActorRef sender) {
        for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", entries.getKey());
            DmActorSystem.get().actorSelection(actorName).tell(msg, ActorRef.noSender());
        }
    }

    private boolean _isMaster(Player player) {
        return player.getPlayerId().equals(roomCtrl.getMasterId());
    }


    public String getRoomId() {
        return roomId;
    }

    public String getMasterId() {
        return masterId;
    }
}
