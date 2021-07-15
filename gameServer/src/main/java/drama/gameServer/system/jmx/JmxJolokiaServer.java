package drama.gameServer.system.jmx;

import dm.relationship.exception.JmxJolokiaServerStartException;
import drama.gameServer.system.config.AppConfig;
import org.jolokia.jvmagent.JolokiaServer;
import org.jolokia.jvmagent.JvmAgentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JmxJolokiaServer {
    public static final String JOLOKIA_AGENT_URL = "jolokia.agent";
    private static final String JOLOKIA_ACCESS_FILE_PATH = "/jolokia-access.xml";
    private static final Logger logger = LoggerFactory.getLogger(JmxJolokiaServer.class);
    private static JolokiaServer server;

    /**
     * 启动JolokiaServer
     */
    public static void start() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("host", AppConfig.getString(AppConfig.Key.DM_Common_Config_jmx_conf_jmx_server_host));
        map.put("port", "" + AppConfig.getInt(AppConfig.Key.DM_Common_Config_jmx_conf_jmx_server_port));
        map.put("agentContext", "/jmx");
        map.put("policyLocation", JmxJolokiaServer.class.getResource(JOLOKIA_ACCESS_FILE_PATH).toString());
        start(new JvmAgentConfig(map), true);
    }

    public static void start(String agentArgs) {
        start(new JvmAgentConfig(agentArgs), true);
    }

    public static void start(JvmAgentConfig pConfig, boolean pLazy) {
        try {
            server = new JolokiaServer(pConfig, pLazy);
            server.start();
            setStateMarker();
            logger.debug("Jolokia: Agent started with URL={} ", server.getUrl());
        } catch (Exception e) {
            throw new JmxJolokiaServerStartException("JolokiaServer start 异常！", e);
        }
    }

    public static void stop() {
        server.stop();
        clearStateMarker();
    }

    private static void setStateMarker() {
        String url = server.getUrl();
        System.setProperty(JOLOKIA_AGENT_URL, url);
    }

    private static void clearStateMarker() {
        System.clearProperty(JOLOKIA_AGENT_URL);
    }
}
