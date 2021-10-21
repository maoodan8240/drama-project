package drama.gameServer.features.extp.itemBag;

import akka.actor.ActorRef;
import dm.relationship.base.msg.interfaces.RoomInnerExtpMsg;
import dm.relationship.base.msg.interfaces.RoomNetWorkMsg;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.room.ctrl.RoomPlayerCtrl;
import drama.gameServer.features.actor.room.mc.extension.AbstractRoomPlayerExtension;
import drama.gameServer.features.extp.itemBag.ctrl.ItemBagCtrl;
import drama.gameServer.features.extp.itemBag.msg.In_RoomGiveItemMsg;
import drama.gameServer.features.extp.itemBag.msg.In_RoomShowItemMsg;
import drama.gameServer.features.extp.itemBag.msg.Pr_InitPropMsg;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.room.ItemBagProtos.Cm_ItemBag;
import drama.protos.room.ItemBagProtos.Cm_ItemBag.Action;
import drama.protos.room.ItemBagProtos.Sm_ItemBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.di.GlobalInjector;
import ws.common.utils.message.interfaces.PrivateMsg;

/**
 * Created by lee on 2021/9/30
 */
public class ItemBagExtp extends AbstractRoomPlayerExtension<ItemBagCtrl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemBagExtp.class);
    public static boolean useExtension = true;

    public ItemBagExtp(RoomPlayerCtrl ownerCtrl) {
        super(ownerCtrl);
    }

    @Override
    public void init() throws Exception {
        ItemBagCtrl itemBagCtrl = GlobalInjector.getInstance(ItemBagCtrl.class);
        ItemBag itemBag = new ItemBag(getOwnerCtrl().getPlayerId());
        itemBag.setDramaId(getOwnerCtrl().getTarget().getDramaId());
        itemBag.setRoomId(getOwnerCtrl().getTarget().getRoomId());
        itemBagCtrl.setTarget(itemBag);
        itemBagCtrl.setRoomPlayerCtrl(ownerCtrl);
        setControler(itemBagCtrl);
        LOGGER.debug("ItemBagExtp init success");

    }

    @Override
    public RoomPlayerCtrl getOwnerCtrl() {
        return super.getOwnerCtrl();
    }


    @Override
    public void postInit() throws Exception {
        super.postInit();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void setCurSender(ActorRef curSender) {
        super.setCurSender(curSender);
    }

    @Override
    public void onRecvInnerExtpMsg(RoomInnerExtpMsg msg) throws Exception {
        if (msg instanceof In_RoomShowItemMsg) {
            onBeShowItem((In_RoomShowItemMsg) msg);
        } else if (msg instanceof In_RoomGiveItemMsg) {
            onBeGiveItem((In_RoomGiveItemMsg) msg);
        }
    }

    private void onBeGiveItem(In_RoomGiveItemMsg msg) {
        getControlerForQuery().onBeGiveItem(msg);
    }

    private void onBeShowItem(In_RoomShowItemMsg msg) {
        getControlerForQuery().onBeShowItem(msg.getSpecialCell(), msg.getSelfRoleName());
    }

    @Override
    public void onRecvPrivateMsg(PrivateMsg privateMsg) {
        if (privateMsg instanceof Pr_InitPropMsg) {
            onInitPropMsg((Pr_InitPropMsg) privateMsg);
        }
    }

    private void onInitPropMsg(Pr_InitPropMsg privateMsg) {
        getControlerForQuery().iniPropItem(privateMsg);
    }

    @Override
    public void onRecvMyNetworkMsg(RoomNetWorkMsg clientMsg) throws Exception {
        if (clientMsg.getMessage() instanceof Cm_ItemBag) {
            onCm_ItemBag((Cm_ItemBag) clientMsg.getMessage());
        }
    }

    private void onCm_ItemBag(Cm_ItemBag message) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        try {
            switch (message.getAction().getNumber()) {
                case Action.SYNC_VALUE:
                    b.setAction(Sm_ItemBag.Action.RESP_SYNC);
                    onSync();
                    break;
                case Action.GIVE_ITEM_VALUE:
                    b.setAction(Sm_ItemBag.Action.RESP_GIVE_ITEM);
                    onGiveItem(message.getId(), message.getCount(), message.getRoleId());
                    break;
                case Action.SHOW_ITEM_VALUE:
                    b.setAction(Sm_ItemBag.Action.RESP_SHOW_ITEM);
                    onShowItem(message.getId(), message.getCount(), message.getRoleId());
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            Response.Builder br = ProtoUtils.create_Response(Code.Sm_ItemBag, b.getAction());
            getControlerForQuery().send(br.build());
            throw e;
        }
    }

    private void onShowItem(int id, int count, int roleId) {
        getControlerForQuery().showItem(id, count, roleId);
    }

    private void onGiveItem(int id, int count, int roleId) {
        getControlerForQuery().giveItem(id, count, roleId);
    }

    private void onSync() {
        getControlerForQuery().sync();
    }


}
