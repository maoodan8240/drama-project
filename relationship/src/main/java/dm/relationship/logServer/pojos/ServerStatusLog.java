package dm.relationship.logServer.pojos;

import org.bson.types.ObjectId;
import dm.relationship.logServer.base.DmLog;

public class ServerStatusLog extends DmLog {
    private static final long serialVersionUID = 387967959441999222L;
    private int innerRealmId;
    private int online;
    private int offline;
    private int other;

    public ServerStatusLog() {
        super(ObjectId.get().toString());
    }

    public ServerStatusLog(int innerRealmId) {
        super(ObjectId.get().toString());
        this.innerRealmId = innerRealmId;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }

    public void setInnerRealmId(int innerRealmId) {
        this.innerRealmId = innerRealmId;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getOffline() {
        return offline;
    }

    public void setOffline(int offline) {
        this.offline = offline;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }
}
