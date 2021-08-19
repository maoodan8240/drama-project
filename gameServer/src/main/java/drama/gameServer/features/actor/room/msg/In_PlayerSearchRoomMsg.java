package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

public class In_PlayerSearchRoomMsg extends _PlayerInnerMsg {
    private int id;
    private int dramaId;
    private RoomPlayer roomPlayer;

    public In_PlayerSearchRoomMsg(int id, RoomPlayer roomPlayer, int dramaId, String playerId) {
        super(playerId);
        this.id = id;
        this.roomPlayer = roomPlayer;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getId() {
        return id;
    }

  
}
