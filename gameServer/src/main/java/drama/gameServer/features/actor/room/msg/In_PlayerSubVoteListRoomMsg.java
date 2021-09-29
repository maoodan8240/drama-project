package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2021/9/28
 */
public class In_PlayerSubVoteListRoomMsg extends _PlayerInnerMsg {
    private Map<Integer, List<Integer>> subVoteRoleIdToPlayerRoleId;
    private Map<Integer, RoomPlayer> subRoleIdToRoomPlayer;

    public In_PlayerSubVoteListRoomMsg(String playerId, Map<Integer, List<Integer>> subVoteRoleIdToPlayerRoleId, Map<Integer, RoomPlayer> subRoleIdToRoomPlayer, int dramaId) {
        super(playerId, dramaId);
        this.subVoteRoleIdToPlayerRoleId = subVoteRoleIdToPlayerRoleId;
        this.subRoleIdToRoomPlayer = subRoleIdToRoomPlayer;
    }

    public Map<Integer, List<Integer>> getSubVoteRoleIdToPlayerRoleId() {
        return subVoteRoleIdToPlayerRoleId;
    }

    public Map<Integer, RoomPlayer> getSubRoleIdToRoomPlayer() {
        return subRoleIdToRoomPlayer;
    }
}
