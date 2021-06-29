package dm.relationship.topLevelPojos.dataCenter.stageDaliyData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Data implements Serializable {
    private static final long serialVersionUID = 4141066826047359410L;

    // Map<type, Map<id, value>>
    // type 为 type 的枚举值
    // typeCode --- missionId --- value
    private Map<Integer, Map<Integer, Long>> tiv = new HashMap<>();

    public Map<Integer, Map<Integer, Long>> getTiv() {
        return tiv;
    }

    public void setTiv(Map<Integer, Map<Integer, Long>> tiv) {
        this.tiv = tiv;
    }
}
