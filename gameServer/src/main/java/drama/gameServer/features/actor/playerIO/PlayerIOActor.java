package drama.gameServer.features.actor.playerIO;

import akka.actor.ActorRef;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_PlayerDisconnectedRequest;
import dm.relationship.base.msg.In_PlayerReconnectResponseMsg;
import dm.relationship.base.msg.interfaces.PlayerInnerMsg;
import dm.relationship.base.msg.interfaces.PlayerNetWorkMsg;
import dm.relationship.base.msg.room.In_PlayerDisconnectedRoomMsg;
import dm.relationship.base.msg.room.In_PlayerQuitRoomMsg;
import dm.relationship.table.tableRows.Table_Draft_Row;
import dm.relationship.table.tableRows.Table_SearchType_Row;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.login.msg.NewLoginResponseMsg;
import drama.gameServer.features.actor.playerIO.ctrl.PlayerIOCtrl;
import drama.gameServer.features.actor.playerIO.msg.In_PlayerUpdateResponse;
import drama.gameServer.features.actor.playerIO.utils.PlayerIConUploadUtils;
import drama.gameServer.features.actor.playerIO.utils.PlayerProtoUtils;
import drama.gameServer.features.actor.room.msg.In_CheckPlayerAllReadyRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerAllFinishChooseDubRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerCanSelectDraftRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerCanSelectRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerChooseRoleRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerCreateRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerIsVotedRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerJoinRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnCanSearchRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnCanVoteSearchRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnOpenDubRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnReadyRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnSwitchStateRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerOnUnlockClueRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerReconnectRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSearchRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSelectDraftRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSelectReadRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSoloResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSyncClueRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerSyncSoloIdxRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteListRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteRemainRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteSearchResultRoomMsg;
import drama.gameServer.features.actor.room.msg.In_PlayerVoteSearchRoomMsg;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.actor.room.utils.RoomProtoUtils;
import drama.gameServer.features.actor.world.msg.In_PrepareToKillPlayerActorRequestMsg;
import drama.gameServer.features.actor.world.msg.In_PrepareToKillPlayerActorResponseMsg;
import drama.gameServer.system.actor.DmActorSystem;
import drama.protos.CodesProtos;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.EnumsProtos;
import drama.protos.MessageHandlerProtos;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.PlayerLoginProtos;
import drama.protos.PlayerLoginProtos.Sm_Login.Action;
import drama.protos.PlayerProtos;
import drama.protos.RoomProtos;
import org.apache.commons.lang3.StringUtils;
import org.jolokia.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static drama.gameServer.features.actor.room.utils.RoomProtoUtils.createSmRoomMurder;


