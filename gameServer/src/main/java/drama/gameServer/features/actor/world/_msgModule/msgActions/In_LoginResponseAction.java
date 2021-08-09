package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_PlayerReconnectMsg;
import dm.relationship.daos.player.PlayerDao;
import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.login.msg.NewLoginResponseMsg;
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

public class In_LoginResponseAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(_WorldCtrl.class);
    private static final PlayerDao PLAYER_DAO = GlobalInjector.getInstance(PlayerDao.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof NewLoginResponseMsg) {
            onGuestLoginMsg((NewLoginResponseMsg) msg, worldCtrl, worldActorContext, self, sender);
        } else if (msg instanceof In_PlayerReconnectMsg) {
            onReconnecet((In_PlayerReconnectMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onReconnecet(In_PlayerReconnectMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        String playerId = worldCtrl.getPlayerId(msg.getConnection());
        LOGGER.debug("=======断线重连中====== playerId={}", playerId);
        Cm_Login cm_login = (Cm_Login) msg.getMessage();
        worldCtrl.beforeReconn(cm_login.getRpid());
        worldCtrl.login(cm_login.getRpid(), msg.getConnection());
        worldCtrl.getPlayerActorRef(cm_login.getRpid()).tell(msg, self);
    }

    private void onGuestLoginMsg(NewLoginResponseMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Cm_Login cm_login = (Cm_Login) msg.getMessage();
        //以下操作是将player设置成在线状态
        Player player = msg.getPlayer();
        makePlayerNewOnline(cm_login, worldCtrl, worldActorContext, player, msg.getConnection());
        LOGGER.debug("游客登录请求 连接成功:playerId={} <--> connection={}", player.getPlayerId(), msg.getConnection().toString());
        //转发给PlayerActor处理回消息
        worldCtrl.getPlayerActorRef(player.getPlayerId()).tell(msg, self);

    }


    private void onLoginMsg(NewLoginResponseMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        Cm_Login cm_login = (Cm_Login) msg.getMessage();
        Player player = msg.getPlayer();
        makePlayerNewOnline(cm_login, worldCtrl, worldActorContext, player, msg.getConnection());
        LOGGER.warn("登录请求 连接成功:mobilNum={} <--> connection={}", cm_login.getMobileNum(), msg.getConnection().toString());
        //转发给PlayerActor处理具体流程
        worldCtrl.getPlayerActorRef(player.getPlayerId()).tell(msg, self);
    }

    private void makePlayerNewOnline(Cm_Login msg, WorldCtrl worldCtrl, ActorContext worldActorContext, Player player, Connection connection) {
        worldCtrl.logout(msg.getRpid());
        player.setConnection(connection);
        PlayerIOCtrl playerIOCtrl = GlobalInjector.getInstance(PlayerIOCtrl.class);
        playerIOCtrl.setTarget(player);
        //创建PlayerActor
        String actorName = ActorSystemPath.DM_GameServer_PlayerIO + player.getPlayerId();
        ActorRef actorRef = worldActorContext.actorOf(Props.create(PlayerIOActor.class, player.getPlayerId(), playerIOCtrl), actorName);
        worldActorContext.watch(actorRef);
        //绑定关系 //TODO 处理电话号码
        worldCtrl.addPlayerIdAndActor(player.getPlayerId(), player.getMobileNum() != null ? player.getMobileNum() : "", actorRef);
        //保存连接
        worldCtrl.login(player.getPlayerId(), connection);
    }
}
