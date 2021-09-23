package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.interfaces.GmRoomMsg;
import drama.gameServer.features.actor.room.pojo.Room;

import java.util.List;

/**
 * Created by lee on 2021/9/14
 */
public class _GmSyncRoomMsg {
    public static class Request implements GmRoomMsg {
        private int simpleId;

        public Request(int simpleId) {
            this.simpleId = simpleId;
        }

        public int getSimpleId() {
            return simpleId;
        }
    }

    public static class Response implements GmRoomMsg {
        private List<Room> roomList;

        public Response(List<Room> roomList) {
            this.roomList = roomList;
        }

        public List<Room> getRoomList() {
            return roomList;
        }
    }

}
