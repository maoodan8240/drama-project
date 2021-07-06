package drama.gameServer.features.actor.roomCenter.ctrl;

import dm.relationship.base.MagicNumbers;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.List;

public class _RoomPlayerCtrl extends AbstractControler<RoomPlayer> implements RoomPlayerCtrl {

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }


    public boolean containsClueId(int clueId) {
        return target.getClueIds().contains(clueId);
    }


    public void addClueId(int clueId) {
        target.getClueIds().add(clueId);
    }

    public void reduceSrchTimes() {
        target.setSrchTimes(target.getSrchTimes() - MagicNumbers.DEFAULT_ONE > MagicNumbers.DEFAULT_ZERO ? target.getSrchTimes() - MagicNumbers.DEFAULT_ONE : MagicNumbers.DEFAULT_ZERO);
    }

    @Override
    public void setRoleId(int roleIdx) {
        target.setRoleId(roleIdx);
    }

    @Override
    public int getRoleId() {
        return target.getRoleId();
    }

    @Override
    public boolean hasRole() {
        return getRoleId() != 0;
    }

    @Override
    public int getSrchTimes() {
        return target.getSrchTimes();
    }

    @Override
    public String getPlayerId() {
        return target.getPlayerId();
    }

    @Override
    public boolean isReady() {
        return target.isReady();
    }

    @Override
    public void setReady(boolean b) {
        target.setReady(b);
    }

    @Override
    public int getIsDub() {
        return target.getIsDub();
    }

    @Override
    public void setDub(int isDub) {
        target.setIsDub(isDub);
    }

    @Override
    public List<Integer> getClueIds() {
        return target.getClueIds();
    }

}
