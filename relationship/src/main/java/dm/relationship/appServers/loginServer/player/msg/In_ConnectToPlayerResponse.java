package dm.relationship.appServers.loginServer.player.msg;

import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_ConnectToPlayerResponse extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private In_ConnectToPlayerRequest request;
    private boolean rs;

    public In_ConnectToPlayerResponse(In_ConnectToPlayerRequest request, boolean rs) {
        this.request = request;
        this.rs = rs;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public In_ConnectToPlayerRequest getRequest() {
        return request;
    }

    public boolean isRs() {
        return rs;
    }

    public String getPlayerId() {
        return request.getPlayerId();
    }
}
