package dm.relationship.appServers.loginServer.player.msg;

import akka.actor.ActorRef;
import ws.common.utils.message.implement.AbstractInnerMsg;
import drama.protos.PlayerLoginProtos.Sm_Login;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;

public class In_ConnectToPlayerRequest extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private Sm_Login.Action action;
    private ActorRef gatewaySender;
    private String connFlag;
    private String playerId;
    private int outerRealmId;
    private boolean newLogin;
    private boolean sync;
    private String deviceUid;
    private CenterPlayer centerPlayer;

    public In_ConnectToPlayerRequest(Sm_Login.Action action, ActorRef gatewaySender, String connFlag, String playerId, int outerRealmId, String deviceUid, CenterPlayer centerPlayer) {
        this.playerId = playerId;
        this.action = action;
        this.gatewaySender = gatewaySender;
        this.connFlag = connFlag;
        this.outerRealmId = outerRealmId;
        this.deviceUid = deviceUid;
        this.centerPlayer = centerPlayer;
    }

    public ActorRef getGatewaySender() {
        return gatewaySender;
    }

    public String getConnFlag() {
        return connFlag;
    }

    public Sm_Login.Action getAction() {
        return action;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public boolean isNewLogin() {
        return newLogin;
    }

    public void setNewLogin(boolean newLogin) {
        this.newLogin = newLogin;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public CenterPlayer getCenterPlayer() {
        return centerPlayer;
    }
}
