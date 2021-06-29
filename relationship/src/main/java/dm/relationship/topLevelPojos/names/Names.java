package dm.relationship.topLevelPojos.names;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;

/**
 * Created by lee on 17-6-7.
 */
public class Names implements TopLevelPojo {
    private static final long serialVersionUID = 7766194719022696280L;
    @JSONField(name = "_id")
    private String autoId;
    private String name;

    public Names() {

    }

    public Names(String autoId, String name) {
        this.autoId = autoId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
