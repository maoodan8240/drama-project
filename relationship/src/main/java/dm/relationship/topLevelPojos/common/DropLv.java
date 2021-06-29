package dm.relationship.topLevelPojos.common;

import java.io.Serializable;

public class DropLv implements Serializable {
    private static final long serialVersionUID = -7351596852670132630L;

    private int dropLvId; // 掉落库层级Id
    private int callTimes;// 调用libraryId的次数

    public DropLv() {
    }

    public DropLv(int dropLvId) {
        this.dropLvId = dropLvId;
    }

    public DropLv(int dropLvId, int callTimes) {
        this.dropLvId = dropLvId;
        this.callTimes = callTimes;
    }

    public int getDropLvId() {
        return dropLvId;
    }

    public void setDropLvId(int dropLvId) {
        this.dropLvId = dropLvId;
    }

    public int getCallTimes() {
        return callTimes;
    }

    public void setCallTimes(int callTimes) {
        this.callTimes = callTimes;
    }

    @Override
    public DropLv clone() {
        return new DropLv(dropLvId, callTimes);
    }
}
