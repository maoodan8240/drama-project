package drama.gameServer.features.actor.login;

import akka.actor.ActorRef;
import dm.relationship.appServers.loginServer.player.msg.In_LoginMsg;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.player.PlayerDao;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.topLevelPojos.player.PlayerBase;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.login.msg.In_PlayerNewLoginMsg;
import drama.gameServer.features.actor.login.msg.NewLoginResponseMsg;
import drama.gameServer.system.actor.DmActorSystem;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.PlayerLoginProtos.Cm_Login;
import drama.protos.PlayerLoginProtos.Sm_Login;
import drama.protos.PlayerLoginProtos.Sm_Login.Action;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;

import static drama.protos.EnumsProtos.ErrorCodeEnum.PLAYER_NOT_EXISTS;
import static drama.protos.EnumsProtos.ErrorCodeEnum.SDK_ACCOUNT_ALREADY_EXISTS;


public class LoginActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginActor.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final PlayerDao PLAYER_DAO = DaoContainer.getDao(Player.class);

    static {
        PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    public LoginActor() {
        try {
            RegisterUtils.init();
        } catch (Exception e) {
            LOGGER.error("ExtensionIniter 加载异常！", e);
        }
    }

    @Override
    public void onRecv(Object innerMsg) throws Exception {
        if (innerMsg instanceof In_PlayerNewLoginMsg) {
            In_LoginMsg msg = ((In_PlayerNewLoginMsg) innerMsg).getMessage();
            onNewLoginMsg(msg);
        }
    }

    private void onNewLoginMsg(In_LoginMsg msg) {
        Cm_Login cmLogin = (Cm_Login) msg.getMessage();
        switch (cmLogin.getAction()) {
            case GUEST_LOGIN:
                _guestLogin(msg);
                break;
            case LOGIN:
                _login(msg);
                break;
            default:
                break;
        }
    }

    private void _guestLogin(In_LoginMsg message) {
        Cm_Login cm_login = (Cm_Login) message.getMessage();
        Player player = null;
        if (StringUtils.isEmpty(cm_login.getRpid())) {
            //参数为空 直接注册
            LOGGER.debug("参数为空 执行游客注册");
            player = new Player();
            player.setPlayerId(ObjectId.get().toString());
            guestRegister(player);
        }
        //查库
        player = PLAYER_DAO.findPlayerByPlayerId(cm_login.getRpid());
        if (player == null) {
            //库里也没有 注册
            LOGGER.debug("库里也没查到 执行游客注册");
            player = new Player();
            player.setPlayerId(ObjectId.get().toString());
            guestRegister(player);
        }
        LOGGER.debug("PlayerId={},查询到发往world执行登录", player.getPlayerId());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new NewLoginResponseMsg(message.getConnection(), player, cm_login), ActorRef.noSender());
    }

    private void guestRegister(Player player) {
        //存库
        PLAYER_DAO.insert(player);
        LOGGER.debug("游客注册GameServer玩家 playerId={} 成功!", player.getPlayerId());
    }


    private void _login(In_LoginMsg loginMsg) {
        Cm_Login cm_login = (Cm_Login) loginMsg.getMessage();
        Connection connection = loginMsg.getConnection();
        Player player = _findPlayerByMobileNum(cm_login.getMobileNum());
        if (player != null) {
            // 发给worldActor 创建PlayerActor
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new NewLoginResponseMsg(connection, player, cm_login), ActorRef.noSender());
        } else {
            //没有找到用户,无法登录
            LOGGER.warn("没有找到用户,无法登录:mobilNum={} <--> connection={}", cm_login.getMobileNum(), connection.toString());
            //回消息
            _sendLoginResponse(connection);
        }
    }

    private void _sendLoginResponse(Connection connection) {
        Response.Builder br = ProtoUtils.create_Response(Code.Sm_Login, Action.RESP_REGISTER);
        Sm_Login.Builder b = Sm_Login.newBuilder();
        b.setAction(Action.RESP_LOGIN);
        br.setErrorCode(PLAYER_NOT_EXISTS);
        br.setResult(false);
        br.setSmLogin(b.build());
        connection.send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
    }

    private void _register(Cm_Login cm_login, Connection connection) {
        String mobileNum = cm_login.getMobileNum();
        String playerName = cm_login.getPlayerName();
        boolean result = false;
        Player player = null;
        //检查用户是否存在
        //查缓存 TODO
        //查库存库
        boolean isExist = _isPlayerExist(mobileNum);
        if (isExist) {
            LOGGER.debug("注册GameServer玩家 mobileNum={},playerName={} 玩家已经存在,不需要注册!", mobileNum, playerName);
        } else {
            player = _createPlayer(mobileNum, playerName);
            PLAYER_DAO.insert(player);
            LOGGER.debug("注册GameServer玩家 mobileNum={},playerName={} playerId={} 成功!", mobileNum, playerName, player.getPlayerId());
            result = true;
        }
        _sendRegisterResponse(connection, mobileNum, playerName, result, isExist, player);
    }

    private void _sendRegisterResponse(Connection connection, String mobileNum, String playerName, boolean result, boolean isExist, Player player) {
        Response.Builder br = ProtoUtils.create_Response(Code.Sm_Login, Action.RESP_REGISTER);
        Sm_Login.Builder b = Sm_Login.newBuilder();
        b.setAction(Action.RESP_REGISTER);
        if (result) {
            b.setMobileNum(mobileNum);
            b.setPlayerName(playerName);
            b.setRpid(player.getPlayerId());
        }
        if (isExist) {
            br.setErrorCode(SDK_ACCOUNT_ALREADY_EXISTS);
        }
        br.setResult(result);
        br.setSmLogin(b);
        connection.send(new MessageSendHolder(br.build(), br.getSmMsgAction(), new ArrayList<>()));
    }

    private boolean _isPlayerExist(String mobileNum) {
        Player player = _findPlayerByMobileNum(mobileNum);
        return player != null;
    }

    private Player _findPlayerByMobileNum(String mobileNum) {
        Player player = PLAYER_DAO.findPlayerByMobileNum(mobileNum);
        return player;
    }


    private Player _createPlayer(String mobileNum, String playerName) {
        Player player = new Player();
        PlayerBase base = new PlayerBase();
        player.setPlayerId(ObjectId.get().toString());
        player.setMobileNum(mobileNum);
        base.setName(playerName);
        player.setBase(base);
        return player;
    }
}