package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._RoomInnerMsg;
import ws.common.network.server.interfaces.Connection;

public class In_PlayerReconnectRoomMsg extends _RoomInnerMsg {
    private Connection connection;
    private String playerId;

    public In_PlayerReconnectRoomMsg(String roomId, Connection connection, String playerId) {
        super(roomId);
        this.connection = connection;
        this.playerId = playerId;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getPlayerId() {
        return playerId;
    }

}
