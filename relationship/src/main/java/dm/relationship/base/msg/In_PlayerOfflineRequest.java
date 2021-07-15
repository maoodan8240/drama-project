package dm.relationship.base.msg;

import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_PlayerOfflineRequest extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;

    private Connection connection;

    public In_PlayerOfflineRequest(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}