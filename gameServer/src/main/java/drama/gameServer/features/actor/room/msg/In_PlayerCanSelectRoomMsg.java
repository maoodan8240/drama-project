package drama.gameServer.features.actor.room.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerCanSelectRoomMsg implements InnerMsg {
    private List<Integer> canSelectRoleIds;
    private int dramaId;

    public In_PlayerCanSelectRoomMsg(List<Integer> canSelectRoleIds, int dramaId) {
        this.canSelectRoleIds = canSelectRoleIds;
        this.dramaId = dramaId;
    }

    public List<Integer> getCanSelectRoleIds() {
        return canSelectRoleIds;
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
