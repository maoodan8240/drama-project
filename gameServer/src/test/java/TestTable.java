import dm.relationship.table.RootTc;
import dm.relationship.table.tableRows.Table_RunDown_Row;
import drama.gameServer.system.ServerGlobalData;
import drama.gameServer.system.config.AppConfig;
import drama.gameServer.system.table.RootTcListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.table.data.PlanningTableData;
import ws.common.utils.dataSource.txt._PlanningTableData;

import java.util.Locale;

public class TestTable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTable.class);

    @Test
    public void test() throws Exception {
        AppConfig.init();
        ServerGlobalData.init();
        PlanningTableData planningTableData = new _PlanningTableData("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\");
        planningTableData.loadAllTablesData();
        RootTc.init(planningTableData, new RootTcListener());
        RootTc.loadAllTables(new Locale("zh", "CN"));
//        Table_RunDown_Row value = RootTc.get(Table_RunDown_Row.class).get(4);
        long nextSTime = Table_RunDown_Row.getNextSTime("TALK", 2);
//        Integer nextSTime = value.getNextSTime();
        System.out.println(nextSTime);
//        System.out.println(EnumsProtos.RoomStateEnum.ANSWER.toString());
//        String name = "RoleId";
//        String value1 = "3,6";
//        ArrayList<CommonProtos.Cm_Common_Args> args = new ArrayList<>();
//        CommonProtos.Cm_Common_Args.Builder b = CommonProtos.Cm_Common_Args.newBuilder();
//        b.setName("RoleId");
//        b.setValue("3,6");
//        CommonProtos.Cm_Common_Args.Builder b1 = CommonProtos.Cm_Common_Args.newBuilder();
//        b1.setName("Dub");
//        b1.setValue("1006");
//        TableData tableDataTxt = RootTc.getPlanningTableDataByName("Table_Stage");
//        args.add(b.build());
//        args.add(b1.build());
//        TableDataRow r = null;
//        List<Boolean> arr = new ArrayList<>();
//        for (int rowIdx = 0; rowIdx < tableDataTxt.getRows().size(); ++rowIdx) {
//            TableDataRow row = (TableDataRow) tableDataTxt.getRows().get(rowIdx);
//            for (CommonProtos.Cm_Common_Args arg : args) {
//                for (int columnIdx = 0; columnIdx < row.getCells().size(); ++columnIdx) {
//                    String columnName = ((TableDataHeader) tableDataTxt.getHeaderDatas().get(columnIdx)).getName();
//                    String name = arg.getName();
//                    String value = arg.getValue();
//                    if (name.equals(columnName) && value.equals(row.getCells().get(columnIdx).getCell())) {
//                        arr.add(true);
//                    }
//                }
//            }
//            if (arr.size() != 0) {
//                boolean contains = arr.contains(false);
//                if (!contains) {
//                    r = row;
//                    break;
//                }
//            }
////
//
//
//        }
//        for (Table_Search_Row value : values) {
//            LOGGER.debug("id={},SrchNum={},Typeid={},Line={},Pic={},Hide={}", value.getId(), value.getSrchNum(), value.getTypeid(), value.getLine(), value.getPic(), value.getHide());
//        }
    }

//    @Test
//    public void test2() throws Exception {
//        MakeCodeFile.genTableRows(//
//                "D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\Table_Stage.tab",//
//                "D:\\work_space\\drama-project\\relationship\\src\\main\\java\\",//
//                "dm.relationship.table.tableRows",//
//                "Table_Stage_Row"
//        );
//    }
}
