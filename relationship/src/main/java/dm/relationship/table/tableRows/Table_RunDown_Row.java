package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.List;
import java.util.Map;

public class Table_RunDown_Row extends AbstractRow {
    /**
     * string 未被投为凶手问题
     */
    private String soloNVQuestion;
    /**
     * string solo问题
     */
    private String soloQuestion;
    /**
     * string 青青剧本
     */
    private ListCell<String> text6;
    /**
     * string solo未被投为凶手
     */
    private String soloNoVote;
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
     * string solo选择
     */
    private TupleListCell<String> soloChoice;
    /**
     * string 为被投位凶手选择
     */
    private String soloNVChoice;
    /**
     * string 未被投为凶手文本
     */
    private String soloNVText;
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
     * string solo文本
     */
    private String soloText;
    /**
     * int solo角色
     */
    private Integer soloChar;
    /**
     * string 标题
     */
    private String title;
    /**
     * string 选择独白内容
     */
    private TupleListCell<String> soloDrama;
    /**
     * string 未被投为凶手独白内容
     */
    private String soloNVDrama;
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
        soloChoice = CellParser.parseTupleListCell("SoloChoice", map, String.class);
        soloDrama = CellParser.parseTupleListCell("SoloDrama", map, String.class);
        // id column = {columnName:"Id", columnDesc:"唯一id"}
        tips = CellParser.parseSimpleCell("Tips", map, String.class);
        task5 = CellParser.parseSimpleCell("Task5", map, String.class);
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
        soloChar = CellParser.parseSimpleCell("SoloChar", map, Integer.class);
        soloNoVote = CellParser.parseSimpleCell("SoloNoVote", map, String.class);
        soloNVText = CellParser.parseSimpleCell("SoloNVText", map, String.class);
        soloNVQuestion = CellParser.parseSimpleCell("SoloNVQuestion", map, String.class);
        soloNVChoice = CellParser.parseSimpleCell("SoloNVChoice", map, String.class);
        soloNVDrama = CellParser.parseSimpleCell("SoloNVDrama", map, String.class);
        soloText = CellParser.parseSimpleCell("SoloText", map, String.class);
        soloQuestion = CellParser.parseSimpleCell("SoloQuestion", map, String.class);
        text5 = CellParser.parseListCell("Text5", map, String.class);
        text6 = CellParser.parseListCell("Text6", map, String.class);
        text4 = CellParser.parseListCell("Text4", map, String.class);
        text3 = CellParser.parseListCell("Text3", map, String.class);
        text2 = CellParser.parseListCell("Text2", map, String.class);
        text1 = CellParser.parseListCell("Text1", map, String.class);

    }

    public String getSoloNVQuestion() {
        return soloNVQuestion;
    }

    public String getSoloQuestion() {
        return soloQuestion;
    }


    public String getSoloNoVote() {
        return soloNoVote;
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

    public List<TupleCell<String>> getSoloChoice() {
        return soloChoice.getAll();
    }

    public String getSoloNVChoice() {
        return soloNVChoice;
    }

    public String getSoloNVText() {
        return soloNVText;
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

    public String getSoloText() {
        return soloText;
    }

    public Integer getSoloChar() {
        return soloChar;
    }

    public String getTitle() {
        return title;
    }

    public List<TupleCell<String>> getSoloDrama() {
        return soloDrama.getAll();
    }

    public String getSoloNVDrama() {
        return soloNVDrama;
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

    public static long getNextSTime(String status, int num) {
        int nextSTime = 0;
        for (Table_RunDown_Row value : RootTc.get(Table_RunDown_Row.class).values()) {
            if (value.getNum() == num && value.getStatus().equals(status)) {
                nextSTime += value.getNextSTime();
            }
        }
        return nextSTime;
    }
}
