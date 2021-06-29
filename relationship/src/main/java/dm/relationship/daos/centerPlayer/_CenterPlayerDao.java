package dm.relationship.daos.centerPlayer;

import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.mongoDB.utils.UpdateOperators;
import drama.protos.EnumsProtos.PlatformTypeEnum;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;

import java.util.List;

public class _CenterPlayerDao extends AbstractBaseDao<CenterPlayer> implements CenterPlayerDao {

    public _CenterPlayerDao() {
        super(CenterPlayer.class);
    }

    @Override
    public CenterPlayer findCenterPlayer(PlatformTypeEnum platformType, int outerRealmId, String platformUid) {
        Document document = new Document("platformType", platformType.toString());
        document.append("platformUid", platformUid).append("outerRealmId", outerRealmId);
        return findOne(document);
    }


    @Override
    public CenterPlayer findCenterPlayer(Integer simpleId) {
        Document document = new Document("simpleId", simpleId);
        return findOne(document);
    }

    @Override
    public CenterPlayer findCenterPlayer(String gameId) {
        Document document = new Document("gameId", gameId);
        return findOne(document);
    }

    @Override
    public boolean addToWhiteList(Integer simpleId) {
        Document query = new Document("simpleId", simpleId);
        Document update = new Document(UpdateOperators.SET.getOperators(), new Document("white", true));
        Document rs = this.getMongoCollection().findOneAndUpdate(query, update, new FindOneAndUpdateOptions().upsert(false));
        return rs != null; // 返回null表示未找到该玩家
    }

    @Override
    public boolean addToBlackList(Integer simpleId) {
        Document query = new Document("simpleId", simpleId);
        Document update = new Document(UpdateOperators.SET.getOperators(), new Document("black", true));
        Document rs = this.getMongoCollection().findOneAndUpdate(query, update, new FindOneAndUpdateOptions().upsert(false));
        return rs != null; // 返回null表示未找到该玩家
    }

    @Override
    public boolean removeFromWhiteList(Integer simpleId) {
        Document query = new Document("simpleId", simpleId);
        Document update = new Document(UpdateOperators.SET.getOperators(), new Document("white", false));
        Document rs = this.getMongoCollection().findOneAndUpdate(query, update, new FindOneAndUpdateOptions().upsert(false));
        return rs != null; // 返回null表示未找到该玩家
    }

    @Override
    public boolean removeFromBlackList(Integer simpleId) {
        Document query = new Document("simpleId", simpleId);
        Document update = new Document(UpdateOperators.SET.getOperators(), new Document("black", false));
        Document rs = this.getMongoCollection().findOneAndUpdate(query, update, new FindOneAndUpdateOptions().upsert(false));
        return rs != null; // 返回null表示未找到该玩家
    }

    @Override
    public boolean lockPlayer(Integer simpleId, int time) {
        CenterPlayer centerPlayer;
        centerPlayer = findCenterPlayer(simpleId);
        if (centerPlayer == null) {
            return false; //未找到该玩家
        }
        long lastTime = Math.max(centerPlayer.getLastLockTime(), System.currentTimeMillis());
        long newtime = lastTime + time * DateUtils.MILLIS_PER_MINUTE;
        Document query = new Document("simpleId", simpleId);
        Document update = new Document(UpdateOperators.SET.getOperators(), new Document("lastLockTime", newtime));
        Document rs = this.getMongoCollection().findOneAndUpdate(query, update, new FindOneAndUpdateOptions().upsert(false));
        return rs != null; // 返回null表示未找到该玩家
    }

    @Override
    public List<CenterPlayer> queryAllWhiteList() {
        Document query = new Document("white", true);
        return findAll(query);
    }

    @Override
    public List<CenterPlayer> queryAllBlackList() {
        Document query = new Document("black", true);
        return findAll(query);
    }

    @Override
    public List<CenterPlayer> queryAllLockPlayerList() {
        long nowtime = System.currentTimeMillis();
        Document query = new Document("lastLockTime", new Document("$gt", nowtime));
        List<CenterPlayer> centerPlayers = findAll(query);
        return centerPlayers;
    }
}