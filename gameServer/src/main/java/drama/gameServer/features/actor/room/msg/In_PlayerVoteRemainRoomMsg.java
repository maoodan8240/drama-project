package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerVoteRemainRoomMsg extends _PlayerInnerMsg {
    private int remainNum;

    public In_PlayerVoteRemainRoomMsg(String playerId, int remainNum) {
        super(playerId);
        this.remainNum = remainNum;
    }

    public int getRemainNum() {
        return remainNum;
    }
}
