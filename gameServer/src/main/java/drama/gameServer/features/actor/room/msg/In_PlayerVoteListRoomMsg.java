package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteListRoomMsg extends _PlayerInnerMsg {
    private Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId;
    private int dramaId;

    public In_PlayerVoteListRoomMsg(String playerId, Map<Integer, List<Integer>> voteRoleIdToPlayerRoleId, int dramaId) {
        super(playerId, dramaId);
        this.voteRoleIdToPlayerRoleId = voteRoleIdToPlayerRoleId;
        this.dramaId = dramaId;
    }

    public Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId() {
        return voteRoleIdToPlayerRoleId;
    }

    public int getDramaId() {
        return dramaId;
    }
}
