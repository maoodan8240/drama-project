package dm.relationship.topLevelPojos.sdk.realm;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;

/**
 * Created by lee on 17-5-5.
 */
public class OuterToInnerRealmList implements TopLevelPojo, Cloneable {
    private static final long serialVersionUID = 8553903732891647206L;
    @JSONField(name = "_id")
    private String auto;
    private int outerRealmId;          // 玩家表面所在的逻辑服Id
    private int innerRealmId;          // 玩家真实所在的逻辑服Id
    private String gateURL;            // 连接的网关地址
    private String gameServerRole;     // gameserver role
    private String chatServerRole;     // chatserver role
    private String dbServerRole;       // dbserver role
    private String date;               // 开服日期,格式: yyyyMMddHHmm
    private RealmStatus realmStatus;   // 服的状态


    public OuterToInnerRealmList() {
    }

    public OuterToInnerRealmList(String auto, int outerRealmId, int innerRealmId, String gateURL, String gameServerRole, String chatServerRole, String dbServerRole, String date, RealmStatus realmStatus) {
        this.auto = auto;
        this.outerRealmId = outerRealmId;
        this.innerRealmId = innerRealmId;
        this.gateURL = gateURL;
        this.gameServerRole = gameServerRole;
        this.chatServerRole = chatServerRole;
        this.dbServerRole = dbServerRole;
        this.date = date;
        this.realmStatus = realmStatus;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }

    public void setInnerRealmId(int innerRealmId) {
        this.innerRealmId = innerRealmId;
    }

    public String getGateURL() {
        return gateURL;
    }

    public void setGateURL(String gateURL) {
        this.gateURL = gateURL;
    }

    public String getGameServerRole() {
        return gameServerRole;
    }

    public void setGameServerRole(String gameServerRole) {
        this.gameServerRole = gameServerRole;
    }

    public String getChatServerRole() {
        return chatServerRole;
    }

    public void setChatServerRole(String chatServerRole) {
        this.chatServerRole = chatServerRole;
    }

    public String getDbServerRole() {
        return dbServerRole;
    }

    public void setDbServerRole(String dbServerRole) {
        this.dbServerRole = dbServerRole;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RealmStatus getRealmStatus() {
        return realmStatus;
    }

    public void setRealmStatus(RealmStatus realmStatus) {
        this.realmStatus = realmStatus;
    }

    @Override
    public String getOid() {
        return auto;
    }

    @Override
    public void setOid(String auto) {
        this.auto = auto;
    }

    @Override
    public OuterToInnerRealmList clone() {
        return new OuterToInnerRealmList(auto, outerRealmId, innerRealmId, gateURL, gameServerRole, chatServerRole, dbServerRole, date, realmStatus);
    }
}
