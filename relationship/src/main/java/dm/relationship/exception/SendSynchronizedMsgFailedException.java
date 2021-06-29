package dm.relationship.exception;

import akka.actor.ActorSelection;
import dm.relationship.base.ServerEnvEnum;
import dm.relationship.base.ServerRoleEnum;

public class SendSynchronizedMsgFailedException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public SendSynchronizedMsgFailedException(ActorSelection actorSelection, Object obj, Throwable e) {
        super("异步发送消息失败! actorSelection=" + actorSelection + ", request=" + obj + " !", e);
    }

    public SendSynchronizedMsgFailedException(ServerRoleEnum serverRoleEnum, ServerEnvEnum serverEnv, Object obj) {
        super("异步发送消息失败!  对应的服务器不存在！serverRoleEnum=" + serverRoleEnum + ", serverEnv=" + serverEnv + ", request=" + obj + " !");
    }
}
