package dm.relationship.topLevelPojos.common;

import dm.relationship.base.DmCloneable;

import java.io.Serializable;

/**
 * Created by lee on 16-10-8..
 */
public class LevelUpObj implements Serializable, DmCloneable<LevelUpObj> {
    private static final long serialVersionUID = 2590615314049641662L;

    private int level;
    private long ovfExp;

    public LevelUpObj() {
    }

    public LevelUpObj(int level, long ovfExp) {
        this.level = level;
        this.ovfExp = ovfExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getOvfExp() {
        return ovfExp;
    }

    public void setOvfExp(long ovfExp) {
        this.ovfExp = ovfExp;
    }

    @Override
    public LevelUpObj clone() {
        return new LevelUpObj(this.level, this.ovfExp);
    }
}
