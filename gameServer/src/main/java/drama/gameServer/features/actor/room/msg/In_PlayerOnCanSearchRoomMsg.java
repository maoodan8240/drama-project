package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnCanSearchRoomMsg implements InnerMsg {
    private List<Integer> typeIds;
    private RoomPlayer roomPlayer;
    private int dramaId;

    public In_PlayerOnCanSearchRoomMsg(List<Integer> typeIds, RoomPlayer roomPlayer, int dramaId) {
        this.typeIds = typeIds;
        this.roomPlayer = roomPlayer;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public List<Integer> getTypeIds() {
        return typeIds;
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
