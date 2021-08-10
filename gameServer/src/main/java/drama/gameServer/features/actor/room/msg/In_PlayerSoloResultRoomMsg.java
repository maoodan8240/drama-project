package drama.gameServer.features.actor.room.msg;

import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerSoloResultRoomMsg implements InnerMsg {
    private int soloDramaId;

    public In_PlayerSoloResultRoomMsg(int soloDramaId) {
        this.soloDramaId = soloDramaId;
    }


    public int getSoloDramaId() {
        return soloDramaId;
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