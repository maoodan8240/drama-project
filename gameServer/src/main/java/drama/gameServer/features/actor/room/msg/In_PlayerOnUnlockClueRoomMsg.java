package drama.gameServer.features.actor.room.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnUnlockClueRoomMsg implements InnerMsg {
    private List<Integer> unlockClueIds;
    private int dramaId;

    public In_PlayerOnUnlockClueRoomMsg(List<Integer> unlockClueIds, int dramaId) {
        this.dramaId = dramaId;
        this.unlockClueIds = unlockClueIds;
    }

    public List<Integer> getUnlockClueIds() {
        return unlockClueIds;
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
}
