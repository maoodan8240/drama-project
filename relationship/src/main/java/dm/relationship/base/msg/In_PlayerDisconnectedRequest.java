package dm.relationship.base.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import ws.common.network.server.interfaces.Connection;


public class In_PlayerDisconnectedRequest extends _PlayerInnerMsg {
    private static final long serialVersionUID = 1L;

    private Connection connection;

    public In_PlayerDisconnectedRequest(Connection connection, String playerId) {
        super(playerId);
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
