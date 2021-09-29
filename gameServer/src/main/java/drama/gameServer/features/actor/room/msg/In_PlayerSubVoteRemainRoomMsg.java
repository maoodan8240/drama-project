package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

/**
 * Created by lee on 2021/9/28
 */
public class In_PlayerSubVoteRemainRoomMsg extends _PlayerInnerMsg {
    private int remainNum;

    public In_PlayerSubVoteRemainRoomMsg(int dramaId, String playerid, int remainNum) {
        super(playerid, dramaId);
        this.remainNum = remainNum;
    }

    public int getRemainNum() {
        return remainNum;
    }
}
