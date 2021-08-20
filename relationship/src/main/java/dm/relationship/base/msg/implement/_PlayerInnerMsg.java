package dm.relationship.base.msg.implement;

import dm.relationship.base.msg.interfaces.PlayerInnerMsg;

public abstract class _PlayerInnerMsg implements PlayerInnerMsg {
    private String playerId;


    public _PlayerInnerMsg(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }
}
