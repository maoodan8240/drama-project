package drama.gameServer.features.actor.room.pojo;


import dm.relationship.table.tableRows.Table_SceneList_Row;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import drama.protos.EnumsProtos;
import ws.common.table.table.interfaces.cell.TupleCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

;

public class Room {
    /**
     * 房间Id
     */
    private String roomId;
    /**
     * 剧本Id
     */
    private int dramaId;
    /**
     * 剧本名
     */
    private String dramaName;
    /**
     * 房主playerId
     */
    private String masterId;
    /**
     * int类型房间Id SimpleRoomId
     */
    private int simpleRoomId;
    /**
     * playerId对应RoomPlayer
     */
    private Map<String, RoomPlayer> idToRoomPlayer = new ConcurrentHashMap<>();
    /**
     * playerId对应roomPlayerCtrl
     */
    private Map<String, RoomPlayerCtrl> idToRoomPlayerCtrl = new ConcurrentHashMap<>();
    /**
     * 房间状态
     */
    private EnumsProtos.RoomStateEnum roomState;
    /**
     * 当前阶段第几次
     */
    private int stateTimes;
    /***
     * 剧本环节
     */
    private List<TupleCell<String>> runRown;
    /**
     * 角色Id对应playerId
     */
    private Map<Integer, String> roleIdToPlayerId = new ConcurrentHashMap<>();
    /**
     * 已公开的线索
     */
    private List<Integer> clueIds = new ArrayList<>();
    /**
     * 本剧本共有几次搜证环节
     */
    private int srchNum;
    /**
     * 剧本所需玩家人数
     */
    private int playerNum;
    /**
     * 被投凶的角色Id对应投凶角色Id
     */
    private Map<Integer, Map<Integer, List<Integer>>> voteNumToVoteRoleIdToRoleId = new ConcurrentHashMap<>();
    /**
     * 下一阶段解锁时间
     */
    private long nextSTime;
    /**
     * 被投票的线索分类Id对应玩家角色Id
     */
    private Map<Integer, List<Integer>> voteTypeIdToRoleId = new ConcurrentHashMap<>();
    /**
     * 可以被投票搜索的线索Id
     */
    private List<Integer> canVoteSearchCuleId = new ArrayList<>();
    /**
     * 可以被选择的轮抽
     */
    private Map<Integer, Map<Integer, Integer>> draftNumToSelectDraftIdToRoleId = new ConcurrentHashMap<>();

    public Room(String roomId, int dramaId, String masterId, int simpleRoomId, Table_SceneList_Row tabRow) {
        this.roomId = roomId;
        this.dramaId = dramaId;
        this.masterId = masterId;
        this.simpleRoomId = simpleRoomId;
        this.dramaName = tabRow.getName();
        this.runRown = new ArrayList<>(tabRow.getRunDown());
        this.srchNum = tabRow.getSrchNum();
        this.playerNum = tabRow.getPlaNum();
    }


    public Map<Integer, Map<Integer, List<Integer>>> getVoteNumToVoteRoleIdToRoleId() {
        return voteNumToVoteRoleIdToRoleId;
    }

    public Map<Integer, List<Integer>> getVoteTypeIdToRoleId() {
        return voteTypeIdToRoleId;
    }


    public List<Integer> getCanVoteSearchCuleId() {
        return canVoteSearchCuleId;
    }

    public void setCanVoteSearchCuleId(List<Integer> canVoteSearchCuleId) {
        this.canVoteSearchCuleId = canVoteSearchCuleId;
    }

    public Map<Integer, Map<Integer, Integer>> getDraftNumToSelectDraftIdToRoleId() {
        return draftNumToSelectDraftIdToRoleId;
    }


    public long getNextSTime() {
        return nextSTime;
    }

    public void setNextSTime(long nextSTime) {
        this.nextSTime = nextSTime;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public int getSrchNum() {
        return srchNum;
    }

    public void setSrchNum(int srchNum) {
        this.srchNum = srchNum;
    }

    public List<Integer> getClueIds() {
        return clueIds;
    }

    public void setClueIds(List<Integer> clueIds) {
        this.clueIds = clueIds;
    }

    public int getStateTimes() {
        return stateTimes;
    }

    public void setStateTimes(int stateTimes) {
        this.stateTimes = stateTimes;
    }

    public Map<Integer, String> getRoleIdToPlayerId() {
        return roleIdToPlayerId;
    }

    public void setRoleIdToPlayerId(Map<Integer, String> roleIdToPlayerId) {
        this.roleIdToPlayerId = roleIdToPlayerId;
    }


    public List<TupleCell<String>> getRunRown() {
        return runRown;
    }

    public int getSimpleRoomId() {
        return simpleRoomId;
    }

    public void setSimpleRoomId(int simpleRoomId) {
        this.simpleRoomId = simpleRoomId;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public int getDramaId() {
        return this.dramaId;
    }

    public String getMasterId() {
        return masterId;
    }


    public EnumsProtos.RoomStateEnum getRoomState() {
        return roomState;
    }

    public void setRoomState(EnumsProtos.RoomStateEnum roomState) {
        this.roomState = roomState;
    }

    public String getDramaName() {
        return dramaName;
    }

    public void setDramaName(String dramaName) {
        this.dramaName = dramaName;
    }

    public Map<String, RoomPlayer> getIdToRoomPlayer() {
        return idToRoomPlayer;
    }

    public Map<String, RoomPlayerCtrl> getIdToRoomPlayerCtrl() {
        return idToRoomPlayerCtrl;
    }

    public void setIdToRoomPlayerCtrl(Map<String, RoomPlayerCtrl> idToRoomPlayerCtrl) {
        this.idToRoomPlayerCtrl = idToRoomPlayerCtrl;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", dramaId=" + dramaId +
                ", dramaName='" + dramaName + '\'' +
                ", masterId='" + masterId + '\'' +
                ", simpleRoomId=" + simpleRoomId +
                ", idToRoomPlayer=" + idToRoomPlayer +
                ", roomState=" + roomState +
                ", roleIdToPlayerId=" + roleIdToPlayerId +
                '}';
    }
}