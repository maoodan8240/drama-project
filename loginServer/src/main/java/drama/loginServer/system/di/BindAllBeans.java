package drama.loginServer.system.di;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import drama.loginServer.system.network.BroadcastNetworkMessageListener;
import ws.common.network.server.interfaces.NetworkListener;

public class BindAllBeans {
    public static void bind(Binder binder) {
        binder.bind(NetworkListener.class).to(BroadcastNetworkMessageListener.class).in(Scopes.SINGLETON);
    }
}
