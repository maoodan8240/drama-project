package drama.gameServer.features.actor.room.msg;

import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerReconnectRoomMsg implements InnerMsg {

    private Connection connection;
    private String playerId;

    public In_PlayerReconnectRoomMsg(Connection connection, String playerId) {
        this.connection = connection;
        this.playerId = playerId;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public ResultCode getResultCode() {
        return null;
    }

    @Override
    public void addReceiver(String s) {

    }

    @Override
    public List<String> getReceivers() {
        return null;
    }
}
