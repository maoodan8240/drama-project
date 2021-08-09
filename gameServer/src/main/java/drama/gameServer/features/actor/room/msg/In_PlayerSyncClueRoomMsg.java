package drama.gameServer.features.actor.room.msg;

import drama.protos.RoomProtos;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerSyncClueRoomMsg implements InnerMsg {
    private List<Integer> clueIds;
    private RoomProtos.Sm_Room.Action action;
    private int dramaId;

    public In_PlayerSyncClueRoomMsg(List<Integer> clueIds, RoomProtos.Sm_Room.Action action, int dramaId) {
        this.clueIds = clueIds;
        this.action = action;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public RoomProtos.Sm_Room.Action getAction() {
        return action;
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
