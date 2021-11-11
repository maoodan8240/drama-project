package drama.gameServer.features.actor.room.enums;

import dm.relationship.base.MagicNumbers;
import dm.relationship.exception.BusinessLogicMismatchConditionException;
import dm.relationship.table.tableRows.Table_SceneList_Row;
import drama.protos.EnumsProtos;
import drama.protos.EnumsProtos.RoomStateEnum;
import ws.common.table.table.interfaces.cell.TupleCell;

import java.util.List;

public enum RoomState {
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
    NOSELECT("NOSELECT", EnumsProtos.RoomStateEnum.NOSELECT),       //  强制选角
    SUBSELECT("SUBSELECT", EnumsProtos.RoomStateEnum.SUBSELECT),    //  选择子角色
    SECRETTALK("SECRETTALK", EnumsProtos.RoomStateEnum.SECRETTALK), //  私聊
    SUBVOTE("SUBVOTE", EnumsProtos.RoomStateEnum.SUBVOTE),          //  子剧本投凶
    CHOICE("CHOICE", EnumsProtos.RoomStateEnum.CHOICE),             //  个人选择
    AUCTION("AUCTION", EnumsProtos.RoomStateEnum.AUCTION),          //  拍卖
    SHOOT("SHOOT", EnumsProtos.RoomStateEnum.SHOOT),                //  开枪
    SUBREAD("SUBREAD", EnumsProtos.RoomStateEnum.SUBREAD),          //  子剧本读本阶段
    UNLOCKINFO("UNLOCKINFO", EnumsProtos.RoomStateEnum.UNLOCKINFO), //  解锁信息(玩家身上的一些动态信息)
    AUCTIONRESULT("AUCTIONRESULT", EnumsProtos.RoomStateEnum.AUCTIONRESULT),//  拍卖结果
    SCORE("SCORE", RoomStateEnum.SCORE),                            //  积分排行

    NULL("", null);

    private String name;
    private EnumsProtos.RoomStateEnum state;

    private RoomState(String name, EnumsProtos.RoomStateEnum state) {
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
        for (RoomState stateEnum : RoomState.values()) {
            if (stateEnum.getName().equals(name)) {
                return stateEnum.getState();
            }
        }
        String msg = String.format("RoomStateEnum解析失败! name=%s", name);
        throw new BusinessLogicMismatchConditionException(msg);
    }

    public static RoomState getRoomState(String name) {
        for (RoomState stateEnum : RoomState.values()) {
            if (stateEnum.getName().equals(name)) {
                return stateEnum;
            }
        }
        String msg = String.format("RoomStateEnum解析失败! name=%s", name);
        throw new BusinessLogicMismatchConditionException(msg);
    }

    public static boolean isFirstState(EnumsProtos.RoomStateEnum roomStateEnum, int dramaId) {
        Table_SceneList_Row row = Table_SceneList_Row.getRowByDramaId(dramaId);
        List<TupleCell<String>> runDown = row.getRunDown();
        String tabRundown = runDown.get(Integer.valueOf(MagicNumbers.DEFAULT_ZERO)).get(TupleCell.FIRST);
        EnumsProtos.RoomStateEnum roomStateByName = RoomState.getRoomStateByName(tabRundown);
        return roomStateByName == roomStateEnum;
    }

    public static boolean isEndState(EnumsProtos.RoomStateEnum roomStateEnum) {
        return roomStateEnum == ENDING.getState();
    }
}
