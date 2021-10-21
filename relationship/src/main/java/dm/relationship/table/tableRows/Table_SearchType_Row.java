package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import org.apache.commons.lang3.StringUtils;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table_SearchType_Row extends AbstractRow {
    /**
     * int 分类ID
     */
    private Integer typeId;
    /**
     * int 搜证轮数
     */
    private Integer srchNum;
    /**
     * string 分类名字
     */
    private String typename;
    /**
     * string 分类图
     */
    private String typePic;
    /***
     * roleId 角色ID
     */
    private int roleId;
    private Integer dramaId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        this.id = CellParser.parseSimpleCell("Id", map, Integer.class);
        typename = CellParser.parseSimpleCell("Typename", map, String.class);
        typePic = CellParser.parseSimpleCell("TypePic", map, String.class);
        srchNum = CellParser.parseSimpleCell("SrchNum", map, Integer.class);
        typeId = CellParser.parseSimpleCell("TypeId", map, Integer.class);
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class);
        dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public int getRoleId() {
        return roleId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Integer getSrchNum() {
        return srchNum;
    }

    public String getTypename() {
        return typename;
    }

    public String getTypePic() {
        return typePic;
    }


    public static Map<String, String> getTypeById(List<Integer> typeIds, int dramaId, int srchNum) {
        Map<String, String> typeNamesAndPic = new HashMap<>();
        for (Table_SearchType_Row value : RootTc.get(Table_SearchType_Row.class).values()) {
            for (Integer id : typeIds) {
                //id对上,并且名字是唯一,pic不能是空的
                if (value.getTypeId() == id && !typeNamesAndPic.containsKey(value.getTypename()) && !StringUtils.isEmpty(value.getTypePic()) && value.getDramaId() == dramaId && value.getSrchNum() == srchNum) {
                    typeNamesAndPic.put(value.getTypename(), value.getTypePic());
                }
            }
        }
        return typeNamesAndPic;
    }

    public static Map<String, String> getVoteSearchTypeById(List<Integer> typeIds, int dramaId) {
        Map<String, String> typeNamesAndPic = new HashMap<>();
        for (Table_SearchType_Row value : RootTc.get(Table_SearchType_Row.class).values()) {
            for (Integer id : typeIds) {
                if (value.getTypeId() == id && value.getDramaId() == dramaId) {
                    typeNamesAndPic.put(value.getTypename(), value.getTypePic());
                }
            }
        }
        return typeNamesAndPic;
    }

    public static Table_SearchType_Row getSearchTypeRow(int typeId, int dramaId) {
        for (Table_SearchType_Row value : RootTc.get(Table_SearchType_Row.class).values()) {
            if (value.getTypeId() == typeId && value.getDramaId() == dramaId) {
                return value;
            }
        }
        String msg = String.format("没有找到对应的typeId, typeId=%s", typeId);
        throw new TableRowLogicCheckFailedException(Table_SearchType_Row.class, typeId, msg);
    }


    public static int getTypeIdByName(String typeName, int dramaId) {
        for (Table_SearchType_Row value : RootTc.get(Table_SearchType_Row.class).values()) {
            if (value.getTypename().equals(typeName) && value.getDramaId() == dramaId) {
                return value.getTypeId();
            }
        }
        String msg = String.format("没有找到对应的Id, typeName=%s", typeName);
        throw new TableRowLogicCheckFailedException(Table_SearchType_Row.class, 0, msg);
    }

    public static List<Integer> getSearchTypeRowByStateTimes(int stateTimes, int dramaId) {
        List<Table_SearchType_Row> values = RootTc.get(Table_SearchType_Row.class).values();
        List<Integer> searchTypeIds = new ArrayList<>();
        for (Table_SearchType_Row row : values) {
            if (row.getSrchNum() == stateTimes && row.getDramaId() == dramaId) {
                if (!searchTypeIds.contains(row.getTypeId())) {
                    searchTypeIds.add(row.getTypeId());
                }
            }
        }
        return searchTypeIds;
    }

    @Override
    public String toString() {
        return "Table_SearchType_Row{" +
                ", id=" + id +
                ", idx=" + idx +
                ", dramaId=" + dramaId +
                ", typeId=" + typeId +
                ", srchNum=" + srchNum +
                ", typename='" + typename + '\'' +
                ", typePic='" + typePic + '\'' +
                ", roleId=" + roleId +
                ", dramaId=" + dramaId +
                '}';
    }
}
