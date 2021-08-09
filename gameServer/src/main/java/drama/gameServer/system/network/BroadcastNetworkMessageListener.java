package drama.gameServer.system.network;

import akka.actor.ActorRef;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_MessageReceiveHolder;
import drama.gameServer.system.actor.DmActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.network.server.interfaces.NetworkListener;

public class BroadcastNetworkMessageListener implements NetworkListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastNetworkMessageListener.class);

    @Override
    public void onReceive(MessageReceiveHolder receiveHolder) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_MessageTransfer).tell(new In_MessageReceiveHolder(receiveHolder), ActorRef.noSender());
    }

    @Override
    public void onHeartBeating(Connection connection) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_MessageTransfer).tell(new In_ConnectionStatusRequest(connection, In_ConnectionStatusRequest.Type.HeartBeating), ActorRef.noSender());
    }

    @Override
    public void onOffline(Connection connection) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_MessageTransfer).tell(new In_ConnectionStatusRequest(connection, In_ConnectionStatusRequest.Type.Offline), ActorRef.noSender());
        LOGGER.debug("=================onOffline==================");
    }

    @Override
    public void onDisconnected(Connection connection) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_MessageTransfer).tell(new In_ConnectionStatusRequest(connection, In_ConnectionStatusRequest.Type.Disconnected), ActorRef.noSender());
        LOGGER.debug("=================onDisconnected==================");
    }
}
