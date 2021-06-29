package drama.gameServer.features.actor.roomCenter.msg;

import drama.gameServer.features.actor.roomCenter.pojo.Room;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnSwitchStateRoomMsg implements InnerMsg {
    private Room room;

    public In_PlayerOnSwitchStateRoomMsg(Room room) {
        this.room = room;
    }

    @Override
    public ResultCode getResultCode() {
        return null;
    }

    @Override
    public void addReceiver(String s) {

    }

    @Override
    public List<String> getReceivers() {
        return null;
    }

    public Room getRoom() {
        return room;
    }
}
