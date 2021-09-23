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

public class Table_Search_Row extends AbstractRow {
    /**
     * string 线索内容
     */
    private String line;
    /**
     * bool 是否隐藏（）
     */
    private Boolean hide;
    /**
     * int 分类
     */
    private Integer typeid;
    /**
     * int  搜证幕数
     */
    private Integer srchNum;
    /**
     * string 线索图
     */
    private String pic;
    /**
     * int 音频文件
     */
    private Integer sound;
    /**
     * int 详情图
     */
    private ListCell<Integer> detailPic;
    private Integer dramaId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        line = CellParser.parseSimpleCell("Line", map, String.class);
        hide = CellParser.parseSimpleCell("Hide", map, Boolean.class);
        srchNum = CellParser.parseSimpleCell("SrchNum", map, Integer.class);
        typeid = CellParser.parseSimpleCell("TypeId", map, Integer.class);
        pic = CellParser.parseSimpleCell("Pic", map, String.class);
        sound = CellParser.parseSimpleCell("Sound", map, Integer.class);
        detailPic = CellParser.parseListCell("DetailPic", map, Integer.class);
        dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public String getLine() {
        return line;
    }

    public Boolean getHide() {
        return hide;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public Integer getSrchNum() {
        return srchNum;
    }

    public String getPic() {
        return pic;
    }

    public Integer getSound() {
        return sound;
    }

    public List<Integer> getDetailPic() {
        return detailPic.getAll();
    }

    public static List<Table_Search_Row> getSearchByTypeNameAndStateTimes(String typeName, int roomStateTimes, int dramaId) {
        List<Table_Search_Row> result = new ArrayList<>();
        int typeId = Table_SearchType_Row.getTypeIdByName(typeName, dramaId);
        for (Table_Search_Row row : RootTc.get(Table_Search_Row.class).values()) {
            if (row.getTypeid() == typeId && row.getSrchNum() == roomStateTimes && row.getDramaId() == dramaId) {
                result.add(row);
            }
        }
        return result;
    }


    public static Table_Search_Row getTableSearchRowByIdAndDramaId(int idx, int dramaId) {
        for (Table_Search_Row row : RootTc.get(Table_Search_Row.class).values()) {
            if (row.getIdx() == idx && row.getDramaId() == dramaId) {
                return row;
            }
        }
        String msg = String.format("getTableSearchRowByTypeIdAndDramaId failed, idx=%s,dramaId=%s", idx, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Search_Row.class, idx, msg);
    }

    public static int getRowIdByIdAndDramaId(int typeid, int dramaId) {
        for (Table_Search_Row row : RootTc.get(Table_Search_Row.class).values()) {
            if (row.getTypeid() == typeid && row.getDramaId() == dramaId) {
                return row.getIdx();
            }
        }
        String msg = String.format("getTableSearchRowByTypeIdAndDramaId failed, typeid=%s, dramaId=%s", typeid, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Search_Row.class, typeid, msg);
    }

    public static List<Integer> getAllHideClueIds(int stateTimes, int dramaId) {
        List<Integer> result = new ArrayList<>();
        for (Table_Search_Row value : RootTc.get(Table_Search_Row.class).values()) {
            if (value.getHide() == true && value.getSrchNum() == stateTimes && value.getDramaId() == dramaId) {
                result.add(value.getIdx());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Table_Search_Row{" +
                ", id=" + id +
                ", idx=" + idx +
                ", dramaId=" + dramaId +
                ", line='" + line + '\'' +
                ", hide=" + hide +
                ", typeid=" + typeid +
                ", srchNum=" + srchNum +
                ", pic='" + pic + '\'' +
                ", sound=" + sound +
                ", detailPic=" + detailPic +
                ", dramaId=" + dramaId +
                '}';
    }
}

