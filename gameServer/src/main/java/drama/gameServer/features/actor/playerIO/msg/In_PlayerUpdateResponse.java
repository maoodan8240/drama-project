package drama.gameServer.features.actor.playerIO.msg;

import com.google.protobuf.Message;
import dm.relationship.base.msg.interfaces.PlayerNetWorkMsg;
import dm.relationship.topLevelPojos.player.Player;
import ws.common.network.server.interfaces.Connection;

public class In_PlayerUpdateResponse implements PlayerNetWorkMsg {
    private Player player;

    public In_PlayerUpdateResponse(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public Message getMessage() {
        return null;
    }
}
