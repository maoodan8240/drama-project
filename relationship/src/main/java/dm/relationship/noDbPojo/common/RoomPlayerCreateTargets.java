package dm.relationship.noDbPojo.common;

import dm.relationship.noDbPojo.RoomPlayerNoDbPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2021/10/8
 */
public class RoomPlayerCreateTargets extends RoomPlayerNoDbPojo {
    private List<String> targetNames = new ArrayList<>();

    public RoomPlayerCreateTargets() {
    }

    public RoomPlayerCreateTargets(String playerId) {
        super(playerId);
    }

    public List<String> getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(List<String> targetNames) {
        this.targetNames = targetNames;
    }
}
