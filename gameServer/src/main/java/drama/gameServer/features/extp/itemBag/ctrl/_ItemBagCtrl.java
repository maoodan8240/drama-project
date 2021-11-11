package drama.gameServer.features.extp.itemBag.ctrl;

import akka.actor.ActorRef;
import dm.relationship.base.IdAndCount;
import dm.relationship.base.IdMaptoCount;
import dm.relationship.base.MagicNumbers;
import dm.relationship.enums.item.IdItemBigTypeEnum;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.utils.ProtoUtils;
import drama.gameServer.features.actor.room.mc.controler.AbstractRoomPlayerExtControler;
import drama.gameServer.features.extp.itemBag.msg.In_RoomGiveItemMsg;
import drama.gameServer.features.extp.itemBag.msg.In_RoomShowItemMsg;
import drama.gameServer.features.extp.itemBag.msg.Pr_InitPropMsg;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;
import drama.gameServer.features.extp.itemBag.pojo.PlainCell;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;
import drama.gameServer.features.extp.itemBag.utils.ItemBagCtrlProtos;
import drama.gameServer.features.extp.itemBag.utils.ItemBagUtils;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.EnumsProtos.ErrorCodeEnum;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.MessageHandlerProtos.Response.Builder;
import drama.protos.room.ItemBagProtos.Sm_ItemBag;
import drama.protos.room.ItemBagProtos.Sm_ItemBag.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by lee on 2021/9/30
 */
