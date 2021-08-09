package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

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
    private String roleId;
    /***
     * int 配音时长
     */
    private int duration;
    private Integer dramaId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        line = CellParser.parseSimpleCell("Line", map, String.class); //string
        dub = CellParser.parseSimpleCell("Dub", map, String.class); //string
        stgNum = CellParser.parseSimpleCell("StgNum", map, Integer.class); //int
        roleId = CellParser.parseSimpleCell("RoleId", map, String.class); //string
        duration = CellParser.parseSimpleCell("Duration", map, Integer.class);//int
        dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
    }

    public Integer getDramaId() {
        return dramaId;
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

    public String getRoleId() {
        return roleId;
    }

    public int getDuration() {
        return duration;
    }
}
