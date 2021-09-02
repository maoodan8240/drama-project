package dm.relationship.logServer.daos.playerLogoutLog;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.logServer.pojos.PlayerLogoutLog;

/**
 * Created by lee on 8/10/16.
 */
public class _PlayerLogoutLogDao extends AbstractBaseDao<PlayerLogoutLog> implements PlayerLogoutLogDao {
    public _PlayerLogoutLogDao() {
        super(PlayerLogoutLog.class);
    }
}
