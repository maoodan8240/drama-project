package drama.gameServer.features.actor.message;

import akka.actor.ActorContext;
import dm.relationship.appServers.loginServer.player.msg.In_LoginMsg;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_MessageReceiveHolder;
import dm.relationship.base.msg.In_PlayerDisconnectedRequest;
import dm.relationship.base.msg.In_PlayerHeartBeatingRequest;
import dm.relationship.base.msg.In_PlayerOfflineRequest;
import dm.relationship.base.msg.implement._ConfigNetWorkMsg;
import dm.relationship.base.msg.implement._Player_NetWorkMsg;
import dm.relationship.base.msg.implement._RoomNetWorkMsg;
import dm.relationship.base.msg.interfaces.ConfigNetWorkMsg;
import dm.relationship.base.msg.interfaces.PlayerNetWorkMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.system.actor.DmActorSystem;
import drama.gameServer.system.network.In_ConnectionStatusRequest;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.CommonProtos;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.PlayerLoginProtos.Cm_Login;
import drama.protos.PlayerLoginProtos.Sm_NeedReLogin;
import drama.protos.PlayerLoginProtos.Sm_NeedReLogin.Action;
import drama.protos.RoomProtos.Cm_Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;

import java.util.ArrayList;

public class MessageTransferForReceiveActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTransferForReceiveActor.class);
    private final ActorContext messageTransferContext;

    public MessageTransferForReceiveActor(ActorContext messageTransferContext) {
        this.messageTransferContext = messageTransferContext;
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof In_MessageReceiveHolder) {
            In_MessageReceiveHolder receiveHolder = (In_MessageReceiveHolder) msg;
            on_In_MessageReceiveHolder(receiveHolder);
            receiveHolder.clear();
            receiveHolder = null;
        } else if (msg instanceof In_ConnectionStatusRequest) {
            on_In_ConnectionStatusRequest((In_ConnectionStatusRequest) msg);
        }
    }

    private void on_In_ConnectionStatusRequest(In_ConnectionStatusRequest request) {
        if (!ConnectionContainer.containsConnection(request.getConnection())) {
            LOGGER.warn("链接管理中心不存在该连接，直接关闭！receiveHolder={}", request.toString());
            request.getConnection().close();
            return;
        }
        Object msg = null;
        if (ConnectionContainer.containsConnection(request.getConnection())) {
            String connFlag = " ConnectionContainer.getFlagByConnInFlagToConn(request.getConnection())";
//            ConnectionAttachment attachment = ConnectionContainer.getAttachmentByConnInFlagToConn(request.getConnection());
//            Address address = attachment.getAddress();
            if (request.getType() == In_ConnectionStatusRequest.Type.HeartBeating) {
                msg = new In_PlayerHeartBeatingRequest(request.getConnection());
            } else if (request.getType() == In_ConnectionStatusRequest.Type.Offline) {
                msg = new In_PlayerOfflineRequest(connFlag, connFlag);
            } else if (request.getType() == In_ConnectionStatusRequest.Type.Disconnected) {
                msg = new In_PlayerDisconnectedRequest(request.getConnection());
            } else {
                return;
            }
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, messageTransferContext.self());
        } else {
            needReLogin(request.getConnection());
            LOGGER.warn("有请求发送到Gameserver！但是玩家还没有成功登录Gameserver,通知客户端重新登录！ requestType={}", request.getType());
        }
    }

    private void needReLogin(Connection connection) {
        Sm_NeedReLogin.Builder builder = Sm_NeedReLogin.newBuilder();
        builder.setAction(Action.RESP_RELOGIN);
        Response.Builder resp = ProtoUtils.create_Response(Code.Sm_NeedReLogin, Action.RESP_RELOGIN);
        resp.setResult(true);
        resp.setErrorCode(EnumsProtos.ErrorCodeEnum.UNKNOWN);
        resp.setSmNeedReLogin(builder);
        connection.send(new MessageSendHolder(resp.build(), resp.getSmMsgAction(), new ArrayList<>()));
    }

    private void on_In_MessageReceiveHolder(In_MessageReceiveHolder receiveHolder) {
        //注册
        if (receiveHolder.getMessage() instanceof Cm_Login) {
            In_LoginMsg msg = new In_LoginMsg(receiveHolder.getMessage(), receiveHolder.getConnection());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_Login).tell(msg, sender());
        } else if (receiveHolder.getMessage() instanceof Cm_Room) {
            RoomNetWorkMsg msg = new _RoomNetWorkMsg(receiveHolder.getConnection(), receiveHolder.getMessage());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, sender());
        } else if (receiveHolder.getMessage() instanceof CommonProtos.Cm_Common_Config) {
            ConfigNetWorkMsg msg = new _ConfigNetWorkMsg(receiveHolder.getConnection(), receiveHolder.getMessage());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, sender());
        } else { //转换成InnerMessage转发给worldActor
            PlayerNetWorkMsg playerNetWorkMsg = new _Player_NetWorkMsg(receiveHolder.getConnection(), receiveHolder.getMessage());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(playerNetWorkMsg, sender());
        }

    }
}
