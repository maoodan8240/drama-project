package dm.relationship.appServers.loginServer.player.msg;

import com.google.protobuf.Message;
import dm.relationship.base.msg.AbstractWorldMsg;
import ws.common.network.server.interfaces.Connection;

public class In_LoginMsg extends AbstractWorldMsg {
    private Message message;
    private Connection connection;

    public In_LoginMsg(Message message, Connection connection) {
        super(connection);
        this.message = message;
        this.connection = connection;
    }


    public Message getMessage() {
        return message;
    }


    public Connection getConnection() {
        return connection;
    }


}
