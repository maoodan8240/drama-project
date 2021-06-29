package drama.gameServer.features.actor.roomCenter.msg;

import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerSearchRoomMsg implements InnerMsg {
    private int id;

    private RoomPlayer roomPlayer;

    public In_PlayerSearchRoomMsg(int id, RoomPlayer roomPlayer) {
        this.id = id;
        this.roomPlayer = roomPlayer;
    }

  
    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getId() {
        return id;
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
