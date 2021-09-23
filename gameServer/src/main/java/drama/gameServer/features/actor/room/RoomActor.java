package drama.gameServer.features.actor.room;

import akka.actor.ActorRef;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.GmRoomMsg;
import dm.relationship.base.msg.interfaces.RoomInnerMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.base.msg.room.In_PlayerDisconnectedRoomMsg;
import dm.relationship.base.msg.room.In_PlayerKillRoomMsg;
import dm.relationship.base.msg.room.In_PlayerQuitRoomMsg;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_RunDown_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import dm.relationship.table.tableRows.Table_SelectRead_Row;
import dm.relationship.table.tableRows.Table_Solo_Row;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.login.utils.LogHandler;
import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import drama.gameServer.features.actor.room.msg.In_CheckPlayerAllReadyRoomMsg;
import drama.gameServer.features.actor.room.msg.In_GmKillRoomMsg;
import drama.gameServer.features.actor.room.msg.In_KillRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerAllFinishChooseDubRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerCanSelectDraftRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerCanSelectRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerChooseRoleRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerCreateRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerHasSelectDraftRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerIsVotedRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerJoinRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnCanSearchRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnCanVoteSearchRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnOpenDubRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnReadyRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnSwitchStateRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnUnlockClueRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerReconnectRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSearchRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSelectDraftRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSelectReadRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSoloResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSyncClueRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSyncSoloIdxRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteListRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteRemainRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteSearchResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteSearchRoomMsg;
import drama.gameServer.features.actor.room.msg._GmAllRoomPlayerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.actor.room.utils.RoomProtoUtils;
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
import ws.common.network.utils.EnumUtils;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static drama.gameServer.features.actor.room.utils.RoomProtoUtils.createSmRoomByActionWithoutRoomPlayer;
import static drama.gameServer.features.actor.room.utils.RoomProtoUtils.setAction;


