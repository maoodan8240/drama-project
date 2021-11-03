package drama.gameServer.features.extp.itemBag.utils;

import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.table.tableRows.Table_Item_Row;
import drama.gameServer.features.extp.itemBag.pojo.ItemBag;
import drama.gameServer.features.extp.itemBag.pojo.PlainCell;
import drama.gameServer.features.extp.itemBag.pojo.SpecialCell;
import drama.protos.room.ItemBagProtos.Sm_ItemBag;
import drama.protos.room.ItemBagProtos.Sm_ItemBag.Action;
import drama.protos.room.ItemBagProtos.Sm_ItemBag_PlainCell;
import drama.protos.room.ItemBagProtos.Sm_ItemBag_PlainCell.Builder;
import drama.protos.room.ItemBagProtos.Sm_ItemBag_SpecialCell;
import drama.protos.room.RoomProtos.Sm_Room_ShootResult;
import drama.protos.room.RoomProtos.Sm_Room_UnlockInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lee on 2021/10/12
 */
public class ItemBagCtrlProtos {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemBagCtrlProtos.class);


    public static Sm_ItemBag createSmItemBagByItem(ItemBag itemBag) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        b.setAction(Sm_ItemBag.Action.RESP_SYNC);
        Sm_ItemBag_PlainCell.Builder b$Plain = Sm_ItemBag_PlainCell.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        itemBag.getTpIdToPlainCell().values().forEach(plainCell -> {
            addPlain(plainCell, b, b$Plain, itemBag.getDramaId());
        });
        // 背包中的特殊物品

        itemBag.getIdToSpecialCell().values().forEach(specialCell -> {
            addSpecial(specialCell, b, b$Special, itemBag.getDramaId());
        });
        return b.build();
    }

    /**
     * 增加特殊物品到 Sm_ItemBag_SpecialCell.Builder
     *
     * @param specialCell
     * @param b
     * @param b$Special
     */
    private static void addSpecial(SpecialCell specialCell, Sm_ItemBag.Builder b, Sm_ItemBag_SpecialCell.Builder b$Special, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(specialCell.getTpId(), dramaId);
        b$Special.clear();
        addSpecial(specialCell, b$Special, row);
        b.addSpecialCells(b$Special.build());
    }

    /**
     * 增加普通物品到 Sm_ItemBag_PlainCell.Builder
     *
     * @param plainCell
     * @param b
     * @param b$Plain
     */
    private static void addPlain(PlainCell plainCell, Sm_ItemBag.Builder b, Sm_ItemBag_PlainCell.Builder b$Plain, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(plainCell.getItemTemplateId(), dramaId);
        b$Plain.clear();
        addPlain(plainCell, b$Plain, row);
        b.addPlainCells(b$Plain.build());
    }

    /**
     * 增加特殊物品到 Sm_ItemBag_SpecialCell.Builder到Sm_Room_UnlockInfo.Builder
     *
     * @param b
     * @param specialCell
     * @param b$Special
     * @return
     */
    public static void addSpecial(SpecialCell specialCell, Sm_Room_UnlockInfo.Builder b, Sm_ItemBag_SpecialCell.Builder b$Special, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(specialCell.getTpId(), dramaId);
        b$Special.clear();
        addSpecial(specialCell, b$Special, row);
        b.addSpecialCells(b$Special.build());
    }


    /**
     * 增加普通物品到 Sm_ItemBag_PlainCell.Builder到Sm_Room_UnlockInfo.Builder
     *
     * @param b
     * @param plainCell
     * @param b$Plain
     * @return
     */
    public static void addPlain(PlainCell plainCell, Sm_Room_UnlockInfo.Builder b, Builder b$Plain, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(plainCell.getItemTemplateId(), dramaId);
        b$Plain.clear();
        addPlain(plainCell, b$Plain, row);
        b.addPlainCells(b$Plain.build());
    }


    /**
     * 添加特殊物品 Sm_ItemBag_SpecialCell.Builder到Sm_Room_ShootResult.Builder
     *
     * @param specialCell
     * @param b
     * @param b$Special
     * @param dramaId
     */
    public static void addSpecial(SpecialCell specialCell, Sm_Room_ShootResult.Builder b, Sm_ItemBag_SpecialCell.Builder b$Special, int dramaId) {
        Table_Item_Row row = Table_Item_Row.getRowByItemId(specialCell.getTpId(), dramaId);
        b$Special.clear();
        addSpecial(specialCell, b$Special, row);
        b.addSpecialCells(b$Special.build());
    }


    public static Sm_ItemBag createShowItemBag(SpecialCell specialCell, String roleName, int dramaId) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        b.setAction(Action.RESP_SHOW_ITEM);
        addSpecial(specialCell, b, b$Special, dramaId);
        b.setRoleName(roleName);
        return b.build();
    }

    public static Sm_ItemBag createBeGiveItemBag(PlainCell plainCell, String roleName, int dramaId) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        Sm_ItemBag_PlainCell.Builder b$Plain = Sm_ItemBag_PlainCell.newBuilder();
        b.setAction(Action.RESP_BE_GIVE_ITEM);
        addPlain(plainCell, b, b$Plain, dramaId);
        b.setRoleName(roleName);
        return b.build();
    }

    public static Sm_ItemBag createBeGiveItemBag(SpecialCell specialCell, String roleName, int dramaId) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        b.setAction(Action.RESP_BE_GIVE_ITEM);
        addSpecial(specialCell, b, b$Special, dramaId);
        b.setRoleName(roleName);
        return b.build();
    }

    public static Sm_ItemBag createGiveItemBag(ItemBag itemBag) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        b.setAction(Sm_ItemBag.Action.RESP_GIVE_ITEM);
        Sm_ItemBag_PlainCell.Builder b$Plain = Sm_ItemBag_PlainCell.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        itemBag.getTpIdToPlainCell().values().forEach(plainCell -> {
            addPlain(plainCell, b, b$Plain, itemBag.getDramaId());
        });
        // 背包中的特殊物品

        itemBag.getIdToSpecialCell().values().forEach(specialCell -> {
            addSpecial(specialCell, b, b$Special, itemBag.getDramaId());
        });
        return b.build();
    }

    public static Sm_ItemBag createBeShowItemBag(SpecialCell specialCell, String roleName, int dramaId) {
        Sm_ItemBag.Builder b = Sm_ItemBag.newBuilder();
        Sm_ItemBag_SpecialCell.Builder b$Special = Sm_ItemBag_SpecialCell.newBuilder();
        b.setAction(Action.RESP_BE_SHOW_ITEM);
        addSpecial(specialCell, b, b$Special, dramaId);
        b.setRoleName(roleName);
        return b.build();
    }

    private static void addPlain(PlainCell plainCell, Builder b$Plain, Table_Item_Row row) {
        b$Plain.setItemTemplateId(plainCell.getItemTemplateId());
        b$Plain.setCount(plainCell.getStackSize());
        b$Plain.setItemPic(row.getItemPic());
        b$Plain.setItemBigPic(row.getItemBigPic());
        b$Plain.setItemName(row.getItemName());
    }

    private static void addSpecial(SpecialCell specialCell, Sm_ItemBag_SpecialCell.Builder b$Special, Table_Item_Row row) {
        b$Special.setItemTemplateId(specialCell.getTpId());
        b$Special.setItemId(specialCell.getId());
        b$Special.setItemPic(row.getItemPic());
        b$Special.setItemBigPic(row.getItemBigPic());
        b$Special.setItemName(row.getItemName());
        b$Special.setItemType(IdItemTypeEnum.parseByItemTemplateId(specialCell.getTpId()).getPrefixNum());
    }
}
