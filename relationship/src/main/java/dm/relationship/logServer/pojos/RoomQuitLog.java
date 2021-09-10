package dm.relationship.logServer.pojos;

import dm.relationship.logServer.base.RoomLog;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;

import java.util.Date;

/**
 * Created by lee on 2021/9/6
 */
public class RoomQuitLog extends RoomLog {
    private int createDate;
    private int createTime;
    private int durationM;

    public RoomQuitLog(long quitTime) {
        super(ObjectId.get().toString());
        Date date = new Date(quitTime);
        this.createDate = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.yyyyMMdd));
        this.createTime = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.HHmmss));
        this.durationM = (int) ((System.currentTimeMillis() - quitTime) / DateUtils.MILLIS_PER_MINUTE);
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getDurationM() {
        return durationM;
    }

    public void setDurationM(int durationM) {
        this.durationM = durationM;
    }
}
