package dm.relationship.utils;

import akka.actor.ActorContext;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.chat.AddChatMsg;
import dm.relationship.chatServer.GroupChatMsg;

/**
 * Created by lee on 17-5-12.
 */
public class BroadcastSystemMsgUtils {

    public static void broad(ActorContext actorContext, int innerRealmId, GroupChatMsg groupChatMsg) {
        AddChatMsg.Request addChatMsg = new AddChatMsg.Request(innerRealmId, groupChatMsg);
        ClusterMessageSender.sendMsgToServer(actorContext, ServerRoleEnum.DM_ChatServer, addChatMsg, ActorSystemPath.DM_ChatServer_Selection_ChatManager);
    }
}
