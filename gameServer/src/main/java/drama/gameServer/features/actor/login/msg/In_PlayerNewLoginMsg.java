package drama.gameServer.features.actor.login.msg;

import dm.relationship.appServers.loginServer.player.msg.In_LoginMsg;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerNewLoginMsg implements InnerMsg {
    private In_LoginMsg msg;

    public In_PlayerNewLoginMsg(In_LoginMsg msg) {
        this.msg = msg;
    }

    public In_LoginMsg getMessage() {
        return msg;
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
