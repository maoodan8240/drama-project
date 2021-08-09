package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerSearchRoomMsg implements InnerMsg {
    private int id;
    private int dramaId;
    private RoomPlayer roomPlayer;

    public In_PlayerSearchRoomMsg(int id, RoomPlayer roomPlayer, int dramaId) {
        this.id = id;
        this.roomPlayer = roomPlayer;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
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
