package drama.gameServer.features.actor.world.pojo;

import akka.actor.ActorRef;
import ws.common.network.server.interfaces.Connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    private Map<String, String> playerIdToMobileNum = new ConcurrentHashMap<>();//玩家Id<->电话

    //
    private Map<String, ActorRef> playerIdToPlayerActorRef = new ConcurrentHashMap<>(); //玩家Id<-> playerActorRef
    private Map<String, Boolean> idToCanUse = new ConcurrentHashMap<>(); //玩家Id<-> 是否可用

    //
    private Map<String, ActorRef> roomIdToRoomActorRef = new ConcurrentHashMap<>(); //房间Id<-> roomActorRef

    // 玩家登录时记录
    private Map<String, Long> idToCodeTime = new ConcurrentHashMap<>();// 玩家id <-> 登录的时间点

    //在线玩家
    private Map<String, Connection> idToConn = new ConcurrentHashMap<>();// 玩家id <-> 玩家conn
    private Map<Connection, String> connToId = new ConcurrentHashMap<>();// 玩家conn <-> 玩家id

    //掉线状态
    private Map<String, Boolean> idToOffline = new ConcurrentHashMap<>(); // 玩家id <-> 是否掉线 true是掉线状态
    private Map<String, Long> idToOfflineTime = new ConcurrentHashMap<>(); // 玩家id <-> 掉线时的时间


    public Map<String, Long> getIdToCodeTime() {
        return idToCodeTime;
    }

    public Map<String, Boolean> getIdToCanUse() {
        return idToCanUse;
    }

    public Map<String, Connection> getIdToConn() {
        return idToConn;
    }

    public Map<Connection, String> getConnToId() {
        return connToId;
    }

    public Map<String, Boolean> getIdToOffline() {
        return idToOffline;
    }

    public Map<String, Long> getIdToOfflineTime() {
        return idToOfflineTime;
    }

    public Map<String, String> getPlayerIdToMobileNum() {
        return playerIdToMobileNum;
    }

    public Map<String, ActorRef> getPlayerIdToPlayerActorRef() {
        return playerIdToPlayerActorRef;
    }

    public Map<String, ActorRef> getRoomIdToRoomActorRef() {
        return roomIdToRoomActorRef;
    }

}
