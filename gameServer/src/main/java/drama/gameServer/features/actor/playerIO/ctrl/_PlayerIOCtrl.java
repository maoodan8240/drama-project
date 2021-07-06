package drama.gameServer.features.actor.playerIO.ctrl;

import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.playerIO.utils.RoomProtoUtils;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import drama.protos.CodesProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.PlayerLoginProtos;
import drama.protos.RoomProtos;
import org.apache.commons.lang3.StringUtils;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;

public class _PlayerIOCtrl extends AbstractControler<Player> implements PlayerIOCtrl {


    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    @Override
    public void send(MessageHandlerProtos.Response build) {
        getTarget().getConnection().send(new MessageSendHolder(build, build.getSmMsgAction(), new ArrayList<>()));
    }

    @Override
    public String getRoomId() {
        return target.getRoomId();
    }

    @Override
    public void quitRoom() {
        target.setRoomId("");
    }

    @Override
    public void joinRoom(String roomId) {
        target.setRoomId(roomId);
    }


    public void sendLoginResponse(Player player, PlayerLoginProtos.Sm_Login.Action action) {
        MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Login, action);
        PlayerLoginProtos.Sm_Login.Builder b = PlayerLoginProtos.Sm_Login.newBuilder();
        b.setAction(action);
        if (action.getNumber() == PlayerLoginProtos.Sm_Login.Action.RESP_LOGIN_VALUE) {
            b.setMobileNum(player.getMobileNum());
            b.setPlayerName(player.getBase().getName());
        }
        b.setRpid(player.getPlayerId());
        br.setResult(true);
        br.setSmLogin(b.build());
        send(br.build());
    }


    public void sendRoomPlayerProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer) {
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder bRoom = RoomProtos.Sm_Room.newBuilder();
        bRoom.setAction(action);
        RoomProtos.Sm_Room_Player sm_room_player = RoomProtoUtils.createSmRoomPlayer(roomPlayer);
        bRoom.setRoomPlayer(sm_room_player);
        response.setSmRoom(bRoom.build());
        send(response.build());
    }

    @Override
    public boolean isInRoom() {
        return !StringUtils.isEmpty(target.getRoomId());
    }
}
