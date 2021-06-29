package dm.relationship.utils;

import akka.actor.ActorContext;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_LogMsgListToLogServer;
import dm.relationship.base.msg.In_LogMsgToLogServer;
import dm.relationship.logServer.base.DmLog;

import java.util.List;

/**
 * Created by lee on 17-5-12.
 */
public class LogServerUtils {

    public static void sendLog(ActorContext actorContext, DmLog dmLog) {
        In_LogMsgToLogServer addLog = new In_LogMsgToLogServer(dmLog);
        ClusterMessageSender.sendMsgToServer(actorContext, ServerRoleEnum.DM_LogServer, addLog, ActorSystemPath.DM_LogServer_Selection_SaveLogsManager);
    }

    public static void sendLogLis(ActorContext actorContext, List<DmLog> dmLogLis) {
        In_LogMsgListToLogServer addLogLis = new In_LogMsgListToLogServer(dmLogLis);
        ClusterMessageSender.sendMsgToServer(actorContext, ServerRoleEnum.DM_LogServer, addLogLis, ActorSystemPath.DM_LogServer_Selection_SaveLogsManager);
    }
}
