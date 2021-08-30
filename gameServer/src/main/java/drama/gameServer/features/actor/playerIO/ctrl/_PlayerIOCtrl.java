package drama.gameServer.features.actor.playerIO.ctrl;

import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.base.msg.interfaces.PlayerInnerMsg;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.player.PlayerDao;
import dm.relationship.topLevelPojos.player.Player;
import dm.relationship.topLevelPojos.player.PlayerBase;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.room.msg.In_PlayerOnOpenDubRoomMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.actor.room.utils.RoomProtoUtils;
import drama.protos.CodesProtos;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.PlayerLoginProtos;
import drama.protos.RoomProtos;
import org.apache.commons.lang3.StringUtils;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;

public class _PlayerIOCtrl extends AbstractControler<Player> implements PlayerIOCtrl {
    private static final PlayerDao PLAYER_DAO = DaoContainer.getDao(Player.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);

    static {
        PLAYER_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    private long oldestLoginTime;


    public PlayerDao getPlayerDao() {
        return PLAYER_DAO;
    }

    @Override
    public void setLsoutTime() {
        target.getOther().setLsoutTime(System.currentTimeMillis());
    }

    @Override
    public void setLsnTime() {
        if (oldestLoginTime <= 0) {
            oldestLoginTime = System.currentTimeMillis();
        }
        target.getOther().setLsinTime(oldestLoginTime);//只要玩家没有从内存中移除，登录时间保持不变
        target.getOther().setLsoutTime(MagicNumbers.DEFAULT_NEGATIVE_ONE); // 离线时间设置成-1,表示在线
        getPlayerDao().insertIfExistThenReplace(target);

    }

    @Override
    public void save() {
        getPlayerDao().insertIfExistThenReplace(target);
    }

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


    public void sendRoomPlayerOnOpenDubProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, PlayerInnerMsg msg) {
        In_PlayerOnOpenDubRoomMsg message = (In_PlayerOnOpenDubRoomMsg) msg;
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder bRoom = RoomProtos.Sm_Room.newBuilder();
        bRoom.setAction(action);
        bRoom.setRoomPlayerNum(message.getPlayerNum());
        RoomProtos.Sm_Room_Player sm_room_player = RoomProtoUtils.createSmRoomPlayer(roomPlayer, message.getDramaId());
        bRoom.setRoomPlayer(sm_room_player);
        response.setSmRoom(bRoom.build());
        send(response.build());
    }

    @Override
    public void sendRoomPlayerOnReadyProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int dramaId, int canReadyTime) {
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder bRoom = RoomProtos.Sm_Room.newBuilder();
        bRoom.setAction(action);
        RoomProtos.Sm_Room_Player sm_room_player = RoomProtoUtils.createSmRoomPlayer(roomPlayer, dramaId);
        bRoom.setRoomPlayer(sm_room_player);
        bRoom.setCanReadyTime(canReadyTime);
        response.setSmRoom(bRoom.build());
        send(response.build());
    }

    @Override
    public void sendRoomPlayerProtos(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int dramaId) {
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder bRoom = RoomProtos.Sm_Room.newBuilder();
        bRoom.setAction(action);
        RoomProtos.Sm_Room_Player sm_room_player = RoomProtoUtils.createSmRoomPlayer(roomPlayer, dramaId);
        bRoom.setRoomPlayer(sm_room_player);
        response.setSmRoom(bRoom.build());
        send(response.build());
    }

    @Override
    public boolean isInRoom() {
        return StringUtils.isNotEmpty(target.getRoomId());
    }

    @Override
    public void sendSoloRoomPlayer(RoomProtos.Sm_Room.Action action, RoomPlayer roomPlayer, int soloNum) {
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder bRoom = RoomProtos.Sm_Room.newBuilder();
        bRoom.setAction(action);
        int soloDramaId = roomPlayer.getSoloAnswer().get(soloNum);
        RoomProtos.Sm_Room_Player sm_room_player = RoomProtoUtils.createSoloSmRoomPlayer(roomPlayer, soloDramaId);
        bRoom.setRoomPlayer(sm_room_player);
        response.setSmRoom(bRoom.build());
        send(response.build());
    }

    @Override
    public void updatePlayer(String iconUrl, String birthday, String place, String name, EnumsProtos.SexEnum sex) {
        PlayerBase base = target.getBase();
        base.setIcon(iconUrl);
        base.setBirthday(birthday);
        base.setPlace(place);
        base.setName(name);
        base.setSex(sex);
    }

}
