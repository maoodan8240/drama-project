package dm.relationship.topLevelPojos.mailCenter;

import drama.protos.EnumsProtos.PlatformTypeEnum;
import dm.relationship.topLevelPojos.common.Iac;
import dm.relationship.topLevelPojos.mails.Mail;
import dm.relationship.utils.CloneUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GmMail extends Mail implements Serializable {
    private static final long serialVersionUID = -4674480965316046249L;
    // 限制信息
    private List<Integer> outerRealmIdsOut = new ArrayList<>();          // 限制 - 不是哪一个服的邮件,(不为空时忽略outerRealmIdsIn字段) (空忽略该字段,再去检查outerRealmIdsIn字段)
    private List<Integer> outerRealmIdsIn = new ArrayList<>();           // 限制 - 哪一个服的邮件, 空为全服邮件
    private List<PlatformTypeEnum> limitPlatforms = new ArrayList<>();   // 限制 - 渠道
    private String limitCreateAtMin;                                     // 限制 - 注册时间下限
    private String limitCreateAtMax;                                     // 限制 - 注册时间上限
    private int limitLevelMin;                                           // 限制 - 等级下限
    private int limitLevelMax;                                           // 限制 - 等级上限
    private int limitVipLevelMin;                                        // 限制 - VIP等级下限
    private int limitVipLevelMax;                                        // 限制 - VIP等级上限

    public GmMail() {
    }

    public GmMail(String mailId, String title, String content, int tpId, String expireTime, String sendTime, String senderId, String senderName, boolean hasRead, boolean deleteAfterRead, boolean hasAttachments, boolean hasReceive, List<Iac> attachments, List<Integer> outerRealmIdsOut, List<Integer> outerRealmIdsIn, List<PlatformTypeEnum> limitPlatforms, String limitCreateAtMin, String limitCreateAtMax, int limitLevelMin, int limitLevelMax, int limitVipLevelMin, int limitVipLevelMax) {
        super(mailId, title, content, tpId, expireTime, sendTime, senderId, senderName, hasRead, deleteAfterRead, hasAttachments, hasReceive, attachments);
        this.outerRealmIdsOut = outerRealmIdsOut;
        this.outerRealmIdsIn = outerRealmIdsIn;
        this.limitPlatforms = limitPlatforms;
        this.limitCreateAtMin = limitCreateAtMin;
        this.limitCreateAtMax = limitCreateAtMax;
        this.limitLevelMin = limitLevelMin;
        this.limitLevelMax = limitLevelMax;
        this.limitVipLevelMin = limitVipLevelMin;
        this.limitVipLevelMax = limitVipLevelMax;
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

    public List<PlatformTypeEnum> getLimitPlatforms() {
        return limitPlatforms;
    }

    public void setLimitPlatforms(List<PlatformTypeEnum> limitPlatforms) {
        this.limitPlatforms = limitPlatforms;
    }

    public String getLimitCreateAtMin() {
        return limitCreateAtMin;
    }

    public void setLimitCreateAtMin(String limitCreateAtMin) {
        this.limitCreateAtMin = limitCreateAtMin;
    }

    public String getLimitCreateAtMax() {
        return limitCreateAtMax;
    }

    public void setLimitCreateAtMax(String limitCreateAtMax) {
        this.limitCreateAtMax = limitCreateAtMax;
    }

    public int getLimitLevelMin() {
        return limitLevelMin;
    }

    public void setLimitLevelMin(int limitLevelMin) {
        this.limitLevelMin = limitLevelMin;
    }

    public int getLimitLevelMax() {
        return limitLevelMax;
    }

    public void setLimitLevelMax(int limitLevelMax) {
        this.limitLevelMax = limitLevelMax;
    }

    public int getLimitVipLevelMin() {
        return limitVipLevelMin;
    }

    public void setLimitVipLevelMin(int limitVipLevelMin) {
        this.limitVipLevelMin = limitVipLevelMin;
    }

    public int getLimitVipLevelMax() {
        return limitVipLevelMax;
    }

    public void setLimitVipLevelMax(int limitVipLevelMax) {
        this.limitVipLevelMax = limitVipLevelMax;
    }

    public List<Integer> getOuterRealmIdsIn() {
        return outerRealmIdsIn;
    }

    public void setOuterRealmIdsIn(List<Integer> outerRealmIdsIn) {
        this.outerRealmIdsIn = outerRealmIdsIn;
    }

    public List<Integer> getOuterRealmIdsOut() {
        return outerRealmIdsOut;
    }

    public void setOuterRealmIdsOut(List<Integer> outerRealmIdsOut) {
        this.outerRealmIdsOut = outerRealmIdsOut;
    }


    @Override
    public GmMail clone() {
        return new GmMail(mailId, title, content, tpId, expireTime, sendTime,
                senderId, senderName, hasRead, deleteAfterRead, hasAttachments,
                hasReceive, CloneUtils.cloneWsCloneableList(attachments), CloneUtils.cloneIntegerList(outerRealmIdsOut),
                CloneUtils.cloneIntegerList(outerRealmIdsIn), CloneUtils.cloneEnumList(limitPlatforms),
                limitCreateAtMin, limitCreateAtMax,
                limitLevelMin, limitLevelMax, limitVipLevelMin, limitVipLevelMax);
    }
}
