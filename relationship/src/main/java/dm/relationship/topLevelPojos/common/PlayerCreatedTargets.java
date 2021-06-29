package dm.relationship.topLevelPojos.common;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 17-3-24.
 */
public class PlayerCreatedTargets extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -6858165190950103024L;
    
    private List<String> targetNames = new ArrayList<>();

    public PlayerCreatedTargets() {
    }

    public PlayerCreatedTargets(String playerId) {
        super(playerId);
    }

    public List<String> getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(List<String> targetNames) {
        this.targetNames = targetNames;
    }
}
