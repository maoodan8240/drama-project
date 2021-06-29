package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.Map;

public class Table_RunDown_Row extends AbstractRow {
    /**
     * string 流程状态
     */
    private String status;
    /**
     * string 了失任务
     */
    private String task2;
    /**
     * string 顾行简任务
     */
    private String task1;
    /**
     * string 路停云任务
     */
    private String task4;
    /**
     * string 靳来生任务
     */
    private String task3;
    /**
     * int 状态次数
     */
    private Integer num;
    /**
     * string 标题
     */
    private String title;
    /**
     * int 下一阶段时间锁（指定时间后才能点击下一阶段按钮）
     */
    private Integer nextSTime;
    /**
     * bool 配音提示
     */
    private Boolean dub;
    /**
     * string 青青任务
     */
    private String task6;
    /**
     * string 太后任务
     */
    private String task5;
    /**
     * string 提示文案
     */
    private String tips;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"唯一id"}
        tips = CellParser.parseSimpleCell("Tips", map, String.class);
        task5 = CellParser.parseSimpleCell("Task5", map, String.class);
        task6 = CellParser.parseSimpleCell("Task6", map, String.class);
        task4 = CellParser.parseSimpleCell("Task4", map, String.class);
        task3 = CellParser.parseSimpleCell("Task3", map, String.class);
        task2 = CellParser.parseSimpleCell("Task2", map, String.class);
        task1 = CellParser.parseSimpleCell("Task1", map, String.class);
        status = CellParser.parseSimpleCell("Tips", map, String.class);
        title = CellParser.parseSimpleCell("Tips", map, String.class);
        num = CellParser.parseSimpleCell("Num", map, Integer.class);
        dub = CellParser.parseSimpleCell("Dub", map, Boolean.class);
        nextSTime = CellParser.parseSimpleCell("NextSTime", map, Integer.class);
    }

    public String getStatus() {
        return status;
    }

    public String getTask2() {
        return task2;
    }

    public String getTask1() {
        return task1;
    }

    public String getTask4() {
        return task4;
    }

    public String getTask3() {
        return task3;
    }

    public Integer getNum() {
        return num;
    }

    public String getTitle() {
        return title;
    }

    public Integer getNextSTime() {
        return nextSTime;
    }

    public Boolean getDub() {
        return dub;
    }

    public String getTask6() {
        return task6;
    }

    public String getTask5() {
        return task5;
    }

    public String getTips() {
        return tips;
    }
}
