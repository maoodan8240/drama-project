package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.enums.RoomState;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.Date;
import java.util.List;

/**
 * Created by lee on 2021/10/19
 */
public class In_AddRoomTimerMsg implements InnerMsg {
    private String timerName;
    private Date date;
    private RoomState action;

    public In_AddRoomTimerMsg(String timerName, Date date, RoomState action) {
        this.timerName = timerName;
        this.date = date;
        this.action = action;
    }


    public RoomState getAction() {
        return action;
    }

    public String getTimerName() {
        return timerName;
    }

    public Date getDate() {
        return date;
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
