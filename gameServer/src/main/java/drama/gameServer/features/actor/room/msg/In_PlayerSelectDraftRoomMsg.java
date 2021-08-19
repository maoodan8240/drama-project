package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerSelectDraftRoomMsg extends _PlayerInnerMsg {
    public In_PlayerSelectDraftRoomMsg(String playerId, int draftId) {
        super(playerId);
    }

  
}
