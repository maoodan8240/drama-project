package dm.relationship.base.msg;

import com.google.protobuf.Message;
import dm.relationship.base.msg.implement._PlayerInnerMsg;
import ws.common.network.server.interfaces.Connection;

public class In_PlayerReconnectResponseMsg extends _PlayerInnerMsg {
    private Connection connection;
    private Message message;
    private String playerId;

    public In_PlayerReconnectResponseMsg(String playerId, Connection connection, Message message) {
        super(playerId);
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