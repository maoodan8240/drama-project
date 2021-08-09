package dm.relationship.table.tableRows;

import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.List;
import java.util.Map;

public class Table_SelectRead_Row extends AbstractRow {
    /**
     * int 剧本ID
     */
    private Integer dramaId;
    /**
     * string 投凶条件
     */
    private TupleListCell<String> voteCondition;
    /**
     * boolean 条件2布尔
     */
    private Boolean c2bool;
    /**
     * boolean 条件1布尔
     */
    private Boolean c1bool;
    /**
     * string 轮抽条件
     */
    private TupleListCell<String> draftCondition;
    /**
     * string 结果
     */
    private String result;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        this.id = CellParser.parseSimpleCell("Id", map, Integer.class);
        this.dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
        voteCondition = CellParser.parseTupleListCell("VoteCondition", map, String.class); //string
        c2bool = CellParser.parseSimpleCell("C2bool", map, Boolean.class); //boolean
        c1bool = CellParser.parseSimpleCell("C1bool", map, Boolean.class); //boolean
        draftCondition = CellParser.parseTupleListCell("DraftCondition", map, String.class); //string
        result = CellParser.parseSimpleCell("Result", map, String.class); //string

    }

    public Integer getDramaId() {
        return dramaId;
    }

    public Boolean getC2bool() {
        return c2bool;
    }

    public Boolean getC1bool() {
        return c1bool;
    }

    public String getResult() {
        return result;
    }

    public List<TupleCell<String>> getVoteCondition() {
        return voteCondition.getAll();
    }

    public List<TupleCell<String>> getDraftCondition() {
        return draftCondition.getAll();
    }
    

}