public class PlayerIOActor extends DmActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerIOActor.class);
    private String playerId;
    private PlayerIOCtrl playerIOCtrl;

    public PlayerIOActor(String playerId, PlayerIOCtrl playerIOCtrl) {
        this.playerId = playerId;
        this.playerIOCtrl = playerIOCtrl;
    }

    public void setPlayerCtrl() {

    }

    @Override
    public void onRecv(Object msg) throws Exception {
//        if (msg instanceof In_LoginMsg) {
//            onLogin((In_LoginMsg) msg);
//        } else
        if (msg instanceof NewLoginResponseMsg) {
            onLogin((NewLoginResponseMsg) msg);
        } else if (msg instanceof PlayerNetWorkMsg) {
            onNetWorkMsg((PlayerNetWorkMsg) msg);
        } else if (msg instanceof PlayerInnerMsg) {
            onPlayerInnerMsg((PlayerInnerMsg) msg);
        }
    }


    private void onNetWorkMsg(PlayerNetWorkMsg msg) {
        Message message = msg.getMessage();
        if (message instanceof PlayerProtos.Cm_Player) {
            PlayerProtos.Cm_Player cmPlayer = (PlayerProtos.Cm_Player) message;
            switch (cmPlayer.getAction().getNumber()) {
                case PlayerProtos.Cm_Player.Action.UPDATE_VALUE:
                    onUpdate(cmPlayer);
                    break;
                case PlayerProtos.Cm_Player.Action.SYNC_VALUE:
                    onSync();
                    break;
                default:
                    break;
            }
        }
    }

    private void onSync() {
        PlayerProtos.Sm_Player.Action action = PlayerProtos.Sm_Player.Action.RESP_SYNC;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Player, action);
        PlayerProtos.Sm_Player bPlayer = PlayerProtoUtils.createSm_Player(playerIOCtrl.getTarget(), action);
        response.setResult(true);
        response.setSmPlayer(bPlayer);
        playerIOCtrl.send(response.build());
    }

    private void onUpdate(PlayerProtos.Cm_Player cmPlayer) {
        String iconUrl = transferIcon(cmPlayer.getIcon());
        String name = cmPlayer.getName();
        EnumsProtos.SexEnum sex = cmPlayer.getSex();
        String birthday = !StringUtils.isEmpty(cmPlayer.getBirthday()) ? cmPlayer.getBirthday() : "";
        String place = !StringUtils.isEmpty(cmPlayer.getPlace()) ? cmPlayer.getPlace() : "";
        playerIOCtrl.updatePlayer(iconUrl, birthday, place, name, sex);
        PlayerProtos.Sm_Player.Action action = PlayerProtos.Sm_Player.Action.RESP_UPDATE;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Player, action);
        PlayerProtos.Sm_Player bPlayer = PlayerProtoUtils.createSm_Player(playerIOCtrl.getTarget(), action);
        response.setResult(true);
        response.setSmPlayer(bPlayer);
        playerIOCtrl.send(response.build());
        playerIOCtrl.getPlayerDao().insertIfExistThenReplace(playerIOCtrl.getTarget());
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerUpdateResponse(playerIOCtrl.getTarget()), ActorRef.noSender());
    }

    private String transferIcon(ByteString icon) {
        byte[] bytes = new byte[0];
        try {
            bytes = Base64Util.decode(icon.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String iconUrl = "";
        if (bytes.length != 0) {
            iconUrl = PlayerIConUploadUtils.uploadPlayerIcon(playerId, bytes);
        }
        return iconUrl;
    }


    private void onPlayerInnerMsg(PlayerInnerMsg msg) {
        if (msg instanceof In_PlayerQuitRoomMsg) {
            onPlayerQuitMsg((In_PlayerQuitRoomMsg) msg);
        } else if (msg instanceof In_PlayerJoinRoomMsg) {
            onPlayerJoinRoomMsg((In_PlayerJoinRoomMsg) msg);
        } else if (msg instanceof In_PlayerCreateRoomMsg) {
            onPlayerCreateRoomMsg((In_PlayerCreateRoomMsg) msg);
        } else if (msg instanceof In_PlayerDisconnectedRequest) {
            onPlayerDisconnected();
        } else if (msg instanceof In_PlayerChooseRoleRoomMsg) {
            onPlayerChooseRoleRoomMsg((In_PlayerChooseRoleRoomMsg) msg);
        } else if (msg instanceof In_PlayerOnReadyRoomMsg) {
            onPlayerOnReadyRoomMsg((In_PlayerOnReadyRoomMsg) msg);
        } else if (msg instanceof In_PlayerOnSwitchStateRoomMsg) {
            onPlayerOnSwitchStateRoomMsg((In_PlayerOnSwitchStateRoomMsg) msg);
        } else if (msg instanceof In_PlayerOnOpenDubRoomMsg) {
            onPlayerOnOpenDubRoomMsg((In_PlayerOnOpenDubRoomMsg) msg);
        } else if (msg instanceof In_PlayerSearchRoomMsg) {
            onPlayerSearchRoomMsg((In_PlayerSearchRoomMsg) msg);
        } else if (msg instanceof In_PlayerSyncClueRoomMsg) {
            onPlayerSyncClueRoomMsg((In_PlayerSyncClueRoomMsg) msg);
        } else if (msg instanceof In_PlayerOnCanSearchRoomMsg) {
            onPlayerOnCanSearchRoomMsg((In_PlayerOnCanSearchRoomMsg) msg);
        } else if (msg instanceof In_PlayerVoteRoomMsg) {
            onPlayerVoteRoomMsg((In_PlayerVoteRoomMsg) msg);
        } else if (msg instanceof In_PlayerVoteResultRoomMsg) {
            onPlayerVoteResultRoomMsg((In_PlayerVoteResultRoomMsg) msg);
        } else if (msg instanceof In_PlayerVoteListRoomMsg) {
            onPlayerVoteListRoomMsg((In_PlayerVoteListRoomMsg) msg);
        } else if (msg instanceof In_PlayerVoteRemainRoomMsg) {
            onPlayerVoteRemainRoomMsg((In_PlayerVoteRemainRoomMsg) msg);
        } else if (msg instanceof In_PlayerSoloResultRoomMsg) {
            onPlayerSoloResultRoomMsg((In_PlayerSoloResultRoomMsg) msg);
        } else if (msg instanceof In_PlayerIsVotedRoomMsg) {
            onPlayerIsVotedRoomMsg((In_PlayerIsVotedRoomMsg) msg);
        } else if (msg instanceof In_PlayerReconnectResponseMsg) {
            onPlayerReconnectResponseMsg((In_PlayerReconnectResponseMsg) msg);
        } else if (msg instanceof In_PlayerCanSelectRoomMsg) {
            onPlayerCanSelectRoomMsg((In_PlayerCanSelectRoomMsg) msg);
        } else if (msg instanceof In_PrepareToKillPlayerActorRequestMsg) {
            onPrepareToKillPlayerActorRequest((In_PrepareToKillPlayerActorRequestMsg) msg);
        } else if (msg instanceof In_PlayerOnCanVoteSearchRoomMsg) {
            onPlayerOnCanVoteSearchRoomMsg((In_PlayerOnCanVoteSearchRoomMsg) msg);
        } else if (msg instanceof In_PlayerVoteSearchRoomMsg) {
            onPlayerVoteSearchRoomMsg((In_PlayerVoteSearchRoomMsg) msg);
        } else if (msg instanceof In_PlayerVoteSearchResultRoomMsg) {
            onPlayerVoteSearchResultRoomMsg((In_PlayerVoteSearchResultRoomMsg) msg);
        } else if (msg instanceof In_PlayerCanSelectDraftRoomMsg) {
            onPlayerCanSelectDraftRoomMsg((In_PlayerCanSelectDraftRoomMsg) msg);
        } else if (msg instanceof In_PlayerSelectDraftRoomMsg) {
            onPlayerSelectDraftRoomMsg((In_PlayerSelectDraftRoomMsg) msg);
        } else if (msg instanceof In_PlayerOnUnlockClueRoomMsg) {
            onPlayerOnUnlockClueRoomMsg((In_PlayerOnUnlockClueRoomMsg) msg);
        } else if (msg instanceof In_PlayerSelectReadRoomMsg) {
            onPlayerSelectReadRoomMsg((In_PlayerSelectReadRoomMsg) msg);
        } else if (msg instanceof In_PlayerSyncSoloIdxRoomMsg) {
            onPlayerSyncSoloIdxRoomMsg((In_PlayerSyncSoloIdxRoomMsg) msg);
        } else if (msg instanceof In_PlayerAllFinishChooseDubRoomMsg) {
            onPlayerAllFinishChooseDubRoomMsg((In_PlayerAllFinishChooseDubRoomMsg) msg);
        }
    }


    private void onPlayerAllFinishChooseDubRoomMsg(In_PlayerAllFinishChooseDubRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_ALL_ROOM_PLAYER;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.addAllAllRoomPlayer(RoomProtoUtils.createSmRoomPlayerList(msg.getRoom()));
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerSyncSoloIdxRoomMsg(In_PlayerSyncSoloIdxRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SYNC_SOLO_IDX;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.setSoloIdx(msg.getSoloIdx());
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerSelectReadRoomMsg(In_PlayerSelectReadRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SELECTREAD;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.setResultId(msg.getResult());
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerOnUnlockClueRoomMsg(In_PlayerOnUnlockClueRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_UNLOCK;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        List<RoomProtos.Sm_Room_Clue> smRoomClueList = RoomProtoUtils.createSmRoomClueList(msg.getUnlockClueIds(), msg.getDramaId());
        b.setAction(action);
        b.addAllRoomClue(smRoomClueList);
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerSelectDraftRoomMsg(In_PlayerSelectDraftRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SELECT_DRAFT;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerCanSelectDraftRoomMsg(In_PlayerCanSelectDraftRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_CAN_SELECT_DRAFT;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        List<Table_Draft_Row> rows = Table_Draft_Row.getTableDraftByIds(msg.getDraftIds(), msg.getDramaId());
        b.addAllDraft(RoomProtoUtils.createSmRoomDraftList(rows));
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerVoteSearchResultRoomMsg(In_PlayerVoteSearchResultRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SYNC_VOTE_RESULT;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        Map<Integer, List<Integer>> roleIdToPlayerRoleId = msg.getVoteTypeIdToPlayerRoleId();
        List<RoomProtos.Sm_Room_Vote_Search> smRoomVoteList = RoomProtoUtils.createSmRoomVoteSearchList(roleIdToPlayerRoleId, msg.getDramaId());
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        RoomProtos.Sm_Room_Player smRoomPlayer = RoomProtoUtils.createSmRoomPlayer(msg.getRoomPlayer(), msg.getDramaId());
        b.setAction(action);
        b.setRoomPlayer(smRoomPlayer);
        b.addAllVoteSearch(smRoomVoteList);
        b.addRoomClue(RoomProtoUtils.createSmRoomClue(msg.getClueId(), msg.getDramaId()));
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerVoteSearchRoomMsg(In_PlayerVoteSearchRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_VOTE_SEARCH;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.setTypeName(msg.getTypeName());
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }


    private void onPlayerOnCanVoteSearchRoomMsg(In_PlayerOnCanVoteSearchRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_CAN_VOTE_SEARCH;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        RoomProtos.Sm_Room_Player smRoomPlayer = RoomProtoUtils.createSmRoomPlayer(msg.getRoomPlayer(), msg.getDramaId());
        b.setAction(action);
        b.setRoomPlayer(smRoomPlayer);
        Map<String, String> voteSearchTypeById = Table_SearchType_Row.getVoteSearchTypeById(msg.getClueIds(), msg.getDramaId());
        b.addAllSearchType(RoomProtoUtils.createSearchType(voteSearchTypeById));
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPrepareToKillPlayerActorRequest(In_PrepareToKillPlayerActorRequestMsg msg) {
        if (playerIOCtrl.isInRoom()) {
            String roomId = playerIOCtrl.getRoomId();
            LOGGER.debug("玩家处于房间中,玩家已掉线即将被移除,通知房间,如果玩家是房主,房间就要被关闭 roomId={},playerId={}", roomId, playerId);
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerDisconnectedRoomMsg(roomId, playerId), ActorRef.noSender());
        }
        playerIOCtrl.setLsoutTime();
        getSender().tell(new In_PrepareToKillPlayerActorResponseMsg(playerId), ActorRef.noSender());
    }

    private void onPlayerCanSelectRoomMsg(In_PlayerCanSelectRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_CAN_SELECT;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.addAllRoleInfo(RoomProtoUtils.createSmRoomRoleInfoList(msg.getCanSelectRoleIds(), msg.getDramaId()));
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }


    private void onPlayerReconnectResponseMsg(In_PlayerReconnectResponseMsg msg) {
        playerIOCtrl.getTarget().setConnection(msg.getConnection());
        playerIOCtrl.sendLoginResponse(playerIOCtrl.getTarget(), Action.RESP_GUEST_LOGIN);
        if (playerIOCtrl.isInRoom()) {
            LOGGER.debug("玩家仍在房间中,发往房间进行断线重连房间信息全量同步 roomId={}", playerIOCtrl.getRoomId());
            String roomId = playerIOCtrl.getRoomId();
            DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_PlayerReconnectRoomMsg(roomId, msg.getConnection(), playerId), ActorRef.noSender());
        }
    }

    private void onPlayerIsVotedRoomMsg(In_PlayerIsVotedRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_IS_VOTED;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        RoomProtos.Sm_Room_Murder.Builder bm = createSmRoomMurder(msg);
        b.setAction(action);
        b.setMurder(bm.build());
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }


    private void onPlayerSoloResultRoomMsg(In_PlayerSoloResultRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SOLO_ANSWER;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setSoloDramaId(msg.getSoloDramaId());
        b.setAction(action);
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerVoteResultRoomMsg(In_PlayerVoteResultRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_VOTE_RESULT;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        Map<Integer, List<Integer>> roleIdToPlayerRoleId = msg.getRoleIdToPlayerRoleId();
        List<RoomProtos.Sm_Room_Vote> smRoomVoteList = RoomProtoUtils.createSmRoomVoteList(roleIdToPlayerRoleId, msg.getDramaId());
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.addAllRoomVote(smRoomVoteList);
        b.setAction(action);
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }

    private void onPlayerVoteListRoomMsg(In_PlayerVoteListRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_VOTE_LIST;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        Map<Integer, List<Integer>> roleIdToPlayerRoleId = msg.getVoteRoleIdToPlayerRoleId();
        List<RoomProtos.Sm_Room_Vote> smRoomVoteList = RoomProtoUtils.createSmRoomVoteList(roleIdToPlayerRoleId, msg.getDramaId());
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.addAllRoomVote(smRoomVoteList);
        b.setAction(action);
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());

    }

    private void onPlayerVoteRemainRoomMsg(In_PlayerVoteRemainRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_VOTE_REMAIN;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.setVoteNum(msg.getRemainNum());
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());

    }

    private void onPlayerVoteRoomMsg(In_PlayerVoteRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_VOTE;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        b.setMurderRoleId(msg.getMurderRoleId());
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }


    private void onPlayerOnCanSearchRoomMsg(In_PlayerOnCanSearchRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SYNC_CAN_SEARCH;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(action);
        RoomProtos.Sm_Room_Player smRoomPlayer = RoomProtoUtils.createSmRoomPlayer(msg.getRoomPlayer(), msg.getDramaId());
        b.setRoomPlayer(smRoomPlayer);
        b.addAllSearchType(RoomProtoUtils.createSearchType(Table_SearchType_Row.getTypeById(msg.getTypeIds(), msg.getDramaId())));
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }


    private void onPlayerSyncClueRoomMsg(In_PlayerSyncClueRoomMsg msg) {
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, msg.getAction());
        response.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(msg.getAction());
        List<Integer> clueIds = msg.getClueIds();
        List<RoomProtos.Sm_Room_Clue> smRoomClueList = RoomProtoUtils.createSmRoomClueList(clueIds, msg.getDramaId());
        b.addAllRoomClue(smRoomClueList);
        response.setSmRoom(b.build());
        playerIOCtrl.send(response.build());
    }


    private void onPlayerSearchRoomMsg(In_PlayerSearchRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SEARCH;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        if (msg.getId() == MagicNumbers.DEFAULT_ZERO) {
            response.setResult(false);
            response.setErrorCode(EnumsProtos.ErrorCodeEnum.NO_CLUE);
        } else {
            response.setResult(true);
            RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
            b.setAction(action);
            RoomProtos.Sm_Room_Player bRoomPlayer = RoomProtoUtils.createSmRoomPlayer(msg.getRoomPlayer(), msg.getDramaId());
            RoomProtos.Sm_Room_Clue.Builder bClue = RoomProtoUtils.createSmRoomClue(msg.getId(), msg.getDramaId());
            b.addRoomClue(bClue.build());
            b.setRoomPlayer(bRoomPlayer);
            response.setSmRoom(b.build());
        }
        playerIOCtrl.send(response.build());
    }


    private void onPlayerOnOpenDubRoomMsg(In_PlayerOnOpenDubRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = msg.getAction();
        RoomPlayer roomPlayer = msg.getRoomPlayer();
        if (action == RoomProtos.Sm_Room.Action.RESP_IS_DUB) {
            playerIOCtrl.sendRoomPlayerProtos(action, roomPlayer, msg);
        } else if (action == RoomProtos.Sm_Room.Action.RESP_SOLO_DUB) {
            playerIOCtrl.sendSoloRoomPlayer(action, roomPlayer, msg.getSoloNum());
        }
    }


    private void onPlayerOnReadyRoomMsg(In_PlayerOnReadyRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_READY;
        RoomPlayer roomPlayer = msg.getRoomPlayer();
        playerIOCtrl.sendRoomPlayerProtos(action, roomPlayer, msg.getDramaId());
        checkRoomPlayerAllReady(msg.getRoomPlayer().getRoomId());
    }


    private void onPlayerChooseRoleRoomMsg(In_PlayerChooseRoleRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = null;
        if (msg.getAction().getNumber() == RoomProtos.Cm_Room.Action.SELECT_VALUE) {
            action = RoomProtos.Sm_Room.Action.RESP_SELECT;
        } else if (msg.getAction().getNumber() == RoomProtos.Cm_Room.Action.ANSWER_VALUE) {
            action = RoomProtos.Sm_Room.Action.RESP_ANSWER;
        } else if (msg.getAction().getNumber() == RoomProtos.Cm_Room.Action.NO_SELECT_VALUE) {
            action = RoomProtos.Sm_Room.Action.RESP_NO_SELECT;
        }
        RoomPlayer roomPlayer = msg.getRoomPlayer();
        playerIOCtrl.sendRoomPlayerProtos(action, roomPlayer, msg.getDramaId());
    }

    private void onPlayerOnSwitchStateRoomMsg(In_PlayerOnSwitchStateRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_SWITCH_STATE;
        Room room = msg.getRoom();
        MessageHandlerProtos.Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room b = RoomProtoUtils.createSmRoomByAction(room, action);
        response.setSmRoom(b);
        playerIOCtrl.send(response.build());
    }


    private void onPlayerCreateRoomMsg(In_PlayerCreateRoomMsg msg) {
        RoomProtos.Sm_Room.Action action = RoomProtos.Sm_Room.Action.RESP_CREATE;
        playerIOCtrl.joinRoom(msg.getRoom().getRoomId());
        Room room = msg.getRoom();
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_Room, action);
        response.setResult(true);
        RoomProtos.Sm_Room b = RoomProtoUtils.createSmRoomByActionWithoutRoomPlayer(room, action);
        response.setSmRoom(b);
        playerIOCtrl.send(response.build());
    }

    private void onPlayerDisconnected() {
        //TODO 考虑断线重连,现在不一定要处理退出房间的逻辑
        if (playerIOCtrl.isInRoom()) {
            //玩家在房间中,但不一定是房主,转发到房间中处理相关逻辑
//            String roomId = playerIOCtrl.getRoomId();
//            playerIOCtrl.quitRoom();
//            String roomActorName = ActorSystemPath.DM_GameServer_Selection_Room + roomId;
//            DmActorSystem.get().actorSelection(roomActorName).tell(new In_PlayerDisconnectedQuitRoomMsg(roomId, playerId), ActorRef.noSender());
        }
        //离线处理保存玩家信息
        playerIOCtrl.setLsoutTime();
        //所处房间的逻辑已经在In_PlayerDisconnectedAction worldCtrl执行了
    }

    private void onPlayerJoinRoomMsg(In_PlayerJoinRoomMsg msg) {
        Room room = msg.getRoom();
        playerIOCtrl.joinRoom(room.getRoomId());
        LOGGER.debug("玩家加入了房间: playerId={} ,roomId={},dramaName={}", playerId, room.getRoomId(), room.getDramaId());
        MessageHandlerProtos.Response.Builder br = ProtoUtils.create_Response(CodesProtos.ProtoCodes.Code.Sm_Room, RoomProtos.Sm_Room.Action.RESP_JION);
        br.setResult(true);
        RoomProtos.Sm_Room b = RoomProtoUtils.createSmRoomByAction(room, RoomProtos.Sm_Room.Action.RESP_JION);
        br.setSmRoom(b);
        playerIOCtrl.send(br.build());
        playerIOCtrl.save();
    }

    private void onPlayerQuitMsg(In_PlayerQuitRoomMsg msg) {
        if (playerIOCtrl.getTarget().getPlayerId() == msg.getMasterId()) {
            LOGGER.debug("房间主人退出了房间,所有玩家清出房间 masterId={} <-->roomId={}", msg.getMasterId(), msg.getRoomId());
        } else {
            LOGGER.debug("玩家退出房间 player={} <-->roomId={}", playerIOCtrl.getTarget().getPlayerId(), msg.getRoomId());
        }
        playerIOCtrl.quitRoom();
        Response.Builder br = ProtoUtils.create_Response(Code.Sm_Room, RoomProtos.Sm_Room.Action.RESP_QUIT);
        br.setResult(true);
        RoomProtos.Sm_Room.Builder b = RoomProtos.Sm_Room.newBuilder();
        b.setAction(RoomProtos.Sm_Room.Action.RESP_QUIT);
        br.setSmRoom(b.build());
        playerIOCtrl.send(br.build());
    }


//    private void onLogin(In_LoginMsg loginMsg) {
//        PlayerLoginProtos.Cm_Login cm_login = (PlayerLoginProtos.Cm_Login) loginMsg.getMessage();
//        playerIOCtrl.setLsnTime();
//        switch (cm_login.getAction().getNumber()) {
//            case PlayerLoginProtos.Cm_Login.Action.LOGIN_VALUE:
//                playerIOCtrl.sendLoginResponse(playerIOCtrl.getTarget(), Action.RESP_LOGIN);
//                break;
//            case PlayerLoginProtos.Cm_Login.Action.GUEST_LOGIN_VALUE:
//                playerIOCtrl.sendLoginResponse(playerIOCtrl.getTarget(), Action.RESP_GUEST_LOGIN);
//                break;
//            default:
//                break;
//        }
//        LOGGER.debug("Player Login Success playerId={},Action={}", playerId, cm_login.getAction().toString());
//    }


    private void onLogin(NewLoginResponseMsg loginMsg) {
        PlayerLoginProtos.Cm_Login cm_login = (PlayerLoginProtos.Cm_Login) loginMsg.getMessage();
        if (!loginMsg.getPlayer().getPlayerId().equals(playerId)) {
            return;
        }
        playerIOCtrl.setLsnTime();
        switch (cm_login.getAction().getNumber()) {
            case PlayerLoginProtos.Cm_Login.Action.LOGIN_VALUE:
                playerIOCtrl.sendLoginResponse(playerIOCtrl.getTarget(), Action.RESP_LOGIN);
                break;
            case PlayerLoginProtos.Cm_Login.Action.GUEST_LOGIN_VALUE:
                playerIOCtrl.sendLoginResponse(playerIOCtrl.getTarget(), Action.RESP_GUEST_LOGIN);
                break;
            default:
                break;
        }
        LOGGER.debug("Player Login Success playerId={},Action={}", playerId, cm_login.getAction().toString());
    }

    private void checkRoomPlayerAllReady(String roomId) {
        DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World).tell(new In_CheckPlayerAllReadyRoomMsg(roomId), ActorRef.noSender());
    }


}
