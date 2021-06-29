package dm.relationship.utils;

import akka.actor.ActorContext;
import akka.actor.ActorSelection;
import akka.actor.Address;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.ServerEnvEnum;
import dm.relationship.base.ServerRoleEnum;
import dm.relationship.base.cluster.AkkaAddressContext;
import dm.relationship.exception.SendSynchronizedMsgFailedException;

import java.util.List;

public class ActorMsgSynchronizedExecutor {

    /**
     * 同步等待发送消息，启用ServerEnv
     *
     * @param serverRoleTarget
     * @param serverEnvTarget
     * @param actorContext
     * @param path
     * @param i
     * @return
     */
    public static <I, O> O sendMsgToSingleServerUseEnv(ServerRoleEnum serverRoleTarget, ServerEnvEnum serverEnvTarget, ActorContext actorContext, String path, I i) {
        List<AkkaAddressContext> addressContexts = ClusterUtils.getAddressContextLisUseEnv(actorContext, serverRoleTarget, serverEnvTarget);
        if (addressContexts.size() == 0) {
            throw new SendSynchronizedMsgFailedException(serverRoleTarget, null, i);
        }
        return sendMsgToServer(addressContexts.get(0).getAddress(), actorContext, path, i);
    }

    /**
     * 同步等待发送消息，忽略ServerEnv
     *
     * @param serverRoleTarget
     * @param actorContext
     * @param path
     * @param i
     * @return
     */
    public static <I, O> O sendMsgToSingleServerIgnoreEnv(ServerRoleEnum serverRoleTarget, ActorContext actorContext, String path, I i) {
        List<AkkaAddressContext> addressContexts = ClusterUtils.getAddressContextLisIgnoreEnv(actorContext, serverRoleTarget);
        if (addressContexts.size() == 0) {
            throw new SendSynchronizedMsgFailedException(serverRoleTarget, null, i);
        }
        return sendMsgToServer(addressContexts.get(0).getAddress(), actorContext, path, i);
    }

    public static <I, O> O sendMsgToServer(Address address, ActorContext context, String path, I i) {
        ActorSelection actorSelection = context.actorSelection(address.toString() + path);
        return sendMsgToServer(actorSelection, i);
    }

    @SuppressWarnings("unchecked")
    public static <I, O> O sendMsgToServer(ActorSelection actorSelection, I i) {
        Timeout timeout = new Timeout(Duration.create(MagicNumbers.AKKA_TIME_OUT, "seconds"));
        Future<Object> future = Patterns.ask(actorSelection, i, timeout);
        try {
            Object result = Await.result(future, timeout.duration());
            return (O) result;
        } catch (Exception e) {
            throw new SendSynchronizedMsgFailedException(actorSelection, i, e);
        }
    }
}
