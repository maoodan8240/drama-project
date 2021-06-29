package drama.gameServer.features.actor.world.pojo;

import akka.actor.ActorRef;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    private Map<String, String> playerIdToMobileNum = new ConcurrentHashMap<>();//玩家Id<->电话
    private Map<String, ActorRef> playerIdToPlayerActorRef = new ConcurrentHashMap<>(); //玩家Id<-> playerActorRef

    private Map<String, ActorRef> roomIdToRoomActorRef = new ConcurrentHashMap<>();       //房间Id<-> roomActorRef


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
