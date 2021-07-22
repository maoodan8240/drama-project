package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.In_PlayerHeartBeatingRequest;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.protos.CodesProtos;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.PlayerProtos.Sm_HeartBeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;

import java.util.ArrayList;

public class In_PlayerHeartBeatingAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_PlayerHeartBeatingAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PlayerHeartBeatingRequest) {
            onHeartBeating((In_PlayerHeartBeatingRequest) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onHeartBeating(In_PlayerHeartBeatingRequest msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_HeartBeat, Sm_HeartBeat.Action.RESP_SYNC);
        br.setResult(true);
        Sm_HeartBeat.Builder b = Sm_HeartBeat.newBuilder();
        b.setAction(Sm_HeartBeat.Action.RESP_SYNC);
        br.setSmHeartBeat(b.build());
        msg.getConnection().send(new MessageSendHolder(br.build(), "", new ArrayList<>()));
        Connection conn = msg.getConnection();
        if (!worldCtrl.contains(conn)) {
            return;
        }
        String playerId = worldCtrl.getPlayerId(conn);
        worldCtrl.setHeartBeating(playerId);
        LOGGER.info("玩家PlayerId={} 接受到客户端发送的心跳包......", playerId);
    }


}
