package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.room.In_PlayerKillRoomMsg;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class In_PlayerKillRoomMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_PlayerKillRoomMsgAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PlayerKillRoomMsg) {
            onPlayerKillRoom((In_PlayerKillRoomMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onPlayerKillRoom(In_PlayerKillRoomMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        String roomId = msg.getRoomId();
        if (roomId != null && worldCtrl.roomActorCanUse(roomId)) {
            worldCtrl.removeRoomActorRef(roomId);
            LOGGER.debug("房间Acotr关系销毁成功:roomId={}", msg.getRoomId());
        }
    }
}
