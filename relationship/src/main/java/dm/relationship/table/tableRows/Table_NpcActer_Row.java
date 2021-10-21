package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.List;
import java.util.Map;

public class Table_NpcActer_Row extends AbstractRow {
    /**
     * string 初始道具
     */
    private TupleListCell<String> prop;
    /**
     * int  角色性别
     */
    private Integer sex;
    /**
     * string 选取角色后的立绘
     */
    private String pic;
    /**
     * int 角色ID
     */
    private Integer roleId;
    /**
     * string 角色头像
     */
    private String profile;
    /**
     * string 角色名
     */
    private String name;

    /***
     * string 任务
     */
    private String npcTask;

    @Override

    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        prop = CellParser.parseTupleListCell("Prop", map, String.class);//string
        sex = CellParser.parseSimpleCell("Sex", map, Integer.class); //int
        pic = CellParser.parseSimpleCell("Pic", map, String.class); //string
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class); //int
        profile = CellParser.parseSimpleCell("Profile", map, String.class); //string
        name = CellParser.parseSimpleCell("Name", map, String.class); //string
        npcTask = CellParser.parseSimpleCell("NpcTask", map, String.class); //string
    }

    public Integer getSex() {
        return sex;
    }

    public String getPic() {
        return pic;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public List<TupleCell<String>> getProp() {
        return prop.getAll();
    }

    public String getNpcTask() {
        return npcTask;
    }

    public static Table_NpcActer_Row getNpcRow(int dramaId) {
        for (Table_NpcActer_Row value : RootTc.get(Table_NpcActer_Row.class).values()) {
            if (value.getDramaId() == dramaId) {
                return value;
            }
        }
        String msg = String.format("getNpcRow failed, dramaId=%s", dramaId);
        throw new TableRowLogicCheckFailedException(Table_NpcActer_Row.class, dramaId, msg);
    }

}
