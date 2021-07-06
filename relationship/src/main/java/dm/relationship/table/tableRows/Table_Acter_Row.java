package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Acter_Row extends AbstractRow {
    /**
     * int 剧本ID
     */
    private Integer sceneId;
    /**
     * string 剧本背景音乐
     */
    private ListCell<String> bgm;
    /**
     * int  角色性别
     */
    private Integer sex;
    /**
     * string 角色对应剧本
     */
    private ListCell<String> scene;
    /**
     * string 选取角色后的立绘
     */
    private String pic;
    /**
     * int 角色ID
     */
    private Integer roleId;
    /**
     * string 角色名
     */
    private String name;
    /**
     * string 搜证次数 key对应search出现的幕数
     */
    private TupleListCell<String> ap;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {

        // id column = {columnName:"Id", columnDesc:"ID"}
        sceneId = CellParser.parseSimpleCell("SceneId", map, Integer.class);
        sex = CellParser.parseSimpleCell("Sex", map, Integer.class);
        pic = CellParser.parseSimpleCell("Pic", map, String.class);
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class);
        name = CellParser.parseSimpleCell("Name", map, String.class);
        scene = CellParser.parseListCell("Scene", map, String.class);
        ap = CellParser.parseTupleListCell("Ap", map, String.class);
        bgm = CellParser.parseListCell("Bgm", map, String.class);
    }

    public Integer getSceneId() {
        return sceneId;
    }


    public Integer getSex() {
        return sex;
    }

    public List<String> getBgm() {
        return bgm.getAll();
    }

    public List<String> getScene() {
        return scene.getAll();
    }

    public String getPic() {
        return pic;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public List<TupleCell<String>> getAp() {
        return ap.getAll();
    }

    public static EnumsProtos.SexEnum getSex(int roleIdx, int dramaId) {
        List<Table_Acter_Row> rowList = getTableActerRowByDramaId(dramaId);
        for (Table_Acter_Row row : rowList) {
            if (row.getRoleId() == roleIdx) {
                return EnumsProtos.SexEnum.valueOf(row.getSex());
            }

        }
        return null;
    }

    public static List<Table_Acter_Row> getTableActerRowByDramaId(int dramaId) {
        List<Table_Acter_Row> rowList = new ArrayList<>();
        List<Table_Acter_Row> values = RootTc.get(Table_Acter_Row.class).values();
        for (Table_Acter_Row value : values) {
            if (value.getSceneId() == dramaId) {
                rowList.add(value);
            }
        }
        return rowList;
    }

    /**
     * 获取对应幕数的搜索次数
     *
     * @param rowList
     * @param roleId
     * @param roomStateTimes
     * @return
     */
    public static int getSrchTimes(List<Table_Acter_Row> rowList, int roleId, int roomStateTimes) {
        TupleCell<String> tupleCell = null;
        for (Table_Acter_Row row : rowList) {
            if (row.getRoleId() == roleId) {
                List<TupleCell<String>> ap = row.getAp();
                for (TupleCell<String> tuple : ap) {
                    Integer stateTimes = Integer.valueOf(tuple.get(TupleCell.FIRST));
                    if (stateTimes == roomStateTimes) {
                        tupleCell = tuple;
                    }
                }
            }
        }

        return Integer.valueOf(tupleCell.get(TupleCell.SECOND));
    }

}
