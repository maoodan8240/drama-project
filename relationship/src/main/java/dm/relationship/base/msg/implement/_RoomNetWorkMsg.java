package dm.relationship.base.msg.implement;

import com.google.protobuf.Message;
import dm.relationship.base.msg.AbstractWorldMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import ws.common.network.server.interfaces.Connection;

public class _RoomNetWorkMsg extends AbstractWorldMsg implements RoomNetWorkMsg {
    private final Connection connection;
    private final Message message;
    private SimplePlayer simplePlayer;


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
    public void setSimplePlayer(SimplePlayer simplePlayer) {
        this.simplePlayer = simplePlayer;
    }

    @Override
    public SimplePlayer getSimplePlayer() {
        return this.simplePlayer;
    }
}
