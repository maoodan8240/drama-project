package drama.gameServer.system.jmx;


import dm.relationship.exception.JmxMBeanManagerInitException;
import drama.gameServer.features.manager.AppDebugger;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class JmxMBeanManager {
    public static void init() {
        MBeanServer server = MBeanServerFactory.createMBeanServer("Http");
        try {
            ObjectName appDebuggerMBeanName = new ObjectName("drama.gameServer.features.manager:name=AppDebugger");
            AppDebugger appDebugger = new AppDebugger();
            server.registerMBean(appDebugger, appDebuggerMBeanName);
        } catch (Exception e) {
            throw new JmxMBeanManagerInitException("JMX MBean 创建失败！", e);
        }
    }
}