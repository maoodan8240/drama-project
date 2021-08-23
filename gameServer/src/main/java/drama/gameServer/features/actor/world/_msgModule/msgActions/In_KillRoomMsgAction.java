package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Kill;
import dm.relationship.base.msg.room.In_PlayerKillRoomMsg;
import drama.gameServer.features.actor.room.msg.In_KillRoomMsg;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class In_KillRoomMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_KillRoomMsgAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PlayerKillRoomMsg) {
            onPlayerKillRoom((In_PlayerKillRoomMsg) msg, worldCtrl, worldActorContext, self, sender);
        } else if (msg instanceof In_KillRoomMsg) {
            onKillRoom((In_KillRoomMsg) msg, worldCtrl);
        }
    }

    private void onKillRoom(In_KillRoomMsg msg, WorldCtrl worldCtrl) {
        String roomId = msg.getRoomId();
        if (roomId != null && worldCtrl.roomActorCanUse(roomId)) {
            ActorRef roomActorRef = worldCtrl.getRoomActorRef(roomId);
            roomActorRef.tell(Kill.getInstance(), ActorRef.noSender());
            worldCtrl.removeRoomActorRef(roomId);
            worldCtrl.removeRoom(roomId, msg.getPlayerId());
            LOGGER.debug("GM操作,房间Acotr关系销毁成功:roomId={}", msg.getRoomId());
        } else {
            LOGGER.debug("GM操作,房间Acotr关系销毁失败,没有找到对应的Actor: roomId={}", msg.getRoomId());
        }
    }

    private void onPlayerKillRoom(In_PlayerKillRoomMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        String roomId = msg.getRoomId();
        if (roomId != null && worldCtrl.roomActorCanUse(roomId)) {
            ActorRef roomActorRef = worldCtrl.getRoomActorRef(roomId);
            roomActorRef.tell(Kill.getInstance(), ActorRef.noSender());
            worldCtrl.removeRoomActorRef(roomId);
            worldCtrl.removeRoom(roomId, msg.getPlayerId());
            LOGGER.debug("房间Acotr关系销毁成功:roomId={}", msg.getRoomId());
        } else {
            LOGGER.debug("房间Acotr关系销毁失败,没有找到对应的Actor: roomId={}", msg.getRoomId());
        }
    }
}
