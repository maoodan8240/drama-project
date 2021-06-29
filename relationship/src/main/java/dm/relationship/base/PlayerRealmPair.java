package dm.relationship.base;

/**
 * Created by lee on 17-5-5.
 */
public class PlayerRealmPair {
    private String playerId;           // 角色Id
    private int outerRealmId;          // 玩家表面所在的逻辑服Id
    private int innerRealmId;          // 玩家真实所在的逻辑服Id

    public PlayerRealmPair(String playerId, int outerRealmId, int innerRealmId) {
        this.playerId = playerId;
        this.outerRealmId = outerRealmId;
        this.innerRealmId = innerRealmId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }


    @Override
    public PlayerRealmPair clone() {
        return new PlayerRealmPair(playerId, outerRealmId, innerRealmId);
    }
}
