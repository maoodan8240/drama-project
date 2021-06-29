package dm.relationship.logServer.pojos;

import org.bson.types.ObjectId;
import dm.relationship.logServer.base.PlayerLog;

public class PlayerVipLvUpLog extends PlayerLog {
    private static final long serialVersionUID = -2815996049078764383L;

    private int lv;
    private int vipLv;

    public PlayerVipLvUpLog() {
    }

    public PlayerVipLvUpLog(int lv, int vipLv) {
        super(ObjectId.get().toString());
        this.lv = lv;
        this.vipLv = vipLv;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }
}
