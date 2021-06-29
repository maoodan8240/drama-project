package dm.relationship.topLevelPojos.player;

import java.io.Serializable;

public class PlayerOther implements Serializable {
    private static final long serialVersionUID = 410646656442049666L;

    private int reNameTs;             // 重命名的次数
    private int loginDays;            // 登陆的天数
    private boolean robot = false;    // 是否为机器人
    private long lsinTime;            // 最近登录的时间点
    private long lsoutTime;           // 最近登出的时间点
    private String lastResetDay = "0";

    public int getReNameTs() {
        return reNameTs;
    }

    public void setReNameTs(int reNameTs) {
        this.reNameTs = reNameTs;
    }

    public int getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(int loginDays) {
        this.loginDays = loginDays;
    }

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    public String getLastResetDay() {
        return lastResetDay;
    }

    public void setLastResetDay(String lastResetDay) {
        this.lastResetDay = lastResetDay;
    }

    public long getLsinTime() {
        return lsinTime;
    }

    public void setLsinTime(long lsinTime) {
        this.lsinTime = lsinTime;
    }

    public long getLsoutTime() {
        return lsoutTime;
    }

    public void setLsoutTime(long lsoutTime) {
        this.lsoutTime = lsoutTime;
    }
}
