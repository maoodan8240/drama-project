package drama.gameServer.features.actor.world._msgModule;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleConfigNetWorkMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandlePlayerNetWorMsgkAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleRoomNetWorkMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_LoginAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerDisconnectedAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerHeartBeatingAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerKillRoomMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerOfflineRequestAction;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;

public enum WorldActorMsgHandleEnums {

    _In_LoginAction(new In_LoginAction()),//
    _In_PlayerHeartBeatingAction(new In_PlayerHeartBeatingAction()),//
    _In_PlayerDisconnectedAction(new In_PlayerDisconnectedAction()),//
    _In_PlayerOfflineRequestAction(new In_PlayerOfflineRequestAction()),//
    _In_PlayerNetWorkMsgAction(new HandlePlayerNetWorMsgkAction()),//
    _In_ConfigNetWorkMsgAction(new HandleConfigNetWorkMsgAction()),//
    _In_RoomNetWorkMsgAction(new HandleRoomNetWorkMsgAction()),//
    _In_PlayerKillRoomMsg(new In_PlayerKillRoomMsgAction()),//
//    _In_LoginMsg(new HandleLoginMsgAction()),

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
