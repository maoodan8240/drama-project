package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import drama.protos.RoomProtos;

import java.util.List;

public class In_PlayerSyncClueRoomMsg extends _PlayerInnerMsg {
    private List<Integer> clueIds;
    private RoomProtos.Sm_Room.Action action;
    private int dramaId;

    public In_PlayerSyncClueRoomMsg(String playerId, List<Integer> clueIds, RoomProtos.Sm_Room.Action action, int dramaId) {
        super(playerId);
        this.clueIds = clueIds;
        this.action = action;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public RoomProtos.Sm_Room.Action getAction() {
        return action;
    }

    public List<Integer> getClueIds() {
        return clueIds;
    }


}
