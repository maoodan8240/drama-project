package dm.relationship.base.msg;

import akka.actor.Address;
import com.google.protobuf.Message;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import ws.common.utils.message.implement.AbstractInnerMsg;
import drama.protos.PlayerLoginProtos.Sm_Login;

import java.util.ArrayList;
import java.util.List;

public class In_MessagePassToGatewayServer extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private Message message; // 发送的消息
    private String connFlag; // 当前消息的发送者Connection的标识
    private Address address; // 当前消息的发送者Server在集群中的Address
    private String msgActionName;
    private List<Long> times = new ArrayList<>(); // 传输过程的时间点

    /**
     * 服务器向gateway发送消息(除了{@link Sm_Login})所用
     *
     * @param connFlag
     * @param message
     * @param msgActionName
     */
    public In_MessagePassToGatewayServer(String connFlag, Message message, String msgActionName) {
        this.connFlag = connFlag;
        this.message = message;
        this.msgActionName = msgActionName;
    }

    /**
     * 服务器向gateway发送{@link Sm_Login}所用
     *
     * @param address
     * @param connFlag
     * @param loginResponse
     * @param msgActionName
     */
    public In_MessagePassToGatewayServer(Address address, String connFlag, Message loginResponse, String msgActionName) {
        this.address = address;
        this.connFlag = connFlag;
        this.message = loginResponse;
        this.msgActionName = msgActionName;
    }

    public Message getMessage() {
        return message;
    }

    public String getConnFlag() {
        return connFlag;
    }

    public Address getAddress() {
        return address;
    }

    public String getMsgActionName() {
        return msgActionName;
    }

    public List<Long> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("[");
        builder.append("msgActionName", msgActionName);
        builder.append("connFlag", connFlag);
        builder.append("message", "\n《\n" + message + "》");
        builder.append("]");
        return builder.toString();
    }
}
