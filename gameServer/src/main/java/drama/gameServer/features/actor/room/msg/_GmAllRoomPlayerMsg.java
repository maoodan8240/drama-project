package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.interfaces.GmRoomMsg;
import drama.gameServer.features.actor.room.pojo.RoomPlayer;

import java.util.List;

/**
 * Created by lee on 2021/9/10
 */
public class _GmAllRoomPlayerMsg {
    public static class Request implements GmRoomMsg {
        private int simpleRoomId;

        public int getSimpleRoomId() {
            return simpleRoomId;
        }


        public Request(int simpleRoomId) {
            this.simpleRoomId = simpleRoomId;
        }
    }

    public static class Response implements GmRoomMsg {
        private List<RoomPlayer> roomPlayerList;
        private int dramaId;

        public Response(List<RoomPlayer> roomPlayerList, int dramaId) {
            this.roomPlayerList = roomPlayerList;
            this.dramaId = dramaId;
        }

        public List<RoomPlayer> getRoomPlayerList() {
            return roomPlayerList;
        }

        public int getDramaId() {
            return dramaId;
        }
    }
}
