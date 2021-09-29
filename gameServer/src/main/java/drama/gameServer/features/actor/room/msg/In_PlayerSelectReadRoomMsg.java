package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerSelectReadRoomMsg extends _PlayerInnerMsg {
    private String result;

    public In_PlayerSelectReadRoomMsg(int dramaId, String playerId, String result) {
        super(playerId, dramaId);
        this.result = result;
    }

    public String getResult() {
        return result;
    }


}
