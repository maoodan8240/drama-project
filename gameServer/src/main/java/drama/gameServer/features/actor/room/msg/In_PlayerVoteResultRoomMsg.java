package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteResultRoomMsg extends _PlayerInnerMsg {
    private Map<Integer, List<Integer>> roleIdToPlayerRoleId;
    private int dramaId;
    private int voteNum;

    public In_PlayerVoteResultRoomMsg(String playerId, Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId, int dramaId, int voteNum) {
        super(playerId, dramaId);
        this.roleIdToPlayerRoleId = voteRoleIdToPlayerRoleId;
        this.dramaId = dramaId;
        this.voteNum = voteNum;
    }

    public Map<Integer, List<Integer>> getRoleIdToPlayerRoleId() {
        return roleIdToPlayerRoleId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public int getVoteNum() {
        return voteNum;
    }
}
