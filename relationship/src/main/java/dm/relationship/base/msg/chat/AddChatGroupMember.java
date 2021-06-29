package dm.relationship.base.msg.chat;

import drama.protos.EnumsProtos.ChatTypeEnum;

/**
 * Created by lee on 17-5-5.
 */
public class AddChatGroupMember {
    public static class Request extends ChatServerInnerMsg {
        private static final long serialVersionUID = 8507676314548398587L;
        private ChatTypeEnum type;
        private String guildId;
        private String playerId;

        public Request(int innerRealmId, ChatTypeEnum type, String guildId, String playerId) {
            super(innerRealmId);
            this.type = type;
            this.guildId = guildId;
            this.playerId = playerId;
        }

        public ChatTypeEnum getType() {
            return type;
        }

        public String getGuildId() {
            return guildId;
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
