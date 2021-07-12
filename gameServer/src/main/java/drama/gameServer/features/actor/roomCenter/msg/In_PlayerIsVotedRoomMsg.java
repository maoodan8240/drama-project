package drama.gameServer.features.actor.roomCenter.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerIsVotedRoomMsg implements InnerMsg {
    private int murderRoleId;
    private boolean isVoted;

    public In_PlayerIsVotedRoomMsg(int murderRoleId, boolean isVoted) {
        this.murderRoleId = murderRoleId;
        this.isVoted = isVoted;
    }

    public int getMurderRoleId() {
        return murderRoleId;
    }

    public boolean isVoted() {
        return isVoted;
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
