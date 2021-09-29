package dm.relationship.base.msg.interfaces;


import com.google.protobuf.Message;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import ws.common.network.server.interfaces.Connection;

public interface RoomNetWorkMsg {
    Connection getConnection();

    Message getMessage();

    void setSimplePlayer(SimplePlayer player);

    SimplePlayer getSimplePlayer();
}
