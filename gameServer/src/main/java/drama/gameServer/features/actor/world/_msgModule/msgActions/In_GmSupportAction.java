package drama.gameServer.features.actor.world._msgModule.msgActions;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import dm.relationship.base.MagicNumbers;
import drama.gameServer.features.actor.room.msg.In_GmKillRoomMsg;
import drama.gameServer.features.actor.room.msg._GmAllRoomPlayerMsg;
import drama.gameServer.features.actor.room.msg._GmSyncRoomMsg;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.actor.world._msgModule.Action;
import drama.gameServer.features.actor.world.ctrl.WorldCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2021/9/10
 */
public class In_GmSupportAction implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(In_GmSupportAction.class);

    @Override
    public void onRecv(Object msg, WorldCtrl worldCtrl, ActorContext worldActorContext, ActorRef self, ActorRef sender) {
        if (msg instanceof _GmSyncRoomMsg.Request) {
            onSyncRoom(worldCtrl, sender, (_GmSyncRoomMsg.Request) msg);
        } else if (msg instanceof In_GmKillRoomMsg) {
            onKillRoom(worldCtrl, sender, (In_GmKillRoomMsg) msg);
        } else if (msg instanceof _GmAllRoomPlayerMsg.Request) {
            onAllRoomPlayer(worldCtrl, sender, (_GmAllRoomPlayerMsg.Request) msg);
        }
    }

    private void onAllRoomPlayer(WorldCtrl worldCtrl, ActorRef sender, _GmAllRoomPlayerMsg.Request msg) {
        int simpleRoomId = msg.getSimpleRoomId();
        if (!worldCtrl.containsRoom(simpleRoomId)) {
            LOGGER.debug("房间不存在了,待处理 roomSimpleId={}", simpleRoomId);
            return;
        }
        String roomId = worldCtrl.getRoomIdBySimpleId(simpleRoomId);
        if (!worldCtrl.roomActorCanUse(roomId)) {
            LOGGER.debug("房间不存在了,待处理 roomId={}", roomId);
            return;
        }
        ActorRef roomActorRef = worldCtrl.getRoomActorRef(roomId);
        roomActorRef.tell(msg, sender);
    }

    private void onSyncRoom(WorldCtrl worldCtrl, ActorRef sender, _GmSyncRoomMsg.Request msg) {
        List<Room> roomList = new ArrayList<>();
        if (msg.getSimpleId() == MagicNumbers.DEFAULT_ZERO) {
            for (Map.Entry<String, Room> entries : worldCtrl.getRoomCenter().getRoomIdToRoom().entrySet()) {
                Room room = (Room) entries.getValue();
                roomList.add(room);
            }
        } else {
            int simpleRoomId = msg.getSimpleId();
            if (!worldCtrl.containsRoom(simpleRoomId)) {
                LOGGER.debug("房间不存在了,待处理 roomSimpleId={}", simpleRoomId);
                return;
            }
            String roomId = worldCtrl.getRoomIdBySimpleId(simpleRoomId);
            if (!worldCtrl.roomActorCanUse(roomId)) {
                LOGGER.debug("房间不存在了,待处理 roomId={}", roomId);
                return;
            }
            Room room = worldCtrl.getRoomCenter().get(simpleRoomId);
            roomList.add(room);

        }
        _GmSyncRoomMsg.Response response = new _GmSyncRoomMsg.Response(roomList);
        sender.tell(response, ActorRef.noSender());
    }

    private void onKillRoom(WorldCtrl worldCtrl, ActorRef sender, In_GmKillRoomMsg msg) {
        int simpleRoomId = msg.getSimpleRoomId();
        if (!worldCtrl.containsRoom(simpleRoomId)) {
            LOGGER.debug("房间不存在了,待处理 roomSimpleId={}", simpleRoomId);
            return;
        }
        String roomId = worldCtrl.getRoomIdBySimpleId(simpleRoomId);
        if (!worldCtrl.roomActorCanUse(roomId)) {
            LOGGER.debug("房间不存在了,待处理 roomId={}", roomId);
            return;
        }
        ActorRef roomActorRef = worldCtrl.getRoomActorRef(roomId);
        roomActorRef.tell(msg, sender);
    }
}
