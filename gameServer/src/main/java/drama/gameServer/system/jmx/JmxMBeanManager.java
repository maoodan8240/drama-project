package drama.gameServer.system.jmx;


import dm.relationship.exception.JmxMBeanManagerInitException;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class JmxMBeanManager {
    public static void init() {
        MBeanServer server = MBeanServerFactory.createMBeanServer("Http");
        try {
            ObjectName appDebugger = new ObjectName("PiecesFunctionServerRuntimeInfo:name=AppDebugger");
            server.createMBean("qhGame.mProject.piecesFunctionServer.features.manager.AppDebugger", appDebugger);
        } catch (Exception e) {
            throw new JmxMBeanManagerInitException("JMX MBean 创建失败！", e);
        }
    }
}