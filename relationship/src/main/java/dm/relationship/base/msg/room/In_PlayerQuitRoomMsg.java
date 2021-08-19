package dm.relationship.base.msg.room;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

public class In_PlayerQuitRoomMsg extends _PlayerInnerMsg {
    private String roomId;
    private String masterId;

    public In_PlayerQuitRoomMsg(String roomId, String masterId, String playerId) {
        super(playerId);
        this.roomId = roomId;
        this.masterId = masterId;
    }


    public String getMasterId() {
        return masterId;
    }

    public String getRoomId() {
        return roomId;
    }

}
