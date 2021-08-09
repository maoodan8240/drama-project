package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Result_Row extends AbstractRow {
    private static final Logger LOGGER = LoggerFactory.getLogger(Table_Result_Row.class);
    /**
     * string 测试答案
     */
    private ListCell<String> answer;

    /**
     * string 最终角色优先级
     */
    private ListCell<Integer> result;
    /**
     * int 性别
     */
    private Integer sex;
    /***
     * int 剧本ID
     */
    private Integer dramaId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        result = CellParser.parseListCell("Result", map, Integer.class);
        answer = CellParser.parseListCell("Answer", map, String.class);

        // id column = {columnName:"ID", columnDesc:"ID"}
        sex = CellParser.parseSimpleCell("Sex", map, Integer.class);
        dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public List<String> getAnswer() {
        return answer.getAll();
    }

    public List<Integer> getResult() {
        return result.getAll();
    }

    public Integer getSex() {
        return sex;
    }


    public static List<Table_Result_Row> getTableResultRowByDramaIdAndSex(int dramaId, EnumsProtos.SexEnum sexEnum) {
        List<Table_Result_Row> dramaRowList = new ArrayList<>();
        List<Table_Result_Row> values = RootTc.get(Table_Result_Row.class).values();
        for (Table_Result_Row value : values) {
            if (value.getDramaId() == dramaId) {
                if (value.getSex() == sexEnum.getNumber()) {
                    dramaRowList.add(value);
                }
            }
        }
        return dramaRowList;
    }


}
