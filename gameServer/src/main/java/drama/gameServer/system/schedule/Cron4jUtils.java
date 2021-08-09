package drama.gameServer.system.schedule;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.gameServer.features.actor.world.msg.In_NoticeToKillOverTimeCachePlayerActorMsg;
import drama.gameServer.system.actor.DmActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cron4jUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(Cron4jUtils.class);

    public static void killOverTimeCachePlayerActor(String[] args) {
        int overTime = Integer.valueOf(args[0]);
        ActorSelection actorSelection = DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World);
        actorSelection.tell(new In_NoticeToKillOverTimeCachePlayerActorMsg(overTime), ActorRef.noSender());
    }
}
