package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import com.google.protobuf.Message;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.PlayerNetWorkMsg;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.playerIO.msg.In_PlayerUpdateResponse;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;

public class HandlePlayerNetWorMsgkAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlePlayerNetWorMsgkAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof PlayerNetWorkMsg) {
            PlayerNetWorkMsg playerNetWorkMsg = (PlayerNetWorkMsg) msg;
            onRecvNetWorkMsg(playerNetWorkMsg, playerNetWorkMsg.getConnection(), playerNetWorkMsg.getMessage(), worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onRecvNetWorkMsg(PlayerNetWorkMsg playerNetWorkMsg, Connection connection, Message message, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (playerNetWorkMsg instanceof In_PlayerUpdateResponse) {
            onPlayerUpdateResponse((In_PlayerUpdateResponse) playerNetWorkMsg, worldCtrl);
        } else {
            sendNetWorkMsgToPlayer(connection, playerNetWorkMsg, worldCtrl, worldActorContext, sender);
        }
    }

    private void onPlayerUpdateResponse(In_PlayerUpdateResponse msg, WorldCtrl worldCtrl) {
        if (worldCtrl.getConnection(msg.getPlayer().getPlayerId()) == null) {
            LOGGER.debug("onPlayerUpdateResponse getConnection World中没有找到对应的PlayerId={}", msg.getPlayer().getPlayerId());
            return;
        }
        if (worldCtrl.getTarget().getPlayerIdToPlayerName().containsKey(msg.getPlayer().getPlayerId())) {
            worldCtrl.getTarget().getPlayerIdToPlayerName().put(msg.getPlayer().getPlayerId(), msg.getPlayer().getBase().getName());
        } else {
            LOGGER.debug("onPlayerUpdateResponse setPlayerName World中没有找到对应的PlayerId={}", msg.getPlayer().getPlayerId());
        }
    }

    private void sendNetWorkMsgToPlayer(Connection connection, PlayerNetWorkMsg playerNetWorkMsg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef sender) {
        if (!worldCtrl.contains(connection)) {
            ProtoUtils.needReLogin(connection);
            return;
        }
        String playerId = worldCtrl.getPlayerId(connection);
        if (playerId == null) {
            LOGGER.debug("从world中得到的playerId是空的!!! 把连接给它断开!!!");
            connection.close();
            throw new BusinessLogicMismatchConditionException("从world中得到的playerId是空的!!!");
        }
        if (worldCtrl.containsPlayerActorRef(playerId)) {
            String playerActorPath = ActorSystemPath.DM_GameServer_Selection_PlayerIO.replaceAll("\\*", playerId);
            worldActorContext.actorSelection(playerActorPath).tell(playerNetWorkMsg, sender);
        } else {
            throw new BusinessLogicMismatchConditionException("玩家不在线或Actor不可用 playerId=" + playerId);
        }
    }
}
