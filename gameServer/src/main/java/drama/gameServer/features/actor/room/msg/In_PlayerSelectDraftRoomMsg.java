package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerSelectDraftRoomMsg extends _PlayerInnerMsg {
    private int draftId;
    private int roleId;

    public In_PlayerSelectDraftRoomMsg(String playerId, int draftId, int roleId) {
        super(playerId);
        this.draftId = draftId;
        this.roleId = roleId;
    }


    public int getDraftId() {
        return draftId;
    }

    public int getRoleId() {
        return roleId;
    }
}

