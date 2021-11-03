package dm.relationship.enums.item;

import dm.relationship.base.MagicNumbers;
import dm.relationship.exception.ParseItemIdTypeFailedException;
import dm.relationship.exception.ParseItemTemplateIdTypeFailedException;
import dm.relationship.table.tableRows.Table_Item_Row;
import drama.protos.EnumsProtos.ItemBigTypePrefixNumEnum;
import ws.common.table.table.implement.AbstractRow;

import java.util.Arrays;

public enum IdItemTypeEnum {

    //----------------------------------------------------------------SpecialItem-------------------------------------------------------------


    //----------------------------------------------------------------PlainItem-------------------------------------------------------------

    /**
     * 金币类型
     */
    MONEY(1, 10, ItemBigTypePrefixNumEnum.PREFIXNUM_MONEY, false, IdItemBigTypeEnum.PlainItem, Table_Item_Row.class), //
    /**
     * 装备类型
     */
    EQUIP(20, 29, ItemBigTypePrefixNumEnum.PREFIXNUM_EQUIP, true, IdItemBigTypeEnum.SpecialItem, Table_Item_Row.class), //
    /**
     * 道具类型
     */
    ITEM(30, 39, ItemBigTypePrefixNumEnum.PREFIXNUM_ITEM, true, IdItemBigTypeEnum.SpecialItem, Table_Item_Row.class),//
    /**
     * 特殊装备类型
     */
    SP_EQUIP(40, 49, ItemBigTypePrefixNumEnum.PREFIXNUM_SP_EQUIP, true, IdItemBigTypeEnum.SpecialItem, Table_Item_Row.class),//
    /**
     * 特殊金币道具
     */
    SP_MONEY(50, 59, ItemBigTypePrefixNumEnum.PREFIXNUM_SP_MONEY, true, IdItemBigTypeEnum.SpecialItem, Table_Item_Row.class),//
    /**
     *
     */
    OTHER(99, 99, ItemBigTypePrefixNumEnum.PREFIXNUM_OTHER, false, IdItemBigTypeEnum.PlainItem, Table_Item_Row.class),//
    ;//

    private int minTpId; // 最小模板Id
    private int maxTpId; // 最大模板Id
    private ItemBigTypePrefixNumEnum prefixNum; // 实例Id的前缀，-1表示模板id（PlainItem）不会存在实例Id，
    private boolean useCell; // 是否使用背包
    private IdItemBigTypeEnum bigType; // 物品大类
    private Class<? extends AbstractRow> rowClass; // 对应的策划表

    IdItemTypeEnum(int minTpId, int maxTpId, ItemBigTypePrefixNumEnum prefixNum, boolean useCell, IdItemBigTypeEnum bigType, Class<? extends AbstractRow> rowClass) {
        this.minTpId = minTpId;
        this.maxTpId = maxTpId;
        this.prefixNum = prefixNum;
        this.useCell = useCell;
        this.bigType = bigType;
        this.rowClass = rowClass;
    }


    public boolean isUseCell() {
        return useCell;
    }

    public IdItemBigTypeEnum getBigType() {
        return bigType;
    }

    public Class<? extends AbstractRow> getRowClass() {
        return rowClass;
    }

    public ItemBigTypePrefixNumEnum getPrefixNum() {
        return prefixNum;
    }

    public static IdItemTypeEnum parseByItemId(int itemId) {
        if (itemId > Math.abs(MagicNumbers.ITEM_ID_PREFIX_DIVISOR)) {
            int prefix = Math.abs(itemId) / MagicNumbers.ITEM_ID_PREFIX_DIVISOR;
            for (IdItemTypeEnum type : values()) {
                if (type.prefixNum.getNumber() == prefix) {
                    return type;
                }
            }
        }
        throw new ParseItemIdTypeFailedException(Arrays.toString(values()), itemId);
    }

    public static IdItemTypeEnum parseByItemTemplateId(int tpId) {
        for (IdItemTypeEnum type : values()) {
            if (tpId >= type.minTpId && tpId <= type.maxTpId) {
                return type;
            }
        }
        throw new ParseItemTemplateIdTypeFailedException(Arrays.toString(values()), tpId);
    }

    public static int genItemId(int tpId, int seq) {
        IdItemTypeEnum type = IdItemTypeEnum.parseByItemTemplateId(tpId);
        return type.getPrefixNum().getNumber() * MagicNumbers.ITEM_ID_PREFIX_DIVISOR + seq;
    }


}
