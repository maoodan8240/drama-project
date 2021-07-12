package drama.gameServer.features.actor.playerIO.ctrl;

import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import drama.protos.MessageHandlerProtos;
import drama.protos.PlayerLoginProtos;
import drama.protos.RoomProtos;
import ws.common.utils.mc.controler.Controler;
import ws.common.utils.message.interfaces.InnerMsg;

public interface PlayerIOCtrl extends Controler<Player> {
    void send(MessageHandlerProtos.Response build);

    String getRoomId();

    void quitRoom();

    void joinRoom(String roomId);

    void sendLoginResponse(Player player, PlayerLoginProtos.Sm_Login.Action action);

    void sendRoomPlayerProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, InnerMsg msg);

    void sendRoomPlayerProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer);

    boolean isInRoom();

    void sendSoloRoomPlayer(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int soloNum);
}
