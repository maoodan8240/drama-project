package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.interfaces.GmRoomMsg;

public class In_GmKillRoomMsg implements GmRoomMsg {
    public int simpleRoomId;

    public In_GmKillRoomMsg(int simpleRoomId) {
        this.simpleRoomId = simpleRoomId;
    }

    public int getSimpleRoomId() {
        return simpleRoomId;
    }
}
