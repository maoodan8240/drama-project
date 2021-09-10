package dm.relationship.logServer.daos.roomQuitLog;

import dm.relationship.logServer.pojos.RoomQuitLog;
import ws.common.mongoDB.implement.AbstractBaseDao;

/**
 * Created by lee on 2021/9/6
 */
public class _RoomQuitLogDao extends AbstractBaseDao<RoomQuitLog> implements RoomQuitLogDao {
    public _RoomQuitLogDao() {
        super(RoomQuitLog.class);
    }
}
