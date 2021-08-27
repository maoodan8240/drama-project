package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerVoteRoomMsg extends _PlayerInnerMsg {
    private int murderRoleId;

    public In_PlayerVoteRoomMsg(String playerId, int murderRoleId) {
        super(playerId);
        this.murderRoleId = murderRoleId;
    }

    public int getMurderRoleId() {
        return murderRoleId;
    }
}
