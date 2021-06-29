package drama.gameServer.features.actor.message;

import dm.relationship.base.actor.DmActor;
import dm.relationship.base.msg.In_MessagePassToGatewayServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;
import drama.protos.CodesProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.PlayerLoginProtos;

public class MessageTransferForSendActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTransferForSendActor.class);

    @Override
    public void onRecv(Object msg) throws Exception {
        // 向客户端发送消息 暂时没有启用

    }

    /**
     * 向客户端发送消息
     *
     * @param sendHolder
     */
    private void on_In_MessagePassToGatewayServer(In_MessagePassToGatewayServer sendHolder) {
        if (ConnectionContainer.containsFlagInFlagToConn(sendHolder.getConnFlag())) {
            Connection connection = ConnectionContainer.getConnByFlagInFlagToConn(sendHolder.getConnFlag());
            if (sendHolder.getMessage() instanceof MessageHandlerProtos.Response) {
                MessageHandlerProtos.Response Response = (MessageHandlerProtos.Response) sendHolder.getMessage();
                // 登录且登录成功时记录Flag对应的GameServer的Address
                if (Response.getMsgCode() == CodesProtos.ProtoCodes.Code.Sm_Login && Response.getResult()) {
                    PlayerLoginProtos.Sm_Login sm_Login = Response.getSmLogin();
                    if (sm_Login.getAction() == PlayerLoginProtos.Sm_Login.Action.RESP_LOGIN || sm_Login.getAction() == PlayerLoginProtos.Sm_Login.Action.RESP_RECONNECT) {
                        if (!StringUtils.isBlank(sm_Login.getRpid())) {
                            ConnectionContainer.put(sendHolder.getConnFlag(), sm_Login.getRpid(), sendHolder.getAddress());
                        } else {
                            LOGGER.warn("登录消息返回中Rpid为空，待检查！ sendHolder={}", sendHolder.toString());
                        }
                    }
                }
                connection.send(new MessageSendHolder(sendHolder.getMessage(), sendHolder.getMsgActionName(), sendHolder.getTimes()));
            } else {
                LOGGER.warn("向客户端发送信息时，发送的对象不为Response！ sendHolder={}", sendHolder.toString());
            }
        } else {
            LOGGER.warn("服务器向客户端发送消息时ConnFlag不存在！sendHolder={}", sendHolder.toString());
        }
    }
}