package drama.gameServer.features.actor.roomCenter.enums;

import dm.relationship.exception.BusinessLogicMismatchConditionException;
import drama.protos.EnumsProtos;

public enum RoomStateEnum {
    ANSWER("ANSWER", EnumsProtos.RoomStateEnum.ANSWER),     //  选角答题
    READ("READ", EnumsProtos.RoomStateEnum.READ),           //  读剧本
    STAGE("STAGE", EnumsProtos.RoomStateEnum.STAGE),        //  小剧场
    SEARCH("SEARCH", EnumsProtos.RoomStateEnum.SEARCH),     //  搜证
    TALK("TALK", EnumsProtos.RoomStateEnum.TALK),           //  公聊
    VOTE("VOTE", EnumsProtos.RoomStateEnum.VOTE),           //  投凶
    REVIEW("REVIEW", EnumsProtos.RoomStateEnum.REVIEW),     //  复盘
    ENDING("ENDING", EnumsProtos.RoomStateEnum.ENDING),     //  结束
    SOLO("SOLO", EnumsProtos.RoomStateEnum.SOLO),           //  独白
    ;

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

}
