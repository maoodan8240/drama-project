package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerVoteRoomMsg extends _PlayerInnerMsg {
    private int voteNum;

    public In_PlayerVoteRoomMsg(String playerId, int voteNum) {
        super(playerId);
        this.voteNum = voteNum;
    }

    public int getVoteNum() {
        return voteNum;
    }

   
}
