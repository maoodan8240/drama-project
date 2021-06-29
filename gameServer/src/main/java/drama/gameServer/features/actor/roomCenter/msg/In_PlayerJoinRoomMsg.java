package drama.gameServer.features.actor.roomCenter.msg;

import drama.gameServer.features.actor.roomCenter.pojo.Room;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerJoinRoomMsg implements InnerMsg {
    private final Room room;

    public In_PlayerJoinRoomMsg(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
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
}
