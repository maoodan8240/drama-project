package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.In_PlayerDisconnectedRequest;
import dm.relationship.base.msg.In_PlayerOfflineRequest;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.gameServer.system.network.In_ConnectionStatusRequest;
import drama.protos.CodesProtos;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.PlayerProtos.Sm_HeartBeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;

import java.util.ArrayList;

public class HandleConnectionStatusAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleConnectionStatusAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_ConnectionStatusRequest) {
            onConnectionStatusRequest((In_ConnectionStatusRequest) msg, worldCtrl);
        }
    }

    private void onConnectionStatusRequest(In_ConnectionStatusRequest request, WorldCtrl worldCtrl) {
        if (request.getType() == In_ConnectionStatusRequest.Type.HeartBeating) {
            onPlayerHeartBeating(request.getConnection(), worldCtrl);
        } else if (request.getType() == In_ConnectionStatusRequest.Type.Offline) {
            onPlayerOffline(request.getConnection(), worldCtrl);
        } else if (request.getType() == In_ConnectionStatusRequest.Type.Disconnected) {
            onPlayerDisconnected(request.getConnection(), worldCtrl);
        }

    }

    private void onPlayerHeartBeating(Connection connection, WorldCtrl worldCtrl) {
        if (!worldCtrl.contains(connection)) {
            ProtoUtils.needReLogin(connection);
            return;
        }
        Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_HeartBeat, Sm_HeartBeat.Action.RESP_SYNC);
        br.setResult(true);
        Sm_HeartBeat.Builder b = Sm_HeartBeat.newBuilder();
        b.setAction(Sm_HeartBeat.Action.RESP_SYNC);
        br.setSmHeartBeat(b.build());
        connection.send(new MessageSendHolder(br.build(), "", new ArrayList<>()));
        String playerId = worldCtrl.getPlayerId(connection);
        worldCtrl.setHeartBeating(playerId);
//        LOGGER.info("玩家playerName={}, PlayerId={} 接受到客户端发送的心跳包......", worldCtrl.getPlayerNameById(playerId), playerId);
    }

    private void onPlayerOffline(Connection connection, WorldCtrl worldCtrl) {
        String playerId = worldCtrl.getPlayerId(connection);
        if (playerId == null) {
            return;
        }
        worldCtrl.setOffline(playerId);
        worldCtrl.getPlayerActorRef(playerId).tell(new In_PlayerOfflineRequest(connection), ActorRef.noSender());
        LOGGER.info("玩家playerName={},PlayerId={} 短暂离线了,进入缓存状态...", worldCtrl.getPlayerNameById(playerId), playerId);
    }

    private void onPlayerDisconnected(Connection connection, WorldCtrl worldCtrl) {
        String playerId = worldCtrl.getPlayerId(connection);
        if (playerId == null) {
            return;
        }
        worldCtrl.setOffline(playerId);

        worldCtrl.getPlayerActorRef(playerId).tell(new In_PlayerDisconnectedRequest(connection, playerId), ActorRef.noSender());
        LOGGER.info("玩家playerName={}, PlayerId={} 短暂离线了,进入缓存状态...", worldCtrl.getPlayerNameById(playerId), playerId);
    }
}
