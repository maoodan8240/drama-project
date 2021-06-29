package drama.loginServer;

import drama.loginServer.system.actor.DmActorSystem;
import drama.loginServer.system.config.AppConfig;
import drama.loginServer.system.di.GlobalInjectorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.config.implement._ConnConfig;
import ws.common.network.server.config.implement._ServerConfig;
import ws.common.network.server.config.interfaces.ConnConfig;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.interfaces.NetworkListener;
import ws.common.network.server.tcp.TcpServer;
import ws.common.network.server.tcp._TcpServer;
import ws.common.utils.di.GlobalInjector;

public class Launcher {
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        System.out.println(Launcher.class.getResource("/").getFile().toString());
        System.out.println("java.version > " + System.getProperty("java.version"));
        System.out.println("java.home > " + System.getProperty("java.home"));
        System.out.println("java.class.path > " + System.getProperty("java.class.path"));
        System.out.println("java.library.path > " + System.getProperty("java.library.path"));
        System.out.println("os.name > " + System.getProperty("os.name"));
        System.out.println("os.arch > " + System.getProperty("os.arch"));
        System.out.println("os.version > " + System.getProperty("os.version"));
        System.out.println("user.name > " + System.getProperty("user.name"));
        System.out.println("user.home > " + System.getProperty("user.home"));
        System.out.println("user.dir > " + System.getProperty("user.dir"));

//        System.setProperty("user.dir", "");
        _init();
        _startActorSystem();
        _startHttpServer();
    }

    private static void _startHttpServer() {
        int port = AppConfig.get().getInt(AppConfig.Key.DM_LOGIN_SERVER_HTTP_SERVER_PORT);
        int timeout = AppConfig.get().getInt(AppConfig.Key.DM_LOGIN_SERVER_HTTP_SERVER_TIMEOUT);
        ServerConfig serverConfig = new _ServerConfig(new _ConnConfig(
                ConnConfig.ServerProtocolType.HTTP,//
                "127.0.0.1",//
                port,//
                timeout,//
                320//

        ));
        TcpServer tcp = new _TcpServer(serverConfig);
        tcp.getNetworkHandler().addListener(GlobalInjector.getInstance(NetworkListener.class));
        tcp.start();

    }

    private static void _init() {
        try {
            AppConfig.init();
            GlobalInjectorUtils.init();
        } catch (Exception e) {
            logger.error("初始化异常！", e);
        }
    }

    private static void _startActorSystem() {
        DmActorSystem.init();
    }
}
