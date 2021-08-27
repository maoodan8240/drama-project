package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.Room;

public class In_PlayerAllFinishChooseDubRoomMsg extends _PlayerInnerMsg {
    private Room room;

    public In_PlayerAllFinishChooseDubRoomMsg(String playerId, Room room) {
        super(playerId);
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
