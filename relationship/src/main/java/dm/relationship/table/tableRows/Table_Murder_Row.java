package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Murder_Row extends AbstractRow {
    /**
     * string 角色名称
     */
    private String roleName;
    /**
     * bool 真凶
     */
    private Boolean murder;
    /**
     * string 角色图片  /root/drama-src/drama
     */
    private String rolePic;
    /**
     * int 第几次投凶
     */
    private Integer voteNum;
    /***
     * int roleId 角色ID
     */
    private int roleId;
    /**
     * bool 是否显示真凶
     */
    private boolean truth;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        roleName = CellParser.parseSimpleCell("RoleName", map, String.class);
        rolePic = CellParser.parseSimpleCell("RolePic", map, String.class);
        voteNum = CellParser.parseSimpleCell("VoteNum", map, Integer.class);
        murder = CellParser.parseSimpleCell("Murder", map, Boolean.class);
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class);
        truth = CellParser.parseSimpleCell("Truth", map, Boolean.class);
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public Boolean isMurder() {
        return murder;
    }

    public String getRolePic() {
        return rolePic;
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public boolean isTruth() {
        return truth;
    }

    public static List<Integer> getAllRoleId() {
        List<Integer> allRoleIds = new ArrayList<>();
        for (Table_Murder_Row value : RootTc.get(Table_Murder_Row.class).values()) {
            if (!allRoleIds.contains(value.getRoleId())) {
                allRoleIds.add(value.getRoleId());
            }
        }
        return allRoleIds;
    }


    public static boolean isMurder(int roleId) {
        for (Table_Murder_Row value : RootTc.get(Table_Murder_Row.class).values()) {
            if (value.getRoleId() == roleId) {
                return value.isMurder();
            }
        }
        return false;
    }

    public static Table_Murder_Row getMurderRowByRoleId(int roleId) {
        for (Table_Murder_Row value : RootTc.get(Table_Murder_Row.class).values()) {
            if (value.getRoleId() == roleId) {
                return value;
            }
        }
        String msg = String.format("getMurderRowByRoleId failed, roleId=%s", roleId);
        throw new TableRowLogicCheckFailedException(Table_Murder_Row.class, roleId, msg);
    }

    public static List<String> getRolePicByRoleIds(List<Integer> roleIds) {
        List<String> rolePic = new ArrayList<>();
        for (Integer roleId : roleIds) {
            Table_Murder_Row murderRow = getMurderRowByRoleId(roleId);
            rolePic.add(murderRow.getRolePic());
        }
        return rolePic;
    }

    public static int getMurderRoleId() {
        for (Table_Murder_Row value : RootTc.get(Table_Murder_Row.class).values()) {
            if (value.isMurder()) {
                return value.getRoleId();
            }
        }
        String msg = String.format("getMurderRoleId failed, roleId=%s", 0);
        throw new TableRowLogicCheckFailedException(Table_Murder_Row.class, 0, msg);
    }
}