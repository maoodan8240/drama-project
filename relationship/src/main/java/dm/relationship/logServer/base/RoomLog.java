package dm.relationship.logServer.base;

import org.bson.types.ObjectId;

public class RoomLog extends DmLog {
    private String roomId;
    private int simpleId;
    private String playerId;
    private String playerName;
    private int dramaId;
    private String dramaName;
    private int createAtDate;                 // 年月日      yyyyMMdd
    private int createAtTime;                 // 时分秒毫秒   HHmmss

    public RoomLog() {
        super(ObjectId.get().toString());
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getDramaId() {
        return dramaId;
    }

    public void setDramaId(int dramaId) {
        this.dramaId = dramaId;
    }

    public String getDramaName() {
        return dramaName;
    }

    public void setDramaName(String dramaName) {
        this.dramaName = dramaName;
    }

    public int getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate(int createAtDate) {
        this.createAtDate = createAtDate;
    }

    public int getCreateAtTime() {
        return createAtTime;
    }

    public void setCreateAtTime(int createAtTime) {
        this.createAtTime = createAtTime;
    }
}
