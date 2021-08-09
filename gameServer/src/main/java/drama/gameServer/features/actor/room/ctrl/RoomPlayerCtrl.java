package drama.gameServer.features.actor.room.ctrl;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.utils.mc.controler.Controler;

import java.util.List;

public interface RoomPlayerCtrl extends Controler<RoomPlayer> {
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
}
