package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.room.RoomActor;
import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.utils.RoomContainer;
import drama.gameServer.features.actor.room.utils.RoomProtoUtils;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.protos.CodesProtos;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.RoomProtos;
import drama.protos.RoomProtos.Cm_Room;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.Map;

public class HandleRoomNetWorkMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleRoomNetWorkMsgAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof RoomNetWorkMsg) {
            onCreateRoomMsg((RoomNetWorkMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onCreateRoomMsg(RoomNetWorkMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg.getMessage() instanceof RoomProtos.Cm_Room) {
            if (!worldCtrl.contains(msg.getConnection())) {
                ProtoUtils.needReLogin(msg.getConnection());
                return;
            }
            String playerId = worldCtrl.getPlayerId(msg.getConnection());
            if (worldCtrl.containsPlayerActorRef(playerId)) {
                RoomProtos.Cm_Room cm_room = (RoomProtos.Cm_Room) msg.getMessage();
                RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
                b = RoomProtoUtils.setAction(b, cm_room);
                try {
                    switch (cm_room.getAction().getNumber()) {
                        case RoomProtos.Cm_Room.Action.CREAT_VALUE:
                            onCreateRoom(msg, playerId, worldCtrl, worldActorContext, self, sender);
                            break;
                        case Cm_Room.Action.SYNC_VALUE:
                            onSyncRoom(msg);
                            break;
                        case Cm_Room.Action.JION_VALUE:
                        case Cm_Room.Action.QUIT_VALUE:
                        case Cm_Room.Action.ANSWER_VALUE:
                        case Cm_Room.Action.READY_VALUE:
                        case Cm_Room.Action.SEARCH_VALUE:
                        case Cm_Room.Action.SYNC_ROOM_CLUE_VALUE:
                        case Cm_Room.Action.SYNC_PLAYER_CLUE_VALUE:
                        case Cm_Room.Action.SYNC_CAN_SEARCH_VALUE:
                        case Cm_Room.Action.IS_DUB_VALUE:
                        case Cm_Room.Action.VOTE_VALUE:
                        case Cm_Room.Action.SOLO_ANSWER_VALUE:
                        case Cm_Room.Action.SOLO_DUB_VALUE:
                        case Cm_Room.Action.VOTE_RESULT_VALUE:
                        case Cm_Room.Action.SELECT_VALUE:
                        case Cm_Room.Action.CAN_SELECT_VALUE:
                        case Cm_Room.Action.CAN_VOTE_SEARCH_VALUE:
                        case Cm_Room.Action.VOTE_SEARCH_VALUE:
                        case Cm_Room.Action.SELECT_DRAFT_VALUE:
                        case Cm_Room.Action.CAN_SELECT_DRAFT_VALUE:
                        case Cm_Room.Action.IS_VOTED_VALUE:
                            //以上Action都是需要传一个roomId的请求
                            onEasyMsg(msg, playerId, worldCtrl, worldActorContext, self, sender);
                            break;
                        default:
                            break;
                    }
                } catch (BusinessLogicMismatchConditionException e) {
                    Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, b.getAction(), e.getErrorCodeEnum());
                    msg.getConnection().send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
                }
            } else {
                throw new BusinessLogicMismatchConditionException("玩家不在线或Actor不可用 playerId=" + playerId);
            }
        }
    }


