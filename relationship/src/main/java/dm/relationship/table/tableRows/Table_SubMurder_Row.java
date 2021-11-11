package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_SubMurder_Row extends AbstractRow {
    /**
     * int 第几次Sub投凶
     */
    private Integer subVoteNum;
    /**
     * string 角色图片
     */
    private String subRolePic;
    /**
     * int Sub角色ID
     */
    private Integer subRoleId;
    /**
     * bool 真凶
     */
    private Boolean murder;
    /**
     * string 角色名称
     */
    private String subRoleName;
    /**
     * bool 是否显示真凶
     */
    private boolean truth;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
// id column = {columnName:"Id", columnDesc:"ID"}
        subVoteNum = CellParser.parseSimpleCell("SubVoteNum", map, Integer.class); //int
        subRolePic = CellParser.parseSimpleCell("SubRolePic", map, String.class); //string
        subRoleId = CellParser.parseSimpleCell("SubRoleId", map, Integer.class); //int
        murder = CellParser.parseSimpleCell("Murder", map, Boolean.class); //bool
        subRoleName = CellParser.parseSimpleCell("SubRoleName", map, String.class); //string
        truth = CellParser.parseSimpleCell("Truth", map, Boolean.class); //bool

    }


    public Integer getSubVoteNum() {
        return subVoteNum;
    }

    public String getSubRolePic() {
        return subRolePic;
    }

    public Integer getSubRoleId() {
        return subRoleId;
    }

    public Boolean getMurder() {
        return murder;
    }

    public String getSubRoleName() {
        return subRoleName;
    }

    public boolean isTruth() {
        return truth;
    }

    public static List<Integer> getAllRoleId(int dramaId, int subVoteNum) {
        List<Integer> arr = new ArrayList<>();
        for (Table_SubMurder_Row row : RootTc.get(Table_SubMurder_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getSubVoteNum() == subVoteNum) {
                arr.add(row.getSubRoleId());
            }
        }
        return arr;
    }


    public static Table_SubMurder_Row getMurderRowByRoleId(int subRoleId, int dramaId, int subVoteNum) {
        for (Table_SubMurder_Row row : RootTc.get(Table_SubMurder_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getSubRoleId() == subRoleId && row.getSubVoteNum() == subVoteNum) {
                return row;
            }
        }
        String msg = String.format("Table_SubMurder_Row.getMurderRowByRoleId subRoleId=%s,dramaId=%s,subVoteNum=%s", subRoleId, dramaId, subVoteNum);
        throw new TableRowLogicCheckFailedException(Table_SubMurder_Row.class, subRoleId, msg);
    }

    public static Table_SubMurder_Row getRealMurderRow(int dramaId, int subVoteNum) {
        for (Table_SubMurder_Row row : RootTc.get(Table_SubMurder_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getSubVoteNum() == subVoteNum && row.getMurder()) {
                return row;
            }
        }
        String msg = String.format("Table_SubMurder_Row.getRealMurderRow dramaId=%s,subVoteNum=%s", dramaId, subVoteNum);
        throw new TableRowLogicCheckFailedException(Table_SubMurder_Row.class, subVoteNum, msg);
    }
}
