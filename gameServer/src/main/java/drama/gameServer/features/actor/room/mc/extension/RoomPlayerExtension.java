package drama.gameServer.features.actor.room.mc.extension;

import akka.actor.ActorRef;
import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import dm.relationship.base.msg.interfaces.RoomInnerMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import drama.gameServer.features.actor.room.ctrl.RoomCtrl;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import ws.common.utils.mc.controler.Controler;
import ws.common.utils.mc.extension.Extension;
import ws.common.utils.message.interfaces.PrivateMsg;

/**
 * Created by lee on 2021/9/29
 */
public interface RoomPlayerExtension<T extends Controler<?>> extends Extension<T> {
    /**
     * 返回所属{@link RoomPlayerCtrl}
     *
     * @return
     */
    RoomPlayerCtrl getOwnerCtrl();

    /**
     * 返回所属{@link RoomCtrl}
     *
     * @return
     */
    RoomCtrl getRoomCtrl();

    /**
     * 初始化Ctrl Pojo Gm
     *
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * 初始化业务逻辑
     *
     * @throws Exception
     */
    void postInit() throws Exception;

    /**
     * 销毁
     */
    void destroy();

    /**
     * 当前消息的发送者
     *
     * @param curSender
     */
    void setCurSender(ActorRef curSender);

    /**
     * 当接收到{@link RoomInnerMsg}时
     *
     * @param innerMsg
     * @throws Exception
     */
    void onRecvInnerExtpMsg(RoomInnerExtpMsg innerMsg) throws Exception;

    /**
     * 当接收到{@link PrivateMsg}时
     *
     * @param privateMsg
     * @throws Exception
     */
    void onRecvPrivateMsg(PrivateMsg privateMsg) throws Exception;

    /**
     * 当接收到自己的网络连接发来的消息
     *
     * @param clientMsg
     * @throws Exception
     */
    void onRecvMyNetworkMsg(RoomNetWorkMsg clientMsg) throws Exception;

}
