package drama.gameServer.features.actor.room.mc.controler;

import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import drama.protos.MessageHandlerProtos.Response;
import ws.common.utils.mc.controler.Controler;

/**
 * Created by lee on 2021/9/29
 */
public interface RoomPlayerExtControler<T> extends Controler<T> {
    /**
     * 初始化业务逻辑 在postInit 引用调用其他模块，得注意，因为这时其他模块的postInit不一定加载完毕
     *
     * @throws Exception
     */
    void postInit() throws Exception;

    /**
     * 初始化所有其他模块的引用
     */
    void initReference() throws Exception;

    /**
     * 同步信息
     */
    void sync();

    RoomPlayerCtrl getRoomPlayerCtrl();

    RoomCtrl getRoomCtrl();

    void setRoomPlayerCtrl(RoomPlayerCtrl roomPlayerCtrl);

    void send(Response msg);
}
