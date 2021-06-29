package dm.relationship.base.msg;

import dm.relationship.logServer.base.DmLog;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_LogMsgToLogServer extends AbstractInnerMsg {
    private static final long serialVersionUID = -23160637341454340L;
    private DmLog dmLog;

    public In_LogMsgToLogServer(DmLog dmLog) {
        this.dmLog = dmLog;
    }

    public DmLog getWsLog() {
        return dmLog;
    }
}
