package dm.relationship.topLevelPojos.common;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-3-24.
 */
public class CommonCreatedTargets implements TopLevelPojo {
    private static final long serialVersionUID = -6858165190950103024L;

    @JSONField(name = "_id")
    private String autoId;
    private List<String> targetNames = new ArrayList<>();

    public CommonCreatedTargets() {
    }

    public CommonCreatedTargets(String autoId) {
        this.autoId = autoId;
    }

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

    public List<String> getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(List<String> targetNames) {
        this.targetNames = targetNames;
    }
}
