package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerHasSelectDraftRoomMsg extends _PlayerInnerMsg {
    private int draftId;

    public In_PlayerHasSelectDraftRoomMsg(String playerId, int draftId) {
        super(playerId);
        this.draftId = draftId;
    }

    public int getDraftId() {
        return draftId;
    }
}
