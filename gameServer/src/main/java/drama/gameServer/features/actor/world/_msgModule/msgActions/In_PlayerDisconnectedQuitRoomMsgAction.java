package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.room.In_PlayerDisconnectedQuitRoomMsg;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class In_PlayerDisconnectedQuitRoomMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_PlayerDisconnectedQuitRoomMsgAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PlayerDisconnectedQuitRoomMsg) {
            onPlayerDisconnectedQuitRoom((In_PlayerDisconnectedQuitRoomMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onPlayerDisconnectedQuitRoom(In_PlayerDisconnectedQuitRoomMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        String roomId = msg.getRoomId();
        if (roomId != null && worldCtrl.roomActorCanUse(roomId)) {
            worldCtrl.getRoomActorRef(roomId).tell(msg, ActorRef.noSender());
        } else {
            LOGGER.debug("玩家掉线了,玩家没有房间: playerId={}<--> roomId={}", msg.getPlayerId(), msg.getRoomId());
        }
    }
}
