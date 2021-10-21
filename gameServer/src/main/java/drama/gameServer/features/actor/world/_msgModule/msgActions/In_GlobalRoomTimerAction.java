package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.room.enums.RoomState;
import drama.gameServer.features.actor.room.msg.In_AddRoomTimerCallBackMsg;
import drama.gameServer.features.actor.room.msg.In_AddRoomTimerMsg;
import drama.gameServer.features.actor.room.msg.In_RemoveRoomTimerMsg;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.cooldown.interfaces.CdList.CallbackOnExpire;

/**
 * Created by lee on 2021/10/19
 */
public class In_GlobalRoomTimerAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_GlobalRoomTimerAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof In_AddRoomTimerMsg) {
            onAddRoomTimer((In_AddRoomTimerMsg) msg, worldCtrl, worldActorContext, self, sender);
        } else if (msg instanceof In_RemoveRoomTimerMsg) {
            onRemoveRoomTimer((In_RemoveRoomTimerMsg) msg, worldCtrl, worldActorContext, self, sender);
        }
    }

    private void onRemoveRoomTimer(In_RemoveRoomTimerMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        worldCtrl.getTarget().getGlobalRoomTimer().removeTasker(msg.getRoomId(), msg.getAction());
    }

    private void onAddRoomTimer(In_AddRoomTimerMsg msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {

        worldCtrl.getTarget().getGlobalRoomTimer().addTasker(msg.getTimerName(), msg.getAction(), msg.getDate(), new CallbackOnExpire() {
            @Override
            public void run(String args) {
                String[] argsArr = args.split("_");
                String roomId = argsArr[0];
                String actionName = argsArr[1];
                LOGGER.debug("roomTimer callback args: roomId={},actionName={}", roomId, actionName);
                In_AddRoomTimerCallBackMsg response = new In_AddRoomTimerCallBackMsg(roomId, RoomState.getRoomState(actionName));
                if (worldCtrl.containsRoom(roomId)) {
                    worldCtrl.getRoomActorRef(roomId).tell(response, self);
                }
            }
        });
    }
}
