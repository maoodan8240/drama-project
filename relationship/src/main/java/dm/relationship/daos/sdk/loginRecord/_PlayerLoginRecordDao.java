package dm.relationship.daos.sdk.loginRecord;

import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import drama.protos.EnumsProtos.PlatformTypeEnum;
import dm.relationship.topLevelPojos.sdk.loginRecord.PlayerLoginRecord;

public class _PlayerLoginRecordDao extends AbstractBaseDao<PlayerLoginRecord> implements PlayerLoginRecordDao {

    public _PlayerLoginRecordDao() {
        super(PlayerLoginRecord.class);
    }

    @Override
    public void updateLoginRecord(String playerId, int outerRealmId) {
        PlayerLoginRecord loginRecord = findOne(playerId);
        // 先移除再增加至最后
        loginRecord.getLoginOuterRealmIds().remove(Integer.valueOf(outerRealmId));
        loginRecord.getLoginOuterRealmIds().add(outerRealmId);
        insertIfExistThenReplace(loginRecord);
    }

    @Override
    public PlayerLoginRecord queryByPlatformUid(String platformUid, PlatformTypeEnum platformType) {
        return findOne(new Document("platformUid", platformUid).append("platformType", platformType));
    }
}
