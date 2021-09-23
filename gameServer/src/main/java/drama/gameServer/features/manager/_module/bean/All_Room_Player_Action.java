package drama.gameServer.features.manager._module.bean;

import com.alibaba.fastjson.JSONArray;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.interfaces.GmRoomMsg;
import dm.relationship.utils.ActorMsgSynchronizedExecutor;
import drama.gameServer.features.actor.room.msg._GmAllRoomPlayerMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import drama.gameServer.features.manager.Action;
import drama.gameServer.system.actor.DmActorSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 2021/9/14
 */
public class All_Room_Player_Action implements Action {
    @Override
    public String handle(String funcName, String args) {
        GmRoomMsg request = new _GmAllRoomPlayerMsg.Request(Integer.valueOf(args));
        _GmAllRoomPlayerMsg.Response response = ActorMsgSynchronizedExecutor.sendMsgToServer(DmActorSystem.get().actorSelection(ActorSystemPath.DM_GameServer_Selection_World), request);
        return createRoomPlayerListJSONString(response.getRoomPlayerList());
    }

    private String createRoomPlayerListJSONString(List<RoomPlayer> roomPlayerList) {
        List<Map<String, Object>> arr = new ArrayList<>();
        for (RoomPlayer roomPlayer : roomPlayerList) {
            arr.add(createRoomPlayerMap(roomPlayer));
        }

        return JSONArray.toJSONString(arr);
    }

    private Map<String, Object> createRoomPlayerMap(RoomPlayer roomPlayer) {
        Map<String, Object> map = new HashMap<>();
        map.put("playerId", roomPlayer.getPlayerId());
        map.put("playerName", roomPlayer.getPlayerName());
        map.put("roleId", roomPlayer.getRoleId());
        map.put("isDub", roomPlayer.getIsDub());
        map.put("isReady", roomPlayer.isReady());
        map.put("srchNum", roomPlayer.getSrchTimes());
        map.put("voteSrchNum", roomPlayer.getVoteSrchTimes());
        return map;
    }

}
