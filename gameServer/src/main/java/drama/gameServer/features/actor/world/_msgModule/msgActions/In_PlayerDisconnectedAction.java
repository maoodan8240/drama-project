package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.In_PlayerDisconnectedRequest;
import drama.gameServer.features.actor.message.ConnectionContainer;
import drama.gameServer.features.actor.roomCenter.utils.RoomContainer;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;


public class In_PlayerDisconnectedAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_PlayerDisconnectedAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_PlayerDisconnectedRequest) {
            onPlayerDisconnectedRequest((In_PlayerDisconnectedRequest) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onPlayerDisconnectedRequest(In_PlayerDisconnectedRequest msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Connection connection = msg.getConnection();
        String playerId = ConnectionContainer.getPlayerIdByConnection(connection);
        //TODO 玩家名下有房间
        if (playerId != null) {
            if (RoomContainer.containsPlayerId(playerId)) {
                String roomId = RoomContainer.getPlayerIdToRoomId().get(playerId);
                worldCtrl.killRoomActor(roomId);
                RoomContainer.remove(roomId, playerId);
            }
            //TODO 玩家处于房间内处理
            worldCtrl.getPlayerActorRef(playerId).tell(new In_PlayerDisconnectedRequest(connection), ActorRef.noSender());
            worldCtrl.remove(playerId);
            ConnectionContainer.removeConnByPlayerId(playerId);
        }
        LOGGER.debug("玩家掉线,连接已断开 playerId={},connection={}", playerId != null ? playerId : "", connection.toString());
        connection.close();

    }
}
