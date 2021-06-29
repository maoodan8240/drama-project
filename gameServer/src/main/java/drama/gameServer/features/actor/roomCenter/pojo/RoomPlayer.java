package drama.gameServer.features.actor.roomCenter.pojo;

import java.util.ArrayList;
import java.util.List;

public class RoomPlayer {
    private String playerId;
    private boolean isReady = false;
    private int roleId;
    private boolean isDub = false;
    private int srchTimes;
    private String roomId;

    private List<Integer> clueIds = new ArrayList<>();

    public RoomPlayer(String playerId, String roomId) {
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isDub() {
        return isDub;
    }

    public void setDub(boolean dub) {
        isDub = dub;
    }

    public List<Integer> getClueIds() {
        return clueIds;
    }


    public int getSrchTimes() {
        return srchTimes;
    }

    public void setSrchTimes(int srchTimes) {
        this.srchTimes = srchTimes;
    }


    @Override
    public String toString() {
        return "RoomPlayer{" +
                "playerId='" + playerId + '\'' +
                ", isReady=" + isReady +
                ", roleId=" + roleId +
                ", isDub=" + isDub +
                ", idToEvidenceId=" + clueIds +
                '}';
    }
}
