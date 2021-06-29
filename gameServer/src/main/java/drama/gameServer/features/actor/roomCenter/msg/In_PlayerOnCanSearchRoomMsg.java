package drama.gameServer.features.actor.roomCenter.msg;

import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnCanSearchRoomMsg implements InnerMsg {
    private List<Integer> typeIds;
    private RoomPlayer roomPlayer;

    public In_PlayerOnCanSearchRoomMsg(List<Integer> typeIds, RoomPlayer roomPlayer) {
        this.typeIds = typeIds;
        this.roomPlayer = roomPlayer;
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
