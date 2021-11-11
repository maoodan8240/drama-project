package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteListRoomMsg extends _PlayerInnerMsg {
    private Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId;
    private int dramaId;
    private int voteNum;

    public In_PlayerVoteListRoomMsg(String playerId, Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId, int dramaId, int voteNum) {
        super(playerId, dramaId);
        this.voteRoleIdToPlayerRoleId = voteRoleIdToPlayerRoleId;
        this.dramaId = dramaId;
        this.voteNum = voteNum;
    }

    public Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId() {
        return voteRoleIdToPlayerRoleId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public int getVoteNum() {
        return voteNum;
    }
}
