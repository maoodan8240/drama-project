package dm.relationship.utils;

import akka.actor.ActorContext;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.cluster.Cluster;
import akka.cluster.Member;
import akka.cluster.metrics.ClusterMetricsChanged;
import akka.cluster.metrics.NodeMetrics;
import akka.cluster.metrics.StandardMetrics;
import akka.cluster.metrics.StandardMetrics.Cpu;
import akka.cluster.metrics.StandardMetrics.HeapMemory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.JavaConversions;
import dm.relationship.base.ServerEnvEnum;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.AkkaAddressContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClusterUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterUtils.class);

    /**
     * 根据roleName获取集群中的Address
     *
     * @param actorContext
     * @param roleName     格式为： WS-GameServer-42.62.90.28-21000
     * @return
     */
    public static Address getServerAddress(ActorContext actorContext, String roleName) {
        if (StringUtils.isBlank(roleName)) {
            return null;
        }
        return getAddressContext(actorContext, roleName).getAddress();
    }

    /**
     * 获取Akka Address
     *
     * @param actorContext
     * @param serverRoleTarget
     * @return
     */
    public static List<AkkaAddressContext> getAddressContextLisIgnoreEnv(ActorContext actorContext, ServerRoleEnum serverRoleTarget) {
        return getAddressContextLis(actorContext, serverRoleTarget, null, false);
    }

    /**
     * 获取Akka Address
     *
     * @param actorContext
     * @param serverRoleTarget
     * @param serverEnvTarget
     * @return
     */
    public static List<AkkaAddressContext> getAddressContextLisUseEnv(ActorContext actorContext, ServerRoleEnum serverRoleTarget, ServerEnvEnum serverEnvTarget) {
        return getAddressContextLis(actorContext, serverRoleTarget, serverEnvTarget, true);
    }

    private static List<AkkaAddressContext> getAddressContextLis(ActorContext actorContext, ServerRoleEnum serverRoleTarget, ServerEnvEnum serverEnvTarget, boolean useEnv) {
        List<AkkaAddressContext> serverRoleAndAddressList = new ArrayList<>();
        Cluster cluster = Cluster.get(actorContext.system());
        for (Member member : cluster.state().getMembers()) {
            List<String> roles = new ArrayList<>(member.getRoles());
            ServerRoleEnum serverRole = ServerRoleEnum.parse(roles.get(0));
            ServerEnvEnum serverEnv = ServerEnvEnum.parse(roles.get(1));
            boolean add = false;
            if (serverRoleTarget == serverRole && member.address() != null) {
                add = !useEnv || (serverEnv == serverEnvTarget);
            }
            if (add) {
                serverRoleAndAddressList.add(new AkkaAddressContext(roles.get(0), member.address(), serverEnvTarget));
            }
        }
        return serverRoleAndAddressList;
    }

    /**
     * 获取Akka Address
     *
     * @param actorContext
     * @param roleNameTarget 格式为： YX-GameServer-42.62.90.28-21000
     * @return
     */
    public static AkkaAddressContext getAddressContext(ActorContext actorContext, String roleNameTarget) {
        Cluster cluster = Cluster.get(actorContext.system());
        for (Member member : cluster.state().getMembers()) {
            List<String> roles = new ArrayList<>(member.getRoles());
            if (roles.get(0).equals(roleNameTarget)) {
                if (member.address() != null) {
                    return new AkkaAddressContext(roleNameTarget, member.address(), null);
                }
            }
        }
        return AkkaAddressContext.NULL;
    }


    public static void displayAllMemberInfo(ActorContext context) {
        ActorSystem actorSystem = context.system();
        StringBuffer sb = new StringBuffer();
        Cluster cluster = Cluster.get(actorSystem);

        int size = cluster.state().members().size();
        sb.append("\n\n------------------------------------------------------------" +
                "集群成员数量:" + size +
                "------------------------------------------------------------\n");
        sb.append("self         :").append(cluster.readView().self()).append("\n");
        sb.append("leader       :").append(cluster.readView().leader()).append("\n");
        sb.append("seenBy       :").append(appendMemberSet(cluster.readView().seenBy())).append("\n");
        sb.append("unreachable  :").append(appendMemberSet(cluster.readView().unreachableMembers())).append("\n");
        cluster.state().getMembers().forEach(member -> {
            String roles = StringUtils.rightPad(member.getRoles().toString(), 60, "");
            String address = StringUtils.rightPad(member.address().toString(), 45, "");
            String status = StringUtils.rightPad(member.status().toString(), 15, "");
            sb.append("address=").append(address).append("  |  ").append("status=").append(status).append("  |  ").append("roles=").append(roles).append("\n");
        });
        sb.append("--------------------------------------------------------------------" +
                "--------------------------------------------------------------------\n\n");
        LOGGER.info(sb.toString());

    }

    public static void displaySystemHealthInfo(ClusterMetricsChanged metricsChanged) {
        int size = metricsChanged.nodeMetrics().size();
        String msg = "";
        for (NodeMetrics metrics : metricsChanged.getNodeMetrics()) {
            msg += String.format("    成员地址=%s", metrics.address());
            HeapMemory heapMemory = StandardMetrics.extractHeapMemory(metrics);
            if (heapMemory != null) {
                msg += String.format(" | 内存使用(%s)MB. ", heapMemory.used() / 1024 / 1024);
            }
            Cpu cpu = StandardMetrics.extractCpu(metrics);
            if (cpu != null) {
                msg += String.format(" | CPU负载(%s) 个数%s. ", cpu.systemLoadAverage().get(), cpu.processors());
            }
            msg += "\n";
        }
        LOGGER.info("\n集群成员健康报告: 数量={}\n{}", size, msg);
    }

    public static String appendMemberSet(scala.collection.immutable.Set<?> set) {
        Set<?> javaSet = JavaConversions.setAsJavaSet(set);
        return appendMemberSet(javaSet);
    }

    public static String appendMemberSet(Set<?> javaSet) {
        StringBuffer sb = new StringBuffer();
        sb.append("\n").append("  {\n");
        javaSet.forEach(e -> {
            sb.append("    ").append(e.toString()).append("\n");
        });
        sb.append("  }");
        return sb.toString();
    }
}
