package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

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

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        itemType = CellParser.parseSimpleCell("ItemType", map, Integer.class); //int
        itemName = CellParser.parseSimpleCell("ItemName", map, String.class); //string
        itemPic = CellParser.parseSimpleCell("ItemPic", map, String.class); //string

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
}
