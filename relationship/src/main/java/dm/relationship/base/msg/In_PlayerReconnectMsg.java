package dm.relationship.base.msg;

import com.google.protobuf.Message;
import ws.common.network.server.interfaces.Connection;

public class In_PlayerReconnectMsg {
    private Connection connection;
    private Message message;
    private String playerId;

    public In_PlayerReconnectMsg(String playerId, Connection connection, Message message) {
        this.playerId = playerId;
        this.connection = connection;
        this.message = message;
    }

    public Connection getConnection() {
        return connection;
    }

    public Message getMessage() {
        return message;
    }

    public String getPlayerId() {
        return playerId;
    }
}
