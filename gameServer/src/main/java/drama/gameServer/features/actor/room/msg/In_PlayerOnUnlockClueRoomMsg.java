package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.List;

public class In_PlayerOnUnlockClueRoomMsg extends _PlayerInnerMsg {
    private List<Integer> unlockClueIds;
    private int dramaId;

    public In_PlayerOnUnlockClueRoomMsg(String playerId, List<Integer> unlockClueIds, int dramaId) {
        super(playerId);
        this.dramaId = dramaId;
        this.unlockClueIds = unlockClueIds;
    }

    public List<Integer> getUnlockClueIds() {
        return unlockClueIds;
    }

    public int getDramaId() {
        return dramaId;
    }

    
}
