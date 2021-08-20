package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

import java.util.List;

public class In_PlayerOnCanSearchRoomMsg extends _PlayerInnerMsg {
    private List<Integer> typeIds;
    private RoomPlayer roomPlayer;
    private int dramaId;

    public In_PlayerOnCanSearchRoomMsg(String playerId, List<Integer> typeIds, RoomPlayer roomPlayer, int dramaId) {
        super(playerId);
        this.typeIds = typeIds;
        this.roomPlayer = roomPlayer;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public List<Integer> getTypeIds() {
        return typeIds;
    }


}
