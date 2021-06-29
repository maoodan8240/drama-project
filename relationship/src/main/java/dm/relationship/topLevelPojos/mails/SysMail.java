package dm.relationship.topLevelPojos.mails;

import dm.relationship.topLevelPojos.common.Iac;
import dm.relationship.utils.CloneUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysMail extends Mail implements Serializable {
    private static final long serialVersionUID = -5594766106813876663L;
    /**
     * 指定了teplateId邮件内容已经有了，用这个集合中参数，就可以拼出需要的邮件内容
     */
    private List<String> args = new ArrayList<>();

    public SysMail() {
    }

    public SysMail(String mailId, String title, String content, int tpId, String expireTime, String sendTime, String senderId, String senderName, boolean hasRead, boolean deleteAfterRead, boolean hasAttachments, boolean hasReceive, List<Iac> attachments, List<String> args) {
        super(mailId, title, content, tpId, expireTime, sendTime, senderId, senderName, hasRead, deleteAfterRead, hasAttachments, hasReceive, attachments);
        this.args = args;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }


    @Override
    public SysMail clone() {
        return new SysMail(mailId, title, content, tpId, expireTime, sendTime, senderId,
                senderName, hasRead, deleteAfterRead, hasAttachments, hasReceive,
                CloneUtils.cloneWsCloneableList(attachments), CloneUtils.cloneStringList(args));
    }
}
