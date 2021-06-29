import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import dm.relationship.table.tableRows.Table_Search_Row;
import drama.gameServer.system.ServerGlobalData;
import drama.gameServer.system.config.AppConfig;
import drama.gameServer.system.table.RootTcListener;
import org.junit.Test;
import ws.common.table.data.PlanningTableData;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.dataSource.txt._PlanningTableData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TestLinkedHashMap {

    @Test
    public void testLinkedHashMap() throws Exception {
        AppConfig.init();
        ServerGlobalData.init();
        PlanningTableData planningTableData = new _PlanningTableData("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\");
        planningTableData.loadAllTablesData();
        RootTc.init(planningTableData, new RootTcListener());
        RootTc.loadAllTables(new Locale("zh", "CN"));
        Table_SceneList_Row row = RootTc.get(Table_SceneList_Row.class).get(101);
        List<TupleCell<String>> list = new LinkedList<>(row.getRunDown());
        for (Iterator<TupleCell<String>> iterator = list.iterator(); iterator.hasNext(); ) {
            TupleCell<String> next = iterator.next();
            System.out.print(next.get(TupleCell.FIRST) + ":" + next.get(TupleCell.SECOND) + ",");
            iterator.remove();
        }

    }

    @Test
    public void test1() throws Exception {
        AppConfig.init();
        ServerGlobalData.init();
        PlanningTableData planningTableData = new _PlanningTableData("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\");
        planningTableData.loadAllTablesData();
        RootTc.init(planningTableData, new RootTcListener());
        RootTc.loadAllTables(new Locale("zh", "CN"));
        List<Integer> ids = new ArrayList<>();
        ids.add(2);
        List<Table_Search_Row> values = RootTc.get(Table_Search_Row.class).values();
        List<Table_Search_Row> result = values.stream().filter(it -> !ids.contains(it.getId())).collect(Collectors.toList());
        List<Integer> typeIds = new ArrayList<>();
        for (Table_Search_Row row : result) {
            if (!result.contains(row.getTypeid())) {
                typeIds.add(row.getTypeid());
            }
        }


    }
}
