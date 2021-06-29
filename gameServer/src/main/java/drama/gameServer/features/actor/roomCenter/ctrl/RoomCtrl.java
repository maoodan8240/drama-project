package drama.gameServer.features.actor.roomCenter.ctrl;

import drama.gameServer.features.actor.roomCenter.pojo.Room;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import drama.protos.EnumsProtos;
import ws.common.utils.mc.controler.Controler;

import java.util.List;

public interface RoomCtrl extends Controler<Room> {
    /**
     * 创建房间
     *
     * @param roomId
     * @param playerId
     * @param dramaId
     */
    void createRoom(String roomId, String playerId, int dramaId);

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
     * 玩家在房间内选定角色
     *
     * @param roomPlayer
     */
    void chooseRole(RoomPlayer roomPlayer);

}
