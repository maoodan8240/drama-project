package dm.relationship.table;

import dm.relationship.base.IdMaptoCount;
import dm.relationship.base.MagicWords;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.exception.TableDateNotFoundException;
import dm.relationship.utils.RelationshipCommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.table.container.RefreshableTableContainer;
import ws.common.table.container.RefreshableTableContainerListener;
import ws.common.table.container._RefreshableTableContainer;
import ws.common.table.data.PlanningTableData;
import ws.common.table.data.TableData;
import ws.common.table.table.implement.AbstractRow;
import ws.common.table.table.interfaces.Row;
import ws.common.table.table.interfaces.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RootTc {
    private static final Logger LOGGER = LoggerFactory.getLogger(RootTc.class);
    private static final RefreshableTableContainer RTC = new _RefreshableTableContainer();
    private static PlanningTableData planningTableData;

    /**
     * 初始化
     *
     * @throws Exception
     */
    public static void init(PlanningTableData planningTableData, RefreshableTableContainerListener listener) throws Exception {
        RTC.setPlanningTableData(planningTableData);
        RTC.setListener(listener);
        RootTc.planningTableData = planningTableData;
    }


    /**
     * 添加全部表
     *
     * @throws Exception
     */
    public static void loadAllTables() throws Exception {
//        for (Class<? extends AbstractRow> row : RowsClassHolder.getAbstractRowClasses()) {
//            String tableName = row.getSimpleName().trim().replaceAll(MagicWords.TableFileNameGenSuffix, "");
//            _addTable(row, tableName);
//        }
    }

    /**
     * 添加全部表
     *
     * @throws Exception
     */
    public static void loadAllTables(Locale locale) throws Exception {
        boolean simplifiedChinese = RelationshipCommonUtils.isSimplifiedChinese(locale);
        ResourceBundle bundle = null;
        if (!simplifiedChinese) {
            bundle = ResourceBundle.getBundle(MagicWords.MULTI_LANGUAGE_KEY_SET, locale);
        }
        for (Class<? extends AbstractRow> row : RowsClassHolder.getAbstractRowClasses()) {
            String tableName = row.getSimpleName().trim().replaceAll(MagicWords.TableFileNameGenSuffix, "");
            if (!simplifiedChinese && bundle.containsKey(tableName)) {
                tableName = bundle.getString(tableName);
            }
            _addTable(row, tableName);
        }
    }


    private static void _addTable(Class<? extends Row> rowClass, String tableName) throws Exception {
        boolean flag = false;
        if (tableName.equals("Table_SceneList")) {
            flag = true;
            TableData tableDataTxt = planningTableData.getTableNameToTableData().get(tableName);
            LOGGER.debug("class={}  tableName={} ！", rowClass, tableName);
            RTC.addTable(rowClass, tableDataTxt);
        } else {
            for (TableData value : planningTableData.getTableNameToTableData().values()) {
                String name = value.getTableName().substring(0, value.getTableName().lastIndexOf("_"));
                if (name.equals(tableName)) {
                    flag = true;
                    String strDramaId = value.getTableName().substring(tableName.length() + 1);
                    if (StringUtils.isEmpty(strDramaId)) {
                        TableData tableDataTxt = planningTableData.getTableNameToTableData().get(value.getTableName());
                        LOGGER.debug("class={}  tableName={} ！", rowClass, tableName);
                        RTC.addTable(rowClass, tableDataTxt);
                    } else {
                        int dramaId = Integer.valueOf(strDramaId);
                        TableData tableDataTxt = planningTableData.getTableNameToTableData().get(value.getTableName());
                        LOGGER.debug("class={}  tableName={} ！", rowClass, tableName);
                        RTC.addTable(rowClass, tableDataTxt, dramaId);
                    }
                }
            }
        }
        if (!flag) {
            LOGGER.warn("class={} 没有对应的tab文件！", rowClass);
        }
    }

    /**
     * 刷新全部
     *
     * @return
     * @throws Exception
     */
    public static List<Class<? extends Row>> refresh() throws Exception {
        return RTC.refresh();
    }

    public static <RowType extends Row> boolean has(Class<RowType> rowType, List<Integer> ids) {
        return has(rowType, ids.toArray(new Integer[]{}));
    }

    public static <RowType extends Row> boolean has(Class<RowType> rowType, Integer... ids) {
        for (Integer id : ids) {
            if (!RootTc.get(rowType).has(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回匿名表
     *
     * @param rowType
     * @return
     */
    public static <RowType extends Row> Table<RowType> get(Class<RowType> rowType) {
        return RTC.get(rowType);
    }

    public static <RowType extends Row> Table<RowType> get(String tableName) {
        return RTC.get(tableName);
    }

    public static <RowType extends Row> RowType get(Class<RowType> rowType, Integer id) {
        RowType row = RTC.get(rowType).get(id);
        if (row == null) {
            LOGGER.error("策划表查询数据异常！ Table={} id={} !", rowType, id);
        }
        return row;
    }

    public static <RowType extends Row> List<RowType> getAll(Class<RowType> rowType) {
        return RTC.get(rowType).values();
    }

    public static <RowType extends Row> List<RowType> gets(Class<RowType> rowType, List<Integer> ids) {
        return gets(rowType, ids.toArray(new Integer[]{}));
    }

    public static <RowType extends Row> List<RowType> gets(Class<RowType> rowType, Integer... ids) {
        List<RowType> rows = new ArrayList<>();
        for (Integer id : ids) {
            rows.add(RTC.get(rowType).get(id));
        }
        return rows;
    }

    public static <RowType extends Row> boolean containsItemTemplateRows(IdMaptoCount idMaptoCount) {
        for (Integer id : idMaptoCount.getAllKeys()) {
            Class<? extends AbstractRow> rowClass = IdItemTypeEnum.parseByItemTemplateId(id).getRowClass();
            if (!has(rowClass, id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回表
     *
     * @param rowType
     * @param hierarchyTableName
     * @return
     */
    public static <RowType extends Row> Table<RowType> get(Class<RowType> rowType, String hierarchyTableName) {
        return RTC.get(rowType, hierarchyTableName);
    }

    public static TableData getPlanningTableDataByName(String tableName) {
        if (planningTableData.getTableNameToTableData().containsKey(tableName)) {
            return planningTableData.getTableNameToTableData().get(tableName);
        }
        String msg = String.format("Can not find this table in RootTc,tableName=%s", tableName);
        throw new TableDateNotFoundException(msg);
    }
}
