package drama.gameServer.features.actor.login.msg;

import com.google.protobuf.Message;
import dm.relationship.base.msg.AbstractWorldMsg;
import dm.relationship.topLevelPojos.player.Player;
import ws.common.network.server.interfaces.Connection;

public class NewLoginResponseMsg extends AbstractWorldMsg {
    private Connection connection;
    private Player player;
    private Message message;

    public NewLoginResponseMsg(Connection connection, Player player, Message message) {
        super(connection, message);
        this.connection = connection;
        this.player = player;
        this.message = message;
    }

    public Connection getConnection() {
        return connection;
    }

    public Player getPlayer() {
        return player;
    }

    public Message getMessage() {
        return message;
    }
}
