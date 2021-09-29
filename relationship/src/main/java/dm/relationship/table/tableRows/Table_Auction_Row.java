package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.Map;

public class Table_Auction_Row extends AbstractRow {
    /**
     * int 拍卖道具id
     */
    private Integer aucItem;
    /**
     * int 最低竞拍价格
     */
    private Integer minPrize;
    /**
     * string 拍品名称
     */
    private String aucItemName;
    /**
     * int 拍卖轮次
     */
    private Integer aucNum;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        aucItem = CellParser.parseSimpleCell("AucItem", map, Integer.class); //string
        minPrize = CellParser.parseSimpleCell("MinPrize", map, Integer.class); //int
        aucItemName = CellParser.parseSimpleCell("AucItemName", map, String.class); //int
        aucNum = CellParser.parseSimpleCell("AucNum", map, Integer.class); //int

    }

    public Integer getAucItem() {
        return aucItem;
    }

    public Integer getMinPrize() {
        return minPrize;
    }

    public String getAucItemName() {
        return aucItemName;
    }

    public Integer getAucNum() {
        return aucNum;
    }
}
