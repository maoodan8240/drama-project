package dm.relationship.topLevelPojos.resourcePoint;

import drama.protos.EnumsProtos.ResourceTypeEnum;
import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.HashMap;
import java.util.Map;

public class ResourcePoint extends PlayerTopLevelPojo {
    private static final long serialVersionUID = 4349116159358891296L;

    private Map<ResourceTypeEnum, Long> resourceTypeMapToValue = new HashMap<>();// 资源当前的状态

    public ResourcePoint() {
    }

    public ResourcePoint(String playerId) {
        super(playerId);
    }

    public Map<ResourceTypeEnum, Long> getResourceTypeMapToValue() {
        return resourceTypeMapToValue;
    }

    public void setResourceTypeMapToValue(Map<ResourceTypeEnum, Long> resourceTypeMapToValue) {
        this.resourceTypeMapToValue = resourceTypeMapToValue;
    }
}
