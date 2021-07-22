package drama.gameServer.features.actor.playerIO.utils;

import dm.relationship.base.MagicNumbers;
import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Acter_Row;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import drama.gameServer.features.actor.roomCenter.msg.In_PlayerIsVotedRoomMsg;
import drama.gameServer.features.actor.roomCenter.pojo.Room;
import drama.gameServer.features.actor.roomCenter.pojo.RoomPlayer;
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
        broom.setPic(Table_SceneList_Row.getPicByDramaId(room.getDramaId()));
        for (Map.Entry<String, RoomPlayer> entry : room.getIdToRoomPlayer().entrySet()) {
            broom.addRoomPlayer(createSmRoomPlayer(entry.getValue()));
        }
        return broom.build();
    }

    public static RoomProtos.Sm_Room_Info createSmRoomInfoWithoutRoomPlayer(Room room) {
        RoomProtos.Sm_Room_Info.Builder broom = RoomProtos.Sm_Room_Info.newBuilder();
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
        broom.setPic(Table_SceneList_Row.getPicByDramaId(room.getDramaId()));
        return broom.build();
    }


    public static RoomProtos.Sm_Room_Vote createSmRoomVote(String name, String rolePic, List<String> votePic) {
        RoomProtos.Sm_Room_Vote.Builder b = RoomProtos.Sm_Room_Vote.newBuilder();
        b.setRoleName(name);
        b.setRolePic(rolePic);
        b.addAllVotePic(votePic);
        return b.build();
    }


    public static List<RoomProtos.Sm_Room_Vote> createSmRoomVoteList(Map<Integer, List<Integer>> roleIdToPlayerRoleId) {
        List<RoomProtos.Sm_Room_Vote> voteList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : roleIdToPlayerRoleId.entrySet()) {
            int roleId = entry.getKey();
            //TODO row get roleName and rolePic
            Table_Murder_Row murderRow = Table_Murder_Row.getMurderRowByRoleId(roleId);
            String roleName = murderRow.getRoleName();
            String rolePic = murderRow.getRolePic();
            List<Integer> voteRoleIds = entry.getValue();
            List<String> votePic = Table_Murder_Row.getRolePicByRoleIds(voteRoleIds);
            voteList.add(createSmRoomVote(roleName, rolePic, votePic));
        }
        return voteList;
    }

    public static RoomProtos.Sm_Room_Murder.Builder createSmRoomMurder(In_PlayerIsVotedRoomMsg msg) {
        RoomProtos.Sm_Room_Murder.Builder bm = RoomProtos.Sm_Room_Murder.newBuilder();
        bm.setIsVoted(msg.isVoted());
        bm.setRoleId(msg.getMurderRoleId());
        return bm;
    }

    public static RoomProtos.Sm_Room_Player createSmRoomPlayer(RoomPlayer roomPlayer) {

        RoomProtos.Sm_Room_Player.Builder bRoomPlayer = RoomProtos.Sm_Room_Player.newBuilder();
        bRoomPlayer.setPlayerId(roomPlayer.getPlayerId());
        bRoomPlayer.setIsReady(roomPlayer.isReady());
        bRoomPlayer.setRoleId(roomPlayer.getRoleId());
        bRoomPlayer.setIsDub(roomPlayer.getIsDub());
        bRoomPlayer.setSrchTimes(roomPlayer.getSrchTimes());
        if (roomPlayer.getRoleId() != MagicNumbers.DEFAULT_ZERO) {
            Table_Acter_Row row = Table_Acter_Row.getTableActerRowByRoleId(roomPlayer.getRoleId());
            bRoomPlayer.setPic(row.getPic());
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
        return bRoomPlayer.build();
    }

    public static List<RoomProtos.Sm_Room_Clue> createSmRoomClueList(List<Integer> clueIds) {
        List<RoomProtos.Sm_Room_Clue> arr = new ArrayList<>();
        for (Integer clueId : clueIds) {
            Table_Search_Row row = RootTc.get(Table_Search_Row.class).get(clueId);
            RoomProtos.Sm_Room_Clue.Builder bClue = RoomProtos.Sm_Room_Clue.newBuilder();
            Table_SearchType_Row searchTypeRow = Table_SearchType_Row.getSearchTypeRow(row.getTypeid());
            bClue.setTypeName(searchTypeRow.getTypename());
            bClue.setLine(row.getLine());
            bClue.setSrchNum(row.getSrchNum());
            bClue.setPic(row.getPic());
            arr.add(bClue.build());
        }
        return arr;
    }

    public static RoomProtos.Sm_Room_Clue.Builder createSmRoomClue(int rowId) {
        Table_Search_Row row = RootTc.get(Table_Search_Row.class).get(rowId);
        RoomProtos.Sm_Room_Clue.Builder bClue = RoomProtos.Sm_Room_Clue.newBuilder();
        Table_SearchType_Row searchTypeRow = Table_SearchType_Row.getSearchTypeRow(row.getTypeid());
        bClue.setTypeName(searchTypeRow.getTypename());
        bClue.setLine(row.getLine());
        bClue.setSrchNum(row.getSrchNum());
        bClue.setPic(row.getPic());
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
}
