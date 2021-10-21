package drama.gameServer.features.actor.world._msgModule;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleConfigMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleConnectionStatusAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleLoginMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandlePlayerMsgkAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.HandleRoomMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_GlobalRoomTimerAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_GmSupportAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_KillRoomMsgAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_LoginResponseAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_NoticeToKillOverTimeCachePlayerActorAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.In_PrepareToKillPlayerActorResponseAction;
import drama.gameServer.features.actor.world._msgModule.msgActions.TerminatedAction;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;

public enum WorldActorMsgHandleEnums {

    _In_LoginMsg(new HandleLoginMsgAction()),//
    _In_LoginResponseAction(new In_LoginResponseAction()),//

    _In_PlayerMsgAction(new HandlePlayerMsgkAction()),//
    _In_RoomMsgAction(new HandleRoomMsgAction()),//

    _In_ConfigNetWorkMsgAction(new HandleConfigMsgAction()),//
    _In_ConnectionStatusAction(new HandleConnectionStatusAction()),//
    _In_PlayerKillRoomMsg(new In_KillRoomMsgAction()),//
    _In_NoticeToKillOverTimeCachePlayerActorAction(new In_NoticeToKillOverTimeCachePlayerActorAction()),//
    _In_PrepareToKillPlayerActorResponse(new In_PrepareToKillPlayerActorResponseAction()),//
    _In_GmSupport(new In_GmSupportAction()),//
    _In_GlobalRoomTimer(new In_GlobalRoomTimerAction()),//

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
