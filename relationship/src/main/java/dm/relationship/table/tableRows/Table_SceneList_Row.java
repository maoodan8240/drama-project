package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.List;
import java.util.Map;

public class Table_SceneList_Row extends AbstractRow {
    /**
     * int 剧本类型（1推理 2情感 3阵营 4撕B）
     */
    private Integer type;
    /**
     * int  剧本人数
     */
    private Integer plaNum;
    /**
     * int 难度（1简单 2 普通 3困难 4烧脑 5 地狱）
     */
    private Integer diff;
    /**
     * float 剧本评分
     */
    private Double rate;
    /**
     * string 剧本题材
     */
    private String theme;
    /**
     * string 剧本流程（ANSWER:选择角色,READ:播放剧本,STAGE:小剧场,TALK:公聊,SEARCH:搜证,VOTE:投凶,SOLO:内心独白,REVIEW:复盘,ENDING:结束）
     */
    private TupleListCell<String> runDown;
    /**
     * float 时长
     */
    private Double time;
    /**
     * int 剧本图片
     */
    private String pic;
    /**
     * string 剧本简介
     */
    private String brief;
    /**
     * string 剧本名
     */
    private String name;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        runDown = CellParser.parseTupleListCell("RunDown", map, String.class);
        // id column = {columnName:"Id", columnDesc:"ID"}
        name = CellParser.parseSimpleCell("Name", map, String.class);
        brief = CellParser.parseSimpleCell("Brief", map, String.class);
        pic = CellParser.parseSimpleCell("Pic", map, String.class);
        time = CellParser.parseSimpleCell("Time", map, Double.class);
        theme = CellParser.parseSimpleCell("Theme", map, String.class);
        rate = CellParser.parseSimpleCell("Rate", map, Double.class);
        diff = CellParser.parseSimpleCell("Diff", map, Integer.class);
        plaNum = CellParser.parseSimpleCell("PlaNum", map, Integer.class);
        type = CellParser.parseSimpleCell("Type", map, Integer.class);
    }

    public Integer getType() {
        return type;
    }

    public Integer getPlaNum() {
        return plaNum;
    }

    public Integer getDiff() {
        return diff;
    }

    public Double getRate() {
        return rate;
    }

    public String getTheme() {
        return theme;
    }

    public List<TupleCell<String>> getRunDown() {
        return runDown.getAll();
    }

    public Double getTime() {
        return time;
    }

    public String getPic() {
        return pic;
    }

    public String getBrief() {
        return brief;
    }

    public String getName() {
        return name;
    }

    public static boolean containsDramaId(int dramaId) {
        return RootTc.get(Table_SceneList_Row.class).get(dramaId) != null;
    }

    public int getSrchNum() {
        int maxSrchNum = 0;
        for (TupleCell<String> tupleCell : getRunDown()) {
            if (tupleCell.get(TupleCell.FIRST).equals(EnumsProtos.RoomStateEnum.SEARCH.name())) {
                Integer times = Integer.valueOf(tupleCell.get(TupleCell.SECOND));
                if (maxSrchNum < times) {
                    maxSrchNum = times;
                }
            }
        }
        return maxSrchNum;
    }
}
