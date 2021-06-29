package dm.relationship.logServer.pojos;

import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import dm.relationship.logServer.base.PlayerLog;

import java.util.Date;

public class PlayerLogoutLog extends PlayerLog {
    private static final long serialVersionUID = 4272651817903427892L;
    private int loginDate;    // 年月日      yyyyMMdd
    private int loginTime;    // 时分秒毫秒   HHmmss
    private int durationM;    // 持续时间(单为:分钟)

    public PlayerLogoutLog() {
    }


    public PlayerLogoutLog(long lsinTime) {
        super(ObjectId.get().toString());
        Date date = new Date(lsinTime);
        this.loginDate = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.yyyyMMdd));
        this.loginTime = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.HHmmss));
        this.durationM = (int) ((System.currentTimeMillis() - lsinTime) / DateUtils.MILLIS_PER_MINUTE);
    }

    public int getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(int loginDate) {
        this.loginDate = loginDate;
    }

    public int getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(int loginTime) {
        this.loginTime = loginTime;
    }

    public int getDurationM() {
        return durationM;
    }

    public void setDurationM(int durationM) {
        this.durationM = durationM;
    }
}
