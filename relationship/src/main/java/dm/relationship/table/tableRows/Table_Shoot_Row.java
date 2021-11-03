package dm.relationship.table.tableRows;

import dm.relationship.base.MagicNumbers;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos.DefPowerEnum;
import drama.protos.EnumsProtos.PowerEnum;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Shoot_Row extends AbstractRow {
    /**
     * string 道具组合 （武器满足条件才可以开枪）
     */
    private ListCell<String> set;
    /**
     * string 组合名称
     */
    private String setName;
    /**
     * int 消耗（射击后子弹和防具均会消耗，2级枪打1级甲 防具也会消耗。)
     */
    private Integer expend;
    /**
     * int 装备类型（1武器2防具）
     */
    private Integer type;
    /**
     * int 能量（能力值，大于等于武器能力值的防具才可以生效。1挡1/2挡1，2）
     */
    private Integer power;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        set = CellParser.parseListCell("Set", map, String.class);
        setName = CellParser.parseSimpleCell("SetName", map, String.class); //string
        expend = CellParser.parseSimpleCell("Expend", map, Integer.class); //int
        type = CellParser.parseSimpleCell("Type", map, Integer.class); //int
        power = CellParser.parseSimpleCell("Power", map, Integer.class); //int
    }


    public String getSetName() {
        return setName;
    }

    public List<String> getSet() {
        return set.getAll();
    }

    public Integer getExpend() {
        return expend;
    }

    public Integer getType() {
        return type;
    }

    public Integer getPower() {
        return power;
    }

    public static List<Table_Shoot_Row> get98KRow() {
        return getRowsByTypeAndPower(MagicNumbers.DRMAAID_103, 1, PowerEnum.WEAPON_98K_VALUE);
    }

    public static List<Table_Shoot_Row> getBrownning() {
        return getRowsByTypeAndPower(MagicNumbers.DRMAAID_103, 1, PowerEnum.WEAPON_BROWNING_VALUE);
    }

    public static Table_Shoot_Row getOneLvArmor() {
        return getRowByTypeAndPower(MagicNumbers.DRMAAID_103, 2, DefPowerEnum.ONE_LV_ARMOR_VALUE);
    }

    public static Table_Shoot_Row getTwoLvArmor() {
        return getRowByTypeAndPower(MagicNumbers.DRMAAID_103, 2, DefPowerEnum.TWO_LV_ARMOR_VALUE);
    }

    private static Table_Shoot_Row getRowByTypeAndPower(int dramaId, int type, int power) {
        for (Table_Shoot_Row row : RootTc.get(Table_Shoot_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getType() == type && row.getPower() == power) {
                return row;
            }
        }
        String msg = String.format("getRowByTypeAndPower power=%s,dramaId=%s", power, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Shoot_Row.class, power, msg);
    }

    private static List<Table_Shoot_Row> getRowsByTypeAndPower(int dramaId, int type, int power) {
        List<Table_Shoot_Row> arr = new ArrayList<>();
        for (Table_Shoot_Row row : RootTc.get(Table_Shoot_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getType() == type && row.getPower() == power) {
                arr.add(row);
            }
        }
        return arr;
    }

    public static List<Table_Shoot_Row> getRowsByType(int dramaId, int type) {
        List<Table_Shoot_Row> arr = new ArrayList<>();
        for (Table_Shoot_Row row : RootTc.get(Table_Shoot_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getType() == type) {
                arr.add(row);
            }
        }
        return arr;
    }

    public static Table_Shoot_Row getSpecialEquip(int dramaId) {
        for (Table_Shoot_Row row : RootTc.get(Table_Shoot_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                IdItemTypeEnum idItemTypeEnum = IdItemTypeEnum.parseByItemTemplateId(row.getExpend());
                if (idItemTypeEnum == IdItemTypeEnum.SP_EQUIP) {
                    return row;
                }
            }
        }
        String msg = String.format("getSpecialEquip power=%s,dramaId=%s", dramaId);
        throw new TableRowLogicCheckFailedException(Table_Shoot_Row.class, dramaId, msg);
    }


}
