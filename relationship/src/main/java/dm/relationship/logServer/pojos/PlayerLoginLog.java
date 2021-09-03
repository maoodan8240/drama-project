package dm.relationship.logServer.pojos;

import dm.relationship.logServer.base.PlayerLog;
import org.bson.types.ObjectId;

/**
 * Created by lee on 17-7-11.
 */
public class PlayerLoginLog extends PlayerLog {

    public PlayerLoginLog() {

        super(ObjectId.get().toString());
    }

}
