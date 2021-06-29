package dm.relationship.daos.simplePlayer;

import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;

/**
 * Created by leetony on 16-9-14.
 */
public class _SimplePlayerDao extends AbstractBaseDao<SimplePlayer> implements SimplePlayerDao {
    public _SimplePlayerDao() {
        super(SimplePlayer.class);
    }

    @Override
    public SimplePlayer findBySimpleId(Integer simpleId) {
        Document document = new Document("simplePlayerId", simpleId);
        return findOne(document);
    }

    @Override
    public SimplePlayer findByPlayerName(String playerName) {
        Document document = new Document("playerName", playerName);
        return findOne(document);
    }
}
