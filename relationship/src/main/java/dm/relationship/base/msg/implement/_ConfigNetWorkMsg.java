package dm.relationship.base.msg.implement;

import com.google.protobuf.Message;
import dm.relationship.base.msg.interfaces.ConfigNetWorkMsg;
import dm.relationship.topLevelPojos.player.Player;
import ws.common.network.server.interfaces.Connection;

public class _ConfigNetWorkMsg implements ConfigNetWorkMsg {
    private Connection connection;
    private Message message;
    private Player player;

    public _ConfigNetWorkMsg(Connection connection, Message message) {
        this.connection = connection;
        this.message = message;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
