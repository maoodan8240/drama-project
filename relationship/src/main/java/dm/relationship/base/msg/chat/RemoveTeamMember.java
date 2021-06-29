package dm.relationship.base.msg.chat;

/**
 * Created by lee on 17-5-5.
 */
public class RemoveTeamMember {
    public static class Request extends ChatServerInnerMsg {
        private static final long serialVersionUID = 8507676314548398587L;
        private String teamId;
        private String playerId;

        public Request(int innerRealmId, String teamId, String playerId) {
            super(innerRealmId);
            this.teamId = teamId;
            this.playerId = playerId;
        }

        public String getTeamId() {
            return teamId;
        }

        public String getPlayerId() {
            return playerId;
        }
    }


    public static class Response extends ChatServerInnerMsg {
        private static final long serialVersionUID = 6670654983207931848L;

        public Response(int innerRealmId) {
            super(innerRealmId);
        }
    }
}
