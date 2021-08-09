package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.gameServer.features.actor.world.msg.In_NoticeToKillOverTimeCachePlayerActorMsg;
import drama.gameServer.features.actor.world.msg.In_PrepareToKillPlayerActorRequestMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class In_NoticeToKillOverTimeCachePlayerActorAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_NoticeToKillOverTimeCachePlayerActorAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_NoticeToKillOverTimeCachePlayerActorMsg) {
            onNoticeToKillOverTimeCachePlayerActorMsg((In_NoticeToKillOverTimeCachePlayerActorMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onNoticeToKillOverTimeCachePlayerActorMsg(In_NoticeToKillOverTimeCachePlayerActorMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        List<String> playerIds = worldCtrl.getAllOverTimeCachePalyerOnlineActor(msg.getOverTime());
        for (String playerId : playerIds) {
            LOGGER.info("玩家PlayerId={} 即将被移除,现在通知其保存玩家数据到数据库中！", playerId);
            worldCtrl.setCanNotUse(playerId);
            worldCtrl.getPlayerActorRef(playerId).tell(new In_PrepareToKillPlayerActorRequestMsg(playerId), self);
        }
    }
}
