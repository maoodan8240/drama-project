package drama.gameServer.features.extp.itemBag.enums;

import com.google.protobuf.ProtocolMessageEnum;
import dm.relationship.base.MagicNumbers;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.tableRows.Table_Shoot_Row;
import drama.protos.EnumsProtos.DefPowerEnum;
import drama.protos.EnumsProtos.PowerEnum;
import ws.common.table.table.implement.AbstractRow;

import java.util.List;

/**
 * Created by lee on 2021/10/21
 */
public enum EquipEnum {


    WEAPON_BROWNING(PowerEnum.WEAPON_BROWNING, Table_Shoot_Row.getBrownning(), MagicNumbers.WEAPON_BROWNING_ID),  //勃朗宁
    WEAPON_98K(PowerEnum.WEAPON_98K, Table_Shoot_Row.get98KRow(), MagicNumbers.WEAPON_98K_ID),            //98K
    ONE_LV_ARMOR(DefPowerEnum.ONE_LV_ARMOR, Table_Shoot_Row.getOneLvArmor(), MagicNumbers.ONE_LV_ARMOR_ID),     //一级防弹衣
    TWO_LV_ARMOR(DefPowerEnum.TWO_LV_ARMOR, Table_Shoot_Row.getTwoLvArmor(), MagicNumbers.TWO_LV_ARMOR_ID),     //二级防弹衣

    NULL();

    private ProtocolMessageEnum power;
    private List<? extends AbstractRow> rows;
    private AbstractRow row;
    private int itemId;

    EquipEnum(ProtocolMessageEnum power, AbstractRow row, int itemId) {
        this.power = power;
        this.row = row;
        this.itemId = itemId;
    }

    EquipEnum(ProtocolMessageEnum power, List<Table_Shoot_Row> rows, int itemId) {
        this.power = power;
        this.rows = rows;
        this.itemId = itemId;
    }

    EquipEnum() {

    }

    public static String getSetName(PowerEnum power) {
        List<Table_Shoot_Row> rows = (List<Table_Shoot_Row>) parseByPower(power).getPowerRows();
        for (Table_Shoot_Row row : rows) {
            return row.getSetName();
        }
        String msg = String.format("没有找到对应的SetName={}", power.toString());
        throw new BusinessLogicMismatchConditionException(msg);
    }


    public static EquipEnum parseByPower(PowerEnum power) {
        for (EquipEnum value : values()) {
            if (value.getPower() == power) {
                return value;
            }
        }
        String msg = String.format("没有找到对应的PowerEnum={}", power.toString());
        throw new BusinessLogicMismatchConditionException(msg);
    }

    public static EquipEnum parseByDefPower(DefPowerEnum defPower) {
        for (EquipEnum value : values()) {
            if (value.getPower() == defPower) {
                return value;
            }
        }
        String msg = String.format("没有找到对应的DefPowerEnum={}", defPower.toString());
        throw new BusinessLogicMismatchConditionException(msg);
    }


    public static EquipEnum parseByItem(int itemId) {
        for (EquipEnum value : values()) {
            if (value.getItemId() == itemId) {
                return value;
            }
        }
        String msg = String.format("没有找到对应的itemId={}", itemId);
        throw new BusinessLogicMismatchConditionException(msg);
    }

    public ProtocolMessageEnum getPower() {
        return power;
    }


    public List<? extends AbstractRow> getPowerRows() {
        return rows;
    }

    public AbstractRow getDefPowerRow() {
        return row;
    }

    public int getItemId() {
        return itemId;
    }
}
