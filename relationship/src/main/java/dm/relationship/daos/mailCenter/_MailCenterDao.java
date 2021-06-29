package dm.relationship.daos.mailCenter;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.mailCenter.MailsCenter;

/**
 * Created by lee on 16-12-13.
 */
public class _MailCenterDao extends AbstractBaseDao<MailsCenter> implements MailCenterDao {
    public _MailCenterDao() {
        super(MailsCenter.class);
    }
}
