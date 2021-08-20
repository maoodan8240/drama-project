package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.Room;

public class In_PlayerOnSwitchStateRoomMsg extends _PlayerInnerMsg {
    private Room room;

    public In_PlayerOnSwitchStateRoomMsg(String playerId, Room room) {
        super(playerId);
        this.room = room;
    }


    public Room getRoom() {
        return room;
    }
}
