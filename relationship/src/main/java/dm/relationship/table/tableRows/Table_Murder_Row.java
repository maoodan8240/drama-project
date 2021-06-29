package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.Map;

public class Table_Murder_Row extends AbstractRow {
    /**
     * string 角色名称
     */
    private String roleName;
    /**
     * bool 真凶
     */
    private Boolean murder;
    /**
     * string 角色图片
     */
    private String rolePic;
    /**
     * int 第几次投凶
     */
    private Integer voteNum;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        roleName = CellParser.parseSimpleCell("RoleName", map, String.class);
        rolePic = CellParser.parseSimpleCell("RolePic", map, String.class);
        voteNum = CellParser.parseSimpleCell("VoteNum", map, Integer.class);
        murder = CellParser.parseSimpleCell("Murder", map, Boolean.class);
    }

    public String getRoleName() {
        return roleName;
    }

    public Boolean getMurder() {
        return murder;
    }

    public String getRolePic() {
        return rolePic;
    }

    public Integer getVoteNum() {
        return voteNum;
    }
}
