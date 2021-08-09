package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnReadyRoomMsg implements InnerMsg {

    private RoomPlayer roomPlayer;
    private int dramaId;

    public In_PlayerOnReadyRoomMsg(RoomPlayer roomPlayer, int dramaId) {
        this.roomPlayer = roomPlayer;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
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
