package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.enums.RoomState;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

/**
 * Created by lee on 2021/10/20
 */
public class In_RemoveRoomTimerMsg implements InnerMsg {
    private String roomId;
    private RoomState action;

    public In_RemoveRoomTimerMsg(String roomId, RoomState action) {
        this.roomId = roomId;
        this.action = action;
    }

    public RoomState getAction() {
        return action;
    }

    public String getRoomId() {
        return roomId;
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
