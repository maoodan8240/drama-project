package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._RoomInnerMsg;

public class In_GmKillRoomMsg extends _RoomInnerMsg {
    public int simpleRoomId;

    public In_GmKillRoomMsg(String roomId, int simpleRoomId) {
        super(roomId);
        this.simpleRoomId = simpleRoomId;
    }

    public int getSimpleRoomId() {
        return simpleRoomId;
    }
}
