package drama.gameServer.features.actor.playerIO.ctrl;

import dm.relationship.base.msg.interfaces.PlayerInnerMsg;
import dm.relationship.daos.player.PlayerDao;
import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.PlayerLoginProtos;
import drama.protos.RoomProtos;
import ws.common.utils.mc.controler.Controler;


public interface PlayerIOCtrl extends Controler<Player> {
    void send(MessageHandlerProtos.Response build);

    String getRoomId();

    void quitRoom();

    void joinRoom(String roomId);

    void sendLoginResponse(Player player, PlayerLoginProtos.Sm_Login.Action action);

    void sendRoomPlayerOnOpenDubProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, PlayerInnerMsg msg);

    void sendRoomPlayerOnReadyProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int dramaId, int canReadyTime);

    void sendRoomPlayerProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int dramaId);

    boolean isInRoom();

    void sendSoloRoomPlayer(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int soloNum, int dramaId);

    void updatePlayer(String iconUrl, String birthday, String place, String name, EnumsProtos.SexEnum sex);

    PlayerDao getPlayerDao();

    /**
     * 登出时间
     *
     * @return
     */
    void setLsoutTime();

    /**
     * 登入时间
     *
     * @return
     */
    void setLsinTime();

    /**
     * 登入时间
     *
     * @return
     */
    long getLsinTime();

    /**
     * 登出时间
     *
     * @return
     */
    long getLsoutTime();

    void save();

}
