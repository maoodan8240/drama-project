package dm.relationship.base.msg;

import com.google.protobuf.Message;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_PlayerReconnectMsg extends AbstractInnerMsg {
    private Connection connection;
    private Message message;

    public In_PlayerReconnectMsg(Connection connection, Message message) {
        this.connection = connection;
        this.message = message;
    }

    public Connection getConnection() {
        return connection;
    }

    public Message getMessage() {
        return message;
    }
}
