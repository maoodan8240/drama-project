package dm.relationship.topLevelPojos.player;

import com.alibaba.fastjson.annotation.JSONField;
import drama.protos.EnumsProtos.PlatformTypeEnum;

import java.io.Serializable;

public class PlayerAccount implements Serializable {
    private static final long serialVersionUID = 6739526425696096611L;

    private int outerRealmId;                 // 显示服id
    @JSONField(serialize = false)
    private int innerRealmId;                 // 内部服Id -- 去所有Center用innerRealmId

    private String createAt;                  // 玩家角色创建的时间
    private PlatformTypeEnum platformType;    // 渠道
    private int subPlatform;                  // 子渠道
    private String platformUid;               // 平台账号

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public PlatformTypeEnum getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformTypeEnum PlatformTypeEnum) {
        this.platformType = PlatformTypeEnum;
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

    public int getInnerRealmId() {
        return innerRealmId;
    }

    public void setInnerRealmId(int innerRealmId) {
        this.innerRealmId = innerRealmId;
    }


}
