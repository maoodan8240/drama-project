package dm.relationship.base.msg.chat;

/**
 * Created by lee on 17-5-5.
 */
public class AddChatServerPlayer {
    public static class Request extends ChatServerInnerMsg {
        private static final long serialVersionUID = 1412131573382205661L;
        private String gameId;
        private int outerRealmId;
        private int innerRealmId;

        public Request(String gameId, int innerRealmId, int outerRealmId) {
            super(innerRealmId);
            this.gameId = gameId;
            this.outerRealmId = outerRealmId;
            this.innerRealmId = innerRealmId;
        }

        public String getGameId() {
            return gameId;
        }

        public int getOuterRealmId() {
            return outerRealmId;
        }

        @Override
        public int getInnerRealmId() {
            return innerRealmId;
        }
    }

    public static class Response extends ChatServerInnerMsg {
        private static final long serialVersionUID = 6670654983207931848L;

        public Response(int innerRealmId) {
            super(innerRealmId);
        }
    }
}
