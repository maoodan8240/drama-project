package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.utils.CellParser;

import java.util.List;
import java.util.Map;

public class Table_Stage_Row extends AbstractRow {
    /**
     * string 台词
     */
    private String line;
    /**
     * string 配音
     */
    private String dub;
    /**
     * int  小剧场环节
     */
    private Integer stgNum;
    /**
     * string 角色名(1顾行简 2了失 3靳来生 4路停云 5太后 6 青青 99旁白)
     */
    private ListCell<Integer> roleId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        roleId = CellParser.parseListCell("RoleId", map, Integer.class);
        // id column = {columnName:"Id", columnDesc:"ID"}
        stgNum = CellParser.parseSimpleCell("StgNum", map, Integer.class);
        dub = CellParser.parseSimpleCell("Dub", map, String.class);
        line = CellParser.parseSimpleCell("Line", map, String.class);
    }


    public String getLine() {
        return line;
    }

    public String getDub() {
        return dub;
    }

    public Integer getStgNum() {
        return stgNum;
    }

    public List<Integer> getRoleId() {
        return roleId.getAll();
    }
}
