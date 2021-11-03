package dm.relationship.table.tableRows;

import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.utils.CellParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Table_Choice_Row extends AbstractRow {
    /**
     * int 选择目标
     */
    private Integer chosenId;
    /**
     * string 目标名
     */
    private String chosenname;
    /**
     * int 行为人
     */
    private Integer roleId;
    /**
     * int 行为人名
     */
    private String actorName;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        // id column = {columnName:"Id", columnDesc:"ID"}
        chosenId = CellParser.parseSimpleCell("ChosenId", map, Integer.class); //int
        chosenname = CellParser.parseSimpleCell("Chosenname", map, String.class); //string
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class); //int
        actorName = CellParser.parseSimpleCell("ActorName", map, String.class); //int

    }


    public Integer getChosenId() {
        return chosenId;
    }

    public String getChosenname() {
        return chosenname;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getActorName() {
        return actorName;
    }

    public static boolean hasChoiceRole(int roleId, int dramaId) {
        for (Table_Choice_Row row : RootTc.get(Table_Choice_Row.class).values()) {
            if (row.getRoleId() == roleId && row.getDramaId() == dramaId) {
                return true;
            }
        }
        return false;
    }

    public static List<Integer> getRowsByRoleId(int roleId, int dramaId) {
        List<Integer> arr = new ArrayList<>();
        for (Table_Choice_Row row : RootTc.get(Table_Choice_Row.class).values()) {
            if (row.getRoleId() == roleId && row.getDramaId() == dramaId) {
                arr.add(row.getChosenId());
            }
        }
        return arr;
    }
}
