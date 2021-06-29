package dm.relationship.gm;

import dm.relationship.topLevelPojos.mailCenter.GmMail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GmMailList implements Serializable {
    private static final long serialVersionUID = -6267349682204802817L;
    private List<Integer> simpleIds = new ArrayList<>();
    private GmMail gmMail;

    public List<Integer> getSimpleIds() {
        return simpleIds;
    }

    public void setSimpleIds(List<Integer> simpleIds) {
        this.simpleIds = simpleIds;
    }

    public GmMail getGmMail() {
        return gmMail;
    }

    public void setGmMail(GmMail gmMail) {
        this.gmMail = gmMail;
    }
}
