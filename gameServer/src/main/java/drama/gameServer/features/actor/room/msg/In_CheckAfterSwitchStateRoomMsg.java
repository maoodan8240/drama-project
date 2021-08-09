package drama.gameServer.features.actor.room.msg;

import drama.protos.EnumsProtos;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_CheckAfterSwitchStateRoomMsg implements InnerMsg {
    private EnumsProtos.RoomStateEnum roomState;

    public In_CheckAfterSwitchStateRoomMsg(EnumsProtos.RoomStateEnum roomState) {
        this.roomState = roomState;
    }

    public EnumsProtos.RoomStateEnum getRoomState() {
        return roomState;
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
