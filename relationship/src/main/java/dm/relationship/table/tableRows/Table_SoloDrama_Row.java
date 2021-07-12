package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.Map;

public class Table_SoloDrama_Row extends AbstractRow {
    /**
     * string 台词
     */
    private String line;
    /**
     * string 配音
     */
    private String dub;
    /**
     * int  Solo环节
     */
    private Integer soloNum;
    /**
     * int 配音时长
     */
    private Integer duration;
    /**
     * string 角色名(1顾行简 2了失 3靳来生 4路停云 5太后 6 青青 99旁白)
     */
    private String roleId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        line = CellParser.parseSimpleCell("Line", map, String.class); //string
        dub = CellParser.parseSimpleCell("Dub", map, String.class); //string
        soloNum = CellParser.parseSimpleCell("SoloNum", map, Integer.class); //int
        duration = CellParser.parseSimpleCell("Duration", map, Integer.class); //int
        roleId = CellParser.parseSimpleCell("RoleId", map, String.class); //string
    }

    public String getLine() {
        return line;
    }


    public String getDub() {
        return dub;
    }

    public Integer getSoloNum() {
        return soloNum;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getRoleId() {
        return roleId;
    }
}
