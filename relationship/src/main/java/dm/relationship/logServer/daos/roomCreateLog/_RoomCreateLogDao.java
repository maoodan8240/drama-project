package dm.relationship.logServer.daos.roomCreateLog;

import dm.relationship.logServer.pojos.RoomCreateLog;
import ws.common.mongoDB.implement.AbstractBaseDao;

/**
 * Created by lee on
 */
public class _RoomCreateLogDao extends AbstractBaseDao<RoomCreateLog> implements RoomCreateLogDao {

    public _RoomCreateLogDao() {
        super(RoomCreateLog.class);
    }
}
