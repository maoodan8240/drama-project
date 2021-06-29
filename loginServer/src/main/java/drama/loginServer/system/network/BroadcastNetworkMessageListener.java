package drama.loginServer.system.network;

import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.network.server.interfaces.NetworkListener;

public class BroadcastNetworkMessageListener implements NetworkListener {
    @Override
    public void onReceive(MessageReceiveHolder messageReceiveHolder) {
        System.out.println("Server 收到消息" + messageReceiveHolder.getMessage());
    }

    @Override
    public void onOffline(Connection connection) {

    }

    @Override
    public void onDisconnected(Connection connection) {

    }

    @Override
    public void onHeartBeating(Connection connection) {
 
    }
}
