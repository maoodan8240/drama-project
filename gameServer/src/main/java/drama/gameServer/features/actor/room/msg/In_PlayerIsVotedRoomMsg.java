package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerIsVotedRoomMsg extends _PlayerInnerMsg {
    private int murderRoleId;
    private boolean isVoted;

    public In_PlayerIsVotedRoomMsg(String playerId, int murderRoleId, boolean isVoted) {
        super(playerId);
        this.murderRoleId = murderRoleId;
        this.isVoted = isVoted;
    }

    public int getMurderRoleId() {
        return murderRoleId;
    }

    public boolean isVoted() {
        return isVoted;
    }

}
