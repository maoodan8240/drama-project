package dm.relationship.base.msg;

import ws.common.network.server.interfaces.Connection;
import ws.common.utils.message.implement.AbstractInnerMsg;


public class AbstractWorldMsg extends AbstractInnerMsg {
    private Connection connection;

    public AbstractWorldMsg(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }


}
