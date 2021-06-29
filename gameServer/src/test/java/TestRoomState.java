import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.roomCenter.ctrl.RoomCtrl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRoomState {
    public static RoomCtrl roomCtrl;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRoomState.class);

    @Test
    public void testRoomState() throws Exception {
//        AppConfig.init();
//        GlobalInjectorUtils.init();
//        ServerGlobalData.init();
//        PlanningTableData planningTableData = new _PlanningTableData("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\");
//        planningTableData.loadAllTablesData();
//        RootTc.init(planningTableData, new RootTcListener());
//        RootTc.loadAllTables(new Locale("zh", "CN"));
//        roomCtrl = GlobalInjector.getInstance(RoomCtrl.class);
//        String roomId = ObjectId.get().toString();
//        String playerId = ObjectId.get().toString();
//        roomCtrl.createRoom(roomId, playerId, 101);
//
//        String playerId2 = ObjectId.get().toString();
//        RoomPlayer roomPlayer2 = new RoomPlayer(playerId2, roomId);
//        roomPlayer2.setReady(true);
////        roomCtrl.addPlayer(roomPlayer2);
////        roomCtrl.chooseRole(1, roomPlayer2);
//
//        RoomPlayer roomPlayer = roomCtrl.getRoomPlayer(playerId);
//        RoomPlayerCtrl roomPlayerCtrl = GlobalInjector.getInstance(RoomPlayerCtrl.class);
//        roomPlayerCtrl.setTarget(roomPlayer);
//        roomPlayerCtrl.chooseRole(2);
//        Player player = new Player();
//        player.setPlayerId(playerId);
//        onReady(player);

    }

    private void onReady(Player player) {
//        if (roomCtrl.getRoomState() == EnumsProtos.RoomStateEnum.ANSWER) {
//            if (roomCtrl.containsPlayer(player.getPlayerId())) {
//                RoomPlayer roomPlayer = roomCtrl.getRoomPlayer(player.getPlayerId());
//                if (roomCtrl.hasChooseRole(roomPlayer.getRoleId(), player.getPlayerId())) {
//                    roomPlayer.setReady(!roomPlayer.isReady());
//                    //通知房间内所有玩家
//                    LOGGER.debug("通知房间内所有玩家,这个玩家的状态");
//                }
//            } else {
//                LOGGER.debug("玩家不在房间中, playerId={}, roomId={}", player.getPlayerId(), roomCtrl.getTarget().getRoomId());
//                throw new BusinessLogicMismatchConditionException("没有找到房间 roomId=" + roomCtrl.getTarget().getRoomId(), EnumsProtos.ErrorCodeEnum.ROOM_NOT_EXISTS);
//            }
//        } else {
//
//        }
//        if (roomCtrl.checkAllPlayerReady() && roomCtrl.hasNextStateAndTimes()) {
//            roomCtrl.setNextStateAndTimes();
//            //TODO 通知玩家房间状态已经更新成播放剧本状态
//            for (Map.Entry<String, RoomPlayer> roomPlayerEntry : roomCtrl.getTarget().getIdToRoomPlayer().entrySet()) {
//                RoomPlayer value = roomPlayerEntry.getValue();
//                LOGGER.debug("通知房间内所有玩家,这个玩家的状态playerId={},isReady={}", value.getPlayerId(), value.isReady());
//            }
//        }
//        LOGGER.debug("通知房间内所有玩家 roomState={},times={}", roomCtrl.getRoomState().toString(), roomCtrl.getRoomStateTimes());
    }
}
