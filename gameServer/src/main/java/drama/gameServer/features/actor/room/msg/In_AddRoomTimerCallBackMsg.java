package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._RoomInnerMsg;
import drama.gameServer.features.actor.room.enums.RoomState;

/**
 * Created by lee on 2021/10/19
 */
public class In_AddRoomTimerCallBackMsg extends _RoomInnerMsg {
    private RoomState action;


    public In_AddRoomTimerCallBackMsg(String roomId, RoomState action) {
        super(roomId);
        this.action = action;
    }

    public RoomState getAction() {
        return action;
    }

    @Override
    public String getRoomId() {
        return super.getRoomId();
    }
}
