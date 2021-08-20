package dm.relationship.base.msg.room;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerKillRoomMsg implements InnerMsg {
    private String roomId;
    private String playerId;

    public In_PlayerKillRoomMsg(String roomId, String playerId) {
        this.roomId = roomId;
        this.playerId = playerId;
    }

    public String getRoomId() {
        return roomId;
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
