package dm.relationship.topLevelPojos.player;

import drama.protos.EnumsProtos.SexEnum;

import java.io.Serializable;

public class PlayerBase implements Serializable {
    private static final long serialVersionUID = -7015486457382954242L;

    private int simpleId;     // 简单Id
    private String name;      // 名字
    private String sign;      // 签名
    private SexEnum sex = SexEnum.MALE;      // 性别
    private String icon;       // 头像
    private int level;        // 等级
    private long overflowExp; // 超出等级之外的经验值
    private String birthday;    //生日
    private String place;       //地方

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

    public String getIcon() {
        return icon;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPlace() {
        return place;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
