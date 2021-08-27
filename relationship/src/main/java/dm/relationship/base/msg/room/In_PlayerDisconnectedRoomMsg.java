package dm.relationship.base.msg.room;

import dm.relationship.base.msg.implement._RoomInnerMsg;

public class In_PlayerDisconnectedRoomMsg extends _RoomInnerMsg {
    private String roomId;
    private String playerId;

    public In_PlayerDisconnectedRoomMsg(String roomId, String playerId) {
        super(roomId);
        this.playerId = playerId;
    }


    public String getPlayerId() {
        return playerId;
    }

}
