package drama.gameServer.features.actor.room.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerCanSelectDraftRoomMsg implements InnerMsg {
    private List<Integer> draftIds;
    private int dramaId;

    public In_PlayerCanSelectDraftRoomMsg(List<Integer> draftIds, int dramaId) {
        this.draftIds = draftIds;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public List<Integer> getDraftIds() {
        return draftIds;
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
