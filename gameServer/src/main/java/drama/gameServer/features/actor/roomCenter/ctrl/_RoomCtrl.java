package drama.gameServer.features.actor.roomCenter.ctrl;

import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.simpleId.SimpleIdDao;
import dm.relationship.enums.SimpleIdTypeEnum;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Result_Row;
import dm.relationship.table.tableRows.Table_RunDown_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.topLevelPojos.simpleId.SimpleId;
import drama.gameServer.features.actor.roomCenter.enums.RoomStateEnum;
import drama.gameServer.features.actor.roomCenter.pojo.Room;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
import drama.protos.EnumsProtos;
import org.apache.commons.lang3.time.DateUtils;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class _RoomCtrl extends AbstractControler<Room> implements RoomCtrl {
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final SimpleIdDao SIMPLE_ID_DAO = DaoContainer.getDao(SimpleId.class);

    static {
        SIMPLE_ID_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Common);
    }

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }

    @Override
    public List<Integer> getRightAnswerIdx(List<String> optionsList, int dramaId, EnumsProtos.SexEnum sex) {
        List<Integer> resIdx = new ArrayList<>();
        List<Table_Result_Row> rows = Table_Result_Row.getTableResultRowByDramaIdAndSex(dramaId, sex);
        for (Table_Result_Row row : rows) {
            if (optionsList.size() == row.getAnswer().size()) {
                long count = row.getAnswer().stream().filter(it -> !optionsList.contains(it)).count();
                if (count == MagicNumbers.DEFAULT_ZERO) {
                    resIdx = row.getResult();
                    break;
                }
            }
        }
        return resIdx;
    }

    @Override
    public void createRoom(String roomId, String playerId, int dramaId) {
        int simpleRoomId = SIMPLE_ID_DAO.nextSimpleId(SimpleIdTypeEnum.ROOM);
//        int simpleRoomId = 10001;
        if (!Table_SceneList_Row.containsDramaId(dramaId)) {
            throw new BusinessLogicMismatchConditionException("剧本ID不存在 dramaId:" + dramaId);
        }
        Table_SceneList_Row tabRow = RootTc.get(Table_SceneList_Row.class).get(dramaId);
        target = new Room(roomId, dramaId, playerId, simpleRoomId, tabRow);
        setNextStateAndTimes();
        setTarget(target);
        RoomPlayer roomPlayer = new RoomPlayer(playerId, roomId);
        RoomPlayerCtrl roomPlayerCtrl = GlobalInjector.getInstance(RoomPlayerCtrl.class);
        roomPlayerCtrl.setTarget(roomPlayer);
        addPlayer(roomPlayer, roomPlayerCtrl);
    }

    @Override
    public void addPlayer(RoomPlayer roomPlayer, RoomPlayerCtrl roomPlayerCtrl) {
        target.getIdToRoomPlayer().put(roomPlayer.getPlayerId(), roomPlayer);
        target.getIdToRoomPlayerCtrl().put(roomPlayer.getPlayerId(), roomPlayerCtrl);

    }

    @Override
    public boolean containsPlayer(String playerId) {
        return target.getIdToRoomPlayer().containsKey(playerId) && target.getIdToRoomPlayerCtrl().containsKey(playerId);
    }

    @Override
    public RoomPlayer getRoomPlayer(String playerId) {
        return target.getIdToRoomPlayer().get(playerId);
    }

    @Override
    public RoomPlayerCtrl getRoomPlayerCtrl(String playerId) {
        return target.getIdToRoomPlayerCtrl().get(playerId);
    }

    @Override
    public String getMasterId() {
        return target.getMasterId();
    }

    @Override
    public int getRoleIdx(List<Integer> idxs) {
        for (Integer idx : idxs) {
            if (!target.getRoleIdToPlayerId().containsKey(idx)) {
                return idx;
            }
        }
        return 0;
    }

    @Override
    public int getDramaId() {
        return getTarget().getDramaId();
    }

    @Override
    public EnumsProtos.RoomStateEnum getRoomState() {
        return target.getRoomState();
    }

    @Override
    public int getRoomStateTimes() {
        return target.getStateTimes();
    }

    @Override
    public boolean hasChooseRole(int idx, String playerId) {
        boolean flag = target.getRoleIdToPlayerId().containsValue(playerId);
        boolean flag1 = target.getRoleIdToPlayerId().containsKey(idx);
        return flag || flag1;
    }

    @Override
    public boolean checkAllPlayerReady() {
//        Integer plaNum = RootTc.get(Table_SceneList_Row.class).get(target.getDramaId()).getPlaNum();
//        if (target.getIdToRoomPlayer().size() < 4) {
//            return false;
//        }
        for (Map.Entry<String, RoomPlayer> roomPlayerEntry : target.getIdToRoomPlayer().entrySet()) {
            if (!roomPlayerEntry.getValue().isReady() || roomPlayerEntry.getValue().getRoleId() == 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void removePlayer(String playerId) {
        target.getIdToRoomPlayer().remove(playerId);
        target.getIdToRoomPlayerCtrl().remove(playerId);
    }


    @Override
    public boolean checkRoomIsFull() {
        Integer plaNum = RootTc.get(Table_SceneList_Row.class).get(target.getDramaId()).getPlaNum();
        return getTarget().getIdToRoomPlayer().size() >= plaNum;
    }

    public boolean hasNextStateAndTimes() {
        Iterator<TupleCell<String>> iterator = this.getTarget().getRunRown().iterator();
        return iterator.hasNext();
    }

    @Override
    public boolean containsClueId(int clueId) {
        return target.getClueIds().contains(clueId);
    }

    @Override
    public void addClueId(int clueId) {
        target.getClueIds().add(clueId);
    }

    @Override
    public List<Integer> getClueIds() {
        return target.getClueIds();
    }

    @Override
    public boolean containsClueIds(List<Integer> clueIds) {
        for (Integer clueId : clueIds) {
            if (!containsClueId(clueId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addClueIds(List<Integer> clueIds) {
        for (Integer clueId : clueIds) {
            if (!containsClueId(clueId)) {
                addClueId(clueId);
            }
        }
    }

    @Override
    public boolean checkPlayerFinishSearch() {
        for (Map.Entry<String, RoomPlayer> entry : getTarget().getIdToRoomPlayer().entrySet()) {
            if (entry.getValue().getSrchTimes() != MagicNumbers.DEFAULT_ZERO) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void chooseRole(RoomPlayer roomPlayer) {
        target.getRoleIdToPlayerId().put(roomPlayer.getRoleId(), roomPlayer.getPlayerId());
    }

    private TupleCell<String> getAndRemoveNextStateAndTimes() {
        TupleCell<String> next = null;
        Iterator<TupleCell<String>> it = this.getTarget().getRunRown().iterator();
        if (it.hasNext()) {
            next = it.next();
            it.remove();
        }
        return next;
    }

    public void setNextStateAndTimes() {
        TupleCell<String> nextStateAndTimes = getAndRemoveNextStateAndTimes();
        EnumsProtos.RoomStateEnum roomState = RoomStateEnum.getRoomStateByName(nextStateAndTimes.get(TupleCell.FIRST));
        Integer stateTimes = Integer.valueOf(nextStateAndTimes.get(TupleCell.SECOND));
        long nextSTime = Table_RunDown_Row.getNextSTime(roomState.toString(), stateTimes);
        target.setRoomState(roomState);
        target.setStateTimes(stateTimes);
        //设置下一个阶段解锁时间
        target.setNextSTime(System.currentTimeMillis() + nextSTime * DateUtils.MILLIS_PER_SECOND);
        //切换到下一个状态所有玩家把手放下取消准备状态
        for (Map.Entry<String, RoomPlayer> entry : target.getIdToRoomPlayer().entrySet()) {
            RoomPlayer player = entry.getValue();
            player.setReady(false);
            player.setSrchTimes(0);
            if (roomState == EnumsProtos.RoomStateEnum.SEARCH) {
                List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(getDramaId());
                int srchNumAndTimes = Table_Acter_Row.getSrchTimes(acterRowList, player.getRoleId(), stateTimes);
                player.setSrchTimes(srchNumAndTimes);
            }
        }
    }


    @Override
    public void removeRole(int roleId) {
        if (target.getRoleIdToPlayerId().containsKey(roleId)) {
            target.getRoleIdToPlayerId().remove(roleId);
        }
    }

    @Override
    public void addVote(String playerId, int roleId) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        if (!target.getVoteRoleIdToPlayerRoleId().get(roleId).contains(roomPlayerCtrl.getRoleId())) {
            target.getVoteRoleIdToPlayerRoleId().get(roleId).add(roomPlayerCtrl.getRoleId());
        }
    }

    @Override
    public int RemainNum() {
        int num = 0;
        for (Map.Entry<Integer, List<Integer>> entry : target.getVoteRoleIdToPlayerRoleId().entrySet()) {
            num += entry.getValue().size();
        }
//        return target.getPlayerNum() - num;
        return 0;
    }

    @Override
    public Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId() {
        return target.getVoteRoleIdToPlayerRoleId();
    }

    @Override
    public boolean isTimeCanReady() {
//        return System.currentTimeMillis() >= getTarget().getNextSTime();
        //TODO 打开判断
        return true;
    }
}
