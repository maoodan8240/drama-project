package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.List;

public class In_PlayerCanSelectDraftRoomMsg extends _PlayerInnerMsg {
    private List<Integer> draftIds;
    private int dramaId;

    public In_PlayerCanSelectDraftRoomMsg(String playerId, List<Integer> draftIds, int dramaId) {
        super(playerId);
        this.draftIds = draftIds;
        this.dramaId = dramaId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public List<Integer> getDraftIds() {
        return draftIds;
    }

   
}
