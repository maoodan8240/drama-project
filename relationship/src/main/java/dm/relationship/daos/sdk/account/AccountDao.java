package dm.relationship.daos.sdk.account;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.topLevelPojos.sdk.account.Account;

public interface AccountDao extends BaseDao<Account> {
    Account query(String accountName);
}
