package drama.gameServer.features.actor.room.utils;

import drama.gameServer.features.actor.room.pojo.Room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoomCenter {
    /**
     * roomIdToRoom
     */
    private static final Map<String, Room> roomIdToRoom = new ConcurrentHashMap<>();

    /**
     * playerIdToRoomId
     */
    private static final Map<String, String> playerIdToRoomId = new ConcurrentHashMap<>();

    public static Map<String, Room> getRoomIdToRoom() {
        return roomIdToRoom;
    }

    public static void remove(String roomId, String playerId) {
        RoomCenter.getRoomIdToRoom().remove(roomId);
        RoomCenter.getPlayerIdToRoomId().remove(playerId);
    }

    public static Room getRoom(String roomId) {
        return roomIdToRoom.get(roomId);
    }

    public static Map<String, String> getPlayerIdToRoomId() {
        return playerIdToRoomId;
    }

    public static String getRoomIdByPlayerId(String playerId) {
        return playerIdToRoomId.get(playerId);
    }

    private static void putRoomIdToRoom(Room room) {
        roomIdToRoom.put(room.getRoomId(), room);
    }

    private static void putPlayerIdToRoomId(String playerId, String roomId) {
        playerIdToRoomId.put(playerId, roomId);
    }

    public static boolean containsRoomId(String roomId) {
        return roomIdToRoom.containsKey(roomId);
    }

    public static boolean containsPlayerId(String playerId) {
        return playerIdToRoomId.containsKey(playerId);
    }

    public static void add(Room room, String playerId) {
        putRoomIdToRoom(room);
        putPlayerIdToRoomId(playerId, room.getRoomId());
    }
}
