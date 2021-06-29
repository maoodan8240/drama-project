package dm.relationship.topLevelPojos;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;

/**
 * Created by lee on 16-9-18.
 */
public class PlayerTopLevelPojo implements TopLevelPojo, IPlayerTopLevelPojo {
    private static final long serialVersionUID = -5441282778484937128L;

    @JSONField(name = "_id")
    private String playerId;
    private String lastResetDay = "0";

    public PlayerTopLevelPojo() {
    }

    public PlayerTopLevelPojo(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String getOid() {
        return playerId;
    }

    @Override
    public void setOid(String oid) {
        this.playerId = oid;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getLastResetDay() {
        return lastResetDay;
    }

    public void setLastResetDay(String lastResetDay) {
        this.lastResetDay = lastResetDay;
    }
}
