package drama.gameServer.system.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.exception.DmActorSystemInitException;
import drama.gameServer.features.actor.cluster.ClusterListener;
import drama.gameServer.system.config.AppConfig;

public class DmActorSystem {
    private static final ActorSystem actorSystem = ActorSystem.create(ActorSystemPath.DM_Common_System, AppConfig.get());

    public static void init() {
        try {
            actorSystem.actorOf(Props.create(ClusterListener.class), ActorSystemPath.DM_Common_ClusterListener);
            actorSystem.actorOf(Props.create(DMRootActor.class), ActorSystemPath.DM_Common_DMRoot);
        } catch (Exception e) {
            throw new DmActorSystemInitException("ActorSystem init() 异常！", e);
        }
    }

    public static ActorSystem get() {
        return actorSystem;
    }
}
