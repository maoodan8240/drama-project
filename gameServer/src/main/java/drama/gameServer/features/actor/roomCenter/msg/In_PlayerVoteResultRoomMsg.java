package drama.gameServer.features.actor.roomCenter.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteResultRoomMsg implements InnerMsg {
    private Map<Integer, List<Integer>> roleIdToPlayerRoleId;

    public In_PlayerVoteResultRoomMsg(Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId) {
        this.roleIdToPlayerRoleId = voteRoleIdToPlayerRoleId;
    }

    public Map<Integer, List<Integer>> getRoleIdToPlayerRoleId() {
        return roleIdToPlayerRoleId;
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
