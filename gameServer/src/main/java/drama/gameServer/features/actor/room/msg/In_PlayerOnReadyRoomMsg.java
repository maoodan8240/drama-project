package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

public class In_PlayerOnReadyRoomMsg extends _PlayerInnerMsg {

    private RoomPlayer roomPlayer;
    private int dramaId;

    public In_PlayerOnReadyRoomMsg(String playerId, RoomPlayer roomPlayer, int dramaId) {
        super(playerId);
        this.roomPlayer = roomPlayer;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }


    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }
}
