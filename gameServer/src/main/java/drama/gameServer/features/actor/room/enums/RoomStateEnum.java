package drama.gameServer.features.actor.room.enums;

import dm.relationship.base.MagicNumbers;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import drama.protos.EnumsProtos;
import ws.common.table.table.interfaces.cell.TupleCell;

import java.util.List;

public enum RoomStateEnum {
    ANSWER("ANSWER", EnumsProtos.RoomStateEnum.ANSWER),             //  选角答题
    READ("READ", EnumsProtos.RoomStateEnum.READ),                   //  读剧本
    STAGE("STAGE", EnumsProtos.RoomStateEnum.STAGE),                //  小剧场
    SEARCH("SEARCH", EnumsProtos.RoomStateEnum.SEARCH),             //  搜证
    TALK("TALK", EnumsProtos.RoomStateEnum.TALK),                   //  公聊
    VOTE("VOTE", EnumsProtos.RoomStateEnum.VOTE),                   //  投凶
    REVIEW("REVIEW", EnumsProtos.RoomStateEnum.REVIEW),             //  复盘
    ENDING("ENDING", EnumsProtos.RoomStateEnum.ENDING),             //  结束
    SOLO("SOLO", EnumsProtos.RoomStateEnum.SOLO),                   //  独白
    SELECT("SELECT", EnumsProtos.RoomStateEnum.SELECT),             //  选角
    VOTESEARCH("VOTESEARCH", EnumsProtos.RoomStateEnum.VOTESEARCH), //  投票搜证
    UNLOCK("UNLOCK", EnumsProtos.RoomStateEnum.UNLOCK),             //  解锁事件
    DRAFT("DRAFT", EnumsProtos.RoomStateEnum.DRAFT),                //  轮抽
    SELECTREAD("SELECTREAD", EnumsProtos.RoomStateEnum.SELECTREAD), //  动态剧本
    NOSELECT("NOSELECT", EnumsProtos.RoomStateEnum.NOSELECT),               //  强制选角

    NULL("", null);

    private String name;
    private EnumsProtos.RoomStateEnum state;

    private RoomStateEnum(String name, EnumsProtos.RoomStateEnum state) {
        this.name = name;
        this.state = state;
    }


    public String getName() {
        return name;
    }

    public EnumsProtos.RoomStateEnum getState() {
        return state;
    }

    public static EnumsProtos.RoomStateEnum getRoomStateByName(String name) {
        for (RoomStateEnum stateEnum : RoomStateEnum.values()) {
            if (stateEnum.getName().equals(name)) {
                return stateEnum.getState();
            }
        }
        String msg = String.format("RoomStateEnum解析失败! name=%s", name);
        throw new BusinessLogicMismatchConditionException(msg);
    }

    public static boolean isFirstState(EnumsProtos.RoomStateEnum roomStateEnum, int dramaId) {
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(dramaId);
        List<TupleCell<String>> runDown = row.getRunDown();
        String tabRundown = runDown.get(Integer.valueOf(MagicNumbers.DEFAULT_ZERO)).get(TupleCell.FIRST);
        EnumsProtos.RoomStateEnum roomStateByName = RoomStateEnum.getRoomStateByName(tabRundown);
        return roomStateByName == roomStateEnum;
    }

    public static boolean isEndState(EnumsProtos.RoomStateEnum roomStateEnum) {
        return roomStateEnum == ENDING.getState();
    }
}
