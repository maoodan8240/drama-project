package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteSearchResultRoomMsg extends _PlayerInnerMsg {
    private Map<Integer, List<Integer>> voteTypeIdToPlayerRoleId;
    private int clueId;
    private int dramaId;
    private RoomPlayer roomPlayer;

    public In_PlayerVoteSearchResultRoomMsg(String playerId, Map<Integer, List<Integer>> voteTypeIdToPlayerRoleId, int clueId, RoomPlayer roomPlayer, int dramaId) {
        super(playerId, dramaId);
        this.voteTypeIdToPlayerRoleId = voteTypeIdToPlayerRoleId;
        this.clueId = clueId;
        this.dramaId = dramaId;
        this.roomPlayer = roomPlayer;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getDramaId() {
        return dramaId;
    }

    public Map<Integer, List<Integer>> getVoteTypeIdToPlayerRoleId() {
        return voteTypeIdToPlayerRoleId;
    }

    public int getClueId() {
        return clueId;
    }


}
