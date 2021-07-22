package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.appServers.loginServer.player.msg.In_LoginMsg;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.protos.PlayerLoginProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;

/**
 * 主要用于检查是否需要去查库或者注册
 * world中没有可用的playerActor,需要去查库 直接发往LoginActor处理 load playerPOJO或者查库查不到,要insert player,在发往world中创建PlayerActor,并绑定连接关系
 * else
 * world中已经存在playerActor并且可用,world中的Conn也存在,判断为重复登录,不处理
 * else
 * world中已经存在playerActor并且可用,当前的Conn在world中不存在,视为断线重连Reconnect,删掉就连接,换乘新连接
 */
public class HandleLoginMsgAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandleLoginMsgAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_LoginMsg) {
            onLoginMsg((In_LoginMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onLoginMsg(In_LoginMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg.getMessage() instanceof PlayerLoginProtos.Cm_Login) {
            Connection connection = msg.getConnection();
            PlayerLoginProtos.Cm_Login cmLogin = (PlayerLoginProtos.Cm_Login) msg.getMessage();
            String rpid = cmLogin.getRpid();
            // PlayerActor不在内存中,新登录 发往LoginActor处理
            if (!worldCtrl.containsPlayerActorRef(rpid)) {
                LOGGER.debug("PlayerActor不在内存中,新登录 playerId={}", rpid);
                worldActorContext.actorSelection(ActorSystemPath.DM_GameServer_Selection_Login).tell(msg, self);
                return;
            }
            if (!worldCtrl.canUse(rpid)) {
                //playerActor存在,但不可用,有可能刚刚 登出或被清出掉线区
                LOGGER.debug("playerActor存在,但不可用,有可能刚刚 登出或被清出掉线区 执行新登录 playerId={}", rpid);
                worldActorContext.actorSelection(ActorSystemPath.DM_GameServer_Selection_Login).tell(msg, self);
                return;
            } else {
                //PlayerActor存在而且可用,重新登录
                worldCtrl.beforeReconn(rpid);
                worldCtrl.login(rpid, connection);
//                worldCtrl.getPlayerActorRef(rpid).tell(new IN);

            }
        }
    }
}
