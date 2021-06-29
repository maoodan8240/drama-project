package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Kill;
import akka.actor.Props;
import dm.relationship.appServers.loginServer.player.msg.In_LoginMsg;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.daos.player.PlayerDao;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.message.ConnectionContainer;
import drama.gameServer.features.actor.playerIO.PlayerIOActor;
import drama.gameServer.features.actor.playerIO.ctrl.PlayerIOCtrl;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import drama.gameServer.features.actor.world.ctrl._WorldCtrl;
import drama.protos.PlayerLoginProtos.Cm_Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.di.GlobalInjector;

public class In_LoginAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(_WorldCtrl.class);
    private static final PlayerDao PLAYER_DAO = GlobalInjector.getInstance(PlayerDao.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_LoginMsg) {
            In_LoginMsg loginMsg = (In_LoginMsg) msg;
            Cm_Login cm_login = (Cm_Login) loginMsg.getMessage();
            switch (cm_login.getAction().getNumber()) {
                case Cm_Login.Action.LOGIN_VALUE:
                    onLoginMsg((In_LoginMsg) msg, worldCtrl, worldActorContext, self, sender);
                    break;
                case Cm_Login.Action.GUEST_LOGIN_VALUE:
                    onGuestLoginMsg((In_LoginMsg) msg, worldCtrl, worldActorContext, self, sender);
                    break;
                default:
                    break;
            }
        }
    }

    private void onGuestLoginMsg(In_LoginMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Cm_Login cm_login = (Cm_Login) msg.getMessage();
        if (ConnectionContainer.containsConnection(msg.getConnection())) {
            throw new BusinessLogicMismatchConditionException("玩家连接已经存在,playerId=" + msg.getPlayer().getPlayerId());
        }

        //以下操作是将player设置成在线状态
        Player player = msg.getPlayer();
        makePlayerOnline(cm_login, worldCtrl, worldActorContext, player, msg.getConnection());
        LOGGER.warn("游客登录请求 连接成功:playerId={} <--> connection={}", player.getPlayerId(), msg.getConnection().toString());
        //转发给PlayerActor处理回消息
        worldCtrl.getPlayerActorRef(player.getPlayerId()).tell(msg, self);

    }


    private void onLoginMsg(In_LoginMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Cm_Login cm_login = (Cm_Login) msg.getMessage();
        //TODO
        if (ConnectionContainer.containsConnection(msg.getConnection())) {
            throw new BusinessLogicMismatchConditionException("玩家连接已经存在,已处于登录状态playerId=" + msg.getPlayer().getPlayerId());
        }
        Player player = msg.getPlayer();
        makePlayerOnline(cm_login, worldCtrl, worldActorContext, player, msg.getConnection());
        LOGGER.warn("登录请求 连接成功:mobilNum={} <--> connection={}", cm_login.getMobileNum(), msg.getConnection().toString());
        //转发给PlayerActor处理具体流程
        worldCtrl.getPlayerActorRef(player.getPlayerId()).tell(msg, self);
    }

    private void makePlayerOnline(Cm_Login msg, WorldCtrl worldCtrl, ActorContext worldActorContext, Player player, Connection connection) {
        player.setConnection(connection);
        PlayerIOCtrl playerIOCtrl = GlobalInjector.getInstance(PlayerIOCtrl.class);
        playerIOCtrl.setTarget(player);
        ActorRef actorRef = null;
        if (worldCtrl.playerActorCanUse(player.getPlayerId())) {
            //连接是新链接,但是Actor早已创建 kill掉重新创建
            worldCtrl.getPlayerActorRef(player.getPlayerId()).tell(Kill.getInstance(), ActorRef.noSender());
        }
        //创建PlayerActor
        String actorName = ActorSystemPath.DM_GameServer_PlayerIO + player.getPlayerId();
        actorRef = worldActorContext.actorOf(Props.create(PlayerIOActor.class, player.getPlayerId(), playerIOCtrl), actorName);
        worldActorContext.watch(actorRef);
        //绑定关系 //TODO 处理电话号码
        worldCtrl.addPlayerIdAndActor(player.getPlayerId(), player.getMobileNum() != null ? player.getMobileNum() : "", actorRef);
        //保存连接
        ConnectionContainer.putPlayerIdToConn(player.getPlayerId(), connection);
    }
}
