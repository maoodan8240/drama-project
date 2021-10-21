package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static Map<Integer, Table_Auction_Row> getIdToAuctionRow(int dramaId) {
        Map<Integer, Table_Auction_Row> map = new HashMap<>();
        for (Table_Auction_Row row : RootTc.get(Table_Auction_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                map.put(row.getId(), row);
            }
        }
        return map;
    }

    public static List<Table_Auction_Row> getAllAuctionByRunDown(int runDown, int dramaId) {
        List<Table_Auction_Row> arr = new ArrayList<>();
        for (Table_Auction_Row row : RootTc.get(Table_Auction_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getAucNum() == runDown) {
                arr.add(row);
            }
        }
        return arr;
    }
}
