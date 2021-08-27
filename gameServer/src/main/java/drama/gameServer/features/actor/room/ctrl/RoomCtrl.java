package drama.gameServer.features.actor.room.ctrl;

import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.EnumsProtos;
import ws.common.utils.mc.controler.Controler;

import java.util.List;
import java.util.Map;

public interface RoomCtrl extends Controler<Room> {
    /**
     * 创建房间
     *
     * @param roomId
     * @param player
     * @param dramaId
     */
    void createRoom(String roomId, Player player, int dramaId);

    /**
     * 清出玩家
     *
     * @param playerId
     */
    void removePlayer(String playerId);

    /**
     * 加入玩家
     *
     * @param player
     */
    void addPlayer(RoomPlayer player, RoomPlayerCtrl roomPlayerCtrl);

    /**
     * 玩家是否在房间内
     *
     * @param playerId
     * @return
     */
    boolean containsPlayer(String playerId);

    /**
     * 获取玩家
     *
     * @param playerId
     * @return
     */
    RoomPlayer getRoomPlayer(String playerId);

    /**
     * 获取roomPlayerCtrl
     *
     * @param playerId
     * @return
     */
    RoomPlayerCtrl getRoomPlayerCtrl(String playerId);

    /**
     * 获取房主playerId
     *
     * @return
     */
    String getMasterId();

    /**
     * 根据答题获得角色优先权选择一个角色ID
     *
     * @param idxs
     * @return
     */
    int getRoleIdx(List<Integer> idxs);

    /**
     * 获取剧本ID
     *
     * @return
     */
    int getDramaId();

    /***
     * 获取房间状态
     * @return
     */
    EnumsProtos.RoomStateEnum getRoomState();

    /***
     * 当前状态的次数
     * @return
     */
    int getRoomStateTimes();

    /**
     * 是否已经选择了角色
     *
     * @param idx
     * @param playerId
     * @return
     */
    boolean hasChooseRole(int idx, String playerId);

    /**
     * 检查所有玩家是否准备就绪
     *
     * @return
     */
    boolean checkAllPlayerReady();

    /**
     * 判断房间是否已满
     *
     * @return
     */
    boolean checkRoomIsFull();

    /**
     * 讲房间设置为下一阶段
     */
    void setNextStateAndTimes();

    /**
     * 是否还有下一个阶段
     *
     * @return
     */
    boolean hasNextStateAndTimes();

    /**
     * 房间内是否已经有这个线索Id
     *
     * @param clueId
     * @return
     */
    boolean containsClueId(int clueId);

    /**
     * 添加线索
     *
     * @param clueId
     */
    void addClueId(int clueId);

    /**
     * 获取房间内所有线索Id
     *
     * @return
     */
    List<Integer> getClueIds();

    /**
     * 房间内是否包含这些clueIds
     *
     * @param clueIds
     * @return
     */
    boolean containsClueIds(List<Integer> clueIds);

    /**
     * 添加线索clueIds
     *
     * @param clueIds
     */
    void addClueIds(List<Integer> clueIds);

    /***
     * 检查所有玩家是否完成搜索  搜证次数用完
     * @return
     */
    boolean checkPlayerFinishSearch();

    /**
     * 检查所有玩家是否完成投票搜证
     *
     * @return
     */
    boolean checkPlayerFinishVoteSearch();

    /**
     * 玩家在房间内选定角色
     *
     * @param roomPlayer
     */
    void chooseRole(RoomPlayer roomPlayer);

    /**
     * 根据答题答案获取对应的角色优先级列表
     *
     * @param optionsList
     * @param dramaId
     * @param sex
     * @return
     */
    List<Integer> getRightAnswerIdx(List<String> optionsList, int dramaId, EnumsProtos.SexEnum sex);

    /**
     * 删除角色
     *
     * @param roleId
     */
    void removeRole(int roleId);

    /**
     * 投凶
     *
     * @param playerId
     * @param roleId
     */
    void addVote(String playerId, int roleId);

    /**
     * 剩余几人未投票
     *
     * @return
     */
    int RemainNum();

    /**
     * 获取投凶结果
     *
     * @return
     */
    Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId(int voteNum);

    /**
     * 获取投票搜证结果
     *
     * @return
     */
    Map<Integer, List<Integer>> getVoteSearchTypeIdToPlayerRoleId();

    /**
     * 是否可以解锁下一阶段
     *
     * @return boolean
     */
    boolean isTimeCanReady();

    /**
     * 凶手是否被投中
     *
     * @return boolean
     */
    boolean isVotedMurder(int voteNum, int roleId);

    /**
     * 房间在线玩家人数
     *
     * @return
     */
    int getRoomPlayerNum();

    /**
     * 这个角色线索是否已经搜完
     *
     * @param typeName
     * @return
     */
    boolean isEmptyClue(String typeName);

    /**
     * 可以选择的角色列表
     *
     * @param dramaId
     * @return
     */
    List<Integer> canSelectRoleIds(int dramaId);

    /**
     * 获取可以投票搜证的线索Id
     *
     * @return
     */
    List<Integer> canVoteSearchTypeIds();

    /**
     * 投票搜证
     *
     * @param roomPlayerCtrl
     * @param typeName
     */
    void voteSearch(RoomPlayerCtrl roomPlayerCtrl, String typeName);

    /**
     * 最终投票选中的线索
     *
     * @return int
     */
    int getVoteSearchClueId();

    /**
     * 清空投票搜证
     */
    Map<Integer, List<Integer>> clearVoteSearchTypeIdToPlayerRoleId();

    /**
     * 删除可以搜索的投票搜证
     *
     * @param typeId
     */
    void removeCanVoteSearchTypeId(int typeId);

    /**
     * 是否包含这个可以选择的轮抽(是否被比人选走了)
     *
     * @param draftId
     */
    boolean canSelectDraft(int draftId);

    /**
     * 选择轮抽
     *
     * @param roomPlayerCtrl
     * @param draftId
     */
    void selectDraft(RoomPlayerCtrl roomPlayerCtrl, int draftId);

    /**
     * 可以选择的轮抽Id
     *
     * @return
     */
    List<Integer> canSelectDraftIds();


    /**
     * 轮抽是否正确
     *
     * @param draftNum
     * @return
     */
    boolean isRightDraft(int draftNum);

    /**
     * 投凶是否正确
     *
     * @param voteNum
     * @return
     */
    boolean isRightVote(int voteNum);

    /**
     * 房间剧本是否开始
     *
     * @return
     */
    boolean isBegin();

    /**
     * 取第一个玩家,房间易主用
     *
     * @return
     */
    String getOnePlayer();

    /**
     * 记录soloIdx
     */
    void setSoloIdx(int soloIdx);

    /**
     * 获取soloIdx
     *
     * @return int
     */
    int getSoloIdx();

    /**
     * @return
     */
    boolean checkPlayerFinishChoosDub();
}
