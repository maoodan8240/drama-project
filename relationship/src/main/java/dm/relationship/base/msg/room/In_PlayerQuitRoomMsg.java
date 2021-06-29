package dm.relationship.base.msg.room;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerQuitRoomMsg implements InnerMsg {
    private String roomId;
    private String masterId;

    public In_PlayerQuitRoomMsg(String roomId, String masterId) {
        this.roomId = roomId;
        this.masterId = masterId;
    }


    public String getMasterId() {
        return masterId;
    }

    public String getRoomId() {
        return roomId;
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