public class _ItemBagCtrl extends AbstractRoomPlayerExtControler<ItemBag> implements ItemBagCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(_ItemBagCtrl.class);

    @Override
    public void extend() {

    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean canAddItem(IdMaptoCount idMaptoCount) {
        return true;
    }

    @Override
    public IdMaptoCount addItem(IdMaptoCount idMaptoCount) {
        IdMaptoCount refresh_IdMaptoCount = new IdMaptoCount();
        for (IdAndCount idAndCount : idMaptoCount.getAll()) {
            IdItemTypeEnum itemType = IdItemTypeEnum.parseByItemTemplateId(idAndCount.getId());
            if (itemType.getBigType() == IdItemBigTypeEnum.PlainItem) {
                ItemBagUtils.addPlainItem(target, idAndCount.getId(), idAndCount.getCount(), itemType.isUseCell());
                refresh_IdMaptoCount.add(idAndCount);
            } else {
                List<SpecialCell> specialCells = ItemBagUtils.addSpecialItem(target, idAndCount.getId(), idAndCount.getCount(), itemType.isUseCell());
                for (SpecialCell specialCell : specialCells) {
                    refresh_IdMaptoCount.add(new IdAndCount(specialCell.getId()));
                }
            }
        }
        return refresh_IdMaptoCount;
    }

    @Override
    public boolean canRemoveItem(IdMaptoCount idMaptoCount) {
        for (IdAndCount idAndCount : idMaptoCount.getAll()) {
            if (ItemBagUtils.isSpecialItemId(idAndCount.getId())) { // 特殊物品
                if (idAndCount.getCount() != 1) {
                    LOGGER.debug("特殊物品通过itemId一次只能移除一个！ idAndCount={}  idMaptoCount={}", idAndCount, idMaptoCount);
                    return false;
                }
                if (!ItemBagUtils.containsItemId(target, idAndCount.getId())) {
                    LOGGER.debug("背包中没有足够的特殊物品，不能移除！ idAndCount={}  idMaptoCount={}", idAndCount, idMaptoCount);
                    return false;
                }
            } else { // 普通物品
                IdItemTypeEnum itemType = IdItemTypeEnum.parseByItemTemplateId(idAndCount.getId());
                if (itemType.getBigType() == IdItemBigTypeEnum.PlainItem) {
                    if (!ItemBagUtils.containsItemNTemplateId(target, idAndCount.getId(), idAndCount.getCount())) {
                        LOGGER.debug("背包中不含有足够的普通物品，不能移除！ idAndCount={}  idMaptoCount={}", idAndCount, idMaptoCount);
                        return false;
                    }
                } else {
                    LOGGER.debug("特殊物品不能通过itemTemplateId移除！ idAndCount={}  idMaptoCount={}", idAndCount, idMaptoCount);
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public IdMaptoCount removeItem(IdMaptoCount idMaptoCount) {
        IdMaptoCount refresh_IdMaptoCount = new IdMaptoCount();
        for (IdAndCount idAndCount : idMaptoCount.getAll()) {
            if (ItemBagUtils.isSpecialItemId(idAndCount.getId())) {
                SpecialCell specialCell = ItemBagUtils.removeSpecialItem(target, idAndCount.getId());
                refresh_IdMaptoCount.add(new IdAndCount(specialCell.getId()));
            } else {
                ItemBagUtils.removePlainCell(target, idAndCount.getId(), idAndCount.getCount());
                refresh_IdMaptoCount.add(idAndCount);
            }
        }
        return refresh_IdMaptoCount;
    }

    @Override
    public boolean canAddItem(IdAndCount idAndCount) {
        return true;
    }

    @Override
    public IdAndCount addItem(IdAndCount idAndCount) {
        IdItemTypeEnum itemType = IdItemTypeEnum.parseByItemTemplateId(idAndCount.getId());
        if (itemType.getBigType() == IdItemBigTypeEnum.PlainItem) {
            ItemBagUtils.addPlainItem(target, idAndCount.getId(), idAndCount.getCount(), itemType.isUseCell());
        } else {
            ItemBagUtils.addSpecialItem(target, idAndCount.getId(), idAndCount.getCount(), itemType.isUseCell());
        }
        return idAndCount;
    }

    @Override
    public boolean canRemoveItem(IdAndCount idAndCount) {
        if (idAndCount.getCount() == MagicNumbers.DEFAULT_ZERO) {
            return true;
        }
        if (ItemBagUtils.isSpecialItemId(idAndCount.getId())) { // 特殊物品
            if (idAndCount.getCount() != 1) {
                LOGGER.debug("特殊物品通过itemId一次只能移除一个！ idAndCount={}", idAndCount);
                return false;
            }
            if (!ItemBagUtils.containsItemId(target, idAndCount.getId())) {
                LOGGER.debug("背包中不含有足够的特殊物品，不能移除！ idAndCount={}", idAndCount);
                return false;
            }
        } else { // 普通物品
            IdItemTypeEnum itemType = IdItemTypeEnum.parseByItemTemplateId(idAndCount.getId());
            if (itemType.getBigType() == IdItemBigTypeEnum.PlainItem) {
                if (!ItemBagUtils.containsItemNTemplateId(target, idAndCount.getId(), idAndCount.getCount())) {
                    LOGGER.debug("背包中不含有足够的普通物品，不能移除！ idAndCount={}", idAndCount);
                    return false;
                }
            } else {
                LOGGER.debug("特殊物品不能通过itemTemplateId移除！ idAndCount={}", idAndCount);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasItem(int tpId) {
        return ItemBagUtils.canRemoveSpecialItem(target, tpId);
    }

    @Override
    public boolean hasItems(List<Integer> tpIds) {
        for (Integer tpId : tpIds) {
            if (!hasItem(tpId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public IdAndCount removeItem(IdAndCount idAndCount) {
        if (ItemBagUtils.isSpecialItemId(idAndCount.getId())) {
            ItemBagUtils.removeSpecialItem(target, idAndCount.getId());
        } else {
            ItemBagUtils.removePlainCell(target, idAndCount.getId(), idAndCount.getCount());
        }
        return idAndCount;
    }

    @Override
    public long queryTemplateItemCount(int itemTemplateId) {
        return ItemBagUtils.templateItemCount(target, itemTemplateId);
    }

    @Override
    public long querySpecialCellCount(int tpId) {
        return ItemBagUtils.SpcialTpIdItemCount(target, tpId);
    }

    @Override
    public SpecialCell getSpecialCell(int itemId) {
        return ItemBagUtils.getSpecialCell(target, itemId);
    }


    @Override
    public PlainCell getPlainCell(int tpId) {
        return target.getTpIdToPlainCell().get(tpId);
    }

    @Override
    public void gmCommond_ClearItemBag() {

    }

    @Override
    public void useItem(int itemTemplateId, int count) {

    }

    @Override
    public void sellItem(int idOrTpId, int count) {

    }

    @Override
    public void giveItem(int idOrTpId, int count, int roleId) {
        if (isMe(roleId)) {
            String msg = String.format("无法赠送物品给自己, 受用玩家roleId=%s,itemId=%s", roleId, idOrTpId);
            throw new BusinessLogicMismatchConditionException(msg, ErrorCodeEnum.UNKNOWN);
        }
        IdAndCount idAndCount = new IdAndCount(idOrTpId, count);
        if (!canRemoveItem(idAndCount)) {
            String msg = String.format("无法赠送物品, 受用玩家roleId=%s,itemId=%s", roleId, idOrTpId);
            throw new BusinessLogicMismatchConditionException(msg, ErrorCodeEnum.NOT_ENOUGH);
        }
        In_RoomGiveItemMsg in_roomGiveItemMsg;
        if (ItemBagUtils.getItemTypeById(idAndCount.getId()) == IdItemTypeEnum.MONEY) {
            ItemBagUtils.removePlainCell(target, idOrTpId, count);
            PlainCell plainCell = new PlainCell(idOrTpId, IdItemTypeEnum.MONEY.isUseCell());
            plainCell.setStackSize(count);
            in_roomGiveItemMsg = new In_RoomGiveItemMsg(plainCell, roleId, getRoomPlayerCtrl().getRoleName(), IdItemTypeEnum.MONEY);
        } else {
            SpecialCell specialCell = ItemBagUtils.removeSpecialItem(target, idOrTpId);
            in_roomGiveItemMsg = new In_RoomGiveItemMsg(specialCell, roleId, getRoomPlayerCtrl().getRoleName(), IdItemTypeEnum.ITEM);
        }
        getRoomPlayerCtrl().getRoomActorRef().tell(in_roomGiveItemMsg, ActorRef.noSender());
        Sm_ItemBag.Action action = Action.RESP_GIVE_ITEM;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_ItemBag, action);
        Sm_ItemBag smItemBag = ItemBagCtrlProtos.createGiveItemBag(getTarget());
        response.setResult(true);
        response.setSmItemBag(smItemBag);
        getRoomPlayerCtrl().send(response.build());
    }

    @Override
    public void showItem(int idOrTpId, int count, int roleId) {
        if (isMe(roleId)) {
            String msg = String.format("无法展示物品给自己, 受用玩家roleId=%s,itemId=%s", roleId, idOrTpId);
            throw new BusinessLogicMismatchConditionException(msg, ErrorCodeEnum.UNKNOWN);
        }
        IdAndCount idAndCount = new IdAndCount(idOrTpId, count);
        if (!canRemoveItem(idAndCount)) {
            String msg = String.format("无法展示物品, 受用玩家roleId=%s,itemId=%s", roleId, idOrTpId);
            throw new BusinessLogicMismatchConditionException(msg, ErrorCodeEnum.NOT_ENOUGH);
        }
        SpecialCell specialCell = getSpecialCell(idOrTpId);
        In_RoomShowItemMsg in_roomShowItemMsg = new In_RoomShowItemMsg(specialCell, roleId, getRoomPlayerCtrl().getRoleName());
        getRoomPlayerCtrl().getRoomActorRef().tell(in_roomShowItemMsg, ActorRef.noSender());
        Sm_ItemBag.Action action = Action.RESP_SHOW_ITEM;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_ItemBag, action);
        Sm_ItemBag smItemBag = ItemBagCtrlProtos.createShowItemBag(specialCell, getRoomPlayerCtrl().getRoleName(), target.getDramaId());
        response.setResult(true);
        response.setSmItemBag(smItemBag);
        getRoomPlayerCtrl().send(response.build());
    }

    @Override
    public void sync() {
        Sm_ItemBag.Action action = Action.RESP_SYNC;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_ItemBag, action);
        Sm_ItemBag smItemBag = ItemBagCtrlProtos.createSmItemBagByItem(getTarget());
        response.setResult(true);
        response.setSmItemBag(smItemBag);
        getRoomPlayerCtrl().send(response.build());
    }

    @Override
    public void iniPropItem(Pr_InitPropMsg msg) {
        IdMaptoCount idMaptoCount = msg.getIdMaptoCount();
        if (canAddItem(idMaptoCount)) {
            addItem(idMaptoCount);
        }
    }

    @Override
    public void onBeShowItem(SpecialCell specialCell, String roleName) {
        Sm_ItemBag.Action action = Action.RESP_BE_SHOW_ITEM;
        Response.Builder response = ProtoUtils.create_Response(Code.Sm_ItemBag, action);
        Sm_ItemBag smItemBag = ItemBagCtrlProtos.createBeShowItemBag(specialCell, roleName, target.getDramaId());
        response.setResult(true);
        response.setSmItemBag(smItemBag);
        getRoomPlayerCtrl().send(response.build());
    }

    @Override
    public void onBeGiveItem(In_RoomGiveItemMsg msg) {
        Sm_ItemBag smItemBag;
        if (msg.getItemType() == IdItemTypeEnum.MONEY) {
            PlainCell plainCell = msg.getPlainCell();
            ItemBagUtils.addPlainItem(target, plainCell.getItemTemplateId(), plainCell.getStackSize(), IdItemTypeEnum.MONEY.isUseCell());
            smItemBag = ItemBagCtrlProtos.createBeGiveItemBag(plainCell, msg.getSelfRoleName(), target.getDramaId());
        } else {
            SpecialCell specialCell = msg.getSpecialCell();
            IdItemTypeEnum idItemTypeEnum = IdItemTypeEnum.parseByItemId(specialCell.getId());
            ItemBagUtils.addOneSpecialItem(target, specialCell.getTpId(), idItemTypeEnum.isUseCell());
            smItemBag = ItemBagCtrlProtos.createBeGiveItemBag(specialCell, msg.getSelfRoleName(), target.getDramaId());
        }
        Action action = Action.RESP_BE_GIVE_ITEM;
        Builder response = ProtoUtils.create_Response(Code.Sm_ItemBag, action);
        response.setResult(true);
        response.setSmItemBag(smItemBag);
        getRoomPlayerCtrl().send(response.build());
    }


    private boolean isMe(int roleId) {
        return getRoomPlayerCtrl().getRoleId() == roleId;
    }

}