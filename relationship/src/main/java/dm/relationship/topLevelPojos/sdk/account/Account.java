package dm.relationship.topLevelPojos.sdk.account;

import com.alibaba.fastjson.annotation.JSONField;
import org.bson.types.ObjectId;
import ws.common.mongoDB.interfaces.TopLevelPojo;

public class Account implements TopLevelPojo {
    private static final long serialVersionUID = -1094687500903405924L;

    @JSONField(name = "_id")
    String accountId;
    String accountName;
    String password;

    public Account() {
    }

    public Account(String accountName) {
        this(accountName, null);
    }

    public Account(String accountName, String password) {
        this.accountId = new ObjectId().toString();
        this.accountName = accountName;
        this.password = password;
    }

    @Override
    public String getOid() {
        return accountId;
    }

    @Override
    public void setOid(String oid) {
        this.accountId = oid;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "accountId=" + accountId + ", accountName=" + accountName + ", password=" + password;
    }
}
