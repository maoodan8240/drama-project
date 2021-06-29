package dm.relationship.logServer.pojos;

import org.bson.types.ObjectId;
import dm.relationship.logServer.base.PlayerLog;

public class PlayerPveLog extends PlayerLog {
    private static final long serialVersionUID = 387967959441999222L;
    private int stageId;
    private int lv;

    public PlayerPveLog() {
    }

    public PlayerPveLog(int stageId, int lv) {
        super(ObjectId.get().toString());
        this.stageId = stageId;
        this.lv = lv;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }
}
