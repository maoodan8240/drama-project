package dm.relationship.base.msg;

import akka.actor.ActorRef;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.general.EnumUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class In_MessagePassToOtherServer extends AbstractGateWayServerInnerMsg {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_MessagePassToOtherServer.class);
    private static final long serialVersionUID = 1L;
    private ActorRef gatewaySender;                // 当前消息的gateway发送者
    private Message message;                       // 消息
    private List<Long> times = new ArrayList<>();  // 传输过程的时间点

    /**
     * gateway向LoginServer服务器发送消息所用
     *
     * @param gatewaySender
     * @param connFlag
     * @param message
     */
    public In_MessagePassToOtherServer(ActorRef gatewaySender, String connFlag, Message message) {
        super(connFlag);
        this.gatewaySender = gatewaySender;
        this.message = message;
    }

    /**
     * gateway向应用服务器[GameServer/ChatServer]服务器发送消息所用
     *
     * @param gatewaySender
     * @param connFlag
     * @param message
     */
    public In_MessagePassToOtherServer(ActorRef gatewaySender, String connFlag, Message message, String playerId) {
        super(connFlag, playerId);
        this.gatewaySender = gatewaySender;
        this.message = message;
    }

    public ActorRef getGatewaySender() {
        return gatewaySender;
    }

    public Message getMessage() {
        return message;
    }

    public List<Long> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        String msgActionName = null;
        if (LOGGER.isDebugEnabled()) {
            try {
                Method method = getMessage().getClass().getMethod("getAction");
                Enum<?> action = (Enum<?>) method.invoke(getMessage());
                msgActionName = EnumUtils.protoActionToString(action);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("msgActionName:").append(msgActionName).append("\n");
        sb.append("gatewaySender:").append(gatewaySender).append("\n");
        sb.append("connFlag:").append(getConnFlag()).append("\n");
        sb.append("playerId:").append(getPlayerId()).append("\n");
        sb.append("msgActionName:").append(msgActionName).append("\n");
        sb.append("message:\n《\n").append(TextFormat.printToUnicodeString(message)).append("\n》\n");
        return sb.toString();
    }
}
