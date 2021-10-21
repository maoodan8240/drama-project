package drama.gameServer.features.extp.itemBag.ctrl;

import dm.relationship.base.IdAndCount;
import dm.relationship.base.IdMaptoCount;
import drama.gameServer.features.actor.room.mc.controler.RoomPlayerExtControler;
import drama.gameServer.features.extp.itemBag.msg.In_RoomGiveItemMsg;
import drama.gameServer.features.extp.itemBag.msg.Pr_InitPropMsg;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;
import drama.gameServer.features.extp.itemBag.pojo.PlainCell;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;

/**
 * Created by lee on 2021/9/30
 */
public interface ItemBagCtrl extends RoomPlayerExtControler<ItemBag> {
    /**
     * 扩充背包
     */
    void extend();

    /**
     * 背包是否已满
     *
     * @return true 背包已满 false 背包未满
     */
    boolean isFull();

    /**
     * 是否可以添加物品 tpId
     *
     * @param idMaptoCount
     * @return
     */
    boolean canAddItem(IdMaptoCount idMaptoCount);

    /**
     * 添加物品 tpId
     *
     * @param idMaptoCount
     */
    IdMaptoCount addItem(IdMaptoCount idMaptoCount);

    /**
     * 是否可以删除物品 id Or tpId
     *
     * @param idMaptoCount
     * @return
     */
    boolean canRemoveItem(IdMaptoCount idMaptoCount);

    /**
     * 删除物品 id Or tpId
     *
     * @param idMaptoCount
     */
    IdMaptoCount removeItem(IdMaptoCount idMaptoCount);


    /**
     * 是否可以添加物品 tpId
     *
     * @param idAndCount
     * @return
     */
    boolean canAddItem(IdAndCount idAndCount);

    /**
     * 添加物品 tpId
     *
     * @param idAndCount
     */
    IdAndCount addItem(IdAndCount idAndCount);

    /**
     * 是否可以删除物品 id Or tpId
     *
     * @param idAndCount
     * @return
     */
    boolean canRemoveItem(IdAndCount idAndCount);

    /**
     * 删除物品 id Or tpId
     *
     * @param idAndCount
     */
    IdAndCount removeItem(IdAndCount idAndCount);

    /**
     * 获取普通物品的数量
     *
     * @param tpId
     * @return
     */
    long queryTemplateItemCount(int tpId);

    /**
     * 查询特殊物品
     *
     * @param itemId
     * @return
     */
    SpecialCell getSpecialCell(int itemId);

    /**
     * 获取普通物品
     *
     * @param tpId
     * @return
     */
    PlainCell getPlainCell(int tpId);

    /**
     * gm命令：清除背包
     */
    void gmCommond_ClearItemBag();

    /**
     * 使用物品
     *
     * @param itemTemplateId
     * @param count
     */
    void useItem(int itemTemplateId, int count);

    /**
     * 卖出物品
     *
     * @param idOrTpId
     * @param count
     */
    void sellItem(int idOrTpId, int count);

    /**
     * 赠与物品
     *
     * @param idOrTpId
     * @param count
     * @param roleId
     */
    void giveItem(int idOrTpId, int count, int roleId);

    /**
     * 展示物品
     *
     * @param idOrTpId
     * @param count
     * @param roleId
     */
    void showItem(int idOrTpId, int count, int roleId);

    void sync();

    /**
     * 初始化装备
     *
     * @param msg
     */
    void iniPropItem(Pr_InitPropMsg msg);

    /**
     * 接到展示物品
     *
     * @param specialCell
     * @param roleName
     */
    void onBeShowItem(SpecialCell specialCell, String roleName);

    /**
     * 接收赠送物品
     *
     * @param msg
     */
    void onBeGiveItem(In_RoomGiveItemMsg msg);
}

