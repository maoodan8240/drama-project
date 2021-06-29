package dm.relationship.base.msg.chat;

/**
 * Created by lee on 17-5-5.
 */
public class RemoveChatServerPlayer {
    public static class Request extends ChatServerInnerMsg {
        private static final long serialVersionUID = 1412131573382205661L;
        private String playerId;

        public Request(int innerRealmId, String playerId) {
            super(innerRealmId);
            this.playerId = playerId;
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
