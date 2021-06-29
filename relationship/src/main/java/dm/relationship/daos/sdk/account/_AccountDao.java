package dm.relationship.daos.sdk.account;

import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.sdk.account.Account;

public class _AccountDao extends AbstractBaseDao<Account> implements AccountDao {

    public _AccountDao() {
        super(Account.class);
    }

    @Override
    public Account query(String accountName) {
        return findOne(new Document("accountName", accountName));
    }
}
