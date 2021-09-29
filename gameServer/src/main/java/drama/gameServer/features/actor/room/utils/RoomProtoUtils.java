package drama.gameServer.features.actor.room.utils;

import dm.relationship.base.MagicNumbers;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Draft_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import dm.relationship.table.tableRows.Table_SubActer_Row;
import dm.relationship.table.tableRows.Table_SubMurder_Row;
import drama.gameServer.features.actor.room.enums.RoomStateEnum;
import drama.gameServer.features.actor.room.msg.In_PlayerIsVotedRoomMsg;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.protos.RoomProtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomProtoUtils {
    public static RoomProtos.Sm_Room createSmRoomByActionWithoutRoomPlayer(Room room, RoomProtos.Sm_Room.Action action) {
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        RoomProtos.Sm_Room_Info broom = createSmRoomInfoWithoutRoomPlayer(room);
        b.addRoomInfos(broom);
        return b.build();
    }

    public static RoomProtos.Sm_Room createSmRoomByAction(Room room, RoomProtos.Sm_Room.Action action) {
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        RoomProtos.Sm_Room_Info broom = createSmRoomInfo(room);
        b.addRoomInfos(broom);
        return b.build();
    }


    public static RoomProtos.Sm_Room_Info createSmRoomInfo(Room room) {
        RoomProtos.Sm_Room_Info.Builder broom = RoomProtos.Sm_Room_Info.newBuilder();
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(room.getDramaId());
        setSmRoomInfo(room, broom, row);
        for (Map.Entry<String, RoomPlayer> entry : room.getIdToRoomPlayer().entrySet()) {
            broom.addRoomPlayer(createSmRoomPlayer(entry.getValue(), room.getDramaId()));
        }
        return broom.build();
    }

    public static List<RoomProtos.Sm_Room_Player> createSmRoomPlayerList(Room room) {
        List<RoomProtos.Sm_Room_Player> arr = new ArrayList<>();
        for (Map.Entry<String, RoomPlayer> entry : room.getIdToRoomPlayer().entrySet()) {
            RoomProtos.Sm_Room_Player smRoomPlayer = createSmRoomPlayer(entry.getValue(), room.getDramaId());
            arr.add(smRoomPlayer);
        }
        return arr;
    }

    public static RoomProtos.Sm_Room_Info createSmRoomInfoWithoutRoomPlayer(Room room) {
        RoomProtos.Sm_Room_Info.Builder broom = RoomProtos.Sm_Room_Info.newBuilder();
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(room.getDramaId());
        setSmRoomInfo(room, broom, row);
        return broom.build();
    }

    private static void setSmRoomInfo(Room room, RoomProtos.Sm_Room_Info.Builder broom, Table_SceneList_Row row) {
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
        broom.setIsInitState(RoomStateEnum.isFirstState(room.getRoomState(), room.getDramaId()));
        if (room.getBeginTime() != MagicNumbers.DEFAULT_ZERO) {
            broom.setDuration(System.currentTimeMillis() - room.getBeginTime());
        } else {
            broom.setDuration(MagicNumbers.DEFAULT_ZERO);
        }
    }


    public static RoomProtos.Sm_Room_Vote createSmRoomVote(int murderRoleId, String name, String rolePic, String roleBigPic, boolean isMurder, boolean isTruth, List<String> votePic, List<Integer> voteRoleIds) {
        RoomProtos.Sm_Room_Vote.Builder b = RoomProtos.Sm_Room_Vote.newBuilder();
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

    public static RoomProtos.Sm_Room_Vote_Search createSmRoomVoteSearch(String name, String rolePic, List<String> votePic) {
        RoomProtos.Sm_Room_Vote_Search.Builder b = RoomProtos.Sm_Room_Vote_Search.newBuilder();
        b.setTypeName(name);
        b.setTypePic(rolePic);
        b.addAllVotePic(votePic);
        return b.build();
    }

    public static List<RoomProtos.Sm_Room_Vote> createSmRoomVoteList(Map<Integer, List<Integer>> roleIdToPlayerRoleId, int dramaId) {
        List<RoomProtos.Sm_Room_Vote> voteList = new ArrayList<>();
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

    public static List<RoomProtos.Sm_Room_SubVote> createSmRoomSubVoteList(Map<Integer, List<Integer>> subRoleIdToPlayerRoleId, Map<Integer, RoomPlayer> subRoleIdToRoomPlayer, int dramaId) {
        List<RoomProtos.Sm_Room_SubVote> voteList = new ArrayList<>();
        Map<Integer, Table_Acter_Row> allRoleIdToRowByDramaId = Table_Acter_Row.getAllRoleIdToRowByDramaId(dramaId);
        for (Map.Entry<Integer, List<Integer>> entry : subRoleIdToPlayerRoleId.entrySet()) {
            int subRoleId = entry.getKey();
            RoomPlayer murderRoomPlayer = subRoleIdToRoomPlayer.get(subRoleId);
            int murderRoleId = murderRoomPlayer.getRoleId();
            Table_Acter_Row row = allRoleIdToRowByDramaId.get(murderRoleId);
            String murderName = row.getName();
            String murderPic = row.getPic();
            String mudrerProfile = row.getProfile();
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

    public static RoomProtos.Sm_Room_SubVote createSmRoomSubVote(int murderRoleId, String murderName, String murderPic, String mudrerProfile, int subRoleId,//
                                                                 String subRoleName, String subRolePic, boolean isSubMurder, boolean isSubTruth, //
                                                                 List<String> votePic, List<Integer> voteRoleIds) {
        RoomProtos.Sm_Room_SubVote.Builder b = RoomProtos.Sm_Room_SubVote.newBuilder();
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

    public static List<RoomProtos.Sm_Room_Vote_Search> createSmRoomVoteSearchList(Map<Integer, List<Integer>> roleIdToPlayerRoleId, int dramaId) {
        List<RoomProtos.Sm_Room_Vote_Search> voteList = new ArrayList<>();
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

    public static RoomProtos.Sm_Room_Murder.Builder createSmRoomMurder(In_PlayerIsVotedRoomMsg msg) {
        RoomProtos.Sm_Room_Murder.Builder bm = RoomProtos.Sm_Room_Murder.newBuilder();
        bm.setIsVoted(msg.isVoted());
        bm.setRoleId(msg.getMurderRoleId());
        return bm;
    }

    public static RoomProtos.Sm_Room_Player createSmRoomPlayer(RoomPlayer roomPlayer, int dramaId) {
        RoomProtos.Sm_Room_Player.Builder bRoomPlayer = RoomProtos.Sm_Room_Player.newBuilder();
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

    public static List<RoomProtos.Sm_Room_SubRoleInfo> createSmRoomSubRoleInfoList(Map<Integer, Integer> subNumToSubRoleIds, int dramaId) {
        List<RoomProtos.Sm_Room_SubRoleInfo> arr = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : subNumToSubRoleIds.entrySet()) {
            RoomProtos.Sm_Room_SubRoleInfo smRoomSubRoleInfo = createSmRoomSubRoleInfo(entry.getValue(), "", dramaId, entry.getKey());
            arr.add(smRoomSubRoleInfo);
        }
        return arr;
    }

    public static RoomProtos.Sm_Room_Player createSoloSmRoomPlayer(RoomPlayer roomPlayer, int soloDramaId, int dramaId) {
        RoomProtos.Sm_Room_Player.Builder bRoomPlayer = RoomProtos.Sm_Room_Player.newBuilder();
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

    public static List<RoomProtos.Sm_Room_Clue> createSmRoomClueList(List<Integer> clueIds, int dramaId) {
        List<RoomProtos.Sm_Room_Clue> arr = new ArrayList<>();
        for (Integer clueId : clueIds) {
            RoomProtos.Sm_Room_Clue.Builder bClue = createSmRoomClue(clueId, dramaId);
            arr.add(bClue.build());
        }
        return arr;
    }

    public static RoomProtos.Sm_Room_Clue.Builder createSmRoomClue(int idx, int dramaId) {
        RoomProtos.Sm_Room_Clue.Builder bClue = RoomProtos.Sm_Room_Clue.newBuilder();
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


    public static RoomProtos.Sm_Room.Builder setAction(RoomProtos.Sm_Room.Builder b, RoomProtos.Cm_Room cm_room) {
        if (cm_room.getAction().getNumber() == RoomProtos.Cm_Room.Action.CREAT_VALUE) {
            b.setAction(RoomProtos.Sm_Room.Action.RESP_CREATE);
        } else if (cm_room.getAction().getNumber() == RoomProtos.Cm_Room.Action.JION_VALUE) {
            b.setAction(RoomProtos.Sm_Room.Action.RESP_JION);
        } else if (cm_room.getAction().getNumber() == RoomProtos.Cm_Room.Action.QUIT_VALUE) {
            b.setAction(RoomProtos.Sm_Room.Action.RESP_QUIT);
        } else if (cm_room.getAction().getNumber() == RoomProtos.Cm_Room.Action.SYNC_VALUE) {
            b.setAction(RoomProtos.Sm_Room.Action.RESP_SYNC);
        } else if (cm_room.getAction().getNumber() == RoomProtos.Cm_Room.Action.ANSWER_VALUE) {
            b.setAction(RoomProtos.Sm_Room.Action.RESP_ANSWER);
        } else if (cm_room.getAction().getNumber() == RoomProtos.Cm_Room.Action.READY_VALUE) {
            b.setAction(RoomProtos.Sm_Room.Action.RESP_READY);
        }
        return b;
    }

    public static List<RoomProtos.Sm_Room_SearchType> createSearchType(Map<String, String> typeById) {
        List<RoomProtos.Sm_Room_SearchType> arr = new ArrayList<>();
        for (Map.Entry<String, String> entry : typeById.entrySet()) {
            RoomProtos.Sm_Room_SearchType.Builder b = RoomProtos.Sm_Room_SearchType.newBuilder();
            b.setTypeName(entry.getKey());
            b.setTypePic(entry.getValue());
            arr.add(b.build());
        }
        return arr;
    }

    public static List<RoomProtos.Sm_Room_RoleInfo> createSmRoomRoleInfoList(List<Integer> roleIds, int dramaId) {
        List<RoomProtos.Sm_Room_RoleInfo> arr = new ArrayList<>();
        for (Table_Acter_Row row : RootTc.get(Table_Acter_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                arr.add(createSmRoomRoleInfo(row.getRoleId(), dramaId, !roleIds.contains(row.getRoleId())));
            }
        }
        return arr;
    }

    public static RoomProtos.Sm_Room_RoleInfo createSmRoomRoleInfo(int roleId, int dramaId, boolean selected) {
        Table_Acter_Row row = Table_Acter_Row.getTableActerRowByRoleId(roleId, dramaId);
        RoomProtos.Sm_Room_RoleInfo.Builder b = RoomProtos.Sm_Room_RoleInfo.newBuilder();
        b.setRoleId(row.getRoleId());
        b.setName(row.getName());
        b.setSex(row.getSex());
        b.setPic(row.getPic());
        b.setProfile(row.getProfile());
        b.addAllBgm(row.getBgm());
        b.setSelected(selected);
        return b.build();
    }

    public static List<RoomProtos.Sm_Room_Draft> createSmRoomDraftList(List<Table_Draft_Row> rows) {
        List<RoomProtos.Sm_Room_Draft> draftList = new ArrayList<>();
        for (Table_Draft_Row row : rows) {
            draftList.add(createSmRoomDraft(row));
        }
        return draftList;
    }

    public static RoomProtos.Sm_Room_Draft createSmRoomDraft(Table_Draft_Row row) {
        RoomProtos.Sm_Room_Draft.Builder b = RoomProtos.Sm_Room_Draft.newBuilder();
        b.setDraftId(row.getDraftID());
        b.setDraftPic(row.getDraftPic());
        b.setDraftPoster(row.getDraftPoster());
        return b.build();
    }


    public static List<RoomProtos.Sm_Room_SubRoleInfo> createSmRoomSubRoleInfoList(Map<Integer, String> canSubSelectIds, int dramaId, int subNum) {
        List<RoomProtos.Sm_Room_SubRoleInfo> arr = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : canSubSelectIds.entrySet()) {
            arr.add(createSmRoomSubRoleInfo(entry.getKey(), entry.getValue(), dramaId, subNum));
        }
        return arr;
    }

    public static RoomProtos.Sm_Room_SubRoleInfo createSmRoomSubRoleInfo(int subRoleId, String playerId, int dramaId, int subNum) {
        RoomProtos.Sm_Room_SubRoleInfo.Builder b = RoomProtos.Sm_Room_SubRoleInfo.newBuilder();
        Table_SubActer_Row row = Table_SubActer_Row.getRowById(dramaId, subRoleId, subNum);
        b.setPlayerId(playerId);
        b.setSubName(row.getSubName());
        b.setSubRoleId(subRoleId);
        b.setSubPic(row.getSubPic());
        b.setSubScene(row.getSubScene());
        return b.build();
    }


}
