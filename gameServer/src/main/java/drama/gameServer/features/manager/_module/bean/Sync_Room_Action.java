package drama.gameServer.features.manager._module.bean;

import com.alibaba.fastjson.JSONArray;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.GmRoomMsg;
import dm.relationship.utils.ActorMsgSynchronizedExecutor;
import drama.gameServer.features.actor.room.msg._GmSyncRoomMsg;
import drama.gameServer.features.actor.room.pojo.Room;
import drama.gameServer.features.manager.Action;
import drama.gameServer.system.actor.DmActorSystem;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2021/9/8
 */
public class Sync_Room_Action implements Action {
    @Override
    public String handle(String funcName, String args) {
        int simpleId = 0;
        if (StringUtils.isNoneEmpty(args)) {
            simpleId = Integer.valueOf(args);
        }
        GmRoomMsg request = new _GmSyncRoomMsg.Request(simpleId);
        _GmSyncRoomMsg.Response response = ActorMsgSynchronizedExecutor.sendMsgToServer(DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World), request);
        return createRoomListJSONString(response.getRoomList());
    }

    public Map<String, Object> createRoomMap(Room room) {
        Map<String, Object> map = new HashMap<>();
        map.put("roomId", room.getRoomId());
        map.put("simpleRoomId", room.getSimpleRoomId());
        map.put("dramaName", room.getDramaName());
        map.put("masterName", room.getMasterName());
        map.put("roomState", room.getRoomState());
        map.put("roomPlayerNum", room.getIdToRoomPlayer().size());
        return map;
    }

    public String createRoomListJSONString(List<Room> roomList) {
        List<Map<String, Object>> arr = new ArrayList<>();
        for (Room room : roomList) {
            arr.add(createRoomMap(room));
        }
        return JSONArray.toJSONString(arr);
    }
}


