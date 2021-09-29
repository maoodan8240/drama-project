package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.Map;

public class Table_SecretTalk_Row extends AbstractRow {
    /**
     * int sub角色ID
     */
    private Integer subRoleId;
    /**
     * string Sub角色对应剧本动画
     */
    private String subScene;
    /**
     * string Sub角色名
     */
    private String subName;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        subRoleId = CellParser.parseSimpleCell("SubRoleId", map, Integer.class); //int
        subScene = CellParser.parseSimpleCell("SubScene", map, String.class); //string
        subName = CellParser.parseSimpleCell("SubName", map, String.class); //string

    }

    public Integer getSubRoleId() {
        return subRoleId;
    }

    public String getSubScene() {
        return subScene;
    }

    public String getSubName() {
        return subName;
    }
}
