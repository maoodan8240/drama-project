package dm.relationship.base.msg.implement;

import dm.relationship.base.msg.interfaces.PlayerInnerMsg;

public abstract class _PlayerInnerMsg implements PlayerInnerMsg {
    private String playerId;
    private int dramaId;

    public _PlayerInnerMsg(String playerId) {
        this.playerId = playerId;
    }

    public _PlayerInnerMsg(String playerId, int dramaId) {
        this.playerId = playerId;
        this.dramaId = dramaId;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public int getDramaId() {
        return dramaId;
    }
}
