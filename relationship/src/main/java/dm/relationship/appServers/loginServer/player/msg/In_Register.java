package dm.relationship.appServers.loginServer.player.msg;

import akka.actor.ActorRef;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;
import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_Register {
    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 8080608574442450048L;

        private ActorRef gatewaySender;
        private String connFlag;
        private CenterPlayer centerPlayer;
        private int sex;
        private int iconId;

        public Request(ActorRef gatewaySender, String connFlag, CenterPlayer centerPlayer, int sex, int iconId) {
            this.gatewaySender = gatewaySender;
            this.connFlag = connFlag;
            this.centerPlayer = centerPlayer;
            this.sex = sex;
            this.iconId = iconId;
        }

        public Request(CenterPlayer centerPlayer) {
            this.centerPlayer = centerPlayer;
        }

        public String getConnFlag() {
            return connFlag;
        }

        public CenterPlayer getCenterPlayer() {
            return centerPlayer;
        }

        public int getSex() {
            return sex;
        }

        public int getIconId() {
            return iconId;
        }

        public ActorRef getGatewaySender() {
            return gatewaySender;
        }

    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 1357396628520337901L;
        private Request request;
        private boolean rs;

        public Response(Request request, boolean rs) {
            this.request = request;
            this.rs = rs;
        }

        public Request getRequest() {
            return request;
        }

        public boolean isRs() {
            return rs;
        }
    }


}
