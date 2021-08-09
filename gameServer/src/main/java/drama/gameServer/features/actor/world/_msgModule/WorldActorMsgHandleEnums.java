package drama.gameServer.features.actor.world._msgModule;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleConfigNetWorkMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleConnectionStatusAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleLoginMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandlePlayerNetWorMsgkAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleRoomNetWorkMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_LoginResponseAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_NoticeToKillOverTimeCachePlayerActorAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PlayerKillRoomMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PrepareToKillPlayerActorResponseAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.TerminatedAction;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;

public enum WorldActorMsgHandleEnums {

    //    _In_LoginAction(new In_LoginAction()),//
//    _In_PlayerHeartBeatingAction(new In_PlayerHeartBeatingAction()),//
//    _In_PlayerOfflineRequestAction(new In_PlayerOfflineRequestAction()),//
    _In_LoginMsg(new HandleLoginMsgAction()),//
    _In_PlayerNetWorkMsgAction(new HandlePlayerNetWorMsgkAction()),//
    _In_ConfigNetWorkMsgAction(new HandleConfigNetWorkMsgAction()),//
    _In_ConnectionStatusAction(new HandleConnectionStatusAction()),//
    _In_RoomNetWorkMsgAction(new HandleRoomNetWorkMsgAction()),//
    _In_PlayerKillRoomMsg(new In_PlayerKillRoomMsgAction()),//
    _In_LoginResponseAction(new In_LoginResponseAction()),//
    _In_NoticeToKillOverTimeCachePlayerActorAction(new In_NoticeToKillOverTimeCachePlayerActorAction()),//
    _In_PrepareToKillPlayerActorResponse(new In_PrepareToKillPlayerActorResponseAction()),//


    _TerminatedAction(new TerminatedAction()), //
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
