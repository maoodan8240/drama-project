package dm.relationship.topLevelPojos.simplePlayer;

import drama.protos.EnumsProtos.HeroPositionEnum;
import drama.protos.EnumsProtos.SexEnum;
import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leetony on 16-8-25.
 */
public class SimplePlayer extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -6148875855249139783L;

    private int outRealmId;        // 服Id
    private int innerRealmId;      // 内部服Id
    private int simplePlayerId;    // 玩家短Id
    private String playerName;     // 玩家名字
    private SexEnum sex;           // 性别
    private int lv;                // 玩家等级
    private int vipLv;             // 玩家VIP等级
    private String sign;           // 签名

    private int icon;              // 玩家头像
    private String guildId;        // 帮派名字

    private long battleValue;      // 战斗力
    private int firstHandValue;    // 先手值
    private long lastLoginTime;    // 最近一次登录时间
    private long lastLogoutTime;   // 最近一次登出时间

    private int pvpRank;           // pvp排名
    private int pvpIcon;           // pvp形象
    private int pvpVictoryTimes;   // pv胜利场次
    private String pvpDeclaration; // pvp宣言
    private int ultimateTestLevel; // 终极试炼最高层数

    //  主阵容位置--武将


    public SimplePlayer() {
    }

    public int getSimplePlayerId() {
        return simplePlayerId;
    }

    public void setSimplePlayerId(int simplePlayerId) {
        this.simplePlayerId = simplePlayerId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getOutRealmId() {
        return outRealmId;
    }

    public void setOutRealmId(int outRealmId) {
        this.outRealmId = outRealmId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public long getBattleValue() {
        return battleValue;
    }

    public void setBattleValue(long battleValue) {
        this.battleValue = battleValue;
    }

    public int getFirstHandValue() {
        return firstHandValue;
    }

    public void setFirstHandValue(int firstHandValue) {
        this.firstHandValue = firstHandValue;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(long lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public int getPvpRank() {
        return pvpRank;
    }

    public void setPvpRank(int pvpRank) {
        this.pvpRank = pvpRank;
    }

    public int getPvpIcon() {
        return pvpIcon;
    }

    public void setPvpIcon(int pvpIcon) {
        this.pvpIcon = pvpIcon;
    }

    public int getPvpVictoryTimes() {
        return pvpVictoryTimes;
    }

    public void setPvpVictoryTimes(int pvpVictoryTimes) {
        this.pvpVictoryTimes = pvpVictoryTimes;
    }

    public String getPvpDeclaration() {
        return pvpDeclaration;
    }

    public void setPvpDeclaration(String pvpDeclaration) {
        this.pvpDeclaration = pvpDeclaration;
    }



    public int getUltimateTestLevel() {
        return ultimateTestLevel;
    }

    public void setUltimateTestLevel(int ultimateTestLevel) {
        this.ultimateTestLevel = ultimateTestLevel;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }

    public void setInnerRealmId(int innerRealmId) {
        this.innerRealmId = innerRealmId;
    }
}
