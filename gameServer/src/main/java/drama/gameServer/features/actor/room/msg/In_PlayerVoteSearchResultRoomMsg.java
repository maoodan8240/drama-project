package drama.gameServer.features.actor.room.msg;

import drama.gameServer.features.actor.room.pojo.RoomPlayer;
import ws.common.utils.message.interfaces.InnerMsg;
import ws.common.utils.message.interfaces.ResultCode;

import java.util.List;
import java.util.Map;

public class In_PlayerVoteSearchResultRoomMsg implements InnerMsg {
    private Map<Integer, List<Integer>> voteTypeIdToPlayerRoleId;
    private int clueId;
    private int dramaId;
    private RoomPlayer roomPlayer;

    public In_PlayerVoteSearchResultRoomMsg(Map<Integer, List<Integer>> voteTypeIdToPlayerRoleId, int clueId, RoomPlayer roomPlayer, int dramaId) {
        this.voteTypeIdToPlayerRoleId = voteTypeIdToPlayerRoleId;
        this.clueId = clueId;
        this.dramaId = dramaId;
        this.roomPlayer = roomPlayer;
    }

    public RoomPlayer getRoomPlayer() {
        return roomPlayer;
    }

    public int getDramaId() {
        return dramaId;
    }

    public Map<Integer, List<Integer>> getVoteTypeIdToPlayerRoleId() {
        return voteTypeIdToPlayerRoleId;
    }

    public int getClueId() {
        return clueId;
    }

    @Override
    public ResultCode getResultCode() {
        return null;
    }

    @Override
    public void addReceiver(String s) {

    }

    @Override
    public List<String> getReceivers() {
        return null;
    }
}
