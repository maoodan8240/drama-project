package drama.gameServer.features.actor.playerIO.utils;

import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Murder_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
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
            String roleName = Table_Murder_Row.getRoleNameByRoleId(roleId);
            String rolePic = Table_Murder_Row.getRolePicByRoleId(roleId);
            List<Integer> voteRoleIds = entry.getValue();
            List<String> votePic = Table_Murder_Row.getRolePicByRoleIds(voteRoleIds);
            voteList.add(createSmRoomVote(roleName, rolePic, votePic));
        }
        return voteList;
    }

    public static RoomProtos.Sm_Room_Player createSmRoomPlayer(RoomPlayer roomPlayer) {
        RoomProtos.Sm_Room_Player.Builder bSimplePlayer = RoomProtos.Sm_Room_Player.newBuilder();
        bSimplePlayer.setPlayerId(roomPlayer.getPlayerId());
        bSimplePlayer.setIsReady(roomPlayer.isReady());
        bSimplePlayer.setRoleId(roomPlayer.getRoleId());
        bSimplePlayer.setIsDub(roomPlayer.getIsDub());
        bSimplePlayer.setSrchTimes(roomPlayer.getSrchTimes());
        return bSimplePlayer.build();
    }

    public static List<RoomProtos.Sm_Room_Clue> createSmRoomClueList(List<Integer> clueIds) {
        List<RoomProtos.Sm_Room_Clue> arr = new ArrayList<>();
        for (Integer clueId : clueIds) {
            Table_Search_Row row = RootTc.get(Table_Search_Row.class).get(clueId);
            RoomProtos.Sm_Room_Clue.Builder bClue = RoomProtos.Sm_Room_Clue.newBuilder();
            bClue.setTypeName(Table_SearchType_Row.getTypeNameById(row.getTypeid()));
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
        bClue.setTypeName(Table_SearchType_Row.getTypeNameById(row.getTypeid()));
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
