package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.In_PlayerDisconnectedRequest;
import dm.relationship.base.msg.room.In_PlayerDisconnectedQuitRoomMsg;
import drama.gameServer.features.actor.message.ConnectionContainer;
import drama.gameServer.features.actor.room.utils.RoomContainer;
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
        if (playerId != null) {
            if (RoomContainer.containsPlayerId(playerId)) {
                //玩家是房主,给所在的房间Actor发消息
                String roomId = RoomContainer.getPlayerIdToRoomId().get(playerId);
                worldCtrl.getRoomActorRef(roomId).tell(new In_PlayerDisconnectedQuitRoomMsg(roomId, playerId), ActorRef.noSender());
            }
            worldCtrl.getPlayerActorRef(playerId).tell(new In_PlayerDisconnectedRequest(connection), ActorRef.noSender());
            worldCtrl.removePlayerActorRef(playerId);
            ConnectionContainer.removeConnByPlayerId(playerId);
            LOGGER.debug("玩家Actor关系已经销毁playerId={}", playerId);
        } else {
            LOGGER.debug("这个连接没有找到对应的playerId={},connection={}", playerId != null ? playerId : "", connection.toString());
        }
        LOGGER.debug("玩家掉线,连接已断开 playerId={},connection={}", playerId != null ? playerId : "", connection.toString());
        connection.close();

    }
}
