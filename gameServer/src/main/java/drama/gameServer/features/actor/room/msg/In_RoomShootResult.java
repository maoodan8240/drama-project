package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._RoomInnerMsg;
import drama.protos.MessageHandlerProtos.Response;

/**
 * Created by lee on 2021/10/25
 */
public class In_RoomShootResult extends _RoomInnerMsg {
    private Response response;

    public In_RoomShootResult(String roomId, Response response) {
        super(roomId);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
