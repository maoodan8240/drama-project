package drama.gameServer.features.actor.roomCenter.msg;

import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnReadyRoomMsg implements InnerMsg {

    private RoomPlayer roomPlayer;

    public In_PlayerOnReadyRoomMsg(RoomPlayer roomPlayer) {
        this.roomPlayer = roomPlayer;
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

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }
}
