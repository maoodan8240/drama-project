package drama.gameServer.features.actor.world.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PrepareToKillPlayerActorRequestMsg extends _PlayerInnerMsg {
    private String playerId;

    public In_PrepareToKillPlayerActorRequestMsg(String playerId) {
        super(playerId);
    }

    public String getPlayerId() {
        return playerId;
    }


}
