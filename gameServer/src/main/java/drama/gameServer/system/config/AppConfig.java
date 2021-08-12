package drama.gameServer.system.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import dm.relationship.base.ServerEnvEnum;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.exception.AppConfigInitException;
import dm.relationship.exception.AppConfigServerRoleNotRightException;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.general.ParseRoute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfig {
    public static class Key {
        public static final String Akka_Remote_Netty_Tcp_Hostname = "akka.remote.netty.tcp.hostname";
        public static final String Akka_Remote_Netty_Tcp_Port = "akka.remote.netty.tcp.port";
        public static final String Akka_Cluster_Roles = "akka.cluster.roles";
        public static final String Akka_Cluster_Join_Point = "akka.cluster.join-point";

        public static final String DM_HttpGatewayServer_tcp_server_port = "DM-HttpGatewayServer.tcp-server.port";
        public static final String DM_HttpGatewayServer_tcp_server_disconnTimeout = "DM-HttpGatewayServer.tcp-server.disconnTimeout";
        public static final String DM_HttpGatewayServer_tcp_server_offlineTimeout = "DM-HttpGatewayServer.tcp-server.offlineTimeout";

        public static final String DM_Common_Config_use_net_ip_first = "DM-Common-Config.use-net-ip-first";

        public static final String DM_Common_Config_lang = "DM-Common-Config.lang";
        public static final String DM_Common_Config_country = "DM-Common-Config.country";
        public static final String DM_Common_Config_planningTableData_tab_file_path = "DM-Common-Config.planningTableData.tab-file-path";

        public static final String DM_Common_Config_jmx_conf_jmx_server_host = "DM-Common-Config.jmx-conf.jmx-server-host";
        public static final String DM_Common_Config_jmx_conf_jmx_server_port = "DM-Common-Config.jmx-conf.jmx-server-port";
        public static final String DM_Common_Config_jmx_conf_jmx_server_enabled = "DM-Common-Config.jmx-conf.jmx-server-enabled";

        public static final String DM_Common_Config_redis_pswd = "DM-Common-Config.redis.pswd";
        public static final String DM_Common_Config_redis_maxTotal = "DM-Common-Config.redis.maxTotal";
        public static final String DM_Common_Config_redis_maxIdlel = "DM-Common-Config.redis.maxIdlel";
        public static final String DM_Common_Config_redis_maxWaitSeconds = "DM-Common-Config.redis.maxWaitSeconds";
        public static final String DM_Common_Config_redis_sentinelIpAndPorts = "DM-Common-Config.redis.sentinelIpAndPorts";
        public static final String DM_Common_Config_redis_masterNames = "DM-Common-Config.redis.masterNames";

        public static final String DM_Common_Config_mongodb_minConnectionsPerHost = "DM-Common-Config.mongodb.minConnectionsPerHost";
        public static final String DM_Common_Config_mongodb_connectionsPerHost = "DM-Common-Config.mongodb.connectionsPerHost";
        public static final String DM_Common_Config_mongodb_dbName = "DM-Common-Config.mongodb.dbName";
        public static final String DM_Common_Config_mongodb_host = "DM-Common-Config.mongodb.host";
        public static final String DM_Common_Config_mongodb_password = "DM-Common-Config.mongodb.password";
        public static final String DM_Common_Config_mongodb_port = "DM-Common-Config.mongodb.port";
        public static final String DM_Common_Config_mongodb_userName = "DM-Common-Config.mongodb.userName";

        public static final String DM_Common_Config_ftp_userName = "DM-Common-Config.ftp.userName";
        public static final String DM_Common_Config_ftp_password = "DM-Common-Config.ftp.password";
        public static final String DM_Common_Config_ftp_host = "DM-Common-Config.ftp.host";
        public static final String DM_Common_Config_ftp_port = "DM-Common-Config.ftp.port";
        public static final String DM_Common_Config_ftp_basePath = "DM-Common-Config.ftp.basePath";
    }

    private static Config config;

    public static void init() {
        try {
            config = ConfigFactory.load();
            List<String> roles = getConfigLis(Key.Akka_Cluster_Roles);
            if (roles.size() != 2) {
                throw new AppConfigServerRoleNotRightException(roles);
            }
            List<String> rolesLis = new ArrayList<>();
            rolesLis.add(roles.get(0) + "-" + getIp() + "-" + config.getInt(Key.Akka_Remote_Netty_Tcp_Port)); // akka_cluster_roles 接上 -ip-host
            rolesLis.addAll(roles.subList(1, roles.size()));
            merge(Key.Akka_Cluster_Roles, rolesLis);
            if (config.getString(Key.Akka_Remote_Netty_Tcp_Hostname).trim().length() == 0) {
                merge(Key.Akka_Remote_Netty_Tcp_Hostname, getIp());
            }
        } catch (Exception e) {
            throw new AppConfigInitException("AppConfig 初始化异常！", e);
        }
    }

    public static void merge(Config merge) {
        config = merge.withFallback(config);
    }

    public static void merge(Map<String, Object> map) {
        merge(ConfigFactory.parseMap(map));
    }

    public static void merge(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        merge(map);
    }

    public static List<String> getConfigLis(String key, List<String> lisDefault) {
        try {
            return config.getStringList(key);
        } catch (Exception e) {
            return lisDefault;
        }
    }

    public static List<String> getConfigLis(String key) {
        return getConfigLis(key, new ArrayList<>());
    }

    public static String getString(String key) {
        try {
            return config.getString(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInt(String key, int defaultValue) {
        String string = getString(key);
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public static String getServerRole() {
        return getConfigLis(Key.Akka_Cluster_Roles).get(0);
    }

    public static ServerRoleEnum getServerRoleEnum() {
        return ServerRoleEnum.parse(getServerRole());
    }

    public static String getServerEnv() {
        return getConfigLis(Key.Akka_Cluster_Roles).get(1);
    }

    public static ServerEnvEnum getServerEnvEnum() {
        return ServerEnvEnum.parse(getServerEnv());
    }

    public static Config get() {
        return config;
    }

    public static String[] getRedisSentinelIpAndPorts() {
        return getString(Key.DM_Common_Config_redis_sentinelIpAndPorts).replaceAll(" ", "").split(",");
    }

    public static String getRedisSentinelIpAndPort() {
        String ipAndPort = "";
        if (getRedisSentinelIpAndPorts().length == 1) {
            ipAndPort = getRedisSentinelIpAndPorts()[0];
        }
        return ipAndPort;
    }

    public static String[] getRedisMasterNames() {
        return getString(Key.DM_Common_Config_redis_masterNames).replaceAll(" ", "").split(",");
    }

    public static String getRedisMasterName() {
        String redisMasterName = "";
        if (getRedisMasterNames().length == 1) {
            redisMasterName = getRedisMasterNames()[0];
        }
        return redisMasterName;
    }

    public static String getIp() {
        boolean useNetIpFirst = getBoolean(Key.DM_Common_Config_use_net_ip_first);
        String localIp = ParseRoute.getInstance().getLocalIPAddress();
        String netIp = ParseRoute.getInstance().getNetIp();
        if (useNetIpFirst) {
            if (!StringUtils.isEmpty(netIp)) {
                return netIp;
            }
        }
        if (!StringUtils.isEmpty(localIp)) {
            return localIp;
        }
        return "127.0.0.1";
    }

    public static void main(String[] args) {
        init();
        System.out.println(getServerRole());
        config.entrySet().forEach(entry -> {
            if (entry.getKey().startsWith("DM-")) {
                String key = entry.getKey();
                String keyGen = "public static final String " + key.replaceAll("\\.", "_").replaceAll("-", "_") + " = \"" + key + "\";";
                System.out.println(keyGen);
            }
        });
        System.out.println(Arrays.toString(getRedisSentinelIpAndPorts()));
        System.out.println(Arrays.toString(getRedisMasterNames()));
    }
}
