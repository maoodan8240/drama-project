package dm.relationship.base.msg;

import com.google.protobuf.Message;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.general.EnumUtils;
import ws.common.utils.message.implement.AbstractInnerMsg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class In_MessageReceiveHolder extends AbstractInnerMsg {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_MessageReceiveHolder.class);
    private static final long serialVersionUID = 1L;

    private MessageReceiveHolder receiveHolder;
    private List<Long> times = new ArrayList<>(); // 传输过程的时间点

    public In_MessageReceiveHolder(MessageReceiveHolder receiveHolder) {
        this.receiveHolder = receiveHolder;
        this.times.add(receiveHolder.getTimeRev());
        this.times.add(receiveHolder.getTimeEnd());
    }

    public Connection getConnection() {
        return receiveHolder.getConnection();
    }

    public Message getMessage() {
        return receiveHolder.getMessage();
    }

    public List<Long> getTimes() {
        return times;
    }

    public List<Long> joinTime(long time) {
        times.add(time);
        return new ArrayList<>(times);
    }

    public void clear() {
        receiveHolder.clear();
        receiveHolder = null;
        times.clear();
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
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        builder.append("[");
        builder.append("msgActionName", msgActionName);
        builder.append("connectionHashCode", getConnection().hashCode());
        builder.append("message", "\n《\n" + getMessage() + "》");
        builder.append("]");
        return builder.toString();
    }
}
