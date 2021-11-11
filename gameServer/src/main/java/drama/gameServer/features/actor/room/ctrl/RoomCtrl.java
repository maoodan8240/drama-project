package drama.gameServer.features.actor.room.ctrl;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.EnumsProtos;
import drama.protos.EnumsProtos.PowerEnum;
import drama.protos.EnumsProtos.RoomStateEnum;
import ws.common.network.server.interfaces.Connection;
import ws.common.table.table.interfaces.cell.TupleCell;
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
    void createRoom(String roomId, SimplePlayer player, int dramaId, Connection connection);

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
    RoomStateEnum getRoomState();

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
     * @param voteNum
     * @return
     */
    boolean checkPlayerFinishVoteSearch(int voteNum);

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
     * 剩余几人未投凶
     *
     * @return
     */
    int remainVoteNum();

    /**
     * 剩余几人子角色未投凶
     *
     * @return
     */
    int remainSubVoteNum();

    /**
     * 获取投凶结果
     *
     * @return
     */
    Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId(int voteNum);

    /**
     * 获取子角色投凶结果
     *
     * @return
     */
    Map<Integer, List<Integer>> getSubVoteRoleIdToPlayerRoleId(int voteNum);

    /**
     * 是否可以解锁下一阶段
     *
     * @return boolean
     */
    boolean isTimeCanReady();

    /**
     * 距离下一阶段的解锁时间
     *
     * @return 返回秒
     */
    int getCanReadyTime();


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
     * 是否已经投票
     *
     * @param roleId
     * @return
     */
    boolean hasVoteSearch(int roleId);

    /**
     * 投票搜证
     *
     * @param roomPlayerCtrl
     * @param typeName
     * @param voteNum
     */
    void voteSearch(RoomPlayerCtrl roomPlayerCtrl, String typeName, int voteNum);

    /**
     * 最终投票选中的线索
     *
     * @param voteNum
     * @return int
     */
    int getVoteSearchClueId(int voteNum);

    /**
     * 清空投票搜证
     *
     * @param voteNum
     */
    Map<Integer, List<Integer>> getVoteSearchTypeIdToPlayerRoleId(int voteNum);

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
    boolean canSelectDraft(int draftId, int draftNum);

    /**
     * 选择轮抽
     *
     * @param roomPlayerCtrl
     * @param draftId
     */
    void selectDraft(RoomPlayerCtrl roomPlayerCtrl, int draftId, int draftNum);

    /**
     * 可以选择的轮抽Id
     *
     * @return
     */
    List<Integer> canSelectDraftIds(int draftNum);

    /**
     * 获取选择的轮抽Id
     *
     * @param draftNum
     * @return
     */
    int getDraftIdByDraftNum(int draftNum, RoomPlayerCtrl roomPlayerCtrl);

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

    /**
     * 子角色列表
     *
     * @return
     */
    Map<Integer, String> subSelectList(int subNum);

    /**
     * 选择子角色
     *
     * @param subRoleId
     * @param playerId
     */
    void onSubSelect(int subRoleId, int subNum, String playerId);

    /**
     * 子剧本投凶
     *
     * @param subRoleId
     * @param subNum
     * @param playerId
     */
    void onSubVote(int subRoleId, int subNum, String playerId);

    /**
     * 子剧本投凶结果
     *
     * @param subNum
     * @param playerId
     */
    void onSubVoteResult(int subNum, String playerId);

    void onSubVoteList(int subNum, String playerId);

    ActorContext getContext();

    void setContext(ActorContext context);

    ActorRef getActorRef();

    void setActorRef(ActorRef actorRef);

    ActorRef getCurSendActorRef();

    void setCurSendActorRef(ActorRef curSendActorRef);

    void onRoomExtpMsg(String playerId, RoomInnerExtpMsg msg);

    /**
     * 解锁信息(玩家的一些动态信息)
     *
     * @param voteNum
     * @param simplePlayer
     */
    void onUnlockInfo(int voteNum, SimplePlayer simplePlayer);


    /**
     * 拍卖结果
     *
     * @param playerId
     */
    void onAuctionResult(String playerId);

    /**
     * 拍卖物品信息
     *
     * @param playerId
     */
    void onAuctionInfo(String playerId);

    /**
     * 出价
     *
     * @param playerId
     * @param idOrTpId
     * @param price
     * @param auctionId
     */
    void onAuction(String playerId, int idOrTpId, int price, int auctionId);

    /**
     * 获取下一个阶段信息
     *
     * @return
     */
    TupleCell<String> getNexStateAndTimes();

    /**
     * 是否可以开枪
     *
     * @param playerId
     */
    void onCanShoot(String playerId);


    /**
     * 开枪
     *
     * @param playerId
     * @param isShoot
     * @param power
     * @param beShootRoleId
     */
    void onShoot(String playerId, boolean isShoot, PowerEnum power, int beShootRoleId);

    /**
     * 开枪列表
     *
     * @param playerId
     */
    void onShootList(String playerId);

    /**
     * 开枪是否结束
     *
     * @param playerId
     */
    void onShootEnding(String playerId);


    /**
     * 个人选择列表
     */
    void onCanChoice(String playerId);

    /**
     * 个人选择
     *
     * @param playerId
     * @param beRoleId
     */
    void onChoice(String playerId, int beRoleId);

    /**
     * 积分排行
     *
     * @param playerId
     */
    void onScoreList(String playerId);

    /**
     * 查看详细积分
     *
     * @param playerId
     * @param roleId
     */
    void onRoleScore(String playerId, int roleId);
}
