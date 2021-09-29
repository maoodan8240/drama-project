package dm.relationship.base.msg.room;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerKillRoomMsg extends _PlayerInnerMsg {
    private String roomId;
    private String playerId;

    public In_PlayerKillRoomMsg(String roomId, String playerId) {
        super(playerId);
        this.roomId = roomId;
        this.playerId = playerId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getPlayerId() {
        return playerId;
    }


}
