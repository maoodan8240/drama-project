package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerSoloResultRoomMsg extends _PlayerInnerMsg {
    private int soloDramaId;

    public In_PlayerSoloResultRoomMsg(int dramaId, String playerId, int soloDramaId) {
        super(playerId, dramaId);
        this.soloDramaId = soloDramaId;
    }


    public int getSoloDramaId() {
        return soloDramaId;
    }


}
