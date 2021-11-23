package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.RoomInnerMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import dm.relationship.utils.ActorMsgSynchronizedExecutor;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.playerIO.msg.In_GetPlayerTargetWorldMsg;
import drama.gameServer.features.actor.room.RoomActor;
import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.utils.RoomProtoUtils;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.protos.CodesProtos;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.room.RoomProtos.Cm_Room;
import drama.protos.room.RoomProtos.Sm_Room;
import drama.protos.room.RoomProtos.Sm_Room_Info;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.Map;

public class HandleRoomMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleRoomMsgAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof RoomNetWorkMsg) {
            onNetWorkRoomMsg((RoomNetWorkMsg) msg, worldCtrl, worldActorContext, self, sender);
        } else if (msg instanceof RoomInnerMsg) {
            onRoomInnerMsg((RoomInnerMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onRoomInnerMsg(RoomInnerMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        String roomId = msg.getRoomId();
        if (!worldCtrl.roomActorCanUse(roomId)) {
            String message = String.format("房间不存在了,待处理 roomId=%s", roomId);
            throw new BusinessLogicMismatchConditionException(message);
        }
        ActorRef roomActorRef = worldCtrl.getRoomActorRef(roomId);
        roomActorRef.tell(msg, sender);
    }

    private void onNetWorkRoomMsg(RoomNetWorkMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (!worldCtrl.contains(msg.getConnection())) {
            ProtoUtils.needReLogin(msg.getConnection());
            return;
        }
        if (msg.getMessage() instanceof Cm_Room) {
            String playerId = worldCtrl.getPlayerId(msg.getConnection());
            if (worldCtrl.containsPlayerActorRef(playerId)) {
                SimplePlayer simplePlayer = getSimplePlayer(playerId, worldCtrl, worldActorContext, self);
                Cm_Room cm_room = (Cm_Room) msg.getMessage();
                Sm_Room.Builder b = Sm_Room.newBuilder();
                b = RoomProtoUtils.setAction(b, cm_room);
                try {
                    if (cm_room.getAction().getNumber() == Cm_Room.Action.CREAT_VALUE) {
                        onCreateRoom(msg, simplePlayer, worldCtrl, worldActorContext, self);
                    } else if (cm_room.getAction().getNumber() == Cm_Room.Action.SYNC_VALUE) {
                        onSyncRoomList(msg, worldCtrl);
                    } else if (cm_room.getAction().getNumber() == Cm_Room.Action.JION_VALUE) {
                        onJoinRoom(msg, simplePlayer, worldCtrl, self);
                    } else {
                        //以上Action都是需要传一个roomId的请求
                        onOtherRoomMsg(msg, simplePlayer, worldCtrl, self);
                    }
                } catch (BusinessLogicMismatchConditionException e) {
                    LOGGER.debug(e.getMessage());
                    Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, b.getAction(), e.getErrorCodeEnum());
                    msg.getConnection().send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
                }
            } else {
                throw new BusinessLogicMismatchConditionException("玩家不在线或Actor不可用 playerId=" + playerId);
            }
        } else {
            String playerId = worldCtrl.getPlayerId(msg.getConnection());
            if (worldCtrl.containsPlayerActorRef(playerId)) {
                SimplePlayer simplePlayer = getSimplePlayer(playerId, worldCtrl, worldActorContext, self);
                onOtherRoomMsg(msg, simplePlayer, worldCtrl, self);
            } else {
                throw new BusinessLogicMismatchConditionException("玩家不在线或Actor不可用 playerId=" + playerId);
            }
        }
    }


    private void onJoinRoom(RoomNetWorkMsg msg, SimplePlayer simplePlayer, WorldCtrl worldCtrl, ActorRef self) {
        if (msg.getMessage() instanceof Cm_Room) {
            Cm_Room cm_room = (Cm_Room) msg.getMessage();
            String roomId = cm_room.getRoomId();
            if (StringUtils.isEmpty(roomId) && !worldCtrl.containsRoom(roomId)) {
                LOGGER.debug("RoomContainer没有找到房间  playerId={},roomId={}", simplePlayer.getPlayerId(), roomId);
                throw new BusinessLogicMismatchConditionException("没有找到房间 roomId=" + roomId, EnumsProtos.ErrorCodeEnum.ROOM_NOT_EXISTS);
            }
            if (!worldCtrl.roomActorCanUse(roomId)) {
                LOGGER.debug("world中没有找到roomActorRef roomId={}", roomId);
                throw new BusinessLogicMismatchConditionException("world中没有找到roomActorRef roomId=" + roomId);
            }
            msg.setSimplePlayer(simplePlayer);
            worldCtrl.getRoomActorRef(roomId).tell(msg, self);
        }
    }

    private void onSyncRoomList(RoomNetWorkMsg msg, WorldCtrl worldCtrl) {
        MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, Sm_Room.Action.RESP_SYNC);
        br.setResult(true);
        Sm_Room.Builder broom = Sm_Room.newBuilder();
        for (Map.Entry<String, Room> entries : worldCtrl.getRoomCenter().getRoomIdToRoom().entrySet()) {
            Room room = (Room) entries.getValue();
            Sm_Room_Info sm_room_info = RoomProtoUtils.createSmRoomInfoWithoutRoomPlayer(room);
            broom.addRoomInfos(sm_room_info);
        }
        broom.setAction(Sm_Room.Action.RESP_SYNC);
        br.setSmRoom(broom.build());
        msg.getConnection().send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
    }

    private void onOtherRoomMsg(RoomNetWorkMsg msg, SimplePlayer simplePlayer, WorldCtrl worldCtrl, ActorRef self) {
        String roomId = simplePlayer.getRoomId();
        if (StringUtils.isEmpty(roomId) && !worldCtrl.containsRoom(roomId)) {
            LOGGER.debug("RoomContainer没有找到房间 或者玩家不在房间内 playerId={},roomId={}", simplePlayer.getPlayerId(), roomId);
            throw new BusinessLogicMismatchConditionException("没有找到房间 roomId=" + roomId, EnumsProtos.ErrorCodeEnum.ROOM_NOT_EXISTS);
        }
        if (!worldCtrl.roomActorCanUse(roomId)) {
            LOGGER.debug("world中没有找到roomActorRef roomId={}", roomId);
            throw new BusinessLogicMismatchConditionException("world中没有找到roomActorRef roomId=" + roomId);
        }
        msg.setSimplePlayer(simplePlayer);
        worldCtrl.getRoomActorRef(roomId).tell(msg, self);
    }


    private void onCreateRoom(RoomNetWorkMsg msg, SimplePlayer simplePlayer, WorldCtrl worldCtrl, ActorContext
            worldActorContext, ActorRef self) {
        Cm_Room cm_room = (Cm_Room) msg.getMessage();
        if (worldCtrl.containsPlayerRoom(simplePlayer.getPlayerId())) {
            String roomId = worldCtrl.getRoomId(simplePlayer.getPlayerId());
            LOGGER.debug("玩家已经有一个房间在名下,不能再创建了,直接进入player={}<->simpleRoomId={}<->roomId={}<->roomSimplePlayerId={}", simplePlayer.getPlayerId(), worldCtrl.getRoomIdToRoom().get(roomId).getSimpleRoomId(), roomId, worldCtrl.getRoomIdToRoom().get(roomId).getSimpleRoomId());
            throw new BusinessLogicMismatchConditionException("玩家名下已经有房间,无法再创建,playerId:" + simplePlayer.getPlayerId() + ",roomId:" + roomId, EnumsProtos.ErrorCodeEnum.CREATE_ROOM_HAS_ONE);
        }
        msg.setSimplePlayer(simplePlayer);
        String roomId = ObjectId.get().toString();
        RoomCtrl roomCtrl = GlobalInjector.getInstance(RoomCtrl.class);
        roomCtrl.createRoom(roomId, simplePlayer, cm_room.getDramaId(), msg.getConnection());
        worldCtrl.addRoom(roomCtrl.getTarget(), simplePlayer.getPlayerId());
        String roomActorName = ActorSystemPath.DM_GameServer_Room + roomId;
        ActorRef actorRef = worldActorContext.actorOf(Props.create(RoomActor.class, roomCtrl, roomId, simplePlayer.getPlayerId()), roomActorName);
        worldActorContext.watch(actorRef);
        worldCtrl.addRoomActorRef(roomId, actorRef);
        actorRef.tell(msg, self);
    }

    private SimplePlayer getSimplePlayer(String playerId, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self) {
        SimplePlayer simplePlayer;
        if (worldCtrl.canUse(playerId)) {
            In_GetPlayerTargetWorldMsg.Response response = ActorMsgSynchronizedExecutor.sendMsgToServer(worldCtrl.getPlayerActorRef(playerId), new In_GetPlayerTargetWorldMsg.Request(playerId));
            simplePlayer = response.getSimplePlayer();
        } else {
            if (playerId == null || !worldCtrl.containsPlayerActorRef(playerId)) {
                LOGGER.debug("没有找到玩家或玩家不可用 connection={}, playerId={}", ((playerId != null) ? playerId : "null"));
                throw new BusinessLogicMismatchConditionException("没有找到玩家或玩家不可用");
            }
            LOGGER.debug("RoomHandle 从DB库中获取了player, playerId={}", playerId);
            Player player = worldCtrl.getPlayerDao().findPlayerByPlayerId(playerId);
            simplePlayer = new SimplePlayer(player.getPlayerId(), //
                    player.getBase().getSimpleId(), player.getBase().getName(),//
                    player.getBase().getSex(), player.getBase().getIcon(), player.getOther().getLsinTime(),//
                    player.getOther().getLsoutTime(), player.getRoomId());
        }
        return simplePlayer;
    }
}
