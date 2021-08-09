package dm.relationship.enums.item;

import dm.relationship.exception.ParseItemTemplateIdTypeFailedException;
import ws.common.table.table.implement.AbstractRow;

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
    private boolean useCell; // 是否使用背包
    private IdItemBigTypeEnum bigType; // 物品大类
    private Class<? extends AbstractRow> rowClass; // 对应的策划表

    IdItemTypeEnum(int minTpId, int maxTpId, boolean useCell, IdItemBigTypeEnum bigType, Class<? extends AbstractRow> rowClass) {
        this.minTpId = minTpId;
        this.maxTpId = maxTpId;
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


    public static IdItemTypeEnum parseByItemTemplateId(int tpId) {
        for (IdItemTypeEnum type : values()) {
            if (tpId >= type.minTpId && tpId <= type.maxTpId) {
                return type;
            }
        }
        throw new ParseItemTemplateIdTypeFailedException(Arrays.toString(values()), tpId);
    }

   
}
