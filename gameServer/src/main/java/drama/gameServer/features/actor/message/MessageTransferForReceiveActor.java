package drama.gameServer.features.actor.message;

import akka.actor.ActorContext;
import dm.relationship.appServers.loginServer.player.msg.In_LoginMsg;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_MessageReceiveHolder;
import dm.relationship.base.msg.implement._ConfigNetWorkMsg;
import dm.relationship.base.msg.implement._PlayerNetWorkMsg;
import dm.relationship.base.msg.implement._RoomNetWorkMsg;
import dm.relationship.base.msg.interfaces.ConfigNetWorkMsg;
import dm.relationship.base.msg.interfaces.PlayerNetWorkMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import drama.gameServer.system.actor.DmActorSystem;
import drama.gameServer.system.network.In_ConnectionStatusRequest;
import drama.protos.CommonProtos;
import drama.protos.PlayerLoginProtos.Cm_Login;
import drama.protos.RoomProtos.Cm_Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageTransferForReceiveActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageTransferForReceiveActor.class);
    private final ActorContext messageTransferContext;

    public MessageTransferForReceiveActor(ActorContext messageTransferContext) {
        this.messageTransferContext = messageTransferContext;
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof In_MessageReceiveHolder) {
            In_MessageReceiveHolder receiveHolder = (In_MessageReceiveHolder) msg;
            on_In_MessageReceiveHolder(receiveHolder);
            receiveHolder.clear();
            receiveHolder = null;
        } else if (msg instanceof In_ConnectionStatusRequest) {
            on_In_ConnectionStatusRequest((In_ConnectionStatusRequest) msg);
        }
    }

    private void on_In_ConnectionStatusRequest(In_ConnectionStatusRequest request) {
//        Object msg = null;
//        if (request.getType() == In_ConnectionStatusRequest.Type.HeartBeating) {
//            msg = new In_PlayerHeartBeatingRequest(request.getConnection());
//        } else if (request.getType() == In_ConnectionStatusRequest.Type.Offline) {
//            msg = new In_PlayerOfflineRequest(request.getConnection());
//        } else if (request.getType() == In_ConnectionStatusRequest.Type.Disconnected) {
//            msg = new In_PlayerDisconnectedRequest(request.getConnection());
//        } else {
//            return;
//        }
//        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, messageTransferContext.self());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(request, messageTransferContext.self());
    }


    private void on_In_MessageReceiveHolder(In_MessageReceiveHolder receiveHolder) {
        if (receiveHolder.getMessage() instanceof Cm_Login) {
            In_LoginMsg msg = new In_LoginMsg(receiveHolder.getMessage(), receiveHolder.getConnection());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, sender());
        } else if (receiveHolder.getMessage() instanceof Cm_Room) {
            RoomNetWorkMsg msg = new _RoomNetWorkMsg(receiveHolder.getConnection(), receiveHolder.getMessage());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, sender());
        } else if (receiveHolder.getMessage() instanceof CommonProtos.Cm_Common_Config) {
            ConfigNetWorkMsg msg = new _ConfigNetWorkMsg(receiveHolder.getConnection(), receiveHolder.getMessage());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, sender());
        } else { //转换成InnerMessage转发给worldActor
            PlayerNetWorkMsg playerNetWorkMsg = new _PlayerNetWorkMsg(receiveHolder.getConnection(), receiveHolder.getMessage());
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(playerNetWorkMsg, sender());
        }
    }
}
