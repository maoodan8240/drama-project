package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

import java.util.List;

public class In_PlayerOnCanVoteSearchRoomMsg extends _PlayerInnerMsg {
    private List<Integer> clueIds;
    private int dramaId;
    private RoomPlayer roomPlayer;

    public In_PlayerOnCanVoteSearchRoomMsg(String playerId, List<Integer> clueIds, int dramaId, RoomPlayer roomPlayer) {
        super(playerId);
        this.clueIds = clueIds;
        this.dramaId = dramaId;
        this.roomPlayer = roomPlayer;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getDramaId() {
        return dramaId;
    }

    public List<Integer> getClueIds() {
        return clueIds;
    }


}
