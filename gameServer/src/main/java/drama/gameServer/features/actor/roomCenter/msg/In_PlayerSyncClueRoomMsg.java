package drama.gameServer.features.actor.roomCenter.msg;

import drama.protos.RoomProtos;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerSyncClueRoomMsg implements InnerMsg {
    private List<Integer> clueIds;
    private RoomProtos.Sm_Room.Action action;

    public In_PlayerSyncClueRoomMsg(List<Integer> clueIds, RoomProtos.Sm_Room.Action action) {
        this.clueIds = clueIds;
        this.action = action;
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
