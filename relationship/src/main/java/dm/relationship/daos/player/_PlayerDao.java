package dm.relationship.daos.player;

import dm.relationship.topLevelPojos.player.Player;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;

public class _PlayerDao extends AbstractBaseDao<Player> implements PlayerDao {

    public _PlayerDao() {
        super(Player.class);
    }

    public Player findPlayerByMobileNum(String mobileNum) {
        Document document = new Document("mobileNum", mobileNum);
        Player player = findOne(document);
        return player;
    }

    @Override
    public Player findPlayerByPlayerId(String playerId) {
        return findOne(playerId);
    }
}
