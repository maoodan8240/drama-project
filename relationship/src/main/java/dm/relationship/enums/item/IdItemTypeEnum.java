package dm.relationship.enums.item;

import ws.common.table.table.implement.AbstractRow;
import drama.protos.EnumsProtos.ItemBigTypePrefixNumEnum;
import dm.relationship.base.MagicNumbers;
import dm.relationship.exception.ParseItemIdTypeFailedException;
import dm.relationship.exception.ParseItemTemplateIdTypeFailedException;

import java.util.Arrays;

public enum IdItemTypeEnum {

    //----------------------------------------------------------------SpecialItem-------------------------------------------------------------



    //----------------------------------------------------------------PlainItem-------------------------------------------------------------

    /**
     * 资源类型
     */
//    RESOURCE(1, 1000, ItemBigTypePrefixNumEnum.PREFIXNUM_OTHER, false, IdItemBigTypeEnum.PlainItem, Table_Resource_Row.class), //
    /**
     * 道具类型
     */
//    ITEM(4000000, 4999999, ItemBigTypePrefixNumEnum.PREFIXNUM_ITEM, false, IdItemBigTypeEnum.PlainItem, Table_Item_Row.class), //

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
