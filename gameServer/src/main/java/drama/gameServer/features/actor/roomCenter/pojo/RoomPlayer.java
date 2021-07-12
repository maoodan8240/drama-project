package drama.gameServer.features.actor.roomCenter.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomPlayer {
    private String playerId;
    private boolean isReady = false;
    private int roleId;
    private int isDub = -1;
    private int srchTimes;
    private String roomId;
    private Map<Integer, Integer> soloAnswer = new HashMap<>();

    private List<Integer> clueIds = new ArrayList<>();

    public RoomPlayer(String playerId, String roomId) {
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public Map<Integer, Integer> getSoloAnswer() {
        return soloAnswer;
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

    public int getIsDub() {
        return isDub;
    }

    public void setIsDub(int isDub) {
        this.isDub = isDub;
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
