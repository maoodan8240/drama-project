package dm.relationship.utils;

import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 17-4-14.
 */
public class SimplePojoUtils {


    public static SimplePlayer querySimplePlayerById(String playerId, int outerRealmId) {
        return DBUtils.getHashPojo(playerId, outerRealmId, SimplePlayer.class);
    }


    public static Map<String, SimplePlayer> querySimplePlayerLisByIds(List<String> playerIds, int outerRealmId) {
        return DBUtils.getHashPojoLis(playerIds, outerRealmId, SimplePlayer.class);
    }




}
