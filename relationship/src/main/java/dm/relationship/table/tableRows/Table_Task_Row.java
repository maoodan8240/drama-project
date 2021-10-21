package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.Map;

public class Table_Task_Row extends AbstractRow {
    /**
     * bool 解锁信息
     */
    private boolean unlockTask;
    /**
     * string 任务内容
     */
    private String task;
    /**
     * int 任务得分
     */
    private Integer score;
    /**
     * string 任务描述
     */
    private String taskDes;
    /**
     * int  角色id
     */
    private Integer roleId;
    /**
     * string 姓名
     */
    private String name;


    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        unlockTask = CellParser.parseSimpleCell("UnlockTask", map, Boolean.class); //bool
        task = CellParser.parseSimpleCell("Task", map, String.class); //string
        score = CellParser.parseSimpleCell("Score", map, Integer.class); //int
        taskDes = CellParser.parseSimpleCell("TaskDes", map, String.class); //string
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class); //int
        name = CellParser.parseSimpleCell("Name", map, String.class); //string
    }

    public boolean isUnlockTask() {
        return unlockTask;
    }

    public String getTask() {
        return task;
    }

    public Integer getScore() {
        return score;
    }

    public String getTaskDes() {
        return taskDes;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public static Table_Task_Row getRowByRoleId(int roleId, int dramaId) {
        for (Table_Task_Row row : RootTc.get(Table_Task_Row.class).values()) {
            if (row.getRoleId() == roleId && row.getDramaId() == dramaId) {
                return row;
            }
        }
        String msg = String.format("getRowByRoleId roleId=%s,dramaId=%s", roleId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Task_Row.class, roleId, msg);
    }

    public static String getUnlockTask(int roleId, int dramaId) {
        for (Table_Task_Row row : RootTc.get(Table_Task_Row.class).values()) {
            if (row.getRoleId() == roleId && row.getDramaId() == dramaId && row.isUnlockTask()) {
                return row.getTaskDes();
            }
        }
        String msg = String.format("getRowByRoleId roleId=%s,dramaId=%s", roleId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Task_Row.class, roleId, msg);
    }
}