    private void onAnswerOrReady(RoomNetWorkMsg msg, String playerId, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg.getMessage() instanceof RoomProtos.Cm_Room) {
            Cm_Room cm_room = (Cm_Room) msg.getMessage();
            String roomId = cm_room.getRoomId();
            if (!RoomContainer.containsRoomId(roomId)) {
                LOGGER.debug("RoomContainer没有找到房间 roomId={}", roomId);
                throw new BusinessLogicMismatchConditionException("没有找到房间 roomId=" + roomId, EnumsProtos.ErrorCodeEnum.ROOM_NOT_EXISTS);
            }
            if (!worldCtrl.roomActorCanUse(roomId)) {
                LOGGER.debug("world中没有找到roomActorRef roomId={}", roomId);
                throw new BusinessLogicMismatchConditionException("world中没有找到roomActorRef roomId=" + roomId);
            }
            Player player = getPlayer(playerId, worldCtrl);
            msg.setPlayer(player);
            worldCtrl.getRoomActorRef(roomId).tell(msg, self);
        }
    }

    private void onSyncRoom(RoomNetWorkMsg msg) {
        MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, RoomProtos.Sm_Room.Action.RESP_SYNC);
        br.setResult(true);
        RoomProtos.Sm_Room.Builder broom = RoomProtos.Sm_Room.newBuilder();
        for (Map.Entry<String, Room> entries : RoomContainer.getRoomIdToRoom().entrySet()) {
            Room room = (Room) entries.getValue();
            RoomProtos.Sm_Room_Info sm_room_info = RoomProtoUtils.createSmRoomInfoWithoutRoomPlayer(room);
            broom.addRoomInfos(sm_room_info);
        }
        broom.setAction(RoomProtos.Sm_Room.Action.RESP_SYNC);
        br.setSmRoom(broom.build());
        msg.getConnection().send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
    }

    private void onEasyMsg(RoomNetWorkMsg msg, String playerId, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg.getMessage() instanceof RoomProtos.Cm_Room) {
            Cm_Room cm_room = (Cm_Room) msg.getMessage();
            String roomId = cm_room.getRoomId();
            if (!RoomContainer.containsRoomId(roomId)) {
                LOGGER.debug("RoomContainer没有找到房间 roomId={}", roomId);
                throw new BusinessLogicMismatchConditionException("没有找到房间 roomId=" + roomId, EnumsProtos.ErrorCodeEnum.ROOM_NOT_EXISTS);
            }
            if (!worldCtrl.roomActorCanUse(roomId)) {
                LOGGER.debug("world中没有找到roomActorRef roomId={}", roomId);
                throw new BusinessLogicMismatchConditionException("world中没有找到roomActorRef roomId=" + roomId);
            }
            Player player = getPlayer(playerId, worldCtrl);
            msg.setPlayer(player);
            worldCtrl.getRoomActorRef(roomId).tell(msg, self);
        }
    }


    private void onCreateRoom(RoomNetWorkMsg msg, String playerId, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        //TODO 校验参数
        Cm_Room cm_room = (Cm_Room) msg.getMessage();
        //检查player是否在线,用Dao取出player
        if (RoomContainer.containsPlayerId(playerId)) {
            String roomId = RoomContainer.getRoomIdByPlayerId(playerId);
            LOGGER.debug("玩家已经有一个房间在名下,不能再创建了,player={}<->roomId={}", playerId, roomId);
            throw new BusinessLogicMismatchConditionException("玩家名下已经有房间,无法再创建,playerId:" + playerId + ",roomId:" + roomId, EnumsProtos.ErrorCodeEnum.CREATE_ROOM_HAS_ONE);
        }
        Player player = getPlayer(playerId, worldCtrl);
        msg.setPlayer(player);
        String roomId = ObjectId.get().toString();
        RoomCtrl roomCtrl = GlobalInjector.getInstance(RoomCtrl.class);
        String roomActorName = ActorSystemPath.DM_GameServer_Room + roomId;
        ActorRef actorRef = worldActorContext.actorOf(Props.create(RoomActor.class, roomCtrl, roomId, player.getPlayerId()), roomActorName);
        worldActorContext.watch(actorRef);
        worldCtrl.addRoomActorRef(roomId, actorRef);
        actorRef.tell(msg, self);
    }

    private Player getPlayer(String playerId, WorldCtrl worldCtrl) {
        if (playerId == null || !worldCtrl.containsPlayerActorRef(playerId)) {
            LOGGER.debug("没有找到玩家或玩家不可用 connection={}, playerId={}", ((playerId != null) ? playerId : "null"));
            throw new BusinessLogicMismatchConditionException("没有找到玩家或玩家不可用");
        }
        Player player = worldCtrl.getPlayerDao().findPlayerByPlayerId(playerId);
        return player;
    }

}
