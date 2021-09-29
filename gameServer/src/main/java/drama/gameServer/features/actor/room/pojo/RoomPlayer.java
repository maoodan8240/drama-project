package drama.gameServer.features.actor.room.pojo;

import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomPlayer {
    private String playerId;
    private String roomId;
    private String playerName;
    private int roleId;
    private String playerIcon;
    /**
     * 子角色Id
     */
    private Map<Integer, Integer> subNumToSubRoleId = new HashMap<>();
    /**
     * 是否已经投凶
     */
    private boolean voteMurder;
    /**
     * 是否已经子角色投凶
     */
    private boolean subVoteMurder;
    /**
     * 是否已经举手准备
     */
    private boolean isReady = false;
    /**
     * 是否配音
     */
    private int isDub = -1;
    /**
     * 搜证次数
     */
    private int srchTimes;
    /**
     * 投票搜证次数
     */
    private int voteSrchTimes;
    /**
     * 独白答题
     */
    private Map<Integer, Integer> soloAnswer = new HashMap<>();
    /**
     * 是否选择心魔
     */
    private boolean selectDraft = false;

    private List<Integer> clueIds = new ArrayList<>();

    public RoomPlayer(SimplePlayer simplePlayer, String roomId) {
        this.playerId = simplePlayer.getPlayerId();
        this.roomId = roomId;
        this.playerName = !StringUtils.isEmpty(simplePlayer.getPlayerName()) ? simplePlayer.getPlayerName() : "";
        this.playerIcon = !StringUtils.isEmpty(simplePlayer.getIcon()) ? simplePlayer.getIcon() : "";
    }

    public boolean isSubVoteMurder() {
        return subVoteMurder;
    }

    public void setSubVoteMurder(boolean subVoteMurder) {
        this.subVoteMurder = subVoteMurder;
    }

    public Map<Integer, Integer> getSubNumToSubRoleId() {
        return subNumToSubRoleId;
    }

    public void setSubNumToSubRoleId(Map<Integer, Integer> subNumToSubRoleId) {
        this.subNumToSubRoleId = subNumToSubRoleId;
    }

    public boolean isVoteMurder() {
        return voteMurder;
    }

    public void setVoteMurder(boolean voteMurder) {
        this.voteMurder = voteMurder;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerIcon() {
        return playerIcon;
    }

    public void setPlayerIcon(String playerIcon) {
        this.playerIcon = playerIcon;
    }

    public boolean isSelectDraft() {
        return selectDraft;
    }

    public void setSelectDraft(boolean selectDraft) {
        this.selectDraft = selectDraft;
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

    public int getVoteSrchTimes() {
        return voteSrchTimes;
    }

    public void setVoteSrchTimes(int voteSrchTimes) {
        this.voteSrchTimes = voteSrchTimes;
    }


    @Override
    public String toString() {
        return "RoomPlayer{" +
                "playerId='" + playerId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roleId=" + roleId +
                ", isReady=" + isReady +
                ", isDub=" + isDub +
                ", srchTimes=" + srchTimes +
                ", voteSrchTimes=" + voteSrchTimes +
                ", soloAnswer=" + soloAnswer +
                ", selectDraft=" + selectDraft +
                ", clueIds=" + clueIds +
                '}';
    }
}
