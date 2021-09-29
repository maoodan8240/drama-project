package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

import java.util.Map;

/**
 * Created by lee on 2021/9/26
 */
public class In_PlayerCanSubSelectInfoRoomMsg extends _PlayerInnerMsg {
    private Map<Integer, String> canSubSelectIds;
    private int dramaId;
    private int subNum;

    public In_PlayerCanSubSelectInfoRoomMsg(String roomId, Map<Integer, String> canSubSelectIds, int dramaId, int subNum) {
        super(roomId);
        this.canSubSelectIds = canSubSelectIds;
        this.dramaId = dramaId;
        this.subNum = subNum;
    }

    public Map<Integer, String> getCanSubSelectIds() {
        return canSubSelectIds;
    }

    public int getDramaId() {
        return dramaId;
    }

    public int getSubNum() {
        return subNum;
    }
}
