package dm.relationship.base.msg;

import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_PlayerReconnectMsg extends AbstractInnerMsg {
    private Connection connection;

    public In_PlayerReconnectMsg(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
