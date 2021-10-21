package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_RunDown_Row extends AbstractRow {
    /**
     * string 青青剧本
     */
    private ListCell<String> text7;
    /**
     * string 青青剧本
     */
    private ListCell<String> text6;
    /**
     * string 太后剧本
     */
    private ListCell<String> text5;
    /**
     * string 路停云剧本
     */
    private ListCell<String> text4;
    /**
     * string 靳来生剧本
     */
    private ListCell<String> text3;
    /**
     * int 下一阶段时间锁（指定时间后才能点击下一阶段按钮）
     */
    private Integer nextSTime;
    /**
     * int 下一阶段最长时间锁(时间到达后阶段强制结束，剩余5分钟时给与倒计时提示)
     */
    private int nextLTime;
    /**
     * string 了失剧本
     */
    private ListCell<String> text2;
    /**
     * string 顾行简剧本
     */
    private ListCell<String> text1;
    /**
     * bool 配音提示
     */
    private Boolean dub;
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
     * string 青青任务
     */
    private String task6;
    /**
     * string 太后任务
     */
    private String task5;
    /**
     * string 青青任务
     */
    private String task7;
    /**
     * string 提示文案
     */
    private String tips;
    /**
     * string 配音提示文案
     */
    private String dubTips;
    /**
     * int 解锁线索
     */
    private int unlockClueId;


    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"唯一id"}
        tips = CellParser.parseSimpleCell("Tips", map, String.class);
        task5 = CellParser.parseSimpleCell("Task5", map, String.class);
        task7 = CellParser.parseSimpleCell("Task7", map, String.class);
        task6 = CellParser.parseSimpleCell("Task6", map, String.class);
        task4 = CellParser.parseSimpleCell("Task4", map, String.class);
        task3 = CellParser.parseSimpleCell("Task3", map, String.class);
        task2 = CellParser.parseSimpleCell("Task2", map, String.class);
        task1 = CellParser.parseSimpleCell("Task1", map, String.class);
        status = CellParser.parseSimpleCell("Status", map, String.class);
        title = CellParser.parseSimpleCell("Title", map, String.class);
        num = CellParser.parseSimpleCell("Num", map, Integer.class);
        dub = CellParser.parseSimpleCell("Dub", map, Boolean.class);
        nextSTime = CellParser.parseSimpleCell("NextSTime", map, Integer.class);
        nextLTime = CellParser.parseSimpleCell("NextLTime", map, Integer.class);
        text5 = CellParser.parseListCell("Text5", map, String.class);
        text6 = CellParser.parseListCell("Text6", map, String.class);
        text7 = CellParser.parseListCell("Text7", map, String.class);
        text4 = CellParser.parseListCell("Text4", map, String.class);
        text3 = CellParser.parseListCell("Text3", map, String.class);
        text2 = CellParser.parseListCell("Text2", map, String.class);
        text1 = CellParser.parseListCell("Text1", map, String.class);
        dubTips = CellParser.parseSimpleCell("DubTips", map, String.class);
        dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
        unlockClueId = CellParser.parseSimpleCell("UnlockClueId", map, Integer.class);
    }

    public int getUnlockClueId() {
        return unlockClueId;
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public Integer getNextSTime() {
        return nextSTime;
    }

    public List<String> getText6() {
        return text6.getAll();
    }

    public List<String> getText5() {
        return text5.getAll();
    }

    public List<String> getText4() {
        return text4.getAll();
    }

    public List<String> getText3() {
        return text3.getAll();
    }

    public List<String> getText2() {
        return text2.getAll();
    }

    public List<String> getText1() {
        return text1.getAll();
    }

    public Boolean getDub() {
        return dub;
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

    public String getTask6() {
        return task6;
    }

    public String getTask5() {
        return task5;
    }

    public String getTips() {
        return tips;
    }

    public String getDubTips() {
        return dubTips;
    }

    public List<String> getText7() {
        return text7.getAll();
    }

    public String getTask7() {
        return task7;
    }

    public int getNextLTime() {
        return nextLTime;
    }

    public static long getNextSTime(String status, int num, int dramaId) {
        int nextSTime = 0;
        for (Table_RunDown_Row value : RootTc.get(Table_RunDown_Row.class).values()) {
            if (value.getNum() == num && value.getStatus().equals(status) && value.getDramaId() == dramaId) {
                nextSTime += value.getNextSTime();
            }
        }
        return nextSTime;
    }

    public static long getNextLTime(String status, int num, int dramaId) {
        int nextSTime = 0;
        for (Table_RunDown_Row value : RootTc.get(Table_RunDown_Row.class).values()) {
            if (value.getNum() == num && value.getStatus().equals(status) && value.getDramaId() == dramaId) {
                nextSTime += value.getNextLTime();
            }
        }
        return nextSTime;
    }

    public static List<Integer> getUnlockClueIds(int dramaId, int stateTimes, String statusName) {
        List<Integer> clueIds = new ArrayList<>();
        for (Table_RunDown_Row value : RootTc.get(Table_RunDown_Row.class).values()) {
            if (value.getDramaId() == dramaId && stateTimes == value.getNum() && value.getStatus().equals(statusName)) {
                clueIds.add(value.getUnlockClueId());
            }
        }
        return clueIds;
    }
}
