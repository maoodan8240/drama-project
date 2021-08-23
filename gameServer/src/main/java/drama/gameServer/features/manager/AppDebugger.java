package drama.gameServer.features.manager;

import akka.actor.ActorRef;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.table.RootTc;
import drama.gameServer.features.actor.room.msg.In_GmKillRoomMsg;
import drama.gameServer.features.manager.enums.ActionEnums;
import drama.gameServer.system.actor.DmActorSystem;

public class AppDebugger implements AppDebuggerMBean {
    @Override
    public String allManager(String actionName, String funcName, String jsonStr) {
        try {
            if (actionName.equals(ActionEnums.FRESH_TABLE_DATE.getActionName())) {
                RootTc.refresh();
            } else if (actionName.equals(ActionEnums.KILL_ROOM.getActionName())) {
                DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_GmKillRoomMsg("", Integer.valueOf(jsonStr)), ActorRef.noSender());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return funcName;
    }
}
