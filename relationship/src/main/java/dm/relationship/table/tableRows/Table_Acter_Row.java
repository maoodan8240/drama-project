package dm.relationship.table.tableRows;

import dm.relationship.base.IdAndCount;
import dm.relationship.base.IdMaptoCount;
import dm.relationship.base.MagicNumbers;
import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import drama.protos.EnumsProtos;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.ListCell;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table_Acter_Row extends AbstractRow {
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
     * string 角色头像
     */
    private String profile;
    /**
     * string 角色名
     */
    private String name;
    /**
     * string 搜证次数 key对应search出现的幕数
     */
    private TupleListCell<String> ap;
    /**
     * string 投票搜证次数
     */
    private TupleListCell<String> vSAp;
    /**
     * string 初始道具
     */
    private TupleListCell<String> prop;

    public static int getNoSelectActer(int dramaId) {
        int roleId = 0;
        for (Table_Acter_Row row : RootTc.get(Table_Acter_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                roleId = row.getRoleId();
            }
        }
        return roleId;
    }


    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        sex = CellParser.parseSimpleCell("Sex", map, Integer.class);
        pic = CellParser.parseSimpleCell("Pic", map, String.class);
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class);
        profile = CellParser.parseSimpleCell("Profile", map, String.class); //string
        name = CellParser.parseSimpleCell("Name", map, String.class);
        scene = CellParser.parseListCell("Scene", map, String.class);
        ap = CellParser.parseTupleListCell("Ap", map, String.class);
        bgm = CellParser.parseListCell("Bgm", map, String.class);
        vSAp = CellParser.parseTupleListCell("VSAp", map, String.class);//string
        prop = CellParser.parseTupleListCell("Prop", map, String.class);//string
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

    public String getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public List<TupleCell<String>> getAp() {
        return ap.getAll();
    }

    public List<TupleCell<String>> getvSAp() {
        return vSAp.getAll();
    }

    public List<TupleCell<String>> getProp() {
        return prop.getAll();
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

    public static Table_Acter_Row getTableActerRowByRoleId(int roleId, int dramaId) {
        for (Table_Acter_Row value : RootTc.get(Table_Acter_Row.class).values()) {
            if (value.getRoleId() == roleId && value.getDramaId() == dramaId) {
                return value;
            }
        }
        String msg = String.format("getTableActerRowByRoleId failed, roleId=%s,dramaId=%s", roleId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Acter_Row.class, roleId, msg);
    }

    public static List<Table_Acter_Row> getTableActerRowByDramaId(int dramaId) {
        List<Table_Acter_Row> rowList = new ArrayList<>();
        List<Table_Acter_Row> values = RootTc.get(Table_Acter_Row.class).values();
        for (Table_Acter_Row value : values) {
            if (value.getDramaId() == dramaId) {
                rowList.add(value);
            }
        }
        return rowList;
    }

    public static int getAllVoteSrchTimes(int roleId, int dramaId) {
        int num = 0;
        for (Table_Acter_Row value : RootTc.get(Table_Acter_Row.class).values()) {
            if (value.getRoleId() == roleId && value.getDramaId() == dramaId) {
                for (TupleCell<String> tupleCell : value.getvSAp()) {
                    num += Integer.valueOf(tupleCell.get(TupleCell.SECOND));
                }
            }
        }
        return num;
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
        List<TupleCell<String>> ap = null;
        for (Table_Acter_Row row : rowList) {
            if (row.getRoleId() == roleId) {
                ap = row.getAp();
            }
        }
        for (TupleCell<String> tuple : ap) {
            Integer stateTimes = Integer.valueOf(tuple.get(TupleCell.FIRST));
            if (stateTimes == roomStateTimes) {
                tupleCell = tuple;
            }
        }
        return Integer.valueOf(tupleCell.get(TupleCell.SECOND));
    }

    public static int getVoteSrchTimes(List<Table_Acter_Row> rowList, int roleId, int roomStateTimes) {
        TupleCell<String> tupleCell = null;
        List<TupleCell<String>> vSAp = null;
        for (Table_Acter_Row row : rowList) {
            if (row.getRoleId() == roleId) {
                vSAp = row.getvSAp();
            }
        }
        for (TupleCell<String> tuple : vSAp) {
            Integer stateTimes = Integer.valueOf(tuple.get(TupleCell.FIRST));
            if (stateTimes == roomStateTimes) {
                tupleCell = tuple;
            }
        }
        return Integer.valueOf(tupleCell.get(TupleCell.SECOND));
    }

    public static List<String> getRolePicByRoleIds(List<Integer> voteRoleIds, int dramaId) {
        List<String> rolePic = new ArrayList<>();
        for (Integer voteRoleId : voteRoleIds) {
            Table_Acter_Row row = getTableActerRowByRoleId(voteRoleId, dramaId);
            rolePic.add(row.getProfile());
        }
        return rolePic;
    }

    public static String getRoleNameByRoleId(int roleId, int dramaId) {
        for (Table_Acter_Row value : RootTc.get(Table_Acter_Row.class).values()) {
            if (value.getRoleId() == roleId && value.getDramaId() == dramaId) {
                return value.getName();
            }
        }
        String msg = String.format("getRoleNameByRoleId roleId=%s,dramaId=%s", roleId, dramaId);
        throw new TableRowLogicCheckFailedException(Table_Acter_Row.class, roleId, msg);
    }

    public static Map<Integer, Table_Acter_Row> getAllRoleIdToRowByDramaId(int dramaId) {
        Map<Integer, Table_Acter_Row> map = new HashMap<>();
        for (Table_Acter_Row row : RootTc.get(Table_Acter_Row.class).values()) {
            if (row.getDramaId() == dramaId) {
                map.put(row.getRoleId(), row);
            }
        }
        return map;
    }

    public static boolean hasProp(int dramaId, int roleId) {
        Table_Acter_Row row = getTableActerRowByRoleId(roleId, dramaId);
        return row.getProp().size() > MagicNumbers.DEFAULT_ZERO;
    }

    public static IdMaptoCount getProp(int dramaId, int roleId) {
        Table_Acter_Row row = getTableActerRowByRoleId(roleId, dramaId);
        IdMaptoCount idMaptoCount = new IdMaptoCount();
        for (TupleCell<String> tupleCell : row.getProp()) {
            Integer id = Integer.valueOf(tupleCell.get(TupleCell.FIRST));
            Long count = Long.valueOf(tupleCell.get(TupleCell.SECOND));
            IdAndCount idAndCount = new IdAndCount(id, count);
            idMaptoCount.add(idAndCount);
        }
        return idMaptoCount;
    }

    @Override
    public String toString() {
        return "Table_Acter_Row{" +
                ", bgm=" + bgm +
                ", sex=" + sex +
                ", scene=" + scene +
                ", pic='" + pic + '\'' +
                ", roleId=" + roleId +
                ", profile='" + profile + '\'' +
                ", name='" + name + '\'' +
                ", ap=" + ap +
                ", dramaId=" + dramaId +
                '}';
    }
}
