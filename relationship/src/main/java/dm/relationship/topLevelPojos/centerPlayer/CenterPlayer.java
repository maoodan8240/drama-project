package dm.relationship.topLevelPojos.centerPlayer;

import com.alibaba.fastjson.annotation.JSONField;
import dm.relationship.base.DmCloneable;
import drama.protos.EnumsProtos.PlatformTypeEnum;
import drama.protos.EnumsProtos.SexEnum;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;

import java.util.Date;

public class CenterPlayer implements TopLevelPojo, DmCloneable<CenterPlayer> {
    private static final long serialVersionUID = 4103278189017506140L;

    @JSONField(name = "_id")
    private String centerId;
    private int simpleId;


    // 基础信息
    private int outerRealmId; // 显示服id
    @JSONField(serialize = false)
    private int innerRealmId;

    private String gameId;                         // gameServer playerId
    private PlatformTypeEnum platformType;         // 平台
    private int subPlatform;                       // 子渠道
    private String platformUid;                    // 平台账号
    private boolean temporary;                     // 是否所临时账号

    // 其他信息
    private String playerName;                     // 角色姓名
    private int playerIcon;                        // 角色Icon
    private SexEnum sex;                           // 角色性别
    private String mobileNum;                      // 电话

    private int createDate;                        // centerPlayer创建时间 年月日      yyyyMMdd
    private int createTime;                        // centerPlayer创建时间 时分秒毫秒   HHmmss
    private int gameIdDate;                        // 设置GameId时间 年月日      yyyyMMdd
    private int gameIdTime;                        // 设置GameId时间 时分秒毫秒   HHmmss


    private boolean white;                         // 是否在白名单
    private boolean black;                         // 是否在黑名单
    private long lastLockTime;                   // 最后被踢出时间


    public CenterPlayer() {
    }

    /**
     * @param simpleId     虚拟id
     * @param outerRealmId 服Id
     * @param platformUid  平台id
     * @param temporary    是否是临时账号
     */
    public CenterPlayer(String centerId, int simpleId, int outerRealmId, String gameId, PlatformTypeEnum platformType, int subPlatform, String platformUid, boolean temporary) {
        this.centerId = centerId;
        this.simpleId = simpleId;
        this.outerRealmId = outerRealmId;
        this.gameId = gameId;
        this.platformType = platformType;
        this.subPlatform = subPlatform;
        this.platformUid = platformUid;
        this.temporary = temporary;
        Date date = new Date();
        this.createDate = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.yyyyMMdd));
        this.createTime = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.HHmmss));
    }

    /**
     * for clone
     *
     * @param centerId
     * @param simpleId
     * @param outerRealmId
     * @param innerRealmId
     * @param gameId
     * @param platformType
     * @param subPlatform
     * @param platformUid
     * @param temporary
     * @param playerName
     * @param playerIcon
     * @param sex
     * @param createDate
     * @param createTime
     * @param gameIdDate
     * @param gameIdTime
     * @param white
     * @param black
     * @param lastLockTime
     */
    public CenterPlayer(String centerId, int simpleId, int outerRealmId, int innerRealmId, String gameId, PlatformTypeEnum platformType, int subPlatform, String platformUid, boolean temporary, String playerName, int playerIcon, SexEnum sex, String mobileNum
            , int createDate, int createTime, int gameIdDate, int gameIdTime, boolean white, boolean black, long lastLockTime) {
        this.centerId = centerId;
        this.simpleId = simpleId;
        this.outerRealmId = outerRealmId;
        this.innerRealmId = innerRealmId;
        this.gameId = gameId;
        this.platformType = platformType;
        this.subPlatform = subPlatform;
        this.platformUid = platformUid;
        this.temporary = temporary;
        this.playerName = playerName;
        this.playerIcon = playerIcon;
        this.sex = sex;
        this.mobileNum = mobileNum;
        this.createDate = createDate;
        this.createTime = createTime;
        this.gameIdDate = gameIdDate;
        this.gameIdTime = gameIdTime;
        this.white = white;
        this.black = black;
        this.lastLockTime = lastLockTime;
    }

    /**
     * 修改玩家名称，玩家Icon, 玩家性别
     *
     * @param playerName
     * @param playerIcon
     * @param sex
     */
    public void updateOtherInfo(String playerName, int playerIcon, SexEnum sex) {
        this.playerName = playerName;
        this.playerIcon = playerIcon;
        this.sex = sex;
    }

    @Override
    public String getOid() {
        return centerId;
    }

    @Override
    public void setOid(String oid) {
        this.centerId = oid;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public PlatformTypeEnum getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformTypeEnum platformType) {
        this.platformType = platformType;
    }

    public int getSubPlatform() {
        return subPlatform;
    }

    public void setSubPlatform(int subPlatform) {
        this.subPlatform = subPlatform;
    }

    public String getPlatformUid() {
        return platformUid;
    }

    public void setPlatformUid(String platformUid) {
        this.platformUid = platformUid;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public int getPlayerIcon() {
        return playerIcon;
    }

    public void setPlayerIcon(int playerIcon) {
        this.playerIcon = playerIcon;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }

    public void setInnerRealmId(int innerRealmId) {
        this.innerRealmId = innerRealmId;
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

    public int getGameIdDate() {
        return gameIdDate;
    }

    public void setGameIdDate(int gameIdDate) {
        this.gameIdDate = gameIdDate;
    }

    public int getGameIdTime() {
        return gameIdTime;
    }

    public void setGameIdTime(int gameIdTime) {
        this.gameIdTime = gameIdTime;
    }


    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public long getLastLockTime() {
        return lastLockTime;
    }

    public void setLastLockTime(long lastLockTime) {
        this.lastLockTime = lastLockTime;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    @Override
    public CenterPlayer clone() {
        return new CenterPlayer(centerId, simpleId, outerRealmId, innerRealmId,
                gameId, platformType, subPlatform, platformUid, temporary,
                playerName, playerIcon, sex, mobileNum, createDate, createTime, gameIdDate, gameIdTime, white, black, lastLockTime);
    }
}
