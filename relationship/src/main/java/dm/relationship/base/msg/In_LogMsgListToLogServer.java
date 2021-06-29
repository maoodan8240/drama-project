package dm.relationship.base.msg;

import dm.relationship.logServer.base.DmLog;
import ws.common.utils.message.implement.AbstractInnerMsg;

import java.util.ArrayList;
import java.util.List;

public class In_LogMsgListToLogServer extends AbstractInnerMsg {
    private static final long serialVersionUID = -23160637341454340L;
    private List<DmLog> dmLogLis = new ArrayList<>();

    public In_LogMsgListToLogServer(List<DmLog> dmLogLis) {
        this.dmLogLis = dmLogLis;
    }

    public List<DmLog> getWsLogLis() {
        return dmLogLis;
    }
}
