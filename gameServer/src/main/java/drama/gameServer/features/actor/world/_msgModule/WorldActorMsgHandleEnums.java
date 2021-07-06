package drama.gameServer.features.actor.world._msgModule;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_ConfigNetWorkMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_LoginAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerDisconnectedAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerHeartBeatingAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerKillRoomMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerNetWorMsgkAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_RoomNetWorkMsgAction;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;

public enum WorldActorMsgHandleEnums {

    _In_LoginAction(new In_LoginAction()),//
    _In_PlayerHeartBeatingAction(new In_PlayerHeartBeatingAction()),//
    _In_PlayerDisconnectedAction(new In_PlayerDisconnectedAction()),//
    _In_PlayerNetWorkMsgAction(new In_PlayerNetWorMsgkAction()),//
    _In_ConfigNetWorkMsgAction(new In_ConfigNetWorkMsgAction()),//

    _In_RoomNetWorkMsgAction(new In_RoomNetWorkMsgAction()),//
    _In_PlayerKillRoomMsg(new In_PlayerKillRoomMsgAction()),//

    NULL(null);

    private Action action;

    WorldActorMsgHandleEnums(Action action) {
        this.action = action;
    }

    public static void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        for (WorldActorMsgHandleEnums msgEnum : values()) {
            if (msgEnum == NULL || msgEnum == null) {
                continue;
            }
            msgEnum.action.onRecv(msg, worldCtrl, worldActorContext, self, sender);
        }
    }
}
