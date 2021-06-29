package dm.relationship.base.msg;

import ws.common.utils.message.implement.AbstractInnerMsg;

public abstract class AbstractGateWayServerInnerMsg extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private String connFlag;
    private String playerId;
    private int outerRealmId;
    private int innerRealmId;

    public AbstractGateWayServerInnerMsg(String connFlag) {
        this.connFlag = connFlag;
    }

    public AbstractGateWayServerInnerMsg(String connFlag, String playerId) {
        this.connFlag = connFlag;
        this.playerId = playerId;
    }

    public String getConnFlag() {
        return connFlag;
    }

    public void setConnFlag(String connFlag) {
        this.connFlag = connFlag;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public int getInnerRealmId() {
        return innerRealmId;
    }

    public void setInnerRealmId(int innerRealmId) {
        this.innerRealmId = innerRealmId;
    }
}
