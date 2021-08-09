package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnCanVoteSearchRoomMsg implements InnerMsg {
    private List<Integer> clueIds;
    private int dramaId;
    private RoomPlayer roomPlayer;

    public In_PlayerOnCanVoteSearchRoomMsg(List<Integer> clueIds, int dramaId, RoomPlayer roomPlayer) {
        this.clueIds = clueIds;
        this.dramaId = dramaId;
        this.roomPlayer = roomPlayer;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getDramaId() {
        return dramaId;
    }

    public List<Integer> getClueIds() {
        return clueIds;
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
