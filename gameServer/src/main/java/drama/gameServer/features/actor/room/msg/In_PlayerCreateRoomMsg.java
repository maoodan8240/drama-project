package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.Room;

public class In_PlayerCreateRoomMsg extends _PlayerInnerMsg {
    private final Room room;

    public In_PlayerCreateRoomMsg(Room room, String playerId) {
        super(playerId);
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

}
