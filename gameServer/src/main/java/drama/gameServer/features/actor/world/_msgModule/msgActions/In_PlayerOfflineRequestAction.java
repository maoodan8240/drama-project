package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.In_PlayerOfflineRequest;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;

public class In_PlayerOfflineRequestAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_PlayerOfflineRequestAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PlayerOfflineRequest) {
            onPlayerOfflineRequest((In_PlayerOfflineRequest) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onPlayerOfflineRequest(In_PlayerOfflineRequest msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Connection connection = msg.getConnection();
        if (!worldCtrl.contains(connection)) {
            return;
        }
        String playerId = worldCtrl.getPlayerId(connection);
        if (playerId == null) {
            return;
        }
        worldCtrl.setOffline(playerId);
        worldCtrl.getPlayerActorRef(playerId).tell(msg, self);
        LOGGER.info("玩家PlayerId={} 短暂离线了,进入缓存状态...", playerId);
    }
}
