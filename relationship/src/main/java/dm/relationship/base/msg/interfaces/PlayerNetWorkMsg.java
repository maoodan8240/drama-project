package dm.relationship.base.msg.interfaces;

import com.google.protobuf.Message;
import ws.common.network.server.interfaces.Connection;

public interface PlayerNetWorkMsg {
    Connection getConnection();

    Message getMessage();

}
