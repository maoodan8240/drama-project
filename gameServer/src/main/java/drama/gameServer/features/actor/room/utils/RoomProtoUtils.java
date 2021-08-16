package drama.gameServer.features.actor.room.utils;

import dm.relationship.base.MagicNumbers;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Draft_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
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
    }


    public static RoomProtos.Sm_Room_Vote createSmRoomVote(String name, String rolePic, boolean isMurder, boolean isTruth, List<String> votePic) {
        RoomProtos.Sm_Room_Vote.Builder b = RoomProtos.Sm_Room_Vote.newBuilder();
        b.setRoleName(name);
        b.setRolePic(rolePic);
        b.setIsTruth(isTruth);
        b.setIsMurder(isMurder);
        b.addAllVotePic(votePic);
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
            String roleName = murderRow.getRoleName();
            String rolePic = murderRow.getRolePic();
            boolean isMurder = murderRow.isMurder();
            boolean isTruth = murderRow.isTruth();
            List<String> votePic = Table_Murder_Row.getRolePicByRoleIds(voteRoleIds, dramaId);
            voteList.add(createSmRoomVote(roleName, rolePic, isMurder, isTruth, votePic));
        }
        return voteList;
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
        }
        return bRoomPlayer.build();
    }

    public static RoomProtos.Sm_Room_Player createSoloSmRoomPlayer(RoomPlayer roomPlayer, int soloDramaId) {
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
}
