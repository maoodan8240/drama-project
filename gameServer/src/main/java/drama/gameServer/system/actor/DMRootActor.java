package drama.gameServer.system.actor;

import akka.actor.Props;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.gameServer.features.actor.login.LoginActor1;
import drama.gameServer.features.actor.message.MessageTransferActor;
import drama.gameServer.features.actor.world.WorldActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DMRootActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DMRootActor.class);

    public DMRootActor() {
        _createChild();
    }


    /**
     * 创建子actor
     */
    private void _createChild() {
        try {
            context().watch(context().actorOf(Props.create(MessageTransferActor.class), ActorSystemPath.DM_GameServer_MessageTransfer));
            context().watch(context().actorOf(Props.create(WorldActor.class), ActorSystemPath.DM_GameServer_World));
            context().watch(context().actorOf(Props.create(LoginActor1.class), ActorSystemPath.DM_GameServer_Login));
        } catch (Exception e) {
            LOGGER.error("", e);
            System.exit(-1);
        }
    }


    @Override
    public void onRecv(Object msg) throws Exception {

    }
}
