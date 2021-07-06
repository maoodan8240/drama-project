package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Search_Row extends AbstractRow {
    /**
     * string 线索内容
     */
    private String line;
    /**
     * bool 是否隐藏（）
     */
    private Boolean hide;
    /**
     * int 分类
     */
    private Integer typeid;
    /**
     * int  搜证幕数
     */
    private Integer srchNum;
    /**
     * string 线索图
     */
    private String pic;


    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        line = CellParser.parseSimpleCell("Line", map, String.class);
        hide = CellParser.parseSimpleCell("Hide", map, Boolean.class);
        srchNum = CellParser.parseSimpleCell("SrchNum", map, Integer.class);
        typeid = CellParser.parseSimpleCell("TypeId", map, Integer.class);
        pic = CellParser.parseSimpleCell("Pic", map, String.class);

    }

    public String getLine() {
        return line;
    }

    public Boolean getHide() {
        return hide;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public Integer getSrchNum() {
        return srchNum;
    }

    public String getPic() {
        return pic;
    }

   
    public static List<Table_Search_Row> getSearchByTypeNameAndStateTimes(String typeName, int roomStateTimes) {
        List<Table_Search_Row> result = new ArrayList<>();
        int typeId = Table_SearchType_Row.getTypeIdByName(typeName, roomStateTimes);
        for (Table_Search_Row row : RootTc.get(Table_Search_Row.class).values()) {
            if (row.getTypeid() == typeId && row.getSrchNum() == roomStateTimes) {
                result.add(row);
            }
        }
        return result;
    }

    public static List<Integer> getAllHideClueIds(int stateTimes) {
        List<Integer> result = new ArrayList<>();
        for (Table_Search_Row value : RootTc.get(Table_Search_Row.class).values()) {
            if (value.getHide() == true && value.getSrchNum() == stateTimes) {
                result.add(value.getId());
            }
        }
        return result;
    }
}

