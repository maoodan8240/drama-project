package drama.loginServer.system.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import ws.common.utils.general.ParseRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfig {
    public static class Key {
        public static final String Akka_Remote_Netty_Tcp_Hostname = "akka.remote.netty.tcp.hostname";
        public static final String Akka_Remote_Netty_Tcp_Port = "akka.remote.netty.tcp.port";
        public static final String Akka_Cluster_Roles = "akka.drama.cluster.roles";
        public static final String Akka_Cluster_Join_Point = "akka.drama.cluster.join-point";

        public static final String DM_Common_Config_use_net_ip_first = "DM-Common-Config.use-net-ip-first";
        public static final String DM_LOGIN_SERVER_HTTP_SERVER_PORT="DM-Login-Server.http-server.port";
        public static final String DM_LOGIN_SERVER_HTTP_SERVER_TIMEOUT= "DM-Login-Server.http-server.timeout";
    }

    private static Config config;

    public static void init() {
        try {
            config = ConfigFactory.load();
            List<String> roles = getConfigLis(Key.Akka_Cluster_Roles);
            if (roles.size() != 2) {
                System.out.println("roles.size != 2");
            }
            List<String> rolesLis = new ArrayList<>();
            rolesLis.add(roles.get(0) + "-" + getIp() + "-" + config.getInt(Key.Akka_Remote_Netty_Tcp_Port)); // akka_cluster_roles 接上 -ip-host
            rolesLis.addAll(roles.subList(1, roles.size()));
            merge(Key.Akka_Cluster_Roles, rolesLis);
            if (config.getString(Key.Akka_Remote_Netty_Tcp_Hostname).trim().length() == 0) {
                merge(Key.Akka_Remote_Netty_Tcp_Hostname, getIp());
            }
        } catch (Exception e) {
            System.out.println("AppConfig 初始化异常！" + e);
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


    public static String getServerEnv() {
        return getConfigLis(Key.Akka_Cluster_Roles).get(1);
    }


    public static Config get() {
        return config;
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
        // System.out.println(getServerRole());
        // config.entrySet().forEach(entry -> {
        // if (entry.getKey().startsWith("WS-")) {
        // String key = entry.getKey();
        // String keyGen = "public static final String " + key.replaceAll("\\.", "_").replaceAll("-", "_") + " = \"" + key + "\";";
        // System.out.println(keyGen);
        // }
        // });
        System.out.println(getServerRole());
        System.out.println(getServerEnv());
    }
}
