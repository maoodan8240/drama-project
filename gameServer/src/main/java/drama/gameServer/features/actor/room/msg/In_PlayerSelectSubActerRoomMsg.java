package drama.gameServer.features.actor.room.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;

/**
 * Created by lee on 2021/9/26
 */
public class In_PlayerSelectSubActerRoomMsg extends _PlayerInnerMsg {
    private int subRoleId;
    private int subNum;
    private String subPlayerId;

    public In_PlayerSelectSubActerRoomMsg(int dramaId, String broadcastPlayerId, String subPlayerId, int subRoleId, int subNum) {
        super(broadcastPlayerId, dramaId);
        this.subRoleId = subRoleId;
        this.subNum = subNum;
        this.subPlayerId = subPlayerId;
    }

    public int getSubRoleId() {
        return subRoleId;
    }

    public int getSubNum() {
        return subNum;
    }

    public String getSubPlayerId() {
        return subPlayerId;
    }
}
