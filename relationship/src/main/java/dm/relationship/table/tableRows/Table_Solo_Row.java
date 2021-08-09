package dm.relationship.table.tableRows;

import dm.relationship.exception.TableRowLogicCheckFailedException;
import dm.relationship.table.RootTc;
import ws.common.table.table.exception.CellParseFailedException;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellParser;

import java.util.List;
import java.util.Map;

public class Table_Solo_Row extends AbstractRow {
    /**
     * string 状态
     */
    private String soloStatus;
    /**
     * string 灵魂选择
     */
    private TupleListCell<String> soloChoice;
    /**
     * string 剧情铺垫
     */
    private String preShow;
    /**
     * int 第几次Solo
     */
    private Integer soloNum;
    /**
     * string 凶手逃脱独白
     */
    private TupleListCell<String> escapeDrama;
    /**
     * string 独白剧本
     */
    private TupleListCell<String> soloDrama;
    /**
     * int 角色ID
     */
    private Integer roleId;
    /**
     * string 凶手逃脱选择
     */
    private String escapeChoice;
    /**
     * string 凶手逃脱剧情铺垫
     */
    private String escapePreShow;
    private Integer dramaId;

    @Override
    public void parseRow(Map<String, String> map) throws CellParseFailedException {
        soloChoice = CellParser.parseTupleListCell("SoloChoice", map, String.class); //string
        soloDrama = CellParser.parseTupleListCell("SoloDrama", map, String.class); //string
        escapeDrama = CellParser.parseTupleListCell("EscapeDrama", map, String.class); //string
        // id column = {columnName:"Id", columnDesc:"ID"}
        soloStatus = CellParser.parseSimpleCell("SoloStatus", map, String.class); //string
        preShow = CellParser.parseSimpleCell("PreShow", map, String.class); //string
        soloNum = CellParser.parseSimpleCell("SoloNum", map, Integer.class); //int
        roleId = CellParser.parseSimpleCell("RoleId", map, Integer.class); //int
        escapeChoice = CellParser.parseSimpleCell("EscapeChoice", map, String.class); //string
        escapePreShow = CellParser.parseSimpleCell("EscapePreShow", map, String.class); //string
        dramaId = CellParser.parseSimpleCell("DramaId", map, Integer.class);
    }

    public Integer getDramaId() {
        return dramaId;
    }

    public String getSoloStatus() {
        return soloStatus;
    }

    public List<TupleCell<String>> getSoloChoice() {
        return soloChoice.getAll();
    }

    public String getPreShow() {
        return preShow;
    }

    public Integer getSoloNum() {
        return soloNum;
    }

    public List<TupleCell<String>> getEscapeDrama() {
        return escapeDrama.getAll();
    }

    public List<TupleCell<String>> getSoloDrama() {
        return soloDrama.getAll();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getEscapeChoice() {
        return escapeChoice;
    }

    public String getEscapePreShow() {
        return escapePreShow;
    }


    public static Table_Solo_Row getSoloRowByRoleId(int roleId, int soloNum, int dramaId) {
        for (Table_Solo_Row value : RootTc.get(Table_Solo_Row.class).values()) {
            if (value.getRoleId() == roleId && value.getSoloNum() == soloNum && value.getDramaId() == dramaId) {
                return value;
            }
        }
        String msg = String.format("getMurderRowByRoleId failed, roleId=%s", roleId);
        throw new TableRowLogicCheckFailedException(Table_Solo_Row.class, roleId, msg);
    }

    public int getSoloDramaId(String dramaOption) {
        for (TupleCell<String> tuple : getSoloDrama()) {
            if (tuple.get(TupleCell.FIRST).equals(dramaOption)) {
                return Integer.valueOf(tuple.get(TupleCell.SECOND));
            }
        }
        String msg = String.format("getSoloDramaId failed, roleId=%s", roleId);
        throw new TableRowLogicCheckFailedException(Table_Solo_Row.class, roleId, msg);
    }

    public int getEscapeDramaId(String dramaOption) {
        for (TupleCell<String> tuple : getEscapeDrama()) {
            if (tuple.get(TupleCell.FIRST).equals(dramaOption)) {
                return Integer.valueOf(tuple.get(TupleCell.SECOND));
            }
        }
        String msg = String.format("getEscapeDramaId failed, roleId=%s", roleId);
        throw new TableRowLogicCheckFailedException(Table_Solo_Row.class, roleId, msg);
    }

}
