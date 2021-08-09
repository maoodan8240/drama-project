package dm.relationship.base.msg.implement;

import com.google.protobuf.Message;
import dm.relationship.base.msg.AbstractWorldMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.topLevelPojos.player.Player;
import ws.common.network.server.interfaces.Connection;

public class _RoomNetWorkMsg extends AbstractWorldMsg implements RoomNetWorkMsg {
    private final Connection connection;
    private final Message message;
    private Player player;


    public _RoomNetWorkMsg(Connection connection, Message message) {
        super(connection);
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

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
