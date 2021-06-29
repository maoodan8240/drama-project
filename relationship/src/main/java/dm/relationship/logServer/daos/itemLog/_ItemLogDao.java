package dm.relationship.logServer.daos.itemLog;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.logServer.pojos.ItemLog;

/**
 * Created by zww on 8/10/16.
 */
public class _ItemLogDao extends AbstractBaseDao<ItemLog> implements ItemLogDao {
    public _ItemLogDao() {
        super(ItemLog.class);
    }
}
