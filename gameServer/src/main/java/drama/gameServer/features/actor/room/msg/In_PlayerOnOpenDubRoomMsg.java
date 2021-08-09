package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.RoomProtos;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerOnOpenDubRoomMsg implements InnerMsg {
    private RoomPlayer roomPlayer;

    private RoomProtos.Sm_Room.Action action;
    private int soloNum;
    private int playerNum;
    private int dramaId;

    public In_PlayerOnOpenDubRoomMsg(RoomPlayer roomPlayer, RoomProtos.Sm_Room.Action action, int soloNum, int playerNum, int dramaId) {
        this.roomPlayer = roomPlayer;
        this.action = action;
        this.soloNum = soloNum;
        this.playerNum = playerNum;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public RoomProtos.Sm_Room.Action getAction() {
        return action;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getSoloNum() {
        return soloNum;
    }

    @Override
    public ResultCode getResultCode() {
        return null;
    }

    @Override
    public void addReceiver(String s) {

    }

    @Override
    public List<String> getReceivers() {
        return null;
    }
}
