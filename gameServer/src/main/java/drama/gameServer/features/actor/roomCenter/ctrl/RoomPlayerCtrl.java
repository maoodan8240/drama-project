package drama.gameServer.features.actor.roomCenter.ctrl;

import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
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

    String getPlayerId();

    boolean hasRole();

    boolean isReady();

    void setReady(boolean b);

    int getIsDub();

    void setDub(int isDub);

    List<Integer> getClueIds();

    void addSoloAnswer(int sloloNum, int soloDramaId);
}
