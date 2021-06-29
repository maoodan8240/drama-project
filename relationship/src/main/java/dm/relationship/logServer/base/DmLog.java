package dm.relationship.logServer.base;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;

import java.util.Date;


public abstract class DmLog implements TopLevelPojo {
    private static final long serialVersionUID = 3979266721542930436L;

    @JSONField(name = "_id")
    private String snid;
    private int date;    // 年月日      yyyyMMdd
    private int time;    // 时分秒毫秒   HHmmss

    public DmLog() {
    }

    public DmLog(String snid) {
        this.snid = snid;
        Date date = new Date();
        this.date = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.yyyyMMdd));
        this.time = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.HHmmss));
    }

    @Override
    public String getOid() {
        return snid;
    }

    @Override
    public void setOid(String oid) {
        this.snid = oid;
    }

    public String getSnid() {
        return snid;
    }

    public void setSnid(String snid) {
        this.snid = snid;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
