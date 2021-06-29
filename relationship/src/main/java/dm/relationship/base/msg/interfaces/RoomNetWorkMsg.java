package dm.relationship.base.msg.interfaces;


import com.google.protobuf.Message;
import dm.relationship.topLevelPojos.player.Player;
import ws.common.network.server.interfaces.Connection;

public interface RoomNetWorkMsg {
    Connection getConnection();

    Message getMessage();

    void setPlayer(Player player);

    Player getPlayer();
}
