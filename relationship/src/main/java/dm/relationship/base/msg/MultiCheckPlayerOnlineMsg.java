package dm.relationship.base.msg;

import akka.actor.ActorRef;
import ws.common.utils.message.implement.AbstractInnerMsg;

import java.util.ArrayList;
import java.util.List;

public class MultiCheckPlayerOnlineMsg {
    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 723285027922089200L;
        private final String checkPlayerId;
        private final List<CheckPlayerOnlineMsgRequest<?>> checkOnlineMsgs = new ArrayList<>();
        private final ActorRef WorldActorRef;

        public Request(String checkPlayerId, List<CheckPlayerOnlineMsgRequest<?>> checkOnlineMsgs, ActorRef worldActorRef) {
            this.checkPlayerId = checkPlayerId;
            this.checkOnlineMsgs.addAll(checkOnlineMsgs);
            WorldActorRef = worldActorRef;
        }

        public String getCheckPlayerId() {
            return checkPlayerId;
        }

        public List<CheckPlayerOnlineMsgRequest<?>> getCheckOnlineMsgs() {
            return checkOnlineMsgs;
        }

        public ActorRef getWorldActorRef() {
            return WorldActorRef;
        }
    }


    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = 8228571723775591042L;
        private Request request;

        public Response(Request request) {
            this.request = request;
        }

        public Request getRequest() {
            return request;
        }
    }
}
