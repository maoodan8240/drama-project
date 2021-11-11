package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Task_Row extends AbstractRow {
    /**
     * bool 解锁信息
     */
    private boolean unlockTask;
    /**
     * int 任务得分
     */
    private Integer score;
    /**
     * int 任务目标角色
     */
    private Integer taskRoleId;
    /**
     * string 任务内容
     */
    private String taskType;
    /**
     * string 任务描述
     */
    private String taskDes;
    /**
     * int 任务目标道具
     */
    private ListCell<String> taskItem;
    /**
     * int  角色id
     */
    private Integer roleId;
    /**
     * string 姓名
     */
    private String name;
    /**
     * int 子投凶轮数（投中凶手或逃脱用）
     */
    private int subVoteNum;

    @Override

    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        unlockTask = CellParser.parseSimpleCell("UnlockTask", map, Boolean.class); //bool
        score = CellParser.parseSimpleCell("Score", map, Integer.class); //int
        taskRoleId = CellParser.parseSimpleCell("TaskRoleId", map, Integer.class); //int
        taskType = CellParser.parseSimpleCell("TaskType", map, String.class); //string
        taskDes = CellParser.parseSimpleCell("TaskDes", map, String.class); //string
        taskItem = CellParser.parseListCell("TaskItem", map, String.class); //string
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class); //int
        name = CellParser.parseSimpleCell("Name", map, String.class); //string
        subVoteNum = CellParser.parseSimpleCell("SubVoteNum", map, Integer.class); //int
    }


    public int getSubVoteNum() {
        return subVoteNum;
    }

    public boolean isUnlockTask() {
        return unlockTask;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getTaskRoleId() {
        return taskRoleId;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTaskDes() {
        return taskDes;
    }

    public List<String> getTaskItem() {
        return taskItem.getAll();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public static List<Table_Task_Row> getRowByRoleId(int roleId, int dramaId) {
        List<Table_Task_Row> arr = new ArrayList<>();
        for (Table_Task_Row row : RootTc.get(Table_Task_Row.class).values()) {
            if (row.getRoleId() == roleId && row.getDramaId() == dramaId) {
                arr.add(row);
            }
        }
        return arr;
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

    public static Table_Task_Row getRowByTaskId(int taskId, int dramaId) {
        for (Table_Task_Row row : RootTc.get(Table_Task_Row.class).values()) {
            if (row.getId() == taskId && row.getDramaId() == dramaId) {
                return row;
            }
        }
        String msg = String.format("Table_Task_Row.getRowByTaskId taskId=%s,dramaId=%s", taskId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Task_Row.class, taskId, msg);
    }


}
