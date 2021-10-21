package drama.gameServer.features.actor.room.utils;

import dm.relationship.base.IdAndCount;
import dm.relationship.base.MagicNumbers;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Draft_Row;
import dm.relationship.table.tableRows.Table_Item_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_NpcActer_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import dm.relationship.table.tableRows.Table_SubActer_Row;
import dm.relationship.table.tableRows.Table_SubMurder_Row;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.room.enums.RoomState;
import drama.gameServer.features.actor.room.msg.In_PlayerIsVotedRoomMsg;
import drama.gameServer.features.actor.room.pojo.AuctionResult;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;
import drama.gameServer.features.extp.itemBag.pojo.PlainCell;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;
import drama.gameServer.features.extp.itemBag.utils.ItemBagCtrlProtos;
import drama.gameServer.features.extp.itemBag.utils.ItemBagUtils;
import drama.protos.room.ItemBagProtos.Sm_ItemBag_PlainCell;
import drama.protos.room.ItemBagProtos.Sm_ItemBag_SpecialCell;
import drama.protos.room.RoomProtos.Cm_Room;
import drama.protos.room.RoomProtos.Sm_Room;
import drama.protos.room.RoomProtos.Sm_Room_AuctionInfo;
import drama.protos.room.RoomProtos.Sm_Room_Clue;
import drama.protos.room.RoomProtos.Sm_Room_Draft;
import drama.protos.room.RoomProtos.Sm_Room_Info;
import drama.protos.room.RoomProtos.Sm_Room_Murder;
import drama.protos.room.RoomProtos.Sm_Room_Player;
import drama.protos.room.RoomProtos.Sm_Room_RoleInfo;
import drama.protos.room.RoomProtos.Sm_Room_SearchType;
import drama.protos.room.RoomProtos.Sm_Room_SubRoleInfo;
import drama.protos.room.RoomProtos.Sm_Room_SubVote;
import drama.protos.room.RoomProtos.Sm_Room_UnlockInfo;
import drama.protos.room.RoomProtos.Sm_Room_Vote;
import drama.protos.room.RoomProtos.Sm_Room_Vote_Search;
import org.apache.commons.lang3.time.DateUtils;
import ws.common.table.table.interfaces.cell.TupleCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomProtoUtils {
    public static Sm_Room createSmRoomByActionWithoutRoomPlayer(Room room, Sm_Room.Action action) {
        Sm_Room.Builder b = Sm_Room.newBuilder();
        b.setAction(action);
        Sm_Room_Info broom = createSmRoomInfoWithoutRoomPlayer(room);
        b.addRoomInfos(broom);
        return b.build();
    }

    public static Sm_Room createSmRoomByAction(Room room, Sm_Room.Action action) {
        Sm_Room.Builder b = Sm_Room.newBuilder();
        b.setAction(action);
        Sm_Room_Info broom = createSmRoomInfo(room);
        b.addRoomInfos(broom);
        return b.build();
    }


    public static Sm_Room_Info createSmRoomInfo(Room room) {
        Sm_Room_Info.Builder broom = Sm_Room_Info.newBuilder();
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(room.getDramaId());
        setSmRoomInfo(room, broom, row);
        for (Map.Entry<String, RoomPlayer> entry : room.getIdToRoomPlayer().entrySet()) {
            broom.addRoomPlayer(createSmRoomPlayer(entry.getValue(), room.getDramaId()));
        }
        return broom.build();
    }

    public static List<Sm_Room_Player> createSmRoomPlayerList(Room room) {
        List<Sm_Room_Player> arr = new ArrayList<>();
        for (Map.Entry<String, RoomPlayer> entry : room.getIdToRoomPlayer().entrySet()) {
            Sm_Room_Player smRoomPlayer = createSmRoomPlayer(entry.getValue(), room.getDramaId());
            arr.add(smRoomPlayer);
        }
        return arr;
    }

    public static Sm_Room_Info createSmRoomInfoWithoutRoomPlayer(Room room) {
        Sm_Room_Info.Builder broom = Sm_Room_Info.newBuilder();
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(room.getDramaId());
        setSmRoomInfo(room, broom, row);
        return broom.build();
    }

    private static void setSmRoomInfo(Room room, Sm_Room_Info.Builder broom, Table_SceneList_Row row) {
        broom.setDramaId(room.getDramaId());
        broom.setRoomdId(room.getRoomId());
        broom.setSimpleRoomId(room.getSimpleRoomId());
        broom.setMasterId(room.getMasterId());
        broom.setRoomPlayerNum(room.getIdToRoomPlayer().size());
        broom.setDramaName(room.getDramaName());
        broom.setState(room.getRoomState());
        broom.setStateTimes(room.getStateTimes());
        broom.setSrchNum(room.getSrchNum());
        broom.setRoomPlayerSize(room.getPlayerNum());
        broom.setPic(row.getPic());
        broom.setRate(row.getRate());
        broom.setDiff(row.getDiff());
        broom.setTime(row.getTime());
        broom.setMasterName(room.getMasterName());
        broom.setNextLTime(getSwitchTime(room.getNextLTime()));
        broom.setIsInitState(RoomState.isFirstState(room.getRoomState(), room.getDramaId()));
        if (room.getBeginTime() != MagicNumbers.DEFAULT_ZERO) {
            broom.setDuration(System.currentTimeMillis() - room.getBeginTime());
        } else {
            broom.setDuration(MagicNumbers.DEFAULT_ZERO);
        }
    }

    private static int getSwitchTime(long nextLTime) {
        int time = 0;
        if (System.currentTimeMillis() < nextLTime) {
            time = (int) ((nextLTime - System.currentTimeMillis()) / DateUtils.MILLIS_PER_SECOND);
        }
        return time;
    }

    public static Sm_Room_Vote createSmRoomVote(int murderRoleId, String name, String rolePic, String roleBigPic, boolean isMurder, boolean isTruth, List<String> votePic, List<Integer> voteRoleIds) {
        Sm_Room_Vote.Builder b = Sm_Room_Vote.newBuilder();
        b.setRoleId(murderRoleId);
        b.setRoleName(name);
        b.setRolePic(rolePic);
        b.setRoleBigPic(roleBigPic);
        b.setIsTruth(isTruth);
        b.setIsMurder(isMurder);
        b.addAllVotePic(votePic);
        b.addAllVoteRoleId(voteRoleIds);
        return b.build();
    }

    public static Sm_Room_Vote_Search createSmRoomVoteSearch(String name, String rolePic, List<String> votePic) {
        Sm_Room_Vote_Search.Builder b = Sm_Room_Vote_Search.newBuilder();
        b.setTypeName(name);
        b.setTypePic(rolePic);
        b.addAllVotePic(votePic);
        return b.build();
    }

    public static List<Sm_Room_Vote> createSmRoomVoteList(Map<Integer, List<Integer>> roleIdToPlayerRoleId, int dramaId) {
        List<Sm_Room_Vote> voteList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : roleIdToPlayerRoleId.entrySet()) {
            int roleId = entry.getKey();
            List<Integer> voteRoleIds = entry.getValue();
            Table_Murder_Row murderRow = Table_Murder_Row.getMurderRowByRoleId(roleId, dramaId);
            int murderRoleId = murderRow.getRoleId();
            String roleName = murderRow.getRoleName();
            String rolePic = murderRow.getRolePic();
            String roleBigPic = murderRow.getRoleBigPic();
            boolean isMurder = murderRow.isMurder();
            boolean isTruth = murderRow.isTruth();
            List<String> votePic = Table_Murder_Row.getRolePicByRoleIds(voteRoleIds, dramaId);
            voteList.add(createSmRoomVote(murderRoleId, roleName, rolePic, roleBigPic, isMurder, isTruth, votePic, voteRoleIds));
        }
        return voteList;
    }

    public static List<Sm_Room_SubVote> createSmRoomSubVoteList(Map<Integer, List<Integer>> subRoleIdToPlayerRoleId, Map<Integer, RoomPlayer> subRoleIdToRoomPlayer, int dramaId) {
        List<Sm_Room_SubVote> voteList = new ArrayList<>();
        Map<Integer, Table_Acter_Row> allRoleIdToRowByDramaId = Table_Acter_Row.getAllRoleIdToRowByDramaId(dramaId);
        for (Map.Entry<Integer, List<Integer>> entry : subRoleIdToPlayerRoleId.entrySet()) {
            int subRoleId = entry.getKey();
            RoomPlayer murderRoomPlayer = subRoleIdToRoomPlayer.get(subRoleId);
            int murderRoleId;
            String murderName;
            String murderPic;
            String mudrerProfile;
            if (_isNpc(murderRoomPlayer)) {
                //TODO npc的读取规则,需要和策划商量定一下,暂时只取匹配到剧本id的第一个
                Table_NpcActer_Row npcRow = Table_NpcActer_Row.getNpcRow(dramaId);
                murderRoleId = npcRow.getRoleId();
                murderName = npcRow.getName();
                murderPic = npcRow.getPic();
                mudrerProfile = npcRow.getProfile();
            } else {
                murderRoleId = murderRoomPlayer.getRoleId();
                Table_Acter_Row row = allRoleIdToRowByDramaId.get(murderRoleId);
                murderName = row.getName();
                murderPic = row.getPic();
                mudrerProfile = row.getProfile();
            }
            // TODO 需要NPC单建一张表获取NPC的名字头像和立绘

            List<Integer> voteRoleIds = entry.getValue();
            Table_SubMurder_Row murderRow = Table_SubMurder_Row.getMurderRowByRoleId(subRoleId, dramaId);
            String subRoleName = murderRow.getSubRoleName();
            String subRolePic = murderRow.getSubRolePic();
            boolean isSubMurder = murderRow.getMurder();
            boolean isSubTruth = murderRow.isTruth();
            List<String> votePic = Table_Acter_Row.getRolePicByRoleIds(voteRoleIds, dramaId);
            voteList.add(createSmRoomSubVote(murderRoleId, murderName, murderPic, mudrerProfile, subRoleId, subRoleName, subRolePic, isSubMurder, isSubTruth, votePic, voteRoleIds));
        }
        return voteList;
    }

    private static boolean _isNpc(RoomPlayer murderRoomPlayer) {
        return murderRoomPlayer == null;
    }

    public static Sm_Room_SubVote createSmRoomSubVote(int murderRoleId, String murderName, String murderPic, String mudrerProfile, int subRoleId,//
                                                      String subRoleName, String subRolePic, boolean isSubMurder, boolean isSubTruth, //
                                                      List<String> votePic, List<Integer> voteRoleIds) {
        Sm_Room_SubVote.Builder b = Sm_Room_SubVote.newBuilder();
        b.setRoleId(murderRoleId);
        b.setRoleName(murderName);
        b.setRoleProfile(mudrerProfile);
        b.setSubRoleId(subRoleId);
        b.setSubRoleName(subRoleName);
        b.setSubRolePic(subRolePic);
        b.setIsSubMurder(isSubMurder);
        b.setIsSubTruth(isSubTruth);
        b.setRolePic(murderPic);
        b.addAllVotePic(votePic);
        b.addAllVoteRoleId(voteRoleIds);
        return b.build();
    }

    public static List<Sm_Room_Vote_Search> createSmRoomVoteSearchList(Map<Integer, List<Integer>> roleIdToPlayerRoleId, int dramaId) {
        List<Sm_Room_Vote_Search> voteList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : roleIdToPlayerRoleId.entrySet()) {
            int roleId = entry.getKey();
            List<Integer> voteRoleIds = entry.getValue();
            Table_SearchType_Row row = Table_SearchType_Row.getSearchTypeRow(roleId, dramaId);
            String roleName = row.getTypename();
            String rolePic = row.getTypePic();
            List<String> votePic = Table_Acter_Row.getRolePicByRoleIds(voteRoleIds, dramaId);
            voteList.add(createSmRoomVoteSearch(roleName, rolePic, votePic));
        }
        return voteList;
    }

    public static Sm_Room_Murder.Builder createSmRoomMurder(In_PlayerIsVotedRoomMsg msg) {
        Sm_Room_Murder.Builder bm = Sm_Room_Murder.newBuilder();
        bm.setIsVoted(msg.isVoted());
        bm.setRoleId(msg.getMurderRoleId());
        return bm;
    }

    public static Sm_Room_Player createSmRoomPlayer(RoomPlayer roomPlayer, int dramaId) {
        Sm_Room_Player.Builder bRoomPlayer = Sm_Room_Player.newBuilder();
        bRoomPlayer.setPlayerId(roomPlayer.getPlayerId());
        bRoomPlayer.setIsReady(roomPlayer.isReady());
        bRoomPlayer.setRoleId(roomPlayer.getRoleId());
        bRoomPlayer.setIsDub(roomPlayer.getIsDub());
        bRoomPlayer.setSrchTimes(roomPlayer.getSrchTimes());
        bRoomPlayer.setVoteSrchTimes(roomPlayer.getVoteSrchTimes());
        bRoomPlayer.setPlayerName(roomPlayer.getPlayerName());
        bRoomPlayer.setPlayerIcon(roomPlayer.getPlayerIcon());
        if (roomPlayer.getRoleId() != MagicNumbers.DEFAULT_ZERO) {
            Table_Acter_Row row = Table_Acter_Row.getTableActerRowByRoleId(roomPlayer.getRoleId(), dramaId);
            bRoomPlayer.setPic(row.getPic());
            bRoomPlayer.setProfile(row.getProfile());
            bRoomPlayer.setRoleName(row.getName());
            bRoomPlayer.addAllBgm(row.getBgm());
            bRoomPlayer.addAllScene(row.getScene());
        }
        bRoomPlayer.addAllSubRoleInfo(createSmRoomSubRoleInfoList(roomPlayer.getSubNumToSubRoleId(), dramaId));
        return bRoomPlayer.build();
    }

    public static List<Sm_Room_SubRoleInfo> createSmRoomSubRoleInfoList(Map<Integer, Integer> subNumToSubRoleIds, int dramaId) {
        List<Sm_Room_SubRoleInfo> arr = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : subNumToSubRoleIds.entrySet()) {
            Sm_Room_SubRoleInfo smRoomSubRoleInfo = createSmRoomSubRoleInfo(entry.getValue(), "", dramaId, entry.getKey());
            arr.add(smRoomSubRoleInfo);
        }
        return arr;
    }

    public static Sm_Room_Player createSoloSmRoomPlayer(RoomPlayer roomPlayer, int soloDramaId, int dramaId) {
        Sm_Room_Player.Builder bRoomPlayer = Sm_Room_Player.newBuilder();
        bRoomPlayer.setPlayerId(roomPlayer.getPlayerId());
        bRoomPlayer.setIsReady(roomPlayer.isReady());
        bRoomPlayer.setRoleId(roomPlayer.getRoleId());
        bRoomPlayer.setIsDub(roomPlayer.getIsDub());
        bRoomPlayer.setSrchTimes(roomPlayer.getSrchTimes());
        bRoomPlayer.setSoloDramaId(soloDramaId);
        bRoomPlayer.setVoteSrchTimes(roomPlayer.getVoteSrchTimes());
        bRoomPlayer.setPlayerName(roomPlayer.getPlayerName());
        bRoomPlayer.setPlayerIcon(roomPlayer.getPlayerIcon());
        if (roomPlayer.getRoleId() != MagicNumbers.DEFAULT_ZERO) {
            Table_Acter_Row row = Table_Acter_Row.getTableActerRowByRoleId(roomPlayer.getRoleId(), dramaId);
            bRoomPlayer.setPic(row.getPic());
            bRoomPlayer.setProfile(row.getProfile());
            bRoomPlayer.setRoleName(row.getName());
            bRoomPlayer.addAllBgm(row.getBgm());
            bRoomPlayer.addAllScene(row.getScene());
        }
        return bRoomPlayer.build();
    }

    public static List<Sm_Room_Clue> createSmRoomClueList(List<Integer> clueIds, int dramaId) {
        List<Sm_Room_Clue> arr = new ArrayList<>();
        for (Integer clueId : clueIds) {
            Sm_Room_Clue.Builder bClue = createSmRoomClue(clueId, dramaId);
            arr.add(bClue.build());
        }
        return arr;
    }

    public static Sm_Room_Clue.Builder createSmRoomClue(int idx, int dramaId) {
        Sm_Room_Clue.Builder bClue = Sm_Room_Clue.newBuilder();
        Table_Search_Row row = Table_Search_Row.getTableSearchRowByIdAndDramaId(idx, dramaId);
        Table_SearchType_Row searchTypeRow = Table_SearchType_Row.getSearchTypeRow(row.getTypeid(), dramaId);
        bClue.setTypeName(searchTypeRow.getTypename());
        bClue.setLine(row.getLine());
        bClue.setSrchNum(row.getSrchNum());
        bClue.setPic(row.getPic());
        bClue.addAllDetailPic(row.getDetailPic());
        bClue.setSound(row.getSound());
        return bClue;
    }


    public static Sm_Room.Builder setAction(Sm_Room.Builder b, Cm_Room cm_room) {
        if (cm_room.getAction().getNumber() == Cm_Room.Action.CREAT_VALUE) {
            b.setAction(Sm_Room.Action.RESP_CREATE);
        } else if (cm_room.getAction().getNumber() == Cm_Room.Action.JION_VALUE) {
            b.setAction(Sm_Room.Action.RESP_JION);
        } else if (cm_room.getAction().getNumber() == Cm_Room.Action.QUIT_VALUE) {
            b.setAction(Sm_Room.Action.RESP_QUIT);
        } else if (cm_room.getAction().getNumber() == Cm_Room.Action.SYNC_VALUE) {
            b.setAction(Sm_Room.Action.RESP_SYNC);
        } else if (cm_room.getAction().getNumber() == Cm_Room.Action.ANSWER_VALUE) {
            b.setAction(Sm_Room.Action.RESP_ANSWER);
        } else if (cm_room.getAction().getNumber() == Cm_Room.Action.READY_VALUE) {
            b.setAction(Sm_Room.Action.RESP_READY);
        }
        return b;
    }

    public static List<Sm_Room_SearchType> createSearchType(Map<String, String> typeById) {
        List<Sm_Room_SearchType> arr = new ArrayList<>();
        for (Map.Entry<String, String> entry : typeById.entrySet()) {
            Sm_Room_SearchType.Builder b = Sm_Room_SearchType.newBuilder();
            b.setTypeName(entry.getKey());
            b.setTypePic(entry.getValue());
            arr.add(b.build());
        }
        return arr;
    }

    public static List<Sm_Room_RoleInfo> createSmRoomRoleInfoList(List<Integer> roleIds, int dramaId) {
        List<Sm_Room_RoleInfo> arr = new ArrayList<>();
        for (Table_Acter_Row row : RootTc.get(Table_Acter_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                arr.add(createSmRoomRoleInfo(row.getRoleId(), dramaId, !roleIds.contains(row.getRoleId())));
            }
        }
        return arr;
    }

    public static Sm_Room_RoleInfo createSmRoomRoleInfo(int roleId, int dramaId, boolean selected) {
        Table_Acter_Row row = Table_Acter_Row.getTableActerRowByRoleId(roleId, dramaId);
        Sm_Room_RoleInfo.Builder b = Sm_Room_RoleInfo.newBuilder();
        b.setRoleId(row.getRoleId());
        b.setName(row.getName());
        b.setSex(row.getSex());
        b.setPic(row.getPic());
        b.setProfile(row.getProfile());
        b.addAllBgm(row.getBgm());
        b.setSelected(selected);
        return b.build();
    }

    public static List<Sm_Room_Draft> createSmRoomDraftList(List<Table_Draft_Row> rows) {
        List<Sm_Room_Draft> draftList = new ArrayList<>();
        for (Table_Draft_Row row : rows) {
            draftList.add(createSmRoomDraft(row));
        }
        return draftList;
    }

    public static Sm_Room_Draft createSmRoomDraft(Table_Draft_Row row) {
        Sm_Room_Draft.Builder b = Sm_Room_Draft.newBuilder();
        b.setDraftId(row.getDraftID());
        b.setDraftPic(row.getDraftPic());
        b.setDraftPoster(row.getDraftPoster());
        return b.build();
    }


    public static List<Sm_Room_SubRoleInfo> createSmRoomSubRoleInfoList(Map<Integer, String> canSubSelectIds, int dramaId, int subNum) {
        List<Sm_Room_SubRoleInfo> arr = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : canSubSelectIds.entrySet()) {
            arr.add(createSmRoomSubRoleInfo(entry.getKey(), entry.getValue(), dramaId, subNum));
        }
        return arr;
    }

    public static Sm_Room_SubRoleInfo createSmRoomSubRoleInfo(int subRoleId, String playerId, int dramaId, int subNum) {
        Sm_Room_SubRoleInfo.Builder b = Sm_Room_SubRoleInfo.newBuilder();
        Table_SubActer_Row row = Table_SubActer_Row.getRowById(dramaId, subRoleId, subNum);
        b.setPlayerId(playerId);
        b.setSubName(row.getSubName());
        b.setSubRoleId(subRoleId);
        b.setSubPic(row.getSubPic());
        b.setSubScene(row.getSubScene());
        return b.build();
    }

    public static Sm_Room_UnlockInfo createSmRoomUnlockInfo(int roleId, String task, ItemBag itemBag, int dramaId) {
        Table_Acter_Row row = Table_Acter_Row.getTableActerRowByRoleId(roleId, dramaId);
        Sm_Room_UnlockInfo.Builder b = Sm_Room_UnlockInfo.newBuilder();
        Sm_ItemBag_PlainCell.Builder b$Plain = Sm_ItemBag_PlainCell.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        itemBag.getTpIdToPlainCell().values().forEach(plainCell -> {
            ItemBagCtrlProtos.addPlain(plainCell, b, b$Plain, itemBag.getDramaId());
        });
        // 背包中的特殊物品

        itemBag.getIdToSpecialCell().values().forEach(specialCell -> {
            ItemBagCtrlProtos.addSpecial(specialCell, b, b$Special, itemBag.getDramaId());
        });
        b.setRoleName(row.getName());
        b.setRolePic(row.getProfile());
        b.setTask(task);
        b.addSpecialCells(b$Special.build());
        b.addPlainCells(b$Plain.build());
        return b.build();
    }

    public static Sm_Room_UnlockInfo createNpcSmRoomUnlockInfo(String task, List<TupleCell<String>> prop, int dramaId) {
        Table_NpcActer_Row npcRow = Table_NpcActer_Row.getNpcRow(dramaId);
        Sm_Room_UnlockInfo.Builder b = Sm_Room_UnlockInfo.newBuilder();
        Sm_ItemBag_PlainCell.Builder b$Plain = Sm_ItemBag_PlainCell.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        for (TupleCell<String> tupleCell : prop) {
            Integer id = Integer.valueOf(tupleCell.get(TupleCell.FIRST));
            Integer count = Integer.valueOf(tupleCell.get(TupleCell.SECOND));
            IdItemTypeEnum itemTypeById = ItemBagUtils.getItemTypeById(id);
            if (itemTypeById == IdItemTypeEnum.MONEY) {
                PlainCell plainCell = new PlainCell(id, false);
                plainCell.setStackSize(count);
                ItemBagCtrlProtos.addPlain(plainCell, b, b$Plain, dramaId);
            } else {
                SpecialCell specialCell = new SpecialCell(MagicNumbers.DEFAULT_ZERO, id, true);
                ItemBagCtrlProtos.addSpecial(specialCell, b, b$Special, dramaId);
            }
        }
        b.setTask(task);
        b.setRoleName(npcRow.getName());
        b.setRolePic(npcRow.getProfile());
        b.addSpecialCells(b$Special.build());
        b.addPlainCells(b$Plain.build());
        return b.build();
    }


    public static Sm_Room_AuctionInfo createSmRoomAuctionInfo(int itemId, int auctionId, IdAndCount auctionPrice, String auctionName, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(itemId, dramaId);
        Sm_Room_AuctionInfo.Builder b = Sm_Room_AuctionInfo.newBuilder();
        b.setAuctionId(auctionId);
        b.setItemPric(row.getItemPic());
        b.setItemBigPic(row.getItemBigPic());
        b.setItemName(row.getItemName());
        b.setAuctionPrice(ProtoUtils.create_Sm_Common_IdAndCount(auctionPrice));
        b.setAuctionName(auctionName);
        return b.build();
    }

    public static Sm_Room_AuctionInfo createSmRoomAuctionInfo(AuctionResult auctionResult, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(auctionResult.getItemId(), dramaId);
        Sm_Room_AuctionInfo.Builder b = Sm_Room_AuctionInfo.newBuilder();
        b.setAuctionId(auctionResult.getAuctionId());
        b.setItemPric(row.getItemPic());
        b.setItemBigPic(row.getItemBigPic());
        b.setItemName(row.getItemName());
        b.setAuctionPrice(ProtoUtils.create_Sm_Common_IdAndCount(auctionResult.getAuctionPrice()));
        b.setAuctionName(auctionResult.getAuctionName());
        return b.build();
    }

    public static List<Sm_Room_AuctionInfo> createSmRoomAuctionInfoList(List<AuctionResult> auctionAndPirce, int dramaId) {
        List<Sm_Room_AuctionInfo> arr = new ArrayList();
        for (AuctionResult auctionResult : auctionAndPirce) {
            arr.add(createSmRoomAuctionInfo(auctionResult, dramaId));
        }
        return arr;
    }


}
