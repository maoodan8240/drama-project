package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.List;

public class In_PlayerCanSelectRoomMsg extends _PlayerInnerMsg {
    private List<Integer> canSelectRoleIds;
    private int dramaId;

    public In_PlayerCanSelectRoomMsg(String playerId, List<Integer> canSelectRoleIds, int dramaId) {
        super(playerId);
        this.canSelectRoleIds = canSelectRoleIds;
        this.dramaId = dramaId;
    }

    public List<Integer> getCanSelectRoleIds() {
        return canSelectRoleIds;
    }

    public int getDramaId() {
        return dramaId;
    }

    
}
