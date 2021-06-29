package dm.relationship.topLevelPojos.player;

import drama.protos.EnumsProtos.SexEnum;

import java.io.Serializable;

public class PlayerBase implements Serializable {
    private static final long serialVersionUID = -7015486457382954242L;

    private int simpleId;     // 简单Id
    private String name;      // 名字
    private String sign;      // 签名
    private SexEnum sex = SexEnum.MALE;      // 性别
    private int iconId;       // 头像
    private int level;        // 等级
    private long overflowExp; // 超出等级之外的经验值

    public int getSimpleId() {
        return simpleId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getOverflowExp() {
        return overflowExp;
    }

    public void setOverflowExp(long overflowExp) {
        this.overflowExp = overflowExp;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
