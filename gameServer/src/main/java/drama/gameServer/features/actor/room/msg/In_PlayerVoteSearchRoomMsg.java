package drama.gameServer.features.actor.room.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerVoteSearchRoomMsg implements InnerMsg {
    private String typeName;

    public In_PlayerVoteSearchRoomMsg(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
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
