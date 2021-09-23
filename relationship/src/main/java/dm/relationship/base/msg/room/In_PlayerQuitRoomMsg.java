package dm.relationship.base.msg.room;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerQuitRoomMsg extends _PlayerInnerMsg {
    private String roomId;
    private String quitPlayerId;

    public In_PlayerQuitRoomMsg(String playerId, String roomId, String quitPlayerId) {
        super(playerId);
        this.roomId = roomId;
        this.quitPlayerId = quitPlayerId;
    }


    public String getQuitPlayerId() {
        return quitPlayerId;
    }

    public String getRoomId() {
        return roomId;
    }

}
