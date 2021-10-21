package dm.relationship.noDbPojo;

import dm.relationship.noDbPojo.interfaces.NoDbPojo;

/**
 * Created by lee on 2021/10/8
 */
public class RoomPlayerNoDbPojo implements NoDbPojo {
    protected String playerId;
    protected String roomId;
    protected int dramaId;


    public RoomPlayerNoDbPojo(String playerId) {
        this.playerId = playerId;
    }

    public RoomPlayerNoDbPojo() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getDramaId() {
        return dramaId;
    }

    public void setDramaId(int dramaId) {
        this.dramaId = dramaId;
    }

    @Override
    public String getOid() {
        return this.playerId;
    }

    @Override
    public void setOid(String oid) {
        this.playerId = oid;
    }
}
