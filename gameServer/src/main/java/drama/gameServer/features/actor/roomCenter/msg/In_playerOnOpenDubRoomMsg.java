package drama.gameServer.features.actor.roomCenter.msg;

import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import drama.protos.RoomProtos;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_playerOnOpenDubRoomMsg implements InnerMsg {
    private RoomPlayer roomPlayer;

    private RoomProtos.Sm_Room.Action action;
    private int soloNum;
    private int playerNum;

    public In_playerOnOpenDubRoomMsg(RoomPlayer roomPlayer, RoomProtos.Sm_Room.Action action, int soloNum, int playerNum) {
        this.roomPlayer = roomPlayer;
        this.action = action;
        this.soloNum = soloNum;
        this.playerNum = playerNum;
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
