package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.ConfigNetWorkMsg;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.protos.CommonProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleConfigMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleConfigMsgAction.class);


    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof ConfigNetWorkMsg) {
            onConfigNetWorkMsg((ConfigNetWorkMsg) msg, worldCtrl, worldActorContext, self, sender);
        }

    }

    private void onConfigNetWorkMsg(ConfigNetWorkMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg.getMessage() instanceof CommonProtos.Cm_Common_Config) {
            if (!worldCtrl.contains(msg.getConnection())) {
                ProtoUtils.needReLogin(msg.getConnection());
                return;
            }
            String playerId = worldCtrl.getPlayerId(msg.getConnection());
            if (worldCtrl.containsPlayerActorRef(playerId)) {
                worldActorContext.actorSelection(ActorSystemPath.DM_GameServer_Config).tell(msg, ActorRef.noSender());
            } else {
                throw new BusinessLogicMismatchConditionException("玩家不在线或Actor不可用 playerId=" + playerId);
            }
        }
    }


}
