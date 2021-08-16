package drama.gameServer.features.actor.config.utils;

import dm.relationship.table.RootTc;
import drama.protos.CommonProtos;
import drama.protos.EnumsProtos;
import ws.common.table.data.TableData;
import ws.common.table.data.TableDataHeader;
import ws.common.table.data.TableDataRow;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    public static EnumsProtos.CommonConfigTypeEnum getJavaType(String type) {
        if (type.contains("int")) {
            return EnumsProtos.CommonConfigTypeEnum.INT;
        } else if (type.contains("float")) {
            return EnumsProtos.CommonConfigTypeEnum.DOUBLE;
        } else if (type.contains("bool")) {
            return EnumsProtos.CommonConfigTypeEnum.BOOLEAN;
        } else {
            return type.contains("string") ? EnumsProtos.CommonConfigTypeEnum.STRING : null;
        }
    }

    public static List<TableDataRow> getTableDataRow(String tableName, int dramaId) {
        List<TableDataRow> result = new ArrayList<>();
        TableData tableDataTxt = RootTc.getPlanningTableDataByName(tableName);
        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); ++rowIdx) {
            TableDataRow row = (TableDataRow) tableDataTxt.getRows().get(rowIdx);
            result.add(row);
        }
        return result;
    }


    public static List<TableDataHeader> getTableDataHeader(String tableName) {
        TableData tableDataTxt = RootTc.getPlanningTableDataByName(tableName);
        return tableDataTxt.getHeaderDatas();
    }

    public static List<TableDataRow> getTableDataRow(String tableName, List<CommonProtos.Cm_Common_Args> argsList, int dramaId) {
        List<TableDataRow> result = new ArrayList<>();
        TableData tableDataTxt = RootTc.getPlanningTableDataByName(tableName);
        List<Boolean> conditionResult = new ArrayList<>();
        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); ++rowIdx) {
            //每遍历一个新的row就将比对结果清空
            conditionResult.clear();
            TableDataRow row = (TableDataRow) tableDataTxt.getRows().get(rowIdx);
            for (int columnIdx = 0; columnIdx < row.getCells().size(); ++columnIdx) {
                String columnName = ((TableDataHeader) tableDataTxt.getHeaderDatas().get(columnIdx)).getName();
                String columnValue = row.getCells().get(columnIdx).getCell();
                for (CommonProtos.Cm_Common_Args arg : argsList) {
                    String name = arg.getName();
                    String value = arg.getValue();
                    if (name.equals(columnName) && value.equals(columnValue)) {
                        conditionResult.add(true);
                    }
                }
                //这个row里的字段的值都和请求的字段和值都对上了
                //比对结果的正确数和请求的字段数量一致,添加row到reslut中
                if (conditionResult.size() == argsList.size()) {
                    result.add(row);
                    break;
                }
            }
        }
        return result;
    }


    public static void setAction(CommonProtos.Sm_Common_Config.Builder b, CommonProtos.Cm_Common_Config cm_common_config) {
        switch (cm_common_config.getAction().getNumber()) {
            case CommonProtos.Cm_Common_Config.Action.ACTER_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_ACTER);
                break;
            case CommonProtos.Cm_Common_Config.Action.STAGE_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_STAGE);
                break;
            case CommonProtos.Cm_Common_Config.Action.SUBJECT_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_SUBJECT);
                break;
            case CommonProtos.Cm_Common_Config.Action.RESULT_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_RESULT);
                break;
            case CommonProtos.Cm_Common_Config.Action.SCENELIST_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_SCENELIST);
                break;
            case CommonProtos.Cm_Common_Config.Action.RUNDOWN_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_RUNDOWN);
                break;
            case CommonProtos.Cm_Common_Config.Action.MURDER_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_MURDER);
                break;
            case CommonProtos.Cm_Common_Config.Action.SOLO_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_SOLO);
                break;
            case CommonProtos.Cm_Common_Config.Action.SOLODRAMA_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_SOLODRAMA);
                break;
            case CommonProtos.Cm_Common_Config.Action.DRAFT_VALUE:
                b.setAction(CommonProtos.Sm_Common_Config.Action.RESP_DRAFT);
                break;
            default:
                break;
        }
    }
}
