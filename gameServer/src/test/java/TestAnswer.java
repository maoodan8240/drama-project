import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestAnswer {
    @Test
    public void test1() throws Exception {
//        GlobalInjectorUtils.init();
//        PlanningTableData planningTableData = new _PlanningTableData("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\");
//        planningTableData.loadAllTablesData();
//        RootTc.init(planningTableData, new RootTcListener());
//        RootTc.loadAllTables(new Locale("zh", "CN"));
//        EnumsProtos.SexEnum sex = EnumsProtos.SexEnum.MALE;
//        List<String> optionsList = new ArrayList<>();
//        optionsList.add("A");
//        optionsList.add("A");
//        RoomCtrl roomCtrl = GlobalInjector.getInstance(RoomCtrl.class);
//        String roomId = ObjectId.get().toString();
//        String playerId = ObjectId.get().toString();
//        roomCtrl.createRoom(roomId, playerId, 101);
//        RoomPlayerCtrl roomPlayerCtrl = roomCtrl.getRoomPlayerCtrl(playerId);
//        roomPlayerCtrl.setRoleId(1);
//        roomCtrl.chooseRole(roomPlayerCtrl.getTarget());
//        List<Integer> rightAnswerIdx = roomCtrl.getRightAnswerIdx(optionsList, 101, sex);
//        int roleIdx = roomCtrl.getRoleIdx(rightAnswerIdx);
//        System.out.println(roleIdx);
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
