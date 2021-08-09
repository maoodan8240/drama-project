package drama.gameServer.features.actor.room.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteResultRoomMsg implements InnerMsg {
    private Map<Integer, List<Integer>> roleIdToPlayerRoleId;
    private int dramaId;

    public In_PlayerVoteResultRoomMsg(Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId, int dramaId) {
        this.roleIdToPlayerRoleId = voteRoleIdToPlayerRoleId;
        this.dramaId = dramaId;
    }

    public Map<Integer, List<Integer>> getRoleIdToPlayerRoleId() {
        return roleIdToPlayerRoleId;
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
