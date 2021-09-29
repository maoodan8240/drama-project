package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_SubActer_Row extends AbstractRow {
    /**
     * int 子剧本次数
     */
    private Integer subNum;
    /**
     * int sub角色ID
     */
    private Integer subRoleId;
    /**
     * string Sub角色对应剧本动画
     */
    private String subScene;
    /**
     * string Sub角色小图
     */
    private String subPic;
    /**
     * string Sub角色名
     */
    private String subName;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        subNum = CellParser.parseSimpleCell("SubNum", map, Integer.class); //int
        subRoleId = CellParser.parseSimpleCell("SubRoleId", map, Integer.class); //int
        subScene = CellParser.parseSimpleCell("SubScene", map, String.class); //string
        subPic = CellParser.parseSimpleCell("SubPic", map, String.class); //string
        subName = CellParser.parseSimpleCell("SubName", map, String.class); //string

    }

    public Integer getSubNum() {
        return subNum;
    }

    public Integer getSubRoleId() {
        return subRoleId;
    }

    public String getSubScene() {
        return subScene;
    }

    public String getSubPic() {
        return subPic;
    }

    public String getSubName() {
        return subName;
    }

    public static List<Integer> getAllSubRoleIds(int dramaId, int subNum) {
        List<Integer> arr = new ArrayList<>();
        for (Table_SubActer_Row row : RootTc.get(Table_SubActer_Row.class).values()) {
            if (row.getDramaId() == dramaId && row.getSubNum() == subNum) {
                arr.add(row.getSubRoleId());
            }
        }
        return arr;
    }

    public static Table_SubActer_Row getRowById(int dramaId, int subRoleId, int subNum) {
        for (Table_SubActer_Row row : RootTc.get(Table_SubActer_Row.class).values()) {
            if (row.getDramaId() == dramaId && //
                    row.getSubRoleId() == subRoleId && //
                    row.getSubNum() == subNum//
            ) {
                return row;
            }
        }
        String msg = String.format("Table_SubActor_Row.getRowById subRoleId=%s,dramaId=%s", subRoleId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_SubActer_Row.class, subRoleId, msg);
    }
}
