package drama.gameServer.features.actor.room.ctrl;

import akka.actor.ActorRef;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.PlayerInnerMsg;
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
import dm.relationship.table.tableRows.Table_SubActer_Row;
import dm.relationship.table.tableRows.Table_SubMurder_Row;
import dm.relationship.topLevelPojos.simpleId.SimpleId;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import drama.gameServer.features.actor.room.enums.RoomStateEnum;
import drama.gameServer.features.actor.room.msg.In_PlayerSelectSubActerRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteListRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteRemainRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteRoomMsg;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.system.actor.DmActorSystem;
import drama.protos.EnumsProtos;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void createRoom(String roomId, SimplePlayer simplePlayer, int dramaId) {
        int simpleRoomId = SIMPLE_ID_DAO.nextSimpleId(SimpleIdTypeEnum.ROOM);
//        int simpleRoomId = 10001;
        Table_SceneList_Row tabRow = RootTc.get(Table_SceneList_Row.class).get(dramaId);
        String playerName = !StringUtils.isEmpty(simplePlayer.getPlayerName()) ? simplePlayer.getPlayerName() : "";
        target = new Room(roomId, dramaId, simplePlayer.getPlayerId(), simpleRoomId, playerName, tabRow);
        setNextStateAndTimes();
        setTarget(target);
        RoomPlayer roomPlayer = new RoomPlayer(simplePlayer, roomId);
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
        if (target.getIdToRoomPlayer().size() < plaNum) {
            return false;
        }
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
    public boolean checkPlayerFinishVoteSearch(int voteNum) {
        int count = 0;
        for (Map.Entry<Integer, List<Integer>> entry : getTarget().getVoteNumToVoteTypeIdToRoleId().get(voteNum).entrySet()) {
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
        //如果不是第一阶段了,说明剧本已经开始,设置剧本开始时间,只设置一次
        if (!RoomStateEnum.isFirstState(getRoomState(), getDramaId())) {
            if (target.getBeginTime() == 0) {
                getTarget().setBeginTime(System.currentTimeMillis());
            }
        }
        //如果是End阶段,设置剧本结束时间
        if (RoomStateEnum.isEndState(getRoomState())) {
            getTarget().setEndTime(System.currentTimeMillis());
        }
        for (Map.Entry<String, RoomPlayerCtrl> entry : target.getIdToRoomPlayerCtrl().entrySet()) {
            RoomPlayerCtrl roomPlayerCtrl = entry.getValue();
            //切换到下一个状态所有玩家把手放下取消准备状态
            roomPlayerCtrl.setReady(false);
            roomPlayerCtrl.setDub(-1);
            if (roomState == EnumsProtos.RoomStateEnum.SEARCH) {
                List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(getDramaId());
                int srchNumAndTimes = Table_Acter_Row.getSrchTimes(acterRowList, roomPlayerCtrl.getRoleId(), stateTimes);
                roomPlayerCtrl.setSrchTimes(srchNumAndTimes);
            } else if (roomState == EnumsProtos.RoomStateEnum.VOTESEARCH) {
                List<Table_Acter_Row> acterRowList = Table_Acter_Row.getTableActerRowByDramaId(getDramaId());
                int voteSrchTimes = Table_Acter_Row.getVoteSrchTimes(acterRowList, roomPlayerCtrl.getRoleId(), stateTimes);
                roomPlayerCtrl.setVoteSrchTimes(voteSrchTimes);
                addCanVoteSearchTypeIds();
            } else if (roomState == EnumsProtos.RoomStateEnum.DRAFT) {
                List<Integer> draftIds = Table_Draft_Row.getDraftIds(getDramaId());
                addSelectDraftIdToRoleId(draftIds);
                roomPlayerCtrl.setSelectDraft(false);
            } else if (roomState == EnumsProtos.RoomStateEnum.UNLOCK) {
                List<Integer> unlockClueIds = Table_RunDown_Row.getUnlockClueIds(getDramaId(), getRoomStateTimes(), getRoomState().toString());
                addClueIds(unlockClueIds);
            } else if (roomState == EnumsProtos.RoomStateEnum.VOTE) {
                roomPlayerCtrl.setVoteMurder(false);
                target.getVoteNumToVoteRoleIdToRoleId().put(getRoomStateTimes(), new ConcurrentHashMap<>());
                List<Integer> allRoleId = Table_Murder_Row.getAllRoleId(getDramaId());
                for (Integer roleId : allRoleId) {
                    target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes()).put(roleId, new ArrayList<>());
                }
            } else if (roomState == EnumsProtos.RoomStateEnum.SUBVOTE) {
                roomPlayerCtrl.setSubVoteMurder(false);
                target.getSubVoteNumToVoteRoleIdToRoleId().put(getRoomStateTimes(), new ConcurrentHashMap<>());
                List<Integer> allSubRoleId = Table_SubMurder_Row.getAllRoleId(getDramaId());
                for (Integer subRoleId : allSubRoleId) {
                    target.getSubVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes()).put(subRoleId, new ArrayList<>());
                }
            } else if (roomState == EnumsProtos.RoomStateEnum.SOLO) {
                setSoloIdx(MagicNumbers.DEFAULT_ZERO);
            }
        }
    }

    private void addCanVoteSearchTypeIds() {
        List<Integer> searchTypeIds = Table_SearchType_Row.getSearchTypeRowByStateTimes(getRoomStateTimes(), getDramaId());
        for (Integer searchTypeId : searchTypeIds) {
            if (!target.getCanVoteSearchTypeId().contains(searchTypeId)) {
                target.getCanVoteSearchTypeId().add(searchTypeId);
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
            selectDraftIdToRoleId.put(draftId, MagicNumbers.DEFAULT_ZERO);
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
            roomPlayerCtrl.setVoteMurder(true);
        }
    }

    @Override
    public int remainVoteNum() {
        int num = 0;
        Map<Integer, List<Integer>> voteRoleIdToRoleId = target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes());
        for (Map.Entry<Integer, List<Integer>> entry : voteRoleIdToRoleId.entrySet()) {
            num += entry.getValue().size();
        }
        //TODO 配置 replace getRoomPlayerNum()
        return getRoomPlayerNum() - num;
    }

    @Override
    public int remainSubVoteNum() {
        int num = 0;
        Map<Integer, List<Integer>> subVoteRoleIdToRoleId = target.getSubVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes());
        for (Map.Entry<Integer, List<Integer>> entry : subVoteRoleIdToRoleId.entrySet()) {
            num += entry.getValue().size();
        }
        //TODO 配置 replace getRoomPlayerNum()
        return getRoomPlayerNum() - num;
    }

    @Override
    public Map<Integer, List<Integer>> getVoteRoleIdToPlayerRoleId(int voteNum) {
        return target.getVoteNumToVoteRoleIdToRoleId().get(voteNum);
    }

    @Override
    public Map<Integer, List<Integer>> getSubVoteRoleIdToPlayerRoleId(int voteNum) {
        return target.getSubVoteNumToVoteRoleIdToRoleId().get(voteNum);
    }


    @Override
    public Map<Integer, List<Integer>> getVoteSearchTypeIdToPlayerRoleId(int voteNum) {
        return target.getVoteNumToVoteTypeIdToRoleId().get(voteNum);
    }

    @Override
    public boolean isTimeCanReady() {
        return System.currentTimeMillis() >= getTarget().getNextSTime();
    }

    @Override
    public int getCanReadyTime() {
        int time = 0;
        if (System.currentTimeMillis() < getTarget().getNextSTime()) {
            time = (int) ((getTarget().getNextSTime() - System.currentTimeMillis()) / DateUtils.MILLIS_PER_SECOND);
        }
        return time;
    }

    @Override
    public boolean isVotedMurder(int voteNum, int roleId) {
        //TODO 需要通过配置控制投凶的幕数
        int size = target.getVoteNumToVoteRoleIdToRoleId().get(voteNum).get(roleId).size();
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
            if (containsClueId(row.getIdx())) {
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
    public List<Integer> canVoteSearchTypeIds() {
        return target.getCanVoteSearchTypeId();
    }

    @Override
    public boolean hasVoteSearch(int roleId) {
        // 配置中ap总和-玩家的次数=第几次投票
        int voteNum = getClueIds().size();
        Map<Integer, List<Integer>> voteTypeIdToRoleId = target.getVoteNumToVoteTypeIdToRoleId().get(voteNum);
        if (voteTypeIdToRoleId == null) {
            voteTypeIdToRoleId = new ConcurrentHashMap<>();
            target.getVoteNumToVoteTypeIdToRoleId().put(voteNum, voteTypeIdToRoleId);
            return false;
        }
        for (Map.Entry<Integer, List<Integer>> entry : target.getVoteNumToVoteTypeIdToRoleId().get(voteNum).entrySet()) {
            for (Integer voteRoleId : entry.getValue()) {
                if (voteRoleId == roleId) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void voteSearch(RoomPlayerCtrl roomPlayerCtrl, String typeName, int voteNum) {
        int typeId = Table_SearchType_Row.getTypeIdByName(typeName, getDramaId());
        Map<Integer, List<Integer>> voteTypeIdToRoleId = target.getVoteNumToVoteTypeIdToRoleId().get(voteNum);
        if (voteTypeIdToRoleId == null) {
            voteTypeIdToRoleId = new ConcurrentHashMap<>();
            target.getVoteNumToVoteTypeIdToRoleId().put(voteNum, voteTypeIdToRoleId);
        }
        List<Integer> votes = voteTypeIdToRoleId.get(typeId);
        if (votes == null) {
            votes = new ArrayList<>();
            voteTypeIdToRoleId.put(typeId, votes);
        }
        if (!votes.contains(roomPlayerCtrl.getRoleId())) {
            votes.add(roomPlayerCtrl.getRoleId());
            roomPlayerCtrl.reduceVoteSrchTimes();
        } else {
            LOGGER.debug("已经投过这个证据 playerId={}, typeName={}", roomPlayerCtrl.getPlayerId(), typeName);
        }
    }

    @Override
    public int getVoteSearchClueId(int voteNum) {
        int maxCount = 0;
        int typeId = 0;
        for (Map.Entry<Integer, List<Integer>> entry : target.getVoteNumToVoteTypeIdToRoleId().get(voteNum).entrySet()) {
            if (entry.getValue().size() > maxCount) {
                maxCount = entry.getValue().size();
                typeId = entry.getKey();
            }
        }
        return Table_Search_Row.getRowIdByIdAndDramaId(typeId, getDramaId());
    }


    @Override
    public void removeCanVoteSearchTypeId(int clueId) {
        Table_Search_Row row = Table_Search_Row.getTableSearchRowByIdAndDramaId(clueId, getDramaId());
        target.getCanVoteSearchTypeId().remove(Integer.valueOf(row.getTypeid()));
    }

    @Override
    public boolean canSelectDraft(int draftId, int draftNum) {
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(draftNum);
        return selectDraftIdToRoleId.get(draftId) == MagicNumbers.DEFAULT_ZERO;
    }

    @Override
    public void selectDraft(RoomPlayerCtrl roomPlayerCtrl, int draftId, int draftNum) {
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(draftNum);
        if (selectDraftIdToRoleId.get(draftId) == MagicNumbers.DEFAULT_ZERO) {
            selectDraftIdToRoleId.put(draftId, roomPlayerCtrl.getRoleId());
            roomPlayerCtrl.setSelectDraft(true);
        } else {
            String msg = String.format("手慢了,被别人选走了selectDraft playerId=%s, draftId=%s", roomPlayerCtrl.getPlayerId(), draftId);
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg, EnumsProtos.ErrorCodeEnum.NO_DRAFT);
        }
    }

    @Override
    public List<Integer> canSelectDraftIds(int draftNum) {
        List<Integer> draftIds = new ArrayList<>();
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(draftNum);
        draftIds.addAll(selectDraftIdToRoleId.keySet());
        return draftIds;
    }

    @Override
    public int getDraftIdByDraftNum(int draftNum, RoomPlayerCtrl roomPlayerCtrl) {
        int draftId = 0;
        Map<Integer, Integer> selectDraftIdToRoleId = target.getDraftNumToSelectDraftIdToRoleId().get(draftNum);
        for (Map.Entry<Integer, Integer> entry : selectDraftIdToRoleId.entrySet()) {
            if (entry.getValue().equals(roomPlayerCtrl.getRoleId())) {
                draftId = entry.getKey();
            }
        }
        return draftId;
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

    @Override
    public boolean isBegin() {
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(getDramaId());
        //TODO 配置一个开始环节
        return false;
    }

    @Override
    public String getOnePlayer() {
        String playerId = null;
        Iterator<Map.Entry<String, RoomPlayer>> iterator = getTarget().getIdToRoomPlayer().entrySet().iterator();
        if (iterator.hasNext()) {
            playerId = iterator.next().getKey();
        }
        return playerId;
    }

    @Override
    public void setSoloIdx(int soloIdx) {
        target.setSoloIdx(soloIdx);
    }

    @Override
    public int getSoloIdx() {
        return target.getSoloIdx();
    }

    @Override
    public boolean checkPlayerFinishChoosDub() {
        for (Map.Entry<String, RoomPlayer> entry : getTarget().getIdToRoomPlayer().entrySet()) {
            if (entry.getValue().getIsDub() == MagicNumbers.DEFAULT_NEGATIVE_ONE) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Integer, String> subSelectList(int subNum) {
        Map<Integer, String> canSubSelect = new HashMap<>();
        List<Integer> allSubRoleIds = Table_SubActer_Row.getAllSubRoleIds(getDramaId(), subNum);
        for (Integer id : allSubRoleIds) {
            for (Map.Entry<String, RoomPlayer> entry : getTarget().getIdToRoomPlayer().entrySet()) {
                int subRoleId = 0;
                if (entry.getValue().getSubNumToSubRoleId().get(subNum) != null) {
                    subRoleId = entry.getValue().getSubNumToSubRoleId().get(subNum);
                }
                if ((subRoleId == id)) {
                    canSubSelect.put(id, entry.getKey());
                } else {
                    canSubSelect.put(id, "");
                }
            }
        }
        return canSubSelect;
    }

    @Override
    public void onSubSelect(int subRoleId, int subNum, String playerId) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        if (_hasSelectedSubRole(roomPlayerCtrl, subNum)) {
            String msg = String.format("玩家已经选过角色了 playerId=%s,  subRoleId=%s", playerId, roomPlayerCtrl.getSubRoleId(subNum));
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        if (canSelectSubRole(subRoleId, subNum)) {
            String msg = String.format("手慢了,被别人抢先了 playerId=%s,  subRoleId=%s", playerId, subRoleId);
            LOGGER.debug(msg);
            throw new BusinessLogicMismatchConditionException(msg);
        }
        selectSubRole(subRoleId, subNum, roomPlayerCtrl);
        for (String id : getTarget().getIdToRoomPlayer().keySet()) {
            In_PlayerSelectSubActerRoomMsg msg = new In_PlayerSelectSubActerRoomMsg(getDramaId(), id, playerId, subRoleId, subNum);
            sendToWorld(msg);
        }
    }

    @Override
    public void onSubVote(int subRoleId, int subNum, String playerId) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        if (roomPlayerCtrl.isSubVoteMurder()) {
            String msg = String.format("已经投过凶,这个请求应该被客户端挡住的! playerId=%s", playerId);
            LOGGER.debug(msg);
            return;
        }
        addSubVote(playerId, subRoleId, subNum);
        int remainNum = remainSubVoteNum();
        sendToWorld(new In_PlayerVoteRoomMsg(getDramaId(), roomPlayerCtrl.getPlayerId(), roomPlayerCtrl.getRoleId()));
        if (remainNum == 0) {
            for (String otherId : getTarget().getIdToRoomPlayer().keySet()) {
                In_PlayerSubVoteResultRoomMsg in_playerVoteResultRoomMsg = new In_PlayerSubVoteResultRoomMsg(otherId, getSubVoteRoleIdToPlayerRoleId(getRoomStateTimes()), getSuRoleIdToRoomPlayer(subNum), getDramaId());
                sendToWorld(in_playerVoteResultRoomMsg);
            }
        } else {
            //还有人未投完凶
            for (String otherId : getTarget().getIdToRoomPlayer().keySet()) {
                In_PlayerSubVoteRemainRoomMsg msg = new In_PlayerSubVoteRemainRoomMsg(getDramaId(), otherId, remainNum);
                sendToWorld(msg);
            }
        }
    }

    @Override
    public void onSubVoteResult(int subNum, String playerId) {
        if (getRoomState() != EnumsProtos.RoomStateEnum.ENDING) {
            for (String otherPlayerId : getTarget().getIdToRoomPlayer().keySet()) {
                In_PlayerSubVoteResultRoomMsg in_playerVoteResultRoomMsg = new In_PlayerSubVoteResultRoomMsg(otherPlayerId, getSubVoteRoleIdToPlayerRoleId(subNum), getSuRoleIdToRoomPlayer(subNum), getDramaId());
                sendToWorld(in_playerVoteResultRoomMsg);
            }
        } else {
            sendToWorld(new In_PlayerVoteResultRoomMsg(playerId, getSubVoteRoleIdToPlayerRoleId(subNum), getDramaId()));
        }
    }

    @Override
    public void onSubVoteList(int subNum, String playerId) {
        sendToWorld(new In_PlayerSubVoteListRoomMsg(playerId, getSubVoteRoleIdToPlayerRoleId(subNum), getSuRoleIdToRoomPlayer(subNum), getDramaId()));
    }

    private Map<Integer, RoomPlayer> getSuRoleIdToRoomPlayer(int subNum) {
        Map<Integer, RoomPlayer> map = new HashMap<>();
        for (Map.Entry<String, RoomPlayer> entry : getTarget().getIdToRoomPlayer().entrySet()) {
            map.put(entry.getValue().getSubNumToSubRoleId().get(subNum), entry.getValue());
        }
        return map;
    }


    public void addSubVote(String playerId, int subRoleId, int subNum) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        Map<Integer, List<Integer>> subVoteRoleIdToRoleId = target.getSubVoteNumToVoteRoleIdToRoleId().get(subNum);
        if (!subVoteRoleIdToRoleId.get(subRoleId).contains(roomPlayerCtrl.getRoleId())) {
            subVoteRoleIdToRoleId.get(subRoleId).add(roomPlayerCtrl.getRoleId());
            roomPlayerCtrl.setSubVoteMurder(true);
        }
    }

    private void selectSubRole(int subRoleId, int subNum, RoomPlayerCtrl roomPlayerCtrl) {
        roomPlayerCtrl.setSelectSubRole(subRoleId, subNum);
    }

    private boolean canSelectSubRole(int subRoleId, int subNum) {
        Map<Integer, String> subSelectList = subSelectList(subNum);
        return !subSelectList.get(subRoleId).equals("");
    }

    private boolean _hasSelectedSubRole(RoomPlayerCtrl roomPlayerCtrl, int subNum) {
        return roomPlayerCtrl.hasSelectedSubRole(subNum);
    }


    private void sendToWorld(PlayerInnerMsg msg) {
        sendToWorld(msg, ActorRef.noSender());
    }

    private void sendToWorld(PlayerInnerMsg msg, ActorRef actorRef) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, actorRef);
    }

    private void sendToWorld(InnerMsg msg) {
        sendToWorld(msg, ActorRef.noSender());
    }

    private void sendToWorld(InnerMsg msg, ActorRef actorRef) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, actorRef);
    }

}