public class RoomActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomActor.class);
    private final RoomCtrl roomCtrl;
    private final String roomId;
    private final String masterId;
    private Player player;

    public RoomActor(RoomCtrl roomCtrl, String roomId, String masterId) {
        this.roomId = roomId;
        this.masterId = masterId;
        this.roomCtrl = roomCtrl;
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof RoomNetWorkMsg) {
            onCmRoomMsg((RoomNetWorkMsg) msg);
        } else if (msg instanceof RoomInnerMsg) {
            onInnerMsg((RoomInnerMsg) msg);
        } else if (msg instanceof GmRoomMsg) {
            onGmRoomMsg((GmRoomMsg) msg);
        }
    }

    private void onGmRoomMsg(GmRoomMsg msg) {
        if (msg instanceof In_GmKillRoomMsg) {
            onGmKillRoomMsg((In_GmKillRoomMsg) msg);
        } else if (msg instanceof _GmAllRoomPlayerMsg.Request) {
            onAllRoomPlayerMsg((_GmAllRoomPlayerMsg.Request) msg);
        }
    }

    private void onAllRoomPlayerMsg(_GmAllRoomPlayerMsg.Request msg) {
        List<RoomPlayer> roomPlayerList = new ArrayList<>();
        for (Map.Entry<String, RoomPlayer> entry : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            roomPlayerList.add(entry.getValue());
        }
        _GmAllRoomPlayerMsg.Response response = new _GmAllRoomPlayerMsg.Response(roomPlayerList);
        getSender().tell(response, ActorRef.noSender());
    }

    private void onInnerMsg(RoomInnerMsg msg) {
        if (msg instanceof In_PlayerDisconnectedRoomMsg) {
            onPlayerDisconnectedRoom((In_PlayerDisconnectedRoomMsg) msg);
        } else if (msg instanceof In_CheckPlayerAllReadyRoomMsg) {
            onCheckPlayerAllReadyMsg((In_CheckPlayerAllReadyRoomMsg) msg);
        } else if (msg instanceof In_PlayerReconnectRoomMsg) {
            onPlayerReconnectRoomMsg((In_PlayerReconnectRoomMsg) msg);
        }
    }

    private void onGmKillRoomMsg(In_GmKillRoomMsg msg) {
        LOGGER.debug("GM关闭房间,所有玩家清出房间");
        for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            RoomPlayerCtrl entryRoomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(entries.getKey());
            if (entryRoomPlayerCtrl.hasRole()) {
                int entryRoleId = entryRoomPlayerCtrl.getRoleId();
                entryRoomPlayerCtrl.setRoleId(0);
                roomCtrl.removeRole(entryRoleId);
            }
            roomCtrl.removePlayer(entries.getKey());
            In_PlayerQuitRoomMsg in_playerQuitRoomMsg = new In_PlayerQuitRoomMsg(entries.getKey(), roomId, masterId);
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerQuitRoomMsg, ActorRef.noSender());
        }
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_KillRoomMsg(roomId, masterId), sender());
    }

    private void onPlayerReconnectRoomMsg(In_PlayerReconnectRoomMsg msg) {
        Connection connection = msg.getConnection();
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SYNC_ROOM;
        RoomProtos.Sm_Room_Info smRoomInfo = RoomProtoUtils.createSmRoomInfo(roomCtrl.getTarget());
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder broom = RoomProtos.Sm_Room.newBuilder();
        broom.setAction(action);
        broom.addRoomInfos(smRoomInfo);
        response.setSmRoom(broom.build());
        connection.send(new MessageSendHolder(response.build(), response.getSmMsgAction(), new ArrayList<>()));
    }


    private void onSelectRead() {
        _checkRoomContainsPlayer(player);
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        List<Boolean> result = new ArrayList<>();
        for (Table_SelectRead_Row value : RootTc.get(Table_SelectRead_Row.class).values()) {
            if (value.getDramaId() == roomCtrl.getDramaId()) {
                for (TupleCell<String> tupleCell : value.getDraftCondition()) {
                    if (tupleCell.get(TupleCell.FIRST).equals("Draft")) {
                        Integer num = Integer.valueOf(tupleCell.get(TupleCell.SECOND));
                        boolean draftResult = roomCtrl.isRightDraft(num);
                        result.add(value.getC1bool() == draftResult);
                    }
                }

                for (TupleCell<String> tupleCell : value.getVoteCondition()) {
                    if (tupleCell.get(TupleCell.FIRST).equals("Vote")) {
                        Integer num = Integer.valueOf(tupleCell.get(TupleCell.SECOND));
                        boolean voteResult = roomCtrl.isRightVote(num);
                        result.add(value.getC2bool() == voteResult);
                    }
                }
                if (!result.contains(false)) {
                    LOGGER.debug("value.getResult={},idx={}", value.getResult(), value.getIdx());
                    In_PlayerSelectReadRoomMsg in_playerSelectReadRoomMsg = new In_PlayerSelectReadRoomMsg(roomPlayerCtrl.getPlayerId(), value.getResult());
                    DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerSelectReadRoomMsg, ActorRef.noSender());
                    break;
                }
            }
            result.clear();
        }
    }

    private void onCheckPlayerAllChooseDubMsg() {
        if (roomCtrl.checkPlayerFinishChoosDub()) {
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                In_PlayerAllFinishChooseDubRoomMsg in_playerAllFinishChooseDubRoomMsg = new In_PlayerAllFinishChooseDubRoomMsg(entries.getKey(), roomCtrl.getTarget());
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerAllFinishChooseDubRoomMsg, ActorRef.noSender());
            }
        }
    }

    private void onCheckPlayerAllVoteSearchRoomMsg() {
        int voteNum = roomCtrl.getClueIds().size();
        if (roomCtrl.checkPlayerFinishVoteSearch(voteNum)) {
            // 通知所有玩家投票结果和最终投票选中的线索
            int clueId = roomCtrl.getVoteSearchClueId(voteNum);
            if (!roomCtrl.containsClueId(clueId)) {
                roomCtrl.addClueId(clueId);
                roomCtrl.removeCanVoteSearchTypeId(clueId);
                Map<Integer, List<Integer>> voteResult = roomCtrl.getVoteSearchTypeIdToPlayerRoleId(voteNum);
                RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
                for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                    In_PlayerVoteSearchResultRoomMsg in_playerVoteSearchResultRoomMsg = new In_PlayerVoteSearchResultRoomMsg(entries.getKey(), voteResult, clueId, roomPlayerCtrl.getTarget(), roomCtrl.getDramaId());
                    DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerVoteSearchResultRoomMsg, ActorRef.noSender());
                }
            }
        }
    }

    private void onCheckPlayerAllReadyMsg(In_CheckPlayerAllReadyRoomMsg msg) {
        if (!roomCtrl.checkAllPlayerReady()) {
            //没有全部举手
            return;
        } else if (!roomCtrl.hasNextStateAndTimes()) {
            //没有下一个环节了
            return;
        } else if (!roomCtrl.isTimeCanReady()) {
            // 没有达到限制的准备时间,还不能举手
            return;
        } else if (!roomCtrl.checkPlayerFinishSearch()) {
            // 有玩家没有搜完证
            return;
        }
        //所有人都已经举手准备好了,并且配置里还有下一个环节
        roomCtrl.setNextStateAndTimes();
        //TODO 通知玩家房间状态已经更新成播放剧本状态
        for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            In_PlayerOnSwitchStateRoomMsg in_playerOnSwitchStateRoomMsg = new In_PlayerOnSwitchStateRoomMsg(entries.getKey(), roomCtrl.getTarget());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerOnSwitchStateRoomMsg, ActorRef.noSender());
        }
    }

    private void onPlayerDisconnectedRoom(In_PlayerDisconnectedRoomMsg msg) {
        String playerId = msg.getPlayerId();
        if (roomCtrl.containsPlayer(playerId)) {
            RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(playerId);
            if (roomPlayerCtrl.hasRole()) {
                int roleId = roomPlayerCtrl.getRoleId();
                roomPlayerCtrl.setRoleId(0);
                roomCtrl.removeRole(roleId);
            }
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerQuitRoomMsg(entries.getKey(), roomId, playerId), ActorRef.noSender());
            }
            //广播发完以后再从房间删除自己,不然广播不到自己
            roomCtrl.removePlayer(playerId);
            if (roomCtrl.getRoomPlayerNum() == MagicNumbers.DEFAULT_ZERO) {
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerKillRoomMsg(roomId, playerId), sender());
            }
        } else {
            LOGGER.debug("玩家不在房间中,退出房间异常 忽略: playerId={}, roomId={}", playerId, roomId);
        }
    }

    private void onCmRoomMsg(RoomNetWorkMsg msg) {
        if (msg.getMessage() instanceof Cm_Room) {
            Cm_Room cm_room = (Cm_Room) msg.getMessage();
            RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
            b = setAction(b, cm_room);
            player = msg.getPlayer();
            if (roomCtrl.containsPlayer(player.getPlayerId())) {
                RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
                if (roomPlayerCtrl.hasRole()) {
                    LOGGER.debug("房间收到消息: RoomSimpleId={}, roleName={}, playerId={}, action={}", roomCtrl.getTarget().getSimpleRoomId(), Table_Acter_Row.getRoleNameByRoleId(roomPlayerCtrl.getRoleId(), roomCtrl.getDramaId()), player.getPlayerId(), EnumUtils.protoActionToString(cm_room.getAction()));
                } else {
                    LOGGER.debug("房间收到消息: RoomSimpleId={}, playerName={}, playerId={}, action={}", roomCtrl.getTarget().getSimpleRoomId(), player.getBase().getName(), player.getPlayerId(), EnumUtils.protoActionToString(cm_room.getAction()));
                }
            }
            try {
                switch (cm_room.getAction().getNumber()) {
                    case Action.CREAT_VALUE:
                        onCreateRoomMsg(cm_room);
                        break;
                    case Action.JION_VALUE:
                        onJoinRoomMsg(cm_room);
                        break;
                    case Action.QUIT_VALUE:
                        onQuitRoomMsg();
                        break;
                    case Action.ANSWER_VALUE:
                        onAnswer(cm_room, cm_room.getAction());
                        break;
                    case Action.READY_VALUE:
                        onReady(cm_room.getState(), cm_room.getStateTimes(), cm_room.getHandsDown());
                        break;
                    case Action.IS_DUB_VALUE:
                    case Action.SOLO_DUB_VALUE:
                        onOpenDub(cm_room.getIsDub(), cm_room.getAction(), cm_room.getSoloNum());
                        break;
                    case Action.SEARCH_VALUE:
                        onSearch(cm_room.getTypeName());
                        break;
                    case Action.SYNC_ROOM_CLUE_VALUE:
                        onSyncRoomClue();
                        break;
                    case Action.SYNC_PLAYER_CLUE_VALUE:
                        onSyncPlayerClue();
                        break;
                    case Action.SYNC_CAN_SEARCH_VALUE:
                        onCanSearch();
                        break;
                    case Action.VOTE_VALUE:
                        onVote(cm_room.getRoleId());
                        break;
                    case Action.VOTE_RESULT_VALUE:
                        onVoteResult(cm_room.getVoteNum());
                        break;
                    case Action.VOTE_LIST_VALUE:
                        onVoteList(cm_room.getVoteNum());
                        break;
                    case Action.SOLO_ANSWER_VALUE:
                        onSoloAnswer(cm_room.getOptionsList(), cm_room.getSoloNum());
                        break;
                    case Action.IS_VOTED_VALUE:
                        onIsVoted(cm_room.getVoteNum());
                        break;
                    case Action.SELECT_VALUE:
                        onSelect(cm_room.getRoleId(), cm_room.getAction());
                        break;
                    case Action.CAN_SELECT_VALUE:
                        onCanSelect();
                        break;
                    case Action.CAN_VOTE_SEARCH_VALUE:
                        onCanVoteSearch();
                        break;
                    case Action.VOTE_SEARCH_VALUE:
                        onVoteSearch(cm_room.getTypeName());
                        break;
                    case Action.SELECT_DRAFT_VALUE:
                        onSelectDraft(cm_room.getDraftId(), cm_room.getDraftNum());
                        break;
                    case Action.CAN_SELECT_DRAFT_VALUE:
                        onCanSelectDraft(cm_room.getDraftNum());
                        break;
                    case Cm_Room.Action.NO_SELECT_VALUE:
                        onNoSelect(cm_room.getAction());
                        break;
                    case Cm_Room.Action.SYNC_SOLO_IDX_VALUE:
                        onSyncSoloIdx();
                        break;
                    case Action.SYNC_VOTE_SEARCH_RESULT_VALUE:
                        onSyncVoteSearchResult();
                        break;
                    case Action.HAS_SELECT_DRAFT_VALUE:
                        onHasSelectDraft(cm_room.getDraftNum());
                        break;
                    case Action.UNLOCK_VALUE:
                        onUnlock();
                        break;
                    case Action.SELECTREAD_VALUE:
                        onSelectRead();
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

    private void onUnlock() {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.UNLOCK) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        String stateName = roomCtrl.getRoomState().toString();
        List<Integer> unlockClueIds = Table_RunDown_Row.getUnlockClueIds(roomCtrl.getDramaId(), roomCtrl.getRoomStateTimes(), stateName);
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        In_PlayerOnUnlockClueRoomMsg in_playerOnUnlockClueRoomMsg = new In_PlayerOnUnlockClueRoomMsg(roomPlayerCtrl.getPlayerId(), unlockClueIds, roomCtrl.getDramaId());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerOnUnlockClueRoomMsg, ActorRef.noSender());
    }


    private void onSyncSoloIdx() {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SOLO) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        String actorName = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", player.getPlayerId());
        DmActorSystem.get().actorSelection(actorName).tell(new In_PlayerSyncSoloIdxRoomMsg(player.getPlayerId(), roomCtrl.getSoloIdx()), ActorRef.noSender());
    }


    private void onCanSelectDraft(int draftNum) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.DRAFT) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        List<Integer> draftIds = roomCtrl.canSelectDraftIds(draftNum);
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerCanSelectDraftRoomMsg(player.getPlayerId(), draftIds, roomCtrl.getDramaId()), ActorRef.noSender());
    }

    private void onHasSelectDraft(int draftNum) {
        _checkRoomContainsPlayer(player);
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        int draftId = 0;
        if (roomPlayerCtrl.hasSelectDraft()) {
            draftId = roomCtrl.getDraftIdByDraftNum(draftNum, roomPlayerCtrl);
        }
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerHasSelectDraftRoomMsg(player.getPlayerId(), draftId), ActorRef.noSender());
    }

    private void onSelectDraft(int draftId, int draftNum) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.DRAFT) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomPlayerCtrl.hasSelectDraft()) {
            LOGGER.debug("已经选择过轮抽了 playerId={}", player.getPlayerId());
            return;
        }
        if (!roomCtrl.canSelectDraft(draftId, draftNum)) {
            String msg = String.format("手慢了,被别人选走了 playerId=%s, draftId=%s", player.getPlayerId(), draftId);
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.NO_DRAFT);
        }
        roomCtrl.selectDraft(roomPlayerCtrl, draftId, draftNum);
        for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerSelectDraftRoomMsg(entries.getKey(), draftId, roomPlayerCtrl.getRoleId()), ActorRef.noSender());
        }
    }

    private void onSyncVoteSearchResult() {
        _checkRoomContainsPlayer(player);
        //用于断线重连
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        int num = Table_Acter_Row.getAllVoteSrchTimes(roomPlayerCtrl.getRoleId(), roomCtrl.getDramaId()) - roomPlayerCtrl.getVoteSrchTimes();
        Map<Integer, List<Integer>> voteTypeIdToRoleId = roomCtrl.getTarget().getVoteNumToVoteTypeIdToRoleId().get(num);
        In_PlayerVoteSearchResultRoomMsg in_playerVoteSearchResultRoomMsg = new In_PlayerVoteSearchResultRoomMsg(player.getPlayerId(), voteTypeIdToRoleId, MagicNumbers.DEFAULT_ZERO, roomPlayerCtrl.getTarget(), roomCtrl.getDramaId());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerVoteSearchResultRoomMsg, ActorRef.noSender());
    }

    private void onVoteSearch(String typeName) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.VOTESEARCH) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomPlayerCtrl.getVoteSrchTimes() == MagicNumbers.DEFAULT_ZERO) {
            String msg = String.format("投票搜证次数已经用完了,这个请求应该被客户端挡住的! playerId=%s", player.getPlayerId());
            LOGGER.debug(msg);
            return;
        }
        if (roomCtrl.hasVoteSearch(roomPlayerCtrl.getRoleId())) {
            String msg = String.format("玩家已经投过票了,这个请求应该被客户端挡住的! playerId=%s", player.getPlayerId());
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.HAS_VOTE_SEARCH);
        }
        int voteNum = roomCtrl.getClueIds().size();
        roomCtrl.voteSearch(roomPlayerCtrl, typeName, voteNum);
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerVoteSearchRoomMsg(player.getPlayerId(), typeName), ActorRef.noSender());
        onCheckPlayerAllVoteSearchRoomMsg();
    }

    private void onCanVoteSearch() {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.VOTESEARCH) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        List<Integer> clueIds = roomCtrl.canVoteSearchTypeIds();
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerOnCanVoteSearchRoomMsg(player.getPlayerId(), clueIds, roomCtrl.getDramaId(), roomCtrl.getRoomPlayer(player.getPlayerId())), ActorRef.noSender());
    }


    private void onIsVoted(int voteNum) {
        _checkRoomContainsPlayer(player);
        int murderRoleId = Table_Murder_Row.getMurderRoleId(roomCtrl.getDramaId());
        boolean isVoted = roomCtrl.isVotedMurder(voteNum, murderRoleId);
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerIsVotedRoomMsg(player.getPlayerId(), murderRoleId, isVoted), ActorRef.noSender());
    }

    private void onVoteResult(int voteNum) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.ENDING) {
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                In_PlayerVoteResultRoomMsg in_playerVoteResultRoomMsg = new In_PlayerVoteResultRoomMsg(entries.getKey(), roomCtrl.getVoteRoleIdToPlayerRoleId(voteNum), roomCtrl.getDramaId());
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerVoteResultRoomMsg, ActorRef.noSender());
            }
        } else {
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerVoteResultRoomMsg(player.getPlayerId(), roomCtrl.getVoteRoleIdToPlayerRoleId(voteNum), roomCtrl.getDramaId()), ActorRef.noSender());
        }
    }

    private void onVoteList(int voteNum) {
        _checkRoomContainsPlayer(player);
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerVoteListRoomMsg(player.getPlayerId(), roomCtrl.getVoteRoleIdToPlayerRoleId(voteNum), roomCtrl.getDramaId()), ActorRef.noSender());
    }

    private void onSoloAnswer(List<String> optionsList, int soloNum) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SOLO) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        int soloDramaId = 0;
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        Table_Solo_Row soloRow = Table_Solo_Row.getSoloRowByRoleId(roomPlayerCtrl.getRoleId(), soloNum, roomCtrl.getDramaId());
        if (Table_Murder_Row.isMurder(roomPlayerCtrl.getRoleId(), roomCtrl.getDramaId()) && !roomCtrl.isVotedMurder(1, roomPlayerCtrl.getRoleId())) {
            //是凶手 没被投中
            soloDramaId = Integer.valueOf(soloRow.getEscapeDramaId(optionsList.get(MagicNumbers.DEFAULT_ZERO)));
        } else {
            soloDramaId = soloRow.getSoloDramaId(optionsList.get(MagicNumbers.DEFAULT_ZERO));
        }
        roomPlayerCtrl.addSoloAnswer(soloNum, soloDramaId);
        roomCtrl.setSoloIdx(soloRow.getIdx());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerSoloResultRoomMsg(player.getPlayerId(), soloDramaId), ActorRef.noSender());
    }

    private void onVote(int roleId) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.VOTE) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomPlayerCtrl.isVoteMurder()) {
            String msg = String.format("已经投过凶,这个请求应该被客户端挡住的! playerId=%s", player.getPlayerId());
            LOGGER.debug(msg);
            return;
        }
        roomCtrl.addVote(player.getPlayerId(), roleId);
        int remainNum = roomCtrl.RemainNum();
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerVoteRoomMsg(player.getPlayerId(), roleId), ActorRef.noSender());
        if (remainNum == 0) {
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                In_PlayerVoteResultRoomMsg in_playerVoteResultRoomMsg = new In_PlayerVoteResultRoomMsg(entries.getKey(), roomCtrl.getVoteRoleIdToPlayerRoleId(roomCtrl.getRoomStateTimes()), roomCtrl.getDramaId());
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerVoteResultRoomMsg, ActorRef.noSender());
            }
        } else {
            //还有人未投完凶
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                In_PlayerVoteRemainRoomMsg msg = new In_PlayerVoteRemainRoomMsg(entries.getKey(), remainNum);
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, ActorRef.noSender());
            }
        }
    }


    private void onSyncPlayerClue() {
        _checkRoomContainsPlayer(player);
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        _tellPlayerSyncClueMsg(player.getPlayerId(), roomPlayerCtrl.getClueIds(), RoomProtos.Sm_Room.Action.RESP_SYNC_PLAYER_CLUE);
    }

    private void onSyncRoomClue() {
        _checkRoomContainsPlayer(player);
        _tellPlayerSyncClueMsg(player.getPlayerId(), roomCtrl.getClueIds(), RoomProtos.Sm_Room.Action.RESP_SYNC_ROOM_CLUE);
    }


    private void checkPlayerFinishSearchShowHideClueMsg() {
        if (roomCtrl.checkPlayerFinishSearch()) {
            //所有人都已经用完了搜证次数,并且有隐藏配置,放进房间里,然后同步给玩家
            List<Integer> allHideClueIds = Table_Search_Row.getAllHideClueIds(roomCtrl.getRoomStateTimes(), roomCtrl.getDramaId());
            if (allHideClueIds.size() != MagicNumbers.DEFAULT_ZERO) {
                if (!roomCtrl.containsClueIds(allHideClueIds)) {
                    roomCtrl.addClueIds(allHideClueIds);
                    for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                        In_PlayerSyncClueRoomMsg in_playerSyncClueRoomMsg = new In_PlayerSyncClueRoomMsg(entries.getKey(), allHideClueIds, RoomProtos.Sm_Room.Action.RESP_SYNC_ROOM_CLUE, roomCtrl.getDramaId());
                        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerSyncClueRoomMsg, ActorRef.noSender());
                    }
                }
            }
        }
    }


    private void onOpenDub(int isDub, Action action, int soloNum) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() == EnumsProtos.RoomStateEnum.STAGE || roomCtrl.getRoomState() == EnumsProtos.RoomStateEnum.SOLO) {
            RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
            roomPlayerCtrl.setDub(isDub);
            RoomProtos.Sm_Room.Action respAction;
            int roomPlayerNum = roomCtrl.getRoomPlayerNum();
            if (action == Action.IS_DUB) {
                //如果是小剧场配音,必须要判断所有人都已经选择过是否配音
                respAction = RoomProtos.Sm_Room.Action.RESP_IS_DUB;
                In_PlayerOnOpenDubRoomMsg in_playerOnOpenDubRoomMsg = new In_PlayerOnOpenDubRoomMsg(player.getPlayerId(), roomPlayerCtrl.getTarget(), respAction, soloNum, roomPlayerNum, roomCtrl.getDramaId());
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerOnOpenDubRoomMsg, ActorRef.noSender());
                onCheckPlayerAllChooseDubMsg();
            } else {
                //如果是solo配音直接广播给所有人
                respAction = RoomProtos.Sm_Room.Action.RESP_SOLO_DUB;
                for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                    In_PlayerOnOpenDubRoomMsg in_playerOnOpenDubRoomMsg = new In_PlayerOnOpenDubRoomMsg(entries.getKey(), roomPlayerCtrl.getTarget(), respAction, soloNum, roomPlayerNum, roomCtrl.getDramaId());
                    DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerOnOpenDubRoomMsg, ActorRef.noSender());
                }
            }
        } else {
            LOGGER.debug("请求准备的状态和房间状态不匹配 playerId={}, RequestStateEnum={},RoomStateEnum={},RequestStateTimes={},RoomStateTimes={}", //
                    player.getPlayerId(), roomCtrl.getRoomState(), roomCtrl.getRoomState().toString(), roomCtrl.getRoomStateTimes(), roomCtrl.getRoomStateTimes());//
        }
    }


    private void onReady(EnumsProtos.RoomStateEnum state, int stateTimes, boolean handsDown) {
        _checkRoomContainsPlayer(player);
        //暂时选角阶段强制判断是否已经选定角色,可能有个别的剧本没有强制选定角色
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomCtrl.getRoomState() == state && roomCtrl.getRoomStateTimes() == stateTimes) {
            //TODO 根据状态获取配置,应该有一些限制举手准备的条件
            if (roomCtrl.getRoomState() == EnumsProtos.RoomStateEnum.SEARCH && roomPlayerCtrl.getSrchTimes() != 0) {
                //如果是搜证阶段,并且搜证次数没有用完,不能举手准备
                LOGGER.debug("搜证阶段,并且搜证次数没有用完,不能举手准备 playerId={},srchTimes={}", player.getPlayerId(), roomPlayerCtrl.getSrchTimes());
                return;
            }
            if (roomCtrl.hasChooseRole(roomPlayerCtrl.getRoleId(), player.getPlayerId())) {
                if (roomCtrl.isTimeCanReady()) {
                    roomPlayerCtrl.setReady(handsDown);
                } else {
                    roomPlayerCtrl.setReady(roomCtrl.isTimeCanReady());
                }
                int canReadyTime = roomCtrl.getCanReadyTime();
                //通知房间内所有玩家
                for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                    In_PlayerOnReadyRoomMsg in_playerOnReadyRoomMsg = new In_PlayerOnReadyRoomMsg(entries.getKey(), roomPlayerCtrl.getTarget(), roomCtrl.getDramaId(), canReadyTime);
                    DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerOnReadyRoomMsg, ActorRef.noSender());
                }
            } else {
                LOGGER.debug("玩家还没有选定角色,不能举手准备 playerId={}", player.getPlayerId());
                return;
            }
        } else {
            LOGGER.debug("请求准备的状态和房间状态不匹配 playerId={}, RequestStateEnum={},RoomStateEnum={},RequestStateTimes={},RoomStateTimes={}", //
                    player.getPlayerId(), state, roomCtrl.getRoomState().toString(), stateTimes, roomCtrl.getRoomStateTimes());//
        }

    }

    private void onCanSelect() {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SELECT) {
            LOGGER.debug("房间状态不是选角状态,非法的请求 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        List<Integer> canSelectRoleIds = roomCtrl.canSelectRoleIds(roomCtrl.getDramaId());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerCanSelectRoomMsg(player.getPlayerId(), canSelectRoleIds, roomCtrl.getDramaId()), ActorRef.noSender());
    }

    private void onSelect(int roleId, Action action) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SELECT) {
            LOGGER.debug("房间状态不是选角状态,非法的请求 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomPlayerCtrl.hasRole()) {
            String msg = String.format("玩家已经选过角色了 playerId=%s,  roleId=%s", player.getPlayerId(), roomPlayerCtrl.getRoleId());
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.HAS_CHOOSE_ROLE);
        }
        List<Integer> canSelectRoleIds = roomCtrl.canSelectRoleIds(roomCtrl.getDramaId());
        if (!canSelectRoleIds.contains(roleId)) {
            String msg = String.format("这个角色已经被选了 playerId=%s, roleId=%s", player.getPlayerId(), roomPlayerCtrl.getRoleId());
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.NO_ROLE);
        } else {
            roomPlayerCtrl.setRoleId(roleId);
            roomCtrl.chooseRole(roomPlayerCtrl.getTarget());
        }
        for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            In_PlayerChooseRoleRoomMsg in_playerChooseRoleRoomMsg = new In_PlayerChooseRoleRoomMsg(entries.getKey(), roomPlayerCtrl.getTarget(), action, roomCtrl.getDramaId());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerChooseRoleRoomMsg, ActorRef.noSender());
        }
    }

    private void onNoSelect(Action action) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.NOSELECT) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        if (roomPlayerCtrl.hasRole()) {
            String msg = String.format("玩家已经选过角色了 playerId=%s,  roleId=%s", player.getPlayerId(), roomPlayerCtrl.getRoleId());
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.HAS_CHOOSE_ROLE);
        }
        int roleId = Table_Acter_Row.getNoSelectActer(roomCtrl.getDramaId());
        roomPlayerCtrl.setRoleId(roleId);
        roomCtrl.chooseRole(roomPlayerCtrl.getTarget());
        for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
            In_PlayerChooseRoleRoomMsg in_playerChooseRoleRoomMsg = new In_PlayerChooseRoleRoomMsg(entries.getKey(), roomPlayerCtrl.getTarget(), action, roomCtrl.getDramaId());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerChooseRoleRoomMsg, ActorRef.noSender());
        }
    }

    private void onAnswer(Cm_Room cm_room, Action action) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.ANSWER) {
            LOGGER.debug("房间状态不是选角状态,非法的请求 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        EnumsProtos.SexEnum sex = player.getBase().getSex();
        List<String> optionsList = cm_room.getOptionsList();
        List<Integer> rightAnswerIdx = roomCtrl.getRightAnswerIdx(optionsList, roomCtrl.getDramaId(), sex);
        int roleIdx = roomCtrl.getRoleIdx(rightAnswerIdx);
        if (roomPlayerCtrl.hasRole()) {
            String msg = String.format("玩家已经选过角色了 playerId=%s, answer=%s, roleId=%s", player.getPlayerId(), roomPlayerCtrl.getRoleId());
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.HAS_CHOOSE_ROLE);
        }
        if (roleIdx != MagicNumbers.DEFAULT_ZERO) {
            roomPlayerCtrl.setRoleId(roleIdx);
            roomCtrl.chooseRole(roomPlayerCtrl.getTarget());
            //通知房间内所有玩家
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                In_PlayerChooseRoleRoomMsg in_playerChooseRoleRoomMsg = new In_PlayerChooseRoleRoomMsg(entries.getKey(), roomPlayerCtrl.getTarget(), action, roomCtrl.getDramaId());
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(in_playerChooseRoleRoomMsg, ActorRef.noSender());
            }
        } else if (roomCtrl.hasChooseRole(roleIdx, roomPlayerCtrl.getPlayerId())) {
            String answer = "";
            for (String s : cm_room.getOptionsList()) {
                answer = answer + s;
            }
            String msg = String.format("这个角色已经被选了 playerId=%s, answer=%s, roleId=%s", player.getPlayerId(), answer, roomPlayerCtrl.getRoleId());
            LOGGER.debug(msg);
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

    private void onSearch(String typeName) {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SEARCH) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        //TODO 读配置 判断剩余次数
        List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(roomCtrl.getDramaId());
        if (acterRowList == null) {
            String msg = String.format("剧本ID不存在....那房间咋能创建成功呢,dramaId=%s", roomCtrl.getDramaId());
            LOGGER.debug(msg);
            return;
        }
        if (roomPlayerCtrl.getSrchTimes() == MagicNumbers.DEFAULT_ZERO) {
            String msg = String.format("搜证次数已经用完了,这个请求应该被客户端挡住的! playerId=%s", player.getPlayerId());
            LOGGER.debug(msg);
            return;
        }
        // 获取线索配置 by typeId
        List<Table_Search_Row> srchRowList = Table_Search_Row.getSearchByTypeNameAndStateTimes(typeName, roomCtrl.getRoomStateTimes(), roomCtrl.getDramaId());
        int id = MagicNumbers.DEFAULT_ZERO;
        for (Table_Search_Row row : srchRowList) {
            //房间内线索和玩家的线索都没有这条线索才可以搜取
            if (!roomCtrl.containsClueId(row.getIdx()) && !roomPlayerCtrl.containsClueId(row.getIdx())) {
                roomPlayerCtrl.addClueId(row.getIdx());
                roomCtrl.addClueId(row.getIdx());
                //一次只能搜一条线索,搜到就break;
                id = row.getIdx();
                roomPlayerCtrl.reduceSrchTimes();
                break;
            }
            //如果没进上面的判断代表关于这个typeId的线索都搜索完了
        }
        //发往playerActor回消息
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerSearchRoomMsg(player.getPlayerId(), id, roomPlayerCtrl.getTarget(), roomCtrl.getDramaId()), ActorRef.noSender());
        checkPlayerFinishSearchShowHideClueMsg();
    }

    private void onCanSearch() {
        _checkRoomContainsPlayer(player);
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SEARCH) {
            LOGGER.debug("房间状态不匹配 playerId={}, RoomStateEnum={}", player.getPlayerId(), roomCtrl.getRoomState().toString());
            return;
        }
        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
        List<Table_SearchType_Row> values = RootTc.get(Table_SearchType_Row.class).values();
        List<Table_SearchType_Row> result = values.stream().filter(it -> roomCtrl.getRoomStateTimes() == it.getSrchNum() && roomCtrl.getDramaId() == it.getDramaId()).collect(Collectors.toList());
        List<Integer> typeIds = new ArrayList<>();
        for (Table_SearchType_Row row : result) {
            //可搜索列表中不能已经包含这个id
//            if (!typeIds.contains(row.getTypeId()) && !roomCtrl.isEmptyClue(row.getTypename()) && row.getRoleId() != roomPlayerCtrl.getRoleId()) {
            if (!typeIds.contains(row.getTypeId()) && !roomCtrl.isEmptyClue(row.getTypename())) {
                typeIds.add(row.getTypeId());
            }
        }
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerOnCanSearchRoomMsg(player.getPlayerId(), typeIds, roomPlayerCtrl.getTarget(), roomCtrl.getDramaId()), ActorRef.noSender());
    }

    private void onJoinRoomMsg(Cm_Room cm_room) {
        if (roomCtrl.checkRoomIsFull()) {
            LOGGER.debug("房间已满,playerId={},roomId={}", player.getPlayerId(), roomId);
            throw new BusinessLogicMismatchConditionException("房间已满 roomId=" + roomId + ",playerId=" + player.getPlayerId(), EnumsProtos.ErrorCodeEnum.ROOM_FULL);
        }
        if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.ANSWER && roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.SELECT) {
            LOGGER.debug("请求准备的状态和房间状态不匹配 playerId={}, RequestStateEnum={},RoomStateEnum={},RequestStateTimes={},RoomStateTimes={}", //
                    player.getPlayerId(), roomCtrl.getRoomState(), roomCtrl.getRoomState().toString(), roomCtrl.getRoomStateTimes(), roomCtrl.getRoomStateTimes());//
            return;
        }
        if (!roomCtrl.containsPlayer(player.getPlayerId())) {
            RoomPlayer roomPlayer = new RoomPlayer(player, roomId);
            RoomPlayerCtrl roomPlayerCtrl = GlobalInjector.getInstance(RoomPlayerCtrl.class);
            roomPlayerCtrl.setTarget(roomPlayer);
            roomCtrl.addPlayer(roomPlayer, roomPlayerCtrl);
        } else {
            LOGGER.debug("玩家已经在房间内 playerId={},roomId ={}", player.getPlayerId(), roomId);
        }
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerJoinRoomMsg(player.getPlayerId(), roomCtrl.getTarget()), ActorRef.noSender());


    }

    private void onQuitRoomMsg() {
        if (roomCtrl.containsPlayer(player.getPlayerId())) {
//            if (roomCtrl.getRoomState() != EnumsProtos.RoomStateEnum.ENDING) {
//                LOGGER.debug("游戏未到完成阶段,不能退出房间 playerId={}, RequestStateEnum={}", //
//                        player.getPlayerId(), roomCtrl.getRoomState());//
//                return;
//            }
            RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(player.getPlayerId());
            if (roomPlayerCtrl.hasRole()) {
                int roleId = roomPlayerCtrl.getRoleId();
                roomPlayerCtrl.setRoleId(0);
                roomCtrl.removeRole(roleId);
            }
            for (Map.Entry<String, RoomPlayer> entries : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerQuitRoomMsg(entries.getKey(), roomId, player.getPlayerId()), ActorRef.noSender());
            }
            //广播发完以后再从房间删除自己,不然广播不到自己
            roomCtrl.removePlayer(player.getPlayerId());
            if (roomCtrl.getRoomPlayerNum() == MagicNumbers.DEFAULT_ZERO) {
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerKillRoomMsg(roomId, player.getPlayerId()), sender());
            }
        } else {
            LOGGER.debug("玩家不在房间中,退出房间异常 忽略: playerId={}, roomId={}", player.getPlayerId(), roomId);
        }
    }


    private void onCreateRoomMsg(Cm_Room cm_room) {
        LOGGER.debug("RoomActor收到消息: onCreateRoom playerId = {}, roomId ={}, dramaName={}", masterId, roomId, cm_room.getDramaId());
        MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, RoomProtos.Sm_Room.Action.RESP_CREATE);
        br.setResult(true);
        RoomProtos.Sm_Room b = createSmRoomByActionWithoutRoomPlayer(roomCtrl.getTarget(), RoomProtos.Sm_Room.Action.RESP_CREATE);
        br.setSmRoom(b);
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerCreateRoomMsg(player.getPlayerId(), roomCtrl.getTarget()), ActorRef.noSender());
        LOGGER.debug("房间创建成功 roomId={},dramaId={},MasterId={}", roomId, cm_room.getDramaId(), masterId);
        LogHandler.roomCreateLog(roomCtrl.getTarget());
    }

    private void _checkRoomContainsPlayer(Player player) {
        if (!roomCtrl.containsPlayer(player.getPlayerId())) {
            LOGGER.debug("玩家不在房间中, playerId={}, roomId={}", player.getPlayerId(), roomId);
            throw new BusinessLogicMismatchConditionException("玩家不在房间中 playerId=" + player.getPlayerId() + ",roomId=" + roomId, EnumsProtos.ErrorCodeEnum.NOT_IN_ROOM);
        }
    }

    private void _tellPlayerSyncClueMsg(String playerId, List<Integer> clueIds, RoomProtos.Sm_Room.Action action) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerSyncClueRoomMsg(playerId, clueIds, action, roomCtrl.getDramaId()), ActorRef.noSender());
    }

    private boolean _isMaster(String playerId) {
        return playerId.equals(roomCtrl.getMasterId());
    }


    public String getRoomId() {
        return roomId;
    }

    public String getMasterId() {
        return masterId;
    }
}
