package drama.gameServer.features.actor.room.mc.controler;

import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import drama.protos.MessageHandlerProtos.Response;
import ws.common.utils.mc.controler.AbstractControler;
import ws.common.utils.message.interfaces.PrivateMsg;

/**
 * Created by lee on 2021/9/29
 */
public abstract class AbstractRoomPlayerExtControler<T> extends AbstractControler<T> implements RoomPlayerExtControler<T> {
    private RoomPlayerCtrl ownerCtrl;
    private RoomCtrl roomCtrl;

    @Override
    public void postInit() throws Exception {
        this.initReference();
    }

    @Override
    public void initReference() throws Exception {

    }

    @Override
    public void sync() {


    }

    @Override
    public RoomCtrl getRoomCtrl() {
        return this.roomCtrl;
    }

    @Override
    public RoomPlayerCtrl getRoomPlayerCtrl() {
        return this.ownerCtrl;
    }

    @Override
    public void setRoomPlayerCtrl(RoomPlayerCtrl roomPlayerCtrl) {
        this.ownerCtrl = roomPlayerCtrl;
    }

    @Override
    public void send(Response msg) {
        ownerCtrl.send(msg);
    }

    @Override
    public void sendPrivateMsg(PrivateMsg privateMsg) {

    }
}
