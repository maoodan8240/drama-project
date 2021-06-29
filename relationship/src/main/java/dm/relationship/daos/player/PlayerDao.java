package dm.relationship.daos.player;

import dm.relationship.topLevelPojos.player.Player;
import ws.common.mongoDB.interfaces.BaseDao;

public interface PlayerDao extends BaseDao<Player> {
    Player findPlayerByMobileNum(String mobileNum);

    Player findPlayerByPlayerId(String playerId);
}
