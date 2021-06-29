//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Subject_Row extends AbstractRow {
    /***
     * int 剧本ID
     */
    private Integer sceneId;
    /***
     * string 答案衔接后续题目，0为答题结束
     */
    private TupleListCell<String> answer;
    /***
     * int 剧本ID
     */
    private ListCell<String> answerData;
    /***
     * int 题目对应性别
     */
    private Integer sex;
    /***
     * stirng 题目对应动画
     */
    private String question;

    public Table_Subject_Row() {
    }

    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        this.answer = CellParser.parseTupleListCell("Answer", map, String.class);
        this.answerData = CellParser.parseListCell("AnswerData", map, String.class);
        this.question = CellParser.parseSimpleCell("Question", map, String.class);
        this.sceneId = (Integer) CellParser.parseSimpleCell("SceneId", map, Integer.class);
        this.sex = (Integer) CellParser.parseSimpleCell("Sex", map, Integer.class);
    }

    public Integer getSceneId() {
        return this.sceneId;
    }

    public List<TupleCell<String>> getAnswer() {
        return this.answer.getAll();
    }

    public List<String> getAnswerData() {
        return this.answerData.getAll();
    }

    public Integer getSex() {
        return this.sex;
    }

    public String getQuestion() {
        return this.question;
    }

  
    public static List<Table_Subject_Row> getTableSubjectRowByDramaId(int dramaId) {
        List<Table_Subject_Row> rowList = new ArrayList<>();
        List<Table_Subject_Row> values = RootTc.get(Table_Subject_Row.class).values();
        for (Table_Subject_Row value : values) {
            if (value.getSceneId() == dramaId) {
                rowList.add(value);
            }
        }
        return rowList;
    }

}
