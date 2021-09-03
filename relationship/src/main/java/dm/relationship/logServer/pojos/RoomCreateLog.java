package dm.relationship.logServer.pojos;

import dm.relationship.logServer.base.RoomLog;
import org.bson.types.ObjectId;

public class RoomCreateLog extends RoomLog {
    public RoomCreateLog() {
        super(ObjectId.get().toString());
    }
    

}
