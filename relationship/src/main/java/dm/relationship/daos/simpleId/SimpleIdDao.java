package dm.relationship.daos.simpleId;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.enums.SimpleIdTypeEnum;
import dm.relationship.topLevelPojos.simpleId.SimpleId;

public interface SimpleIdDao extends BaseDao<SimpleId> {
    /**
     * 下一个简单Id
     *
     * @param outerRealmId
     * @param type
     * @return
     */
    int nextSimpleId(int outerRealmId, SimpleIdTypeEnum type);

    /**
     * 下一个简单Id
     *
     * @param type
     * @return
     */
    int nextSimpleId(SimpleIdTypeEnum type);
}
