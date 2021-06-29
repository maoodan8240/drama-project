package dm.relationship.topLevelPojos.mails;


import dm.relationship.topLevelPojos.common.Iac;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class Mail implements Serializable {
    private static final long serialVersionUID = -3627641359147367368L;

    protected String mailId;                               // 邮件唯一ID
    protected String title;                                // 邮件主题
    protected String content;                              // 邮件内容
    protected int tpId;                                    // 对于表ID
    protected String expireTime;                           // 过期时间 格式-- yyyy-MM-dd HH:mm:ss
    protected String sendTime;                             // 发送时间
    protected String senderId;                             // 发送者Id
    protected String senderName;                           // 发送者名字
    protected boolean hasRead;                             // 是否已读或者已经领取附件(只要未领取附件一律视为未读)
    protected boolean deleteAfterRead;                     // 是否阅后即焚
    protected boolean hasAttachments;                      // 是否有附件
    protected boolean hasReceive;                          // 附件是否已领取
    protected List<Iac> attachments = new ArrayList<>();   // 附件列表


    public Mail() {
    }

    public Mail(String mailId, String title, String content, int tpId, String expireTime, String sendTime, String senderId, String senderName, boolean hasRead, boolean deleteAfterRead, boolean hasAttachments, boolean hasReceive, List<Iac> attachments) {
        this.mailId = mailId;
        this.title = title;
        this.content = content;
        this.tpId = tpId;
        this.expireTime = expireTime;
        this.sendTime = sendTime;
        this.senderId = senderId;
        this.senderName = senderName;
        this.hasRead = hasRead;
        this.deleteAfterRead = deleteAfterRead;
        this.hasAttachments = hasAttachments;
        this.hasReceive = hasReceive;
        this.attachments = attachments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public int getTpId() {
        return tpId;
    }

    public void setTpId(int tpId) {
        this.tpId = tpId;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public boolean isDeleteAfterRead() {
        return deleteAfterRead;
    }

    public void setDeleteAfterRead(boolean deleteAfterRead) {
        this.deleteAfterRead = deleteAfterRead;
    }

    public List<Iac> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Iac> attachments) {
        this.attachments = attachments;
    }

    public boolean isHasReceive() {
        return hasReceive;
    }

    public void setHasReceive(boolean hasReceive) {
        this.hasReceive = hasReceive;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
