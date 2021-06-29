package dm.relationship.topLevelPojos.friends;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 17-5-12.
 */
public class Friends extends PlayerTopLevelPojo {
    private static final long serialVersionUID = 2835850752574205680L;
    private Map<String, Friend> idToFriend = new HashMap<>();    // 好友列表
    private List<String> applyLis = new ArrayList<>();           // 申请列表
    private int getEnergyTimes;                                  // 已经领取体力的次数

    public Friends() {
    }

    public Friends(String playerId) {
        super(playerId);
    }

    public Map<String, Friend> getIdToFriend() {
        return idToFriend;
    }

    public void setIdToFriend(Map<String, Friend> idToFriend) {
        this.idToFriend = idToFriend;
    }

    public List<String> getApplyLis() {
        return applyLis;
    }

    public void setApplyLis(List<String> applyLis) {
        this.applyLis = applyLis;
    }

    public int getGetEnergyTimes() {
        return getEnergyTimes;
    }

    public void setGetEnergyTimes(int getEnergyTimes) {
        this.getEnergyTimes = getEnergyTimes;
    }
}
