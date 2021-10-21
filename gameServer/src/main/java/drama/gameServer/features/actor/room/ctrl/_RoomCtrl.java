package drama.gameServer.features.actor.room.ctrl;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.IdAndCount;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.PlayerInnerMsg;
import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import dm.relationship.daos.DaoContainer;
import dm.relationship.daos.simpleId.SimpleIdDao;
import dm.relationship.enums.SimpleIdTypeEnum;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Auction_Row;
import dm.relationship.table.tableRows.Table_Draft_Row;
import dm.relationship.table.tableRows.Table_Item_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_NpcActer_Row;
import dm.relationship.table.tableRows.Table_Result_Row;
import dm.relationship.table.tableRows.Table_RunDown_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import dm.relationship.table.tableRows.Table_SubActer_Row;
import dm.relationship.table.tableRows.Table_SubMurder_Row;
import dm.relationship.table.tableRows.Table_Task_Row;
import dm.relationship.topLevelPojos.simpleId.SimpleId;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.room.enums.RoomState;
import drama.gameServer.features.actor.room.mc.extension.RoomPlayerExtension;
import drama.gameServer.features.actor.room.msg.In_AddRoomTimerMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSelectSubActerRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteListRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteRemainRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSubVoteRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_RemoveRoomTimerMsg;
import drama.gameServer.features.actor.room.pojo.Auction;
import drama.gameServer.features.actor.room.pojo.AuctionResult;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.actor.room.utils.RoomProtoUtils;
import drama.gameServer.features.extp.itemBag.ItemBagExtp;
import drama.gameServer.features.extp.itemBag.ctrl.ItemBagCtrl;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;
import drama.gameServer.features.extp.itemBag.utils.ItemBagUtils;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.EnumsProtos;
import drama.protos.EnumsProtos.ErrorCodeEnum;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.room.RoomProtos.Sm_Room;
import drama.protos.room.RoomProtos.Sm_Room.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.network.server.interfaces.Connection;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.PrivateMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class _RoomCtrl extends AbstractControler<Room> implements RoomCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_RoomCtrl.class);

    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final SimpleIdDao SIMPLE_ID_DAO = DaoContainer.getDao(SimpleId.class);

    private ActorContext context;
    private ActorRef actorRef;
    private ActorRef curSendActorRef;

    public ActorRef getCurSendActorRef() {
        return curSendActorRef;
    }

    public void setCurSendActorRef(ActorRef curSendActorRef) {
        this.curSendActorRef = curSendActorRef;
    }

    @Override
    public void onRoomExtpMsg(String playerId, RoomInnerExtpMsg msg) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        for (RoomPlayerExtension<?> roomPlayerExtension : roomPlayerCtrl.getAllExtensions().values()) {
            try {
                roomPlayerExtension.onRecvInnerExtpMsg(msg);
            } catch (Throwable t) {
                LOGGER.error("RoomPlayerExtension onRoomExtpMsg Error,ext ={}", roomPlayerExtension, t);
                continue;
            }
        }
    }


    public ActorContext getContext() {
        return context;
    }

    public void setContext(ActorContext context) {
        this.context = context;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    public void setActorRef(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

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
    public void createRoom(String roomId, SimplePlayer simplePlayer, int dramaId, Connection connection) {
        int simpleRoomId = SIMPLE_ID_DAO.nextSimpleId(SimpleIdTypeEnum.ROOM);
        Table_SceneList_Row tabRow = RootTc.get(Table_SceneList_Row.class).get(dramaId);
        String playerName = !StringUtils.isEmpty(simplePlayer.getPlayerName()) ? simplePlayer.getPlayerName() : "";
        target = new Room(roomId, dramaId, simplePlayer.getPlayerId(), simpleRoomId, playerName, tabRow);
        setNextStateAndTimes();
        setTarget(target);
        RoomPlayerCtrl roomPlayerCtrl = GlobalInjector.getInstance(RoomPlayerCtrl.class);
        RoomPlayer roomPlayer = roomPlayerCtrl.createRoomPlayer(simplePlayer, roomId, dramaId, connection);
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

    public TupleCell<String> getNexStateAndTimes() {
        TupleCell<String> next = null;
        Iterator<TupleCell<String>> it = this.getTarget().getRunRown().iterator();
        if (it.hasNext()) {
            next = it.next();
        }
        return next;
    }

    public void setNextStateAndTimes() {
        TupleCell<String> nextStateAndTimes = getAndRemoveNextStateAndTimes();
        EnumsProtos.RoomStateEnum roomState = RoomState.getRoomStateByName(nextStateAndTimes.get(TupleCell.FIRST));
        Integer stateTimes = Integer.valueOf(nextStateAndTimes.get(TupleCell.SECOND));
        long nextSTime = Table_RunDown_Row.getNextSTime(roomState.toString(), stateTimes, getDramaId());
        long nextLTime = Table_RunDown_Row.getNextLTime(roomState.toString(), stateTimes, getDramaId());
        target.setRoomState(roomState);
        target.setStateTimes(stateTimes);
        //设置下一个阶段解锁时间
        target.setNextSTime(System.currentTimeMillis() + nextSTime * DateUtils.MILLIS_PER_SECOND);
        target.setNextLTime(System.currentTimeMillis() + nextLTime * DateUtils.MILLIS_PER_SECOND);
        //如果不是第一阶段了,说明剧本已经开始,设置剧本开始时间,只设置一次
        if (!RoomState.isFirstState(getRoomState(), getDramaId())) {
            if (target.getBeginTime() == 0) {
                getTarget().setBeginTime(System.currentTimeMillis());
            }
        }
        //如果是End阶段,设置剧本结束时间
        if (RoomState.isEndState(getRoomState())) {
            getTarget().setEndTime(System.currentTimeMillis());
        }
        //预设一些房间的信息
        presetRoomInfo(roomState);
        //设置下一阶段玩家的一些信息
        presetRoomPlayerInfo(roomState, getRoomStateTimes());

    }

    private void presetRoomPlayerInfo(EnumsProtos.RoomStateEnum roomState, int stateTimes) {
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
            } else if (roomState == EnumsProtos.RoomStateEnum.DRAFT) {
                roomPlayerCtrl.setSelectDraft(false);
            } else if (roomState == EnumsProtos.RoomStateEnum.VOTE) {
                roomPlayerCtrl.setVoteMurder(false);
            } else if (roomState == EnumsProtos.RoomStateEnum.SUBVOTE) {
                roomPlayerCtrl.setSubVoteMurder(false);
            }
        }
    }

    private void presetRoomInfo(EnumsProtos.RoomStateEnum roomState) {
        if (roomState == EnumsProtos.RoomStateEnum.AUCTION) {
            _initAuctionInfo();
        } else if (roomState == EnumsProtos.RoomStateEnum.SOLO) {
            setSoloIdx(MagicNumbers.DEFAULT_ZERO);
        } else if (roomState == EnumsProtos.RoomStateEnum.VOTE) {
            _initVoteInfo();
        } else if (roomState == EnumsProtos.RoomStateEnum.SUBVOTE) {
            _initSubVoteInfo();
        } else if (roomState == EnumsProtos.RoomStateEnum.UNLOCK) {
            List<Integer> unlockClueIds = Table_RunDown_Row.getUnlockClueIds(getDramaId(), getRoomStateTimes(), getRoomState().toString());
            addClueIds(unlockClueIds);
        } else if (roomState == EnumsProtos.RoomStateEnum.DRAFT) {
            List<Integer> draftIds = Table_Draft_Row.getDraftIds(getDramaId());
            _addSelectDraftIdToRoleId(draftIds);
        } else if (roomState == EnumsProtos.RoomStateEnum.VOTESEARCH) {
            _addCanVoteSearchTypeIds();
        } else if (roomState == EnumsProtos.RoomStateEnum.AUCTIONRESULT) {
            _onAuctionResult();
        }
    }

    private void _onAuctionResult() {
        sendToWorld(new In_RemoveRoomTimerMsg(getTarget().getRoomId(), RoomState.AUCTIONRESULT));
        Map<Integer, Table_Auction_Row> auctions = Table_Auction_Row.getIdToAuctionRow(getDramaId());
        Map<Integer, Table_Item_Row> itemRows = Table_Item_Row.getAllRow(getDramaId());
        //计算收集拍卖结果扣除资源支付
        Iterator<Auction> it = target.getAuctionList().iterator();
        while (it.hasNext()) {
            Auction auction = it.next();
            Table_Auction_Row auctionRow = auctions.get(auction.getAuctionId());
            Table_Item_Row itemRow = itemRows.get(auction.getItemId());
            //计算拍卖结果并扣除资源进行支付
            AuctionResult auctionResult = getAuctionResultAndDeductConsume(auction, auctionRow, itemRow);
            if (auctionResult != null) {//当前竞拍品结果非流拍,并删除这条竞拍品
                it.remove();
            }
            //竞拍品列表中剩余的商品为流拍商品,累计到下一轮中
        }
    }

    /**
     * 计算拍卖结果并扣除资源进行支付
     *
     * @param auction
     * @param auctionRow
     * @param itemRow
     * @return 返回当前竞拍品结果
     */
    private AuctionResult getAuctionResultAndDeductConsume(Auction auction, Table_Auction_Row auctionRow, Table_Item_Row itemRow) {
        int maxPriceRoleId = 0;
        IdAndCount maxPrice = new IdAndCount(MagicNumbers.DEFAULT_ONE, MagicNumbers.DEFAULT_ZERO);
        AuctionResult auctionResult = null;
        for (Entry<Integer, IdAndCount> entry : auction.getRoleIdAndAuctionPice().entrySet()) {
            String playerId = getTarget().getRoleIdToPlayerId().get(entry.getKey());
            RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
            ItemBagCtrl itemBagCtrl = roomPlayerCtrl.getExtension(ItemBagExtp.class).getControlerForQuery();
            IdAndCount auctionPirce = entry.getValue();
            IdItemTypeEnum itemType = ItemBagUtils.getItemTypeById(auctionPirce.getId());
            if (itemType == IdItemTypeEnum.SP_MONEY) {
                //出价是耳环,直接break,结束判断,认定为最高价
                //由于客户端不好做选择实例id的界面,这里传入的耳环实例id都是同一个 服务器这边将耳环ID写死,只要背包内有这个耳环就可以删除
                if (ItemBagUtils.canRemoveEarRings(itemBagCtrl.getTarget(), MagicNumbers.ERARINGS_ID)) {
                    //从背包中获取一个可以删除的耳环的实例ID
                    int canRemoveEarRingItemId = ItemBagUtils.getCanRemoveEarRingItemId(itemBagCtrl.getTarget(), MagicNumbers.ERARINGS_ID);
                    maxPrice = new IdAndCount(canRemoveEarRingItemId, MagicNumbers.DEFAULT_ONE);
                    maxPriceRoleId = entry.getKey();
                    break;
                }
            } else if (itemType == IdItemTypeEnum.MONEY) {
                if (auctionPirce.getCount() > maxPrice.getCount()) {
                    //出价是否比最高金额更高
                    if (itemBagCtrl.canRemoveItem(auctionPirce)) {
                        maxPrice = auctionPirce;
                        maxPriceRoleId = entry.getKey();
                    }
                }
            }
        }
        if (maxPriceRoleId != MagicNumbers.DEFAULT_ZERO) {//出价最高者不是0,否则认为没有人竞拍这个拍品
            String playerId = getTarget().getRoleIdToPlayerId().get(maxPriceRoleId);
            RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
            ItemBagCtrl itemBagCtrl = roomPlayerCtrl.getExtension(ItemBagExtp.class).getControlerForQuery();
            auctionResult = new AuctionResult(auction.getItemId(), auction.getAuctionId(), maxPriceRoleId, maxPrice, itemRow.getItemName(), auctionRow.getAucItemName());
            deductConsume(auctionResult, itemBagCtrl);
            roomPlayerCtrl.addAuctionResult(getRoomStateTimes(), auctionResult);
        }
        return auctionResult;
    }


    /**
     * 扣除资源,并发放竞拍物品
     *
     * @param auctionResult
     * @param itemBagCtrl
     */
    private void deductConsume(AuctionResult auctionResult, ItemBagCtrl itemBagCtrl) {
        IdItemTypeEnum itemType = ItemBagUtils.getItemTypeById(auctionResult.getAuctionPrice().getId());
        if (itemType == IdItemTypeEnum.SP_MONEY) {
            ItemBagUtils.removeSpecialItem(itemBagCtrl.getTarget(), auctionResult.getAuctionPrice().getId());
        } else {
            ItemBagUtils.removePlainCell(itemBagCtrl.getTarget(), auctionResult.getAuctionPrice().getId(), auctionResult.getAuctionPrice().getCount());
        }
        ItemBagUtils.addOneSpecialItem(itemBagCtrl.getTarget(), auctionResult.getItemId(), IdItemTypeEnum.parseByItemTemplateId(auctionResult.getItemId()).isUseCell());

    }

    private void _initAuctionInfo() {
        //上一轮流拍的物品价格清零
        for (Auction auction : target.getAuctionList()) {
            auction.getRoleIdAndAuctionPice().clear();
        }
        //本轮拍卖物品添加
        List<Table_Auction_Row> rows = Table_Auction_Row.getAllAuctionByRunDown(getRoomStateTimes(), getDramaId());
        for (Table_Auction_Row row : rows) {
            Auction auction = new Auction(row.getAucItem(), row.getId(), row.getAucItemName());
            target.getAuctionList().add(auction);
        }
        //TODO
        // 读配置获取  强制结束本阶段时间
        long nextLTime = Table_RunDown_Row.getNextLTime(getRoomState().toString(), getRoomStateTimes(), getDramaId());
        long millisPerSecond = System.currentTimeMillis() + nextLTime * DateUtils.MILLIS_PER_SECOND;
        Date date = new Date(millisPerSecond);
        String timerName = getTarget().getRoomId();
        sendToWorld(new In_AddRoomTimerMsg(timerName, date, RoomState.AUCTIONRESULT));
    }


    private void _initVoteInfo() {
        target.getVoteNumToVoteRoleIdToRoleId().put(getRoomStateTimes(), new ConcurrentHashMap<>());
        List<Integer> allRoleId = Table_Murder_Row.getAllRoleId(getDramaId());
        for (Integer roleId : allRoleId) {
            target.getVoteNumToVoteRoleIdToRoleId().get(getRoomStateTimes()).put(roleId, new ArrayList<>());
        }
    }

    private void _initSubVoteInfo() {
        target.getSubVoteNumToVoteSubRoleIdToRoleId().put(getRoomStateTimes(), new ConcurrentHashMap<>());
        List<Integer> allSubRoleId = Table_SubMurder_Row.getAllRoleId(getDramaId());
        for (Integer subRoleId : allSubRoleId) {
            target.getSubVoteNumToVoteSubRoleIdToRoleId().get(getRoomStateTimes()).put(subRoleId, new ArrayList<>());
        }
    }

    private void _addCanVoteSearchTypeIds() {
        List<Integer> searchTypeIds = Table_SearchType_Row.getSearchTypeRowByStateTimes(getRoomStateTimes(), getDramaId());
        for (Integer searchTypeId : searchTypeIds) {
            if (!target.getCanVoteSearchTypeId().contains(searchTypeId)) {
                target.getCanVoteSearchTypeId().add(searchTypeId);
            }
        }
    }

    private void _addSelectDraftIdToRoleId(List<Integer> draftIds) {
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
        Map<Integer, List<Integer>> subVoteRoleIdToRoleId = target.getSubVoteNumToVoteSubRoleIdToRoleId().get(getRoomStateTimes());
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
        return target.getSubVoteNumToVoteSubRoleIdToRoleId().get(voteNum);
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
    public void onUnlockInfo(int voteNum, SimplePlayer simplePlayer) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(simplePlayer.getPlayerId());
        Sm_Room.Action action = Action.RESP_UNLOCKINFO;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        Sm_Room.Builder b = Sm_Room.newBuilder();
        response.setResult(true);
        b.setAction(action);
        boolean isNpc = true;
        //目前只有一次投凶
        int murderRoleId = getSubVoteMurder(voteNum);
        for (Entry<String, RoomPlayerCtrl> entry : getTarget().getIdToRoomPlayerCtrl().entrySet()) {
            if (entry.getValue().getSubRoleId(voteNum) == murderRoleId) {
                isNpc = false;
                ItemBag itemBag = entry.getValue().getExtension(ItemBagExtp.class).getControlerForQuery().getTarget();
                String task = Table_Task_Row.getUnlockTask(entry.getValue().getRoleId(), getDramaId());
                b.setUnlockInfo(RoomProtoUtils.createSmRoomUnlockInfo(entry.getValue().getRoleId(), task, itemBag, getDramaId()));
                response.setSmRoom(b.build());
                break;
            }
        }
        if (isNpc) {
            Table_NpcActer_Row npcRow = Table_NpcActer_Row.getNpcRow(getDramaId());
            String npcTask = npcRow.getNpcTask();
            List<TupleCell<String>> prop = npcRow.getProp();
            b.setUnlockInfo(RoomProtoUtils.createNpcSmRoomUnlockInfo(npcTask, prop, getDramaId()));
            response.setSmRoom(b.build());
        }
        roomPlayerCtrl.send(response.build());
    }

    @Override
    public void onAuctionResult(String playerId) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        List<AuctionResult> auctionResultList = roomPlayerCtrl.getAuctionResult(getRoomStateTimes());
        int sumPrice = _getSumPrice(auctionResultList);
        Sm_Room.Action action = Action.RESP_AUCTION_RESULT;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        Sm_Room.Builder b = Sm_Room.newBuilder();
        response.setResult(true);
        b.setAction(action);
        b.setSumPic(sumPrice);
        b.addAllAuctionInfo(RoomProtoUtils.createSmRoomAuctionInfoList(auctionResultList, getDramaId()));
        response.setSmRoom(b.build());
        roomPlayerCtrl.send(response.build());
    }


    private int _getSumPrice(List<AuctionResult> auctionResultList) {
        int sumPrice = 0;
        for (AuctionResult auctionResult : auctionResultList) {
            int id = auctionResult.getAuctionPrice().getId();
            IdItemTypeEnum itemType = ItemBagUtils.getItemTypeById(id);
            if (itemType == IdItemTypeEnum.MONEY) {
                sumPrice += auctionResult.getAuctionPrice().getCount();
            }
        }
        return sumPrice;
    }


    @Override
    public void onAuctionInfo(String playerId) {
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        Sm_Room.Action action = Action.RESP_AUCTION_INFO;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        Sm_Room.Builder b = Sm_Room.newBuilder();
        response.setResult(true);
        b.setAction(action);
        List<Auction> auctions = target.getAuctionList();
        for (Auction auction : auctions) {
            IdAndCount auctionPrice = null;
            if (auction.getRoleIdAndAuctionPice().get(roomPlayerCtrl.getRoleId()) == null) {
                auctionPrice = new IdAndCount(MagicNumbers.DEFAULT_ONE, MagicNumbers.DEFAULT_ZERO);
            } else {
                auctionPrice = auction.getRoleIdAndAuctionPice().get(roomPlayerCtrl.getRoleId());
            }
            b.addAuctionInfo(RoomProtoUtils.createSmRoomAuctionInfo(auction.getItemId(), auction.getAuctionId(), auctionPrice, auction.getAuctionName(), getDramaId()));
        }
        response.setSmRoom(b.build());
        roomPlayerCtrl.send(response.build());
    }

    @Override
    public void onAuction(String playerId, int idOrTpId, int price, int auctionId) {
        Sm_Room.Action action = Action.RESP_AUCTION;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        Sm_Room.Builder b = Sm_Room.newBuilder();
        response.setResult(true);
        b.setAction(action);
        RoomPlayerCtrl roomPlayerCtrl = getRoomPlayerCtrl(playerId);
        ItemBagCtrl itemBagCtrl = roomPlayerCtrl.getExtension(ItemBagExtp.class).getControlerForQuery();
        _checkPriceEnough(idOrTpId, price, itemBagCtrl);
        IdAndCount auctionPirce = new IdAndCount(idOrTpId, price);
        List<Auction> auctions = getTarget().getAuctionList();
        for (Auction auction : auctions) {
            if (auction.getAuctionId() == auctionId) {
                auction.getRoleIdAndAuctionPice().put(roomPlayerCtrl.getRoleId(), auctionPirce);
                b.addAuctionInfo(RoomProtoUtils.createSmRoomAuctionInfo(auction.getItemId(), auction.getAuctionId(), auctionPirce, auction.getAuctionName(), getDramaId()));
                break;
            }
        }
        response.setSmRoom(b.build());
        roomPlayerCtrl.send(response.build());
    }


    private void _checkPriceEnough(int idOrTpId, int price, ItemBagCtrl itemBagCtrl) {
        boolean enough = false;
        //9999代表耳环,判断身上是否有耳环
        IdItemTypeEnum itemType = ItemBagUtils.getItemTypeById(idOrTpId);
        IdAndCount idAndCount = new IdAndCount(idOrTpId, price);
        if (itemBagCtrl.canRemoveItem(idAndCount)) {
            enough = true;
        }
        if (!enough) {
            String msg = String.format("资源不足, price=%", price);
            throw new BusinessLogicMismatchConditionException(msg, ErrorCodeEnum.NOT_ENOUGH);
        }
    }

    private int getSubVoteMurder(int voteNum) {
        int roleId = 0;
        int count = 0;
        for (Entry<Integer, List<Integer>> entry : target.getSubVoteNumToVoteSubRoleIdToRoleId().get(voteNum).entrySet()) {
            if (entry.getValue().size() > count) {
                count = entry.getValue().size();
                roleId = entry.getKey();
            }
        }
        return roleId;
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
            int subRoleId = MagicNumbers.DEFAULT_ZERO;
            String playerId = "";
            for (Map.Entry<String, RoomPlayer> entry : getTarget().getIdToRoomPlayer().entrySet()) {
                if (entry.getValue().getSubNumToSubRoleId().get(subNum) != null) {
                    subRoleId = entry.getValue().getSubNumToSubRoleId().get(subNum);
                }
                if (subRoleId == id) {
                    playerId = entry.getKey();
                    break;
                }
            }
            canSubSelect.put(id, playerId);
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
        sendToWorld(new In_PlayerSubVoteRoomMsg(getDramaId(), roomPlayerCtrl.getPlayerId(), roomPlayerCtrl.getRoleId()));
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
        Map<Integer, List<Integer>> subVoteRoleIdToRoleId = target.getSubVoteNumToVoteSubRoleIdToRoleId().get(subNum);
        if (!subVoteRoleIdToRoleId.get(subRoleId).contains(roomPlayerCtrl.getRoleId())) {
            subVoteRoleIdToRoleId.get(subRoleId).add(roomPlayerCtrl.getRoleId());
            roomPlayerCtrl.setSubVoteMurder(true);
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
        context.actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, actorRef);
    }

    private void sendToWorld(InnerMsg msg) {
        sendToWorld(msg, ActorRef.noSender());
    }

    private void sendToWorld(InnerMsg msg, ActorRef actorRef) {
        context.actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(msg, actorRef);
    }

}

