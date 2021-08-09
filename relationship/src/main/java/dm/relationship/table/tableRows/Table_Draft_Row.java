package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Draft_Row extends AbstractRow {
    /**
     * int 剧本ID
     */
    private Integer dramaId;
    /**
     * string 心魔小图
     */
    private String draftPic;
    /**
     * int 心魔ID
     */
    private Integer draftID;
    /**
     * string 心魔大图
     */
    private String draftPoster;
    /**
     * string 对应人物ID
     */
    private Integer roleID;
    /**
     * int 轮数
     */
    private Integer draftNum;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"ID", columnDesc:"ID"}
        this.id = CellParser.parseSimpleCell("Id", map, Integer.class);
        this.dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
        draftPic = CellParser.parseSimpleCell("DraftPic", map, String.class); //string
        draftID = CellParser.parseSimpleCell("DraftID", map, Integer.class); //int
        draftPoster = CellParser.parseSimpleCell("DraftPoster", map, String.class); //string
        roleID = CellParser.parseSimpleCell("RoleID", map, Integer.class); //string
        draftNum = CellParser.parseSimpleCell("DraftNum", map, Integer.class); //int
    }


    public Integer getDraftNum() {
        return draftNum;
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public String getDraftPic() {
        return draftPic;
    }

    public Integer getDraftID() {
        return draftID;
    }

    public String getDraftPoster() {
        return draftPoster;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public static List<Integer> getDraftIds(int dramaId) {
        List<Integer> draftIds = new ArrayList<>();
        for (Table_Draft_Row value : RootTc.get(Table_Draft_Row.class).values()) {
            if (value.getDramaId() == dramaId) {
                draftIds.add(value.getDraftID());
            }
        }
        return draftIds;
    }

    public static List<Table_Draft_Row> getTableDraftByIds(List<Integer> draftIds, int dramaId) {
        List<Table_Draft_Row> rows = new ArrayList<>();
        for (Table_Draft_Row row : RootTc.get(Table_Draft_Row.class).values()) {
            for (Integer draftId : draftIds) {
                if (draftId == row.getDraftID() && dramaId == row.getDramaId()) {
                    rows.add(row);
                }
            }
        }
        return rows;
    }
}
