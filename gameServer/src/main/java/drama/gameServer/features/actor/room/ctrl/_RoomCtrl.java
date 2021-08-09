package drama.gameServer.features.actor.room.ctrl;

import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.simpleId.SimpleIdDao;
import dm.relationship.enums.SimpleIdTypeEnum;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Draft_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_Result_Row;
import dm.relationship.table.tableRows.Table_RunDown_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import dm.relationship.topLevelPojos.simpleId.SimpleId;
import drama.gameServer.features.actor.room.enums.RoomStateEnum;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.EnumsProtos;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class _RoomCtrl extends AbstractControler<Room> implements RoomCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_RoomCtrl.class);

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
        List<String> result = new ArrayList<>();
        List<Table_Result_Row> rows = Table_Result_Row.getTableResultRowByDramaIdAndSex(dramaId, sex);
        for (Table_Result_Row row : rows) {
            if (optionsList.size() == row.getAnswer().size()) {
                //位置和答案都对上才算完全答对
                for (int i = 0; i < optionsList.size(); i++) {
                    if (!row.getAnswer().get(i).equals(optionsList.get(i))) {
                        result.add(row.getAnswer().get(i));
                    }
                }
            }
            if (result.size() == 0) {
                resIdx = row.getResult();
            } else {
                result.clear();
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
        Integer plaNum = RootTc.get(Table_SceneList_Row.class).get(target.getDramaId()).getPlaNum();
//        if (target.getIdToRoomPlayer().size() < plaNum) {
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
            if (entry.getValue().getSrchTimes() != MagicNumbers.DEFAULT_ZERO || entry.getValue().getVoteSrchTimes() != MagicNumbers.DEFAULT_ZERO) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkPlayerFinishVoteSearch() {
        int count = 0;
        for (Map.Entry<Integer, List<Integer>> entry : getTarget().getVoteTypeIdToRoleId().entrySet()) {
            count += entry.getValue().size();
        }

        return getRoomPlayerNum() == count;
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
        long nextSTime = Table_RunDown_Row.getNextSTime(roomState.toString(), stateTimes, getDramaId());
        target.setRoomState(roomState);
        target.setStateTimes(stateTimes);
        //设置下一个阶段解锁时间
        target.setNextSTime(System.currentTimeMillis() + nextSTime * DateUtils.MILLIS_PER_SECOND);
        //切换到下一个状态所有玩家把手放下取消准备状态
        for (Map.Entry<String, RoomPlayerCtrl> entry : target.getIdToRoomPlayerCtrl().entrySet()) {
            RoomPlayerCtrl roomPlayerCtrl = entry.getValue();
            roomPlayerCtrl.setReady(false);
            roomPlayerCtrl.setDub(-1);
            if (roomState == EnumsProtos.RoomStateEnum.SEARCH) {
                List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(getDramaId());
                int srchNumAndTimes = Table_Acter_Row.getSrchTimes(acterRowList, roomPlayerCtrl.getRoleId(), stateTimes);
                roomPlayerCtrl.setSrchTimes(srchNumAndTimes);
            } else if (roomState == EnumsProtos.RoomStateEnum.VOTESEARCH) {
                List<Integer> searchTypeIds = Table_SearchType_Row.getSearchTypeRowByStateTimes(getRoomStateTimes(), getDramaId());
                target.getCanVoteSearchCuleId().addAll(searchTypeIds);
                List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(getDramaId());
                int voteSrchTimes = Table_Acter_Row.getVoteSrchTimes(acterRowList, roomPlayerCtrl.getRoleId(), stateTimes);
                roomPlayerCtrl.setVoteSrchTimes(voteSrchTimes);
            } else if (roomState == EnumsProtos.RoomStateEnum.DRAFT) {
                List<Integer> draftIds = Table_Draft_Row.getDraftIds(getDramaId());
                addSelectDraftIdToRoleId(draftIds);
                roomPlayerCtrl.setSelectDraft(false);
            } else if (roomState == EnumsProtos.RoomStateEnum.UNLOCK) {
                List<Integer> unlockClueIds = Table_RunDown_Row.getUnlockClueIds(getDramaId(), getRoomStateTimes(), getRoomState().toString());
                addClueIds(unlockClueIds);
            } else if (roomState == EnumsProtos.RoomStateEnum.VOTE) {
                target.getVoteNumToVoteRoleIdToRoleId().put(getRoomStateTimes(), new ConcurrentHashMap<>());
                List<Integer> allRoleId = Table_Murder_Row.getAllRoleId(getDramaId());
                for (Integer roleId : allRoleId) {
                    target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes()).put(roleId, new ArrayList<>());
                }
            }
        }
    }

    private void addSelectDraftIdToRoleId(List<Integer> draftIds) {
        if (!target.getDraftNumToSelectDraftIdToRoleId().containsKey(getRoomStateTimes())) {
            Map<Integer, Integer> newMap = new ConcurrentHashMap<>();
            target.getDraftNumToSelectDraftIdToRoleId().put(getRoomStateTimes(), newMap);
        }
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(getRoomStateTimes());
        for (Integer draftId : draftIds) {
            selectDraftIdToRoleId.put(draftId, 0);
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
        Map<Integer, List<Integer>> voteRoleIdToRoleId = target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes());
        if (!voteRoleIdToRoleId.get(roleId).contains(roomPlayerCtrl.getRoleId())) {
            voteRoleIdToRoleId.get(roleId).add(roomPlayerCtrl.getRoleId());
        }
    }

    @Override
    public int RemainNum() {
        int num = 0;
        Map<Integer, List<Integer>> voteRoleIdToRoleId = target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes());
        for (Map.Entry<Integer, List<Integer>> entry : voteRoleIdToRoleId.entrySet()) {
            num += entry.getValue().size();
        }
        //TODO
        return getRoomPlayerNum() - num;
    }

    @Override
    public Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId() {
        return target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes());
    }

    @Override
    public Map<Integer, List<Integer>> getVoteSearchTypeIdToPlayerRoleId() {
        return target.getVoteTypeIdToRoleId();
    }

    @Override
    public boolean isTimeCanReady() {
//        return System.currentTimeMillis() >= getTarget().getNextSTime();
        //TODO 打开判断
        return true;
    }

    @Override
    public boolean isVotedMurder(int roleId) {
        //TODO 需要通过配置控制投凶的幕数
        int size = target.getVoteNumToVoteRoleIdToRoleId().get(1).get(roleId).size();
        boolean flag = true;
        Map<Integer, List<Integer>> voteRoleIdToRoleId = target.getVoteNumToVoteRoleIdToRoleId().get(1);
        for (Map.Entry<Integer, List<Integer>> entry : voteRoleIdToRoleId.entrySet()) {
            if (entry.getKey() != roleId && entry.getValue().size() >= size) {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public int getRoomPlayerNum() {
        return target.getIdToRoomPlayerCtrl().size();
    }

    @Override
    public boolean isEmptyClue(String typeName) {
        boolean flag = true;
        List<Table_Search_Row> rows = Table_Search_Row.getSearchByTypeNameAndStateTimes(typeName, getRoomStateTimes(), getDramaId());
        for (Table_Search_Row row : rows) {
            if (containsClueId(row.getId())) {
                continue;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public List<Integer> canSelectRoleIds(int dramaId) {
        List<Integer> roleIds = new ArrayList<>();
        List<Integer> canSelectRoleIds = new ArrayList<>();
        for (RoomPlayerCtrl roomPlayerCtrl : target.getIdToRoomPlayerCtrl().values()) {
            if (roomPlayerCtrl.hasRole()) {
                roleIds.add(roomPlayerCtrl.getRoleId());
            }
        }
        for (Table_Acter_Row row : RootTc.get(Table_Acter_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                if (!roleIds.contains(row.getRoleId())) {
                    canSelectRoleIds.add(row.getRoleId());
                }
            }
        }
        return canSelectRoleIds;
    }

    @Override
    public List<Integer> canVoteSearchClueIds() {
        return target.getCanVoteSearchCuleId();
    }

    @Override
    public void voteSearch(RoomPlayerCtrl roomPlayerCtrl, String typeName) {
        int typeId = Table_SearchType_Row.getTypeIdByName(typeName, getDramaId());
        List<Integer> votes = target.getVoteTypeIdToRoleId().get(typeId);
        if (votes == null) {
            votes = new ArrayList<>();
            target.getVoteTypeIdToRoleId().put(typeId, votes);
        }
        if (!votes.contains(roomPlayerCtrl.getRoleId())) {
            votes.add(roomPlayerCtrl.getRoleId());
            roomPlayerCtrl.reduceVoteSrchTimes();
        } else {
            LOGGER.debug("已经投过这个证据 playerId={}, typeName={}", roomPlayerCtrl.getPlayerId(), typeName);
        }
    }

    @Override
    public int getVoteSearchClueId() {
        int maxCount = 0;
        int clueId = 0;
        for (Map.Entry<Integer, List<Integer>> entry : target.getVoteTypeIdToRoleId().entrySet()) {
            if (entry.getValue().size() > maxCount) {
                maxCount = entry.getValue().size();
                clueId = entry.getKey();
            }
        }
        return clueId;
    }

    @Override
    public Map<Integer, List<Integer>> clearVoteSearchTypeIdToPlayerRoleId() {
        Map<Integer, List<Integer>> voteTypeIdToRoleId = new ConcurrentHashMap<>();
        voteTypeIdToRoleId.putAll(target.getVoteTypeIdToRoleId());
        target.getVoteTypeIdToRoleId().clear();
        return voteTypeIdToRoleId;
    }

    @Override
    public void removeCanVoteSearchClue(int typeId) {
        target.getCanVoteSearchCuleId().remove(Integer.valueOf(typeId));
    }

    @Override
    public boolean containsSelectDraft(int typeId) {
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(getRoomStateTimes());
        return selectDraftIdToRoleId.containsKey(typeId);
    }

    @Override
    public void selectDraft(RoomPlayerCtrl roomPlayerCtrl, int draftId) {
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(getRoomStateTimes());
        if (selectDraftIdToRoleId.containsKey(draftId)) {
            selectDraftIdToRoleId.put(draftId, roomPlayerCtrl.getRoleId());
            roomPlayerCtrl.setSelectDraft(true);
        } else {
            LOGGER.debug("手慢了,被别人选走了 playerId={}, draftId={}", roomPlayerCtrl.getPlayerId(), draftId);
        }
    }

    @Override
    public List<Integer> canSelectDraftIds() {
        List<Integer> draftIds = new ArrayList<>();
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(getRoomStateTimes());
        draftIds.addAll(selectDraftIdToRoleId.keySet());
        return draftIds;
    }

    @Override
    public boolean isRightDraft(int draftNum) {
        List<Boolean> resultArr = new ArrayList<>();
        for (Table_Draft_Row value : RootTc.get(Table_Draft_Row.class).values()) {
            if (value.getDramaId() == getDramaId()) {
                Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(draftNum);
                Integer roleId = selectDraftIdToRoleId.get(value.getDraftID());
                resultArr.add(roleId == value.getRoleID());

            }
        }
        return !resultArr.contains(false);
    }

    @Override
    public boolean isRightVote(int voteNum) {
        int murderRoleId = Table_Murder_Row.getMurderRoleId(getDramaId(), voteNum);
        Map<Integer, List<Integer>> voteRoleIdToRoleId = target.getVoteNumToVoteRoleIdToRoleId().get(voteNum);
        int size = voteRoleIdToRoleId.get(murderRoleId).size();
        for (Map.Entry<Integer, List<Integer>> entry : voteRoleIdToRoleId.entrySet()) {
            if (entry.getValue().size() >= size && entry.getKey() != murderRoleId) {
                return false;
            }
        }
        return true;
    }

}
