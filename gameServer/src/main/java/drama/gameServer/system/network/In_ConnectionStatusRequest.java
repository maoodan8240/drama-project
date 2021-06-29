package drama.gameServer.system.network;

import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_ConnectionStatusRequest extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private Type type;

    public In_ConnectionStatusRequest(Connection connection, Type type) {
        this.connection = connection;
        this.type = type;
    }

    public Connection getConnection() {
        return connection;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        HeartBeating, Offline, Disconnected
    }

    @Override
    public String toString() {
        return "[connection=" + connection + ", type=" + type + "]";
    }
}
