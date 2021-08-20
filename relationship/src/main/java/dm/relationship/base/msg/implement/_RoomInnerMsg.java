package dm.relationship.base.msg.implement;

import dm.relationship.base.msg.interfaces.RoomInnerMsg;

public abstract class _RoomInnerMsg implements RoomInnerMsg {
    private String roomId;

    public _RoomInnerMsg(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String getRoomId() {
        return roomId;
    }
}
