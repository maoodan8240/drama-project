package dm.relationship.utils;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import akka.actor.Address;
import akka.cluster.Cluster;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.AkkaAddressContext;

import java.util.List;
import java.util.Set;

public class ClusterMessageSender {

    /**
     * 根据ServerRole向server发送信息
     *
     * @param actorContext     akka 上下文
     * @param serverRoleTarget 信息发送的serverRole
     * @param msg
     * @param path             发送路径
     * @return
     */
    public static int sendMsgToServer(ActorContext actorContext, ServerRoleEnum serverRoleTarget, Object msg, String path) {
        Cluster cluster = Cluster.get(actorContext.system());
        Set<String> roles = cluster.state().getAllRoles();
        int num = 0;
        for (String roleName : roles) {
            ServerRoleEnum serverRole = ServerRoleEnum.parse(roleName);
            if (serverRole == serverRoleTarget) {
                Address address = cluster.state().getRoleLeader(roleName);
                if (address != null) {
                    ActorSelection actorSelection = actorContext.actorSelection(address.toString() + path);
                    actorSelection.tell(msg, actorContext.self());
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * @param actorContext
     * @param serverRoleAndAddressList
     * @param msg
     * @param path
     * @return
     */
    public static int sendMsgToServer(ActorContext actorContext, List<AkkaAddressContext> serverRoleAndAddressList, Object msg, String path) {
        if (serverRoleAndAddressList != null) {
            for (AkkaAddressContext akkaServerRoleAndAddress : serverRoleAndAddressList) {
                Address address = akkaServerRoleAndAddress.getAddress();
                ActorSelection actorSelection = actorContext.actorSelection(address.toString() + path);
                actorSelection.tell(msg, actorContext.self());
            }
        }
        return serverRoleAndAddressList.size();
    }

    public static void sendMsgToServer(ActorContext actorContext, Address address, Object msg, String path) {
        ActorSelection actorSelection = actorContext.actorSelection(address.toString() + path);
        actorSelection.tell(msg, actorContext.self());
    }

    public static void sendMsgToPath(ActorContext actorContext, Object msg, String path) {
        ActorSelection actorSelection = actorContext.actorSelection(path);
        actorSelection.tell(msg, actorContext.self());
    }
}
