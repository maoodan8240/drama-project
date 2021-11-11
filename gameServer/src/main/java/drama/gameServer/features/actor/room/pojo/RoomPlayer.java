package drama.gameServer.features.actor.room.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import dm.relationship.noDbPojo.RoomPlayerNoDbPojo;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import org.apache.commons.lang3.StringUtils;
import ws.common.network.server.interfaces.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RoomPlayer extends RoomPlayerNoDbPojo {
    private String playerId;
    private String playerName;
    private String roleName;
    private int roleId;
    private String playerIcon;
    private Connection connection;

    @JSONField(serialize = false)
    private String roomId;


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

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
    private boolean isReady;
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
    private boolean selectDraft;

    /**
     * 线索
     */
    private List<Integer> clueIds = new ArrayList<>();

    /**
     * 轮数对应拍卖结果
     */
    private Map<Integer, List<AuctionResult>> numToAuctionResults = new HashMap<>();

    /**
     * 是否存活
     */
    private boolean isLive = true;

    /**
     * 个人选择
     */
    private int choiceRoleId;
    /**
     * 任务id对应结果
     */
    private Map<Integer, Boolean> taskIdToResult = new TreeMap<>();

    /**
     * 被谁击杀
     */
    private int killerRoleId;

    public RoomPlayer(SimplePlayer simplePlayer, String roomId, int dramaId, Connection connection) {
        this.playerId = simplePlayer.getPlayerId();
        this.roomId = roomId;
        super.dramaId = dramaId;
        this.connection = connection;
        this.playerName = !StringUtils.isEmpty(simplePlayer.getPlayerName()) ? simplePlayer.getPlayerName() : "";
        this.playerIcon = !StringUtils.isEmpty(simplePlayer.getIcon()) ? simplePlayer.getIcon() : "";
    }


    public int getKillerRoleId() {
        return killerRoleId;
    }

    public void setKillerRoleId(int killerRoleId) {
        this.killerRoleId = killerRoleId;
    }

    public Map<Integer, Boolean> getTaskIdToResult() {
        return taskIdToResult;
    }

    public void setTaskIdToResult(Map<Integer, Boolean> taskIdToResult) {
        this.taskIdToResult = taskIdToResult;
    }

    public int getChoiceRoleId() {
        return choiceRoleId;
    }

    public void setChoiceRoleId(int choiceRoleId) {
        this.choiceRoleId = choiceRoleId;
    }

    public Map<Integer, List<AuctionResult>> getNumToAuctionResults() {
        return numToAuctionResults;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
                ", playerName='" + playerName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleId=" + roleId +
                ", playerIcon='" + playerIcon + '\'' +
                ", connection=" + connection +
                ", roomId='" + roomId + '\'' +
                ", subNumToSubRoleId=" + subNumToSubRoleId +
                ", voteMurder=" + voteMurder +
                ", subVoteMurder=" + subVoteMurder +
                ", isReady=" + isReady +
                ", isDub=" + isDub +
                ", srchTimes=" + srchTimes +
                ", voteSrchTimes=" + voteSrchTimes +
                ", soloAnswer=" + soloAnswer +
                ", selectDraft=" + selectDraft +
                ", clueIds=" + clueIds +
                ", numToAuctionResults=" + numToAuctionResults +
                ", isLive=" + isLive +
                ", dramaId=" + dramaId +
                '}';
    }
}
