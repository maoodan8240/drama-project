package dm.relationship.topLevelPojos.mails;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;
import dm.relationship.topLevelPojos.mailCenter.GmMail;

import java.util.HashMap;
import java.util.Map;

public class Mails extends PlayerTopLevelPojo {
    private static final long serialVersionUID = 8860077302390615882L;

    private String lastGmMailSendTime;
    private Map<String, SysMail> idToSysMail = new HashMap<>();
    private Map<String, GmMail> idToGmMail = new HashMap<>();

    public Mails() {
    }

    public Mails(String playerId) {
        super(playerId);
    }

    public String getLastGmMailSendTime() {
        return lastGmMailSendTime;
    }

    public void setLastGmMailSendTime(String lastGmMailSendTime) {
        this.lastGmMailSendTime = lastGmMailSendTime;
    }

    public Map<String, SysMail> getIdToSysMail() {
        return idToSysMail;
    }

    public void setIdToSysMail(Map<String, SysMail> idToSysMail) {
        this.idToSysMail = idToSysMail;
    }

    public Map<String, GmMail> getIdToGmMail() {
        return idToGmMail;
    }

    public void setIdToGmMail(Map<String, GmMail> idToGmMail) {
        this.idToGmMail = idToGmMail;
    }
}
