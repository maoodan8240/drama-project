package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerVoteSearchRoomMsg extends _PlayerInnerMsg {
    private String typeName;

    public In_PlayerVoteSearchRoomMsg(int dramaId, String playerId, String typeName) {
        super(playerId, dramaId);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }


}
