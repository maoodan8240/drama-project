package drama.gameServer.features.actor.roomCenter;

import dm.relationship.base.actor.DmActor;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;

public class RoomCenterActor extends DmActor {

    @Override
    public void onRecv(Object msg) throws Exception {
        if (msg instanceof RoomNetWorkMsg) {

        }
    }
}
