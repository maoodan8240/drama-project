package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

/**
 * Created by lee on 2021/10/14
 */
public class In_PlayerSubVoteRoomMsg extends _PlayerInnerMsg {
    private int murderRoleId;

    public In_PlayerSubVoteRoomMsg(int dramaId, String playerId, int murderRoleId) {
        super(playerId, dramaId);
        this.murderRoleId = murderRoleId;
    }

    public int getMurderRoleId() {
        return murderRoleId;
    }
}
