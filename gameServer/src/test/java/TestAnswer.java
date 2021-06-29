import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_Result_Row;
import drama.gameServer.system.table.RootTcListener;
import drama.protos.EnumsProtos;
import org.junit.Test;
import ws.common.table.data.PlanningTableData;
import ws.common.utils.dataSource.txt._PlanningTableData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestAnswer {
    @Test
    public void test1() throws Exception {
        PlanningTableData planningTableData = new _PlanningTableData("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\");
        planningTableData.loadAllTablesData();
        RootTc.init(planningTableData, new RootTcListener());
        RootTc.loadAllTables(new Locale("zh", "CN"));
        EnumsProtos.SexEnum sex = EnumsProtos.SexEnum.MALE;
        List<String> optionsList = new ArrayList<>();
        optionsList.add("A");
        optionsList.add("A");
        for (Table_Result_Row row : RootTc.get(Table_Result_Row.class).values()) {
            if (row.getSex() == sex.getNumber()) {
                if (optionsList.size() == row.getAnswer().size()) {
//                    List<String> reuslt = row.getAnswer().stream().filter(it -> !optionsList.contains(it)).collect(Collectors.toList());
//                    reuslt.stream().forEach(System.out::println);
                    long count = row.getAnswer().stream().filter(it -> !optionsList.contains(it)).count();
                    System.out.println(count);
                }
            }
        }
    }

    @Test
    public void test() {
        Map<Integer, String> map = new ConcurrentHashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);


        map.put(1, "ss");
        for (Integer idx : list) {
            map.containsKey(idx);
            System.out.println();
        }
    }
}
