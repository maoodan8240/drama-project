package dm.relationship.topLevelPojos.sdk.loginRecord;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import drama.protos.EnumsProtos.PlatformTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoginRecord implements TopLevelPojo {
    private static final long serialVersionUID = -7701547346938744493L;

    @JSONField(name = "_id")
    private String playerId;
    private PlatformTypeEnum PlatformTypeEnum; // 渠道
    private String platformUid; // 平台账号
    private List<Integer> loginOuterRealmIds = new ArrayList<>();

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public PlatformTypeEnum getPlatformType() {
        return PlatformTypeEnum;
    }

    public void setPlatformType(PlatformTypeEnum PlatformTypeEnum) {
        this.PlatformTypeEnum = PlatformTypeEnum;
    }

    public String getPlatformUid() {
        return platformUid;
    }

    public void setPlatformUid(String platformUid) {
        this.platformUid = platformUid;
    }

    @Override
    public String getOid() {
        return playerId;
    }

    @Override
    public void setOid(String oid) {
        this.playerId = oid;
    }

    public List<Integer> getLoginOuterRealmIds() {
        return loginOuterRealmIds;
    }

    public void setLoginOuterRealmIds(List<Integer> loginOuterRealmIds) {
        this.loginOuterRealmIds = loginOuterRealmIds;
    }
}
