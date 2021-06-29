package dm.relationship.base.msg.implement;

import com.google.protobuf.Message;
import dm.relationship.base.msg.interfaces.PlayerNetWorkMsg;
import ws.common.network.server.interfaces.Connection;

public class _Player_NetWorkMsg implements PlayerNetWorkMsg {
    private final Connection connection;
    private final Message message;


    public _Player_NetWorkMsg(Connection connection, Message message) {
        this.connection = connection;
        this.message = message;
    }


    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public Message getMessage() {
        return this.message;
    }


}
