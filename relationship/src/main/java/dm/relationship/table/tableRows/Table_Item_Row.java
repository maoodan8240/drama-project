package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos.ItemBigTypePrefixNumEnum;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table_Item_Row extends AbstractRow {
    /**
     * int 道具类型（1角色道具,2武器装备,3金币）
     */
    private Integer itemType;
    /**
     * string 道具名
     */
    private String itemName;
    /**
     * string 道具图片
     */
    private String itemPic;
    /**
     * string 道具详情图片
     */
    private String itemBigPic;

    public static List<Integer> getEarringsIds(int dramaId) {
        List<Integer> arr = new ArrayList<>();
        for (Table_Item_Row row : RootTc.get(Table_Item_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getItemType() == ItemBigTypePrefixNumEnum.PREFIXNUM_SP_MONEY.getNumber()) {
                arr.add(row.getId());
            }
        }
        return arr;
    }

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        itemType = CellParser.parseSimpleCell("ItemType", map, Integer.class); //int
        itemName = CellParser.parseSimpleCell("ItemName", map, String.class); //string
        itemPic = CellParser.parseSimpleCell("ItemPic", map, String.class); //string
        itemBigPic = CellParser.parseSimpleCell("ItemBigPic", map, String.class); //string

    }

    public Integer getItemType() {
        return itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPic() {
        return itemPic;
    }

    public String getItemBigPic() {
        return itemBigPic;
    }

    public static Table_Item_Row getRowByItemId(int itemId, int dramaId) {
        for (Table_Item_Row row : RootTc.get(Table_Item_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getId() == itemId) {
                return row;
            }
        }
        String msg = String.format("getRowByItemId itemId=%s,dramaId=%s", itemId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Item_Row.class, itemId, msg);
    }

    public static Map<Integer, Table_Item_Row> getAllRow(int dramaId) {
        Map<Integer, Table_Item_Row> map = new HashMap<>();
        for (Table_Item_Row row : RootTc.get(Table_Item_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                map.put(row.getId(), row);
            }
        }
        return map;
    }
}
