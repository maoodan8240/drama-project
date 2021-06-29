package dm.relationship.base.msg;

public class In_PlayerOfflineRequest extends AbstractGateWayServerInnerMsg {
    private static final long serialVersionUID = 1L;

    public In_PlayerOfflineRequest(String connFlag, String playerId) {
        super(connFlag, playerId);
    }
}
