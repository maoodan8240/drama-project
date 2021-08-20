package dm.relationship.base.msg.room;

import dm.relationship.base.msg.implement._RoomInnerMsg;

public class In_PlayerDisconnectedQuitRoomMsg extends _RoomInnerMsg {
    private String roomId;
    private String playerId;

    public In_PlayerDisconnectedQuitRoomMsg(String roomId, String playerId) {
        super(roomId);
        this.playerId = playerId;
    }


    public String getPlayerId() {
        return playerId;
    }

    public String getRoomId() {
        return roomId;
    }

    
}
