package dm.relationship.logServer.daos.playerVipLvUpLog;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.logServer.pojos.PlayerVipLvUpLog;

/**
 * Created by zww on 8/10/16.
 */
public class _PlayerVipLvUpLogDao extends AbstractBaseDao<PlayerVipLvUpLog> implements PlayerVipLvUpLogDao {
    public _PlayerVipLvUpLogDao() {
        super(PlayerVipLvUpLog.class);
    }
}
