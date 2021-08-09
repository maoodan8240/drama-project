package dm.relationship.appServers.loginServer.player.msg;

import com.google.protobuf.Message;
import dm.relationship.base.msg.AbstractWorldMsg;
import dm.relationship.topLevelPojos.player.Player;
import ws.common.network.server.interfaces.Connection;

public class In_LoginMsg extends AbstractWorldMsg {
    private Message message;
    private Connection connection;
    private Player player;

    public In_LoginMsg(Message message, Connection connection) {
        super(connection);
        this.message = message;
        this.connection = connection;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
