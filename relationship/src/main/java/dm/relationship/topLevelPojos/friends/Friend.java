package dm.relationship.topLevelPojos.friends;

import java.io.Serializable;

/**
 * Created by lee on 17-5-12.
 */
public class Friend implements Serializable {
    private static final long serialVersionUID = -5842406078184734060L;
    private String playerId; // 好友ID
    private boolean beGive;  // 被该好友赠送了体力
    private boolean get;     // 该好友赠送的体力是否已经领取
    private boolean give;    // 是否已经赠送体力给该好友

    public Friend() {
    }

    public Friend(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isBeGive() {
        return beGive;
    }

    public void setBeGive(boolean beGive) {
        this.beGive = beGive;
    }

    public boolean isGet() {
        return get;
    }

    public void setGet(boolean get) {
        this.get = get;
    }

    public boolean isGive() {
        return give;
    }

    public void setGive(boolean give) {
        this.give = give;
    }
}
