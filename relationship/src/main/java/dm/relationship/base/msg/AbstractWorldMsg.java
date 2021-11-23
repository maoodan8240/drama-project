package dm.relationship.base.msg;

import com.google.protobuf.Message;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.implement.AbstractInnerMsg;


public class AbstractWorldMsg extends AbstractInnerMsg {
    private Connection connection;
    private Message message;

    public AbstractWorldMsg(Connection connection, Message message) {
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
