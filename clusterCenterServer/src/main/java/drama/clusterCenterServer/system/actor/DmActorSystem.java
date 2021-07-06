package drama.clusterCenterServer.system.actor;

import akka.actor.ActorSystem;
import akka.actor.Props;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.clusterCenterServer.features.actor.cluster.ClusterListener;
import drama.clusterCenterServer.system.config.AppConfig;
public class DmActorSystem {
    private static final ActorSystem actorSystem = ActorSystem.create(ActorSystemPath.DM_Common_System, AppConfig.get());

    public static void init() {
        try {
            actorSystem.actorOf(Props.create(ClusterListener.class), ActorSystemPath.DM_Common_ClusterListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
