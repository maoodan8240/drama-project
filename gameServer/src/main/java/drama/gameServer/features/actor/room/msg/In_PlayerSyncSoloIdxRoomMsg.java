package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerSyncSoloIdxRoomMsg extends _PlayerInnerMsg {
    private int soloIdx;

    public In_PlayerSyncSoloIdxRoomMsg(String playerId, int soloIdx) {
        super(playerId);
        this.soloIdx = soloIdx;
    }

    public int getSoloIdx() {
        return soloIdx;
    }
}
