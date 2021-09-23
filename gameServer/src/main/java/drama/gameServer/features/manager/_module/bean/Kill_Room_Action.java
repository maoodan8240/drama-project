package drama.gameServer.features.manager._module.bean;

import akka.actor.ActorRef;
import com.alibaba.fastjson.JSON;
import dm.relationship.base.cluster.ActorSystemPath;
import drama.gameServer.features.actor.room.msg.In_GmKillRoomMsg;
import drama.gameServer.features.manager.Action;
import drama.gameServer.system.actor.DmActorSystem;

/**
 * Created by lee on 2021/9/8
 */
public class Kill_Room_Action implements Action {
    @Override
    public String handle(String funcName, String args) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_GmKillRoomMsg(Integer.valueOf(args)), ActorRef.noSender());
        return JSON.toJSONString("true");
    }
}
