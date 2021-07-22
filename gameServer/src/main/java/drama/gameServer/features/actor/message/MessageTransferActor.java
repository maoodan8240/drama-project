package drama.gameServer.features.actor.message;

import akka.actor.ActorRef;
import akka.actor.Kill;
import akka.actor.Props;
import dm.relationship.base.actor.DmActor;
import dm.relationship.base.cluster.ActorSystemPath;
import dm.relationship.base.msg.In_MessageReceiveHolder;
import drama.gameServer.system.network.In_ConnectionStatusRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageTransferActor extends DmActor {
    //网络请求总分发器,通过这个分发给各个MessageTransferForReceiveActor
    private static final Random RD = new Random();
    private static int forReceive = 10;
    private static int forSend = 10;

    private List<ActorRef> actorRefsForReceive = new ArrayList<>();
    private List<ActorRef> actorRefsForSend = new ArrayList<>();

    public MessageTransferActor() {

        enableWsActorLogger = false;
        init();
    }

    private void init() {
        for (ActorRef actorRef : actorRefsForReceive) {
            actorRef.tell(Kill.getInstance(), self());
        }
        for (ActorRef actorRef : actorRefsForSend) {
            actorRef.tell(Kill.getInstance(), self());
        }
        actorRefsForReceive.clear();
        actorRefsForSend.clear();
        for (int i = 0; i < forReceive; i++) {
            ActorRef actorRef = getContext().actorOf(Props.create(MessageTransferForReceiveActor.class, context()), ActorSystemPath.DM_GameServer_MessageTransferForReceive + i);
            getContext().watch(actorRef);
            actorRefsForReceive.add(actorRef);
        }
        for (int i = 0; i < forSend; i++) {
            ActorRef actorRef = getContext().actorOf(Props.create(MessageTransferForSendActor.class), ActorSystemPath.DM_GameServer_MessageTransferForSend + i);
            getContext().watch(actorRef);
            actorRefsForSend.add(actorRef);
        }
    }

    @Override
    public void onRecv(Object msg) throws Exception {
        // 接受到客户端发送来的消息,随机分发给下面的MessageTransferForReceiveActor执行
        if (msg instanceof In_MessageReceiveHolder) {
            actorRefsForReceive.get(RD.nextInt(forReceive)).tell(msg, self());
        } else if (msg instanceof In_ConnectionStatusRequest) {
            actorRefsForReceive.get(RD.nextInt(forReceive)).tell(msg, self());
        }
    }
}
