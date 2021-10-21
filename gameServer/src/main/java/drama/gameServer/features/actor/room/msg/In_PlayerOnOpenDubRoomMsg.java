package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.room.RoomProtos;

public class In_PlayerOnOpenDubRoomMsg extends _PlayerInnerMsg {
    private RoomPlayer roomPlayer;

    private RoomProtos.Sm_Room.Action action;
    private int soloNum;
    private int playerNum;
    private int dramaId;

    public In_PlayerOnOpenDubRoomMsg(String playerId, RoomPlayer roomPlayer, RoomProtos.Sm_Room.Action action, int soloNum, int playerNum, int dramaId) {
        super(playerId);
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


}
