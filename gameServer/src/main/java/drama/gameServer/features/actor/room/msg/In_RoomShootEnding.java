package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._RoomInnerMsg;
import drama.protos.MessageHandlerProtos.Response;

/**
 * Created by lee on 2021/10/28
 */
public class In_RoomShootEnding extends _RoomInnerMsg {
    private Response response;

    public In_RoomShootEnding(String roomId, Response response) {
        super(roomId);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
