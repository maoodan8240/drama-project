package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.RoomProtos;

public class In_PlayerChooseRoleRoomMsg extends _PlayerInnerMsg {
    private RoomPlayer roomPlayer;
    private RoomProtos.Cm_Room.Action action;
    private int dramaId;

    public In_PlayerChooseRoleRoomMsg(String playerId, RoomPlayer roomPlayer, RoomProtos.Cm_Room.Action action, int dramaId) {
        super(playerId);
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


}

