package drama.gameServer.features.actor.room.mc.extension;

import akka.actor.ActorRef;
import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.mc.controler.Controler;
import ws.common.utils.mc.extension.AbstractExtension;
import ws.common.utils.message.interfaces.PrivateMsg;

/**
 * Created by lee on 2021/9/29
 */
public abstract class AbstractRoomPlayerExtension<T extends Controler<?>> extends AbstractExtension<T> implements RoomPlayerExtension<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRoomPlayerExtension.class);

    protected final RoomPlayerCtrl ownerCtrl;
    protected final RoomCtrl roomCtrl;
    protected ActorRef curSender;
    protected String playerId;

    public AbstractRoomPlayerExtension(RoomPlayerCtrl ownerCtrl, RoomCtrl roomCtrl) {
        this.roomCtrl = roomCtrl;
        this.ownerCtrl = ownerCtrl;
    }


    public RoomCtrl getRoomCtrl() {
        return roomCtrl;
    }

    @Override
    public RoomPlayerCtrl getOwnerCtrl() {
        return ownerCtrl;
    }

    @Override
    public void init() throws Exception {
        playerId = ownerCtrl.getPlayerId();
    }

    @Override
    public void postInit() throws Exception {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setCurSender(ActorRef curSender) {
        this.curSender = curSender;
    }

    @Override
    public void onRecvInnerExtpMsg(RoomInnerExtpMsg innerMsg) throws Exception {

    }

    @Override
    public void onRecvPrivateMsg(PrivateMsg privateMsg) throws Exception {

    }
}
