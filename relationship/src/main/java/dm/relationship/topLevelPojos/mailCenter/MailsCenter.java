package dm.relationship.topLevelPojos.mailCenter;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;

import java.util.LinkedList;
import java.util.List;


public class MailsCenter implements TopLevelPojo {
    private static final long serialVersionUID = -5130869634779623895L;

    @JSONField(name = "_id")
    private String autoId;
    private String lastResetDay = "0";

    private List<GmMail> gmMails = new LinkedList<>();

    public MailsCenter() {
    }

    public MailsCenter(String autoId) {
        this.autoId = autoId;
    }

    public List<GmMail> getGmMails() {
        return gmMails;
    }

    public void setGmMails(List<GmMail> gmMails) {
        this.gmMails = gmMails;
    }

    @Override
    public String getOid() {
        return autoId;
    }

    @Override
    public void setOid(String autoId) {
        this.autoId = autoId;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getLastResetDay() {
        return lastResetDay;
    }

    public void setLastResetDay(String lastResetDay) {
        this.lastResetDay = lastResetDay;
    }
}
