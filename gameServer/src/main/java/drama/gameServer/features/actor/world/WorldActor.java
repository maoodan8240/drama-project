package drama.gameServer.features.actor.world;

import akka.actor.Props;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.gameServer.features.actor.config.ConfigActor;
import drama.gameServer.features.actor.world._msgModule.WorldActorMsgHandleEnums;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.gameServer.features.actor.world.pojo.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.di.GlobalInjector;

public class WorldActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldActor.class);
    private WorldCtrl worldCtrl;

    public WorldActor() {
        WorldCtrl worldCtrl = GlobalInjector.getInstance(WorldCtrl.class);
        worldCtrl.setTarget(new World());
        this.worldCtrl = worldCtrl;
        context().watch(context().actorOf(Props.create(ConfigActor.class), ActorSystemPath.DM_GameServer_Config));
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        WorldActorMsgHandleEnums.onRecv(msg, worldCtrl, getContext(), getSelf(), getSender());
    }


}
