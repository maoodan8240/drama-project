package drama.gameServer;

import dm.relationship.base.LauncherUtils;
import dm.relationship.exception.LauncherInitException;
import dm.relationship.table.RootTc;
import drama.gameServer.system.ServerGlobalData;
import drama.gameServer.system.actor.DmActorSystem;
import drama.gameServer.system.config.AppConfig;
import drama.gameServer.system.di.GlobalInjectorUtils;
import drama.gameServer.system.table.RootTcListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.config.MongoConfig;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.network.server.config.implement._ConnConfig;
import ws.common.network.server.config.implement._ServerConfig;
import ws.common.network.server.config.interfaces.ConnConfig;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.interfaces.CodeToMessagePrototype;
import ws.common.network.server.interfaces.NetworkListener;
import ws.common.network.server.tcp.TcpServer;
import ws.common.network.server.tcp._TcpServer;
import ws.common.redis.jedis.interfaces.JedisClient;
import ws.common.table.data.PlanningTableData;
import ws.common.utils.dataSource.txt._PlanningTableData;
import ws.common.utils.di.GlobalInjector;

public class Launcher {

    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        _init();
        _startActorSystem();
        _waitForSeconds("TcpServer");
        _startTcpServer();
    }

    private static void _init() {
        try {
            AppConfig.init();
            GlobalInjectorUtils.init();
//            GlobalScheduler.init();
            _redisInit();
            _mongodbInit();
            ServerGlobalData.init();
            _initPlanningTableData();
            LauncherUtils._redisInit_SingleTest();
            LauncherUtils._mongodbInit_Test();
        } catch (Exception e) {
            throw new LauncherInitException("初始化异常", e);
        }
    }

    private static void _redisInit() {
        String redisSentinelIpAndPort = AppConfig.getRedisSentinelIpAndPort();
        String[] ipAndPort = StringUtils.split(redisSentinelIpAndPort, ":");
        int maxTotal = AppConfig.getInt(AppConfig.Key.DM_Common_Config_redis_maxTotal);
        int maxIdlel = AppConfig.getInt(AppConfig.Key.DM_Common_Config_redis_maxIdlel);
        int maxWaitSeconds = AppConfig.getInt(AppConfig.Key.DM_Common_Config_redis_maxWaitSeconds);
        String pswd = AppConfig.getString(AppConfig.Key.DM_Common_Config_redis_pswd);
        JedisClient jedisClient = GlobalInjector.getInstance(JedisClient.class);
        jedisClient._init(
                ipAndPort[0],//
                Integer.valueOf(ipAndPort[1]),//
                pswd,//
                maxTotal,//
                maxIdlel,//
                maxWaitSeconds,//
                1);
    }

    private static void _startActorSystem() {
        DmActorSystem.init();
    }

    private static void _startTcpServer() {
        ServerConfig serverConfig = new _ServerConfig(new _ConnConfig(//
                ConnConfig.ServerProtocolType.TCP, //
                AppConfig.getIp(), //
                AppConfig.getInt(AppConfig.Key.DM_HttpGatewayServer_tcp_server_port), //
                AppConfig.getInt(AppConfig.Key.DM_HttpGatewayServer_tcp_server_offlineTimeout), //
                AppConfig.getInt(AppConfig.Key.DM_HttpGatewayServer_tcp_server_disconnTimeout) //
        ));
        TcpServer tcpServer = new _TcpServer(serverConfig);
        tcpServer.getNetworkContext().setCodeToMessagePrototype(GlobalInjector.getInstance(CodeToMessagePrototype.class));
        tcpServer.getNetworkHandler().addListener(GlobalInjector.getInstance(NetworkListener.class));
        tcpServer.start();
    }

    private static void _mongodbInit() {
        String host = AppConfig.getString(AppConfig.Key.DM_Common_Config_mongodb_host);
        int port = AppConfig.getInt(AppConfig.Key.DM_Common_Config_mongodb_port);
        String userName = AppConfig.getString(AppConfig.Key.DM_Common_Config_mongodb_userName);
        String password = AppConfig.getString(AppConfig.Key.DM_Common_Config_mongodb_password);
        String dbName = AppConfig.getString(AppConfig.Key.DM_Common_Config_mongodb_dbName);
        int connectionsPerHost = AppConfig.getInt(AppConfig.Key.DM_Common_Config_mongodb_connectionsPerHost);
        MongoConfig config = new MongoConfig(host, port, userName, password, dbName, connectionsPerHost);
        GlobalInjector.getInstance(MongoDBClient.class).init(config);
    }

    private static void _initPlanningTableData() throws Exception {
        PlanningTableData planningTableData = new _PlanningTableData(AppConfig.getString(AppConfig.Key.DM_Common_Config_planningTableData_tab_file_path));
        planningTableData.loadAllTablesData();
        RootTc.init(planningTableData, new RootTcListener());
        RootTc.loadAllTables(ServerGlobalData.getLocale());
    }

    /**
     * 保证停止服务与重新启动服务有稍许时间差
     */
    private static void _waitForSeconds(String msg) {
        int seconds = 3;
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000);
                logger.info("wait for start {}! {} ...", msg, (seconds - i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
