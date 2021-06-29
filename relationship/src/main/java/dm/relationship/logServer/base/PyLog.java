package dm.relationship.logServer.base;

import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;

import java.util.Date;

public abstract class PyLog extends DmLog {
    private static final long serialVersionUID = -7010381484108156385L;
    private int sid;                    // simpleId
    private int orid;                   // outerRealmId
    private int createAtDate;    // 年月日      yyyyMMdd
    private int createAtTime;    // 时分秒毫秒   HHmmss

    public PyLog() {
    }

    public PyLog(String pid, int sid, int orid, String createAt) {
        super(pid);
        this.sid = sid;
        this.orid = orid;
        Date date1 = WsDateUtils.dateToFormatDate(createAt, WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss);
        this.createAtDate = Integer.valueOf(WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd));
        this.createAtTime = Integer.valueOf(WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.HHmmss));
    }


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getOrid() {
        return orid;
    }

    public void setOrid(int orid) {
        this.orid = orid;
    }

    public int getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate(int createAtDate) {
        this.createAtDate = createAtDate;
    }

    public int getCreateAtTime() {
        return createAtTime;
    }

    public void setCreateAtTime(int createAtTime) {
        this.createAtTime = createAtTime;
    }
}