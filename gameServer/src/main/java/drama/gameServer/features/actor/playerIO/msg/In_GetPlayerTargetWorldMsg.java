package drama.gameServer.features.actor.playerIO.msg;

import dm.relationship.base.msg.implement._PlayerInnerMsg;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;

/**
 * Created by lee on 2021/9/28
 */
public class In_GetPlayerTargetWorldMsg {
    public static class Request extends _PlayerInnerMsg {
        
        public Request(String playerId) {
            super(playerId);
        }
    }

    public static class Response {
        private SimplePlayer simplePlayer;

        public Response(SimplePlayer simplePlayer) {
            this.simplePlayer = simplePlayer;
        }

        public SimplePlayer getSimplePlayer() {
            return simplePlayer;
        }
    }

}
