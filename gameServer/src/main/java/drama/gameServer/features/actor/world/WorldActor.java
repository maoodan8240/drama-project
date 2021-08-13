package drama.gameServer.features.actor.world;

import akka.actor.Props;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.AbstractWorldMsg;
import drama.gameServer.features.actor.config.ConfigActor;
import drama.gameServer.features.actor.world._msgModule.WorldActorMsgHandleEnums;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.gameServer.features.actor.world.pojo.World;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;
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
        if (msg instanceof AbstractWorldMsg) {
            AbstractWorldMsg worldMsg = (AbstractWorldMsg) msg;
            Connection connection = worldMsg.getConnection();
            String playerId = worldCtrl.getPlayerId(connection);
            if (playerId != null) {
                String name = !StringUtils.isEmpty(worldCtrl.getTarget().getPlayerIdToPlayerName().get(playerId)) ? worldCtrl.getTarget().getPlayerIdToPlayerName().get(playerId) : "";
                LOGGER.debug("接收到playerName={},playerId={}的消息", name, playerId);
            } else {
                LOGGER.debug("新登录用户,还没有对应的playerId");
            }
        }
        WorldActorMsgHandleEnums.onRecv(msg, worldCtrl, getContext(), getSelf(), getSender());
    }


}
