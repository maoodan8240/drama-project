package drama.gameServer.features.actor.roomCenter.ctrl;

import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import ws.common.utils.mc.controler.Controler;

import java.util.List;

public interface RoomPlayerCtrl extends Controler<RoomPlayer> {
    /**
     * 是否持有这个证物
     *
     * @param clueId
     * @return
     */
    public boolean containsClueId(int clueId);

    /**
     * 添加线索
     *
     * @param clueId
     */
    public void addClueId(int clueId);

    /**
     * 扣一次搜证次数
     */
    public void subSrchTimes();

    public void setRoleId(int roleIdx);

    public int getRoleId();

    public int getSrchTimes();

    public String getPlayerId();

    boolean isReady();

    void setReady(boolean b);

    boolean isDub();

    void setDub(boolean b);

    List<Integer> getClueIds();
}
