package drama.gameServer.utils;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author lee
 * @Date 2019/9/19 19:41
 **/
public class ActorUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActorUtils.class);
    private static final int RETRY_TIMES = 25;

    public static void stop(ActorContext worldActorContext, ActorRef actorRefFor, Object resolver) {
        //Bug Fix:stop resolve and avoid (akka.actor.InvalidActorNameException)
        int times = RETRY_TIMES;
        boolean isStop = false;
        worldActorContext.stop(actorRefFor);
        while (times-- > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
                if (actorRefFor == null) {
                    isStop = true;
                    break;
                }
                if (actorRefFor.isTerminated()) {
                    isStop = true;
                    break;
                }
            } catch (InterruptedException e) {
                LOGGER.error("Stop actor {} exception.", resolver.toString(), e);
                Thread.currentThread().interrupt();
            } catch (NullPointerException e) {
                isStop = true;
                break;
            }
        }
        if (isStop) {
            LOGGER.debug("resolver:{} stop success", resolver);
        } else {
            LOGGER.debug("resolver:{} stop failed", resolver);
        }
    }

}
