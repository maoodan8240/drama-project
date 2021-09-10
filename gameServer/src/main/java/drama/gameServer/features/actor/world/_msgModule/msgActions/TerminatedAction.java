package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.gameServer.features.actor.login.utils.LogHandler;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminatedAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(TerminatedAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof Terminated) {
            onTerminated((Terminated) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onTerminated(Terminated terminated, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        String actorName = terminated.actor().path().elements().toList().last();
        if (actorName.contains("room")) {
            String roomId = actorName.replaceFirst(ActorSystemPath.DM_GameServer_Room, "");
            LOGGER.info("房间RoomId={}, 已经从内存中彻底移除...", roomId);
            Room room = worldCtrl.getRoomCenter().getRoom(roomId);
            LogHandler.roomQuitLog(room, System.currentTimeMillis());
            worldCtrl.removeRoomActorRef(roomId);
            worldCtrl.removeRoom(roomId);
        } else if (actorName.contains("player")) {
            String playerId = actorName.replaceFirst(ActorSystemPath.DM_GameServer_PlayerIO, "");
            LOGGER.info("玩家PlayerId={} 掉线了, 已经从内存中彻底移除...", playerId);
            worldCtrl.logout(playerId);
        }
    }
}
