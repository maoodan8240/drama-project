package dm.relationship.topLevelPojos.simpleId;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import dm.relationship.enums.SimpleIdTypeEnum;

public class SimpleId implements TopLevelPojo {
    private static final long serialVersionUID = 7812463856702058016L;

    @JSONField(name = "_id")
    private String autoId;
    private int simpleId;
    private int outerRealmId; // 显示服id
    private SimpleIdTypeEnum type;

    @Override
    public String getOid() {
        return autoId;
    }

    @Override
    public void setOid(String oid) {
        this.autoId = oid;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public int getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public SimpleIdTypeEnum getType() {
        return type;
    }

    public void setType(SimpleIdTypeEnum type) {
        this.type = type;
    }
}
