package dm.relationship.base.actor;

import akka.actor.UntypedActor;
import dm.relationship.base.msg.In_DisplayActorSelfPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.message.interfaces.InnerMsg;

public abstract class DmActor extends UntypedActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DmActor.class);
    protected boolean enableWsActorLogger = false;

    @Override
    public final void onReceive(final Object msg) throws Exception {
        try {
            if (enableWsActorLogger && LOGGER.isDebugEnabled()) {
                LOGGER.trace("\n接收方：{}.{}\n接收到消息：{}.{}\n内容：{}\n", this.getClass().getPackage(), this.getClass().getSimpleName(), msg.getClass().getPackage(), msg.getClass().getSimpleName(), msg.toString());
                if (msg instanceof In_DisplayActorSelfPath) {
                    getContext().getChildren().forEach(ref -> {
                        ref.tell(msg, self());
                    });
                    LOGGER.debug("          Actor self path = {}", self());
                    return;
                }
            }
            if (enableWsActorLogger && LOGGER.isTraceEnabled()) {
                if (msg instanceof InnerMsg) {
                    InnerMsg msgInnerMsg = (InnerMsg) msg;
                    msgInnerMsg.addReceiver(getSelf().toString());
                    LOGGER.trace("InnerMsg传递的消息链>{}", msgInnerMsg.getReceivers());
                }
            }
            onRecv(msg);
        } catch (Throwable t) {
            LOGGER.error("{}.{} 处理消息异常！ {}.{}", this.getClass().getPackage(), this.getClass().getSimpleName(), msg.getClass().getPackage(), msg.getClass().getSimpleName(), t);
        }
    }

    public abstract void onRecv(final Object msg) throws Exception;


    @Override
    public void postStop() throws Exception {
        LOGGER.debug(this.getClass() + " 准备停止了！");
    }
}
