package dm.relationship.daos.simplePlayer;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;

/**
 * Created by leetony on 16-9-14.
 */
public interface SimplePlayerDao extends BaseDao<SimplePlayer> {

    SimplePlayer findBySimpleId(Integer simpleId);

    SimplePlayer findByPlayerName(String playerName);

}
