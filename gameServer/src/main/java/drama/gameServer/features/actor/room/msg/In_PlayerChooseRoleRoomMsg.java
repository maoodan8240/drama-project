package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.RoomProtos;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;

public class In_PlayerChooseRoleRoomMsg implements InnerMsg {
    private RoomPlayer roomPlayer;
    private RoomProtos.Cm_Room.Action action;
    private int dramaId;

    public In_PlayerChooseRoleRoomMsg(RoomPlayer roomPlayer, RoomProtos.Cm_Room.Action action, int dramaId) {
        this.roomPlayer = roomPlayer;
        this.action = action;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public RoomProtos.Cm_Room.Action getAction() {
        return action;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
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

