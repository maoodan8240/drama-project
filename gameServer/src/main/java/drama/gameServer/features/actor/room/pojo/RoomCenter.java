package drama.gameServer.features.actor.room.pojo;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomCenter {
    /**
     * roomIdToRoom
     */
    private final Map<String, Room> roomIdToRoom = new ConcurrentHashMap<>();

    /**
     * playerIdToRoomId
     */
    private final Map<String, String> playerIdToRoomId = new ConcurrentHashMap<>();

    private final Map<Integer, String> SimpleIdToRoomId = new ConcurrentHashMap<>();

    public Map<Integer, String> getSimpleIdToRoomId() {
        return SimpleIdToRoomId;
    }

    public Map<String, Room> getRoomIdToRoom() {
        return roomIdToRoom;
    }

    public Room getRoom(String roomId) {
        return roomIdToRoom.get(roomId);
    }

    public Map<String, String> getPlayerIdToRoomId() {
        return playerIdToRoomId;
    }

    public String getRoomIdByPlayerId(String playerId) {
        return playerIdToRoomId.get(playerId);
    }

    private void putRoomIdToRoom(Room room) {
        roomIdToRoom.put(room.getRoomId(), room);
    }

    private void putPlayerIdToRoomId(String playerId, String roomId) {
        playerIdToRoomId.put(playerId, roomId);
    }

    public boolean containsRoomId(String roomId) {
        return roomIdToRoom.containsKey(roomId);
    }

    public boolean containsPlayerId(String playerId) {
        return playerIdToRoomId.containsKey(playerId);
    }

    public void add(Room room, String playerId) {
        putRoomIdToRoom(room);
        putPlayerIdToRoomId(playerId, room.getRoomId());
        getSimpleIdToRoomId().put(room.getSimpleRoomId(), room.getRoomId());
    }

    public void remove(String roomId, String playerId) {
        this.roomIdToRoom.remove(roomId);
        this.playerIdToRoomId.remove(playerId);
        Iterator<Map.Entry<Integer, String>> iterator = this.getSimpleIdToRoomId().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> next = iterator.next();
            if (next.getValue().equals(roomId)) {
                iterator.remove();
            }
        }
    }

    public void remove(String roomId) {
        this.roomIdToRoom.remove(roomId);
        Iterator<Map.Entry<String, String>> it = this.playerIdToRoomId.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            if (next.getValue().equals(roomId)) {
                it.remove();
            }
        }
        Iterator<Map.Entry<Integer, String>> iterator = this.getSimpleIdToRoomId().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> next = iterator.next();
            if (next.getValue().equals(roomId)) {
                iterator.remove();
            }
        }
    }
}
