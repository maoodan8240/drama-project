package drama.gameServer.features.actor.roomCenter.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerVoteRoomMsg implements InnerMsg {
    private int voteNum;

    public In_PlayerVoteRoomMsg(int voteNum) {
        this.voteNum = voteNum;
    }

    public int getVoteNum() {
        return voteNum;
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
