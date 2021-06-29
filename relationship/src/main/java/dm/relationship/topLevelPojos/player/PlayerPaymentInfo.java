package dm.relationship.topLevelPojos.player;

import java.io.Serializable;

public class PlayerPaymentInfo implements Serializable {
    private static final long serialVersionUID = -8956810826700379775L;

    private String date;
    private String innerOrderId;
    private int num;

    public PlayerPaymentInfo() {
    }

    public PlayerPaymentInfo(String date, String innerOrderId, int num) {
        this.date = date;
        this.innerOrderId = innerOrderId;
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInnerOrderId() {
        return innerOrderId;
    }

    public void setInnerOrderId(String innerOrderId) {
        this.innerOrderId = innerOrderId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
