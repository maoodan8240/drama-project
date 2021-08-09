package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.gameServer.features.actor.world.msg.In_PrepareToKillPlayerActorResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class In_PrepareToKillPlayerActorResponseAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_PrepareToKillPlayerActorResponseAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PrepareToKillPlayerActorResponseMsg) {
            onPrepareToKillPlayerActorResponse((In_PrepareToKillPlayerActorResponseMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onPrepareToKillPlayerActorResponse(In_PrepareToKillPlayerActorResponseMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        LOGGER.info("玩家PlayerId={} 掉线了, 准备从内存中彻底移除...", msg.getPlayerId());
        worldCtrl.beginLogout(msg.getPlayerId(), worldCtrl, self);
    }
}
