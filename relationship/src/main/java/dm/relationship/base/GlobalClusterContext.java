package dm.relationship.base;

import akka.actor.ActorContext;

/**
 * Created by lee on 17-6-28.
 */
public class GlobalClusterContext {
    private static ActorContext context;

    public static void setContext(ActorContext context) {
        GlobalClusterContext.context = context;
    }

    public static ActorContext getContext() {
        return context;
    }
}
