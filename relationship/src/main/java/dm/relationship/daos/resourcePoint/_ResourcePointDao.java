package dm.relationship.daos.resourcePoint;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.resourcePoint.ResourcePoint;

public class _ResourcePointDao extends AbstractBaseDao<ResourcePoint> implements ResourcePointDao {

    public _ResourcePointDao() {
        super(ResourcePoint.class);
    }
}
