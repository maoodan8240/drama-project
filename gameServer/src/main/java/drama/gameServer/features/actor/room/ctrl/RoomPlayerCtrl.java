package drama.gameServer.features.actor.room.ctrl;

import akka.actor.ActorRef;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import drama.gameServer.features.actor.room.mc.extension.RoomPlayerExtension;
import drama.gameServer.features.actor.room.pojo.AuctionResult;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.MessageHandlerProtos;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.mc.controler.Controler;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface RoomPlayerCtrl extends Controler<RoomPlayer> {

    RoomPlayer createRoomPlayer(SimplePlayer simplePlayer, String roomId, int dramaId, Connection connection, RoomCtrl roomCtrl);

    /**
     * 是否持有这个证物
     *
     * @param clueId
     * @return boolean
     */
    boolean containsClueId(int clueId);

    /**
     * 添加线索
     *
     * @param clueId
     */
    void addClueId(int clueId);

    /**
     * 扣一次搜证次数
     */
    void reduceSrchTimes();

    void setRoleId(int roleIdx);

    int getRoleId();

    int getSrchTimes();


    void setSrchTimes(int times);

    String getPlayerId();

    /**
     * 是否已经选角
     *
     * @return
     */

    boolean hasRole();

    boolean isReady();

    void setReady(boolean b);

    int getIsDub();

    void setDub(int isDub);

    List<Integer> getClueIds();

    void addSoloAnswer(int sloloNum, int soloDramaId);

    /**
     * 是否已经选择了轮抽
     *
     * @return
     */
    boolean hasSelectDraft();

    void setSelectDraft(boolean isSelect);

    /**
     * 设置投票搜证次数
     *
     * @param voteSrchTimes
     */
    void setVoteSrchTimes(int voteSrchTimes);

    /**
     * 获取投票搜证次数
     */
    int getVoteSrchTimes();

    /**
     * 扣一次投票搜证次数
     */
    void reduceVoteSrchTimes();

    /**
     * 设置投凶状态
     *
     * @param voteMurder
     */
    void setVoteMurder(boolean voteMurder);

    /**
     * 设置子角色投凶状态
     *
     * @param subVoteMurder
     */
    void setSubVoteMurder(boolean subVoteMurder);

    /**
     * 是否投凶
     *
     * @return
     */
    boolean isVoteMurder();

    /**
     * 子角色是否投凶
     *
     * @return
     */
    boolean isSubVoteMurder();

    /**
     * 是否已经选择子角色
     */
    boolean hasSelectedSubRole(int subNum);

    /**
     * 返回子角色Id
     *
     * @return
     */
    int getSubRoleId(int subNum);

    /**
     * 选择子角色
     *
     * @param subRoleId
     */
    void setSelectSubRole(int subRoleId, int subNum);

    /**
     * 添加扩展
     *
     * @param extension
     */
    void addExtension(RoomPlayerExtension<?> extension);

    /**
     * 返回扩展
     *
     * @param type
     * @return
     */
    <T extends RoomPlayerExtension<?>> T getExtension(Class<T> type);

    /**
     * 返回全部扩展
     *
     * @return
     */
    TreeMap<String, RoomPlayerExtension<?>> getAllExtensions();

    /**
     * 发放初始道具
     *
     * @param dramaId
     */
    void initProp(int dramaId);


    String getRoleName();

    void setRoleName(String roleName);

    ActorRef getRoomActorRef();

    void setRoomActorRef(ActorRef roomActorRef);

    void send(MessageHandlerProtos.Response build);

    void addAuctionResult(int runDown, AuctionResult auctionResult);

    List<AuctionResult> getAuctionResult(int runDown);

    void setLive(boolean isLive);

    boolean isLive();

    void setChoice(int roleId);

    int getChoice();

    void addTaskResult(int taskId, boolean result);

    int getKillerRoleId();

    void setKillerRoleId(int killerRoleId);

    int getTotal(int dramaId);

    Map<Integer, Boolean> getTaskIdToResult();
}
