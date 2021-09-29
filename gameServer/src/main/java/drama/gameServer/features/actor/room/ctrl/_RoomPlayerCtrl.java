package drama.gameServer.features.actor.room.ctrl;

import dm.relationship.base.MagicNumbers;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
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

    public void reduceVoteSrchTimes() {
        target.setVoteSrchTimes(target.getVoteSrchTimes() - MagicNumbers.DEFAULT_ONE > MagicNumbers.DEFAULT_ZERO ? target.getVoteSrchTimes() - MagicNumbers.DEFAULT_ONE : MagicNumbers.DEFAULT_ZERO);
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
        return getRoleId() != MagicNumbers.DEFAULT_ZERO;
    }

    @Override
    public int getSrchTimes() {
        return target.getSrchTimes();
    }

    @Override
    public void setSrchTimes(int times) {
        target.setSrchTimes(times);
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

    @Override
    public void addSoloAnswer(int soloNum, int soloDramaId) {
        target.getSoloAnswer().put(soloNum, soloDramaId);
    }


    @Override
    public boolean hasSelectDraft() {
        return target.isSelectDraft();
    }

    @Override
    public void setSelectDraft(boolean isSelect) {
        target.setSelectDraft(isSelect);
    }

    @Override
    public void setVoteSrchTimes(int voteSrchTimes) {
        target.setVoteSrchTimes(voteSrchTimes);
    }

    @Override
    public int getVoteSrchTimes() {
        return target.getVoteSrchTimes();
    }

    @Override
    public void setVoteMurder(boolean voteMurder) {
        target.setVoteMurder(voteMurder);
    }

    @Override
    public void setSubVoteMurder(boolean subVoteMurder) {
        target.setSubVoteMurder(subVoteMurder);
    }

    @Override
    public boolean isVoteMurder() {
        return target.isVoteMurder();
    }

    @Override
    public boolean isSubVoteMurder() {
        return target.isSubVoteMurder();
    }

    @Override
    public boolean hasSelectedSubRole(int subNum) {
        return target.getSubNumToSubRoleId().get(subNum) != null;
    }

    @Override
    public int getSubRoleId(int subNum) {
        return target.getSubNumToSubRoleId().get(subNum) != null ? target.getSubNumToSubRoleId().get(subNum) : 0;
    }

    @Override
    public void setSelectSubRole(int subRoleId, int subNum) {
        target.getSubNumToSubRoleId().put(subNum, subRoleId);
    }
}
