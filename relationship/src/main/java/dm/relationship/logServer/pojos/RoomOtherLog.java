package dm.relationship.logServer.pojos;

import dm.relationship.logServer.base.RoomLog;
import org.bson.types.ObjectId;
import ws.common.table.table.interfaces.cell.TupleCell;

import java.util.List;

/**
 * Created by lee on 2021/9/6
 */
public class RoomOtherLog extends RoomLog {
    private String action;
    private List<TupleCell<String>> args;

    public RoomOtherLog(String action, List<TupleCell<String>> args) {
        super(ObjectId.get().toString());
        this.action = action;
        this.args = args;
    }
}
