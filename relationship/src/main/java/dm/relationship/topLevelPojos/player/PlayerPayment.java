package dm.relationship.topLevelPojos.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PlayerPayment implements Serializable {
    private static final long serialVersionUID = -2228847354169190847L;

    private int vipLevel; // VIP等级
    private int vipTotalExp;// VIP的经验值
    // 充值的详细信息 日期为系统日期 年月日时分秒
    private Map<String, PlayerPaymentInfo> dateToPaymentInfo = new HashMap<String, PlayerPaymentInfo>();
    // 每日充值金额 日期为切日日期
    private Map<String, Integer> dateToPaymentNum = new HashMap<String, Integer>();

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getVipTotalExp() {
        return vipTotalExp;
    }

    public void setVipTotalExp(int vipTotalExp) {
        this.vipTotalExp = vipTotalExp;
    }

    public Map<String, PlayerPaymentInfo> getDateToPaymentInfo() {
        return dateToPaymentInfo;
    }

    public void setDateToPaymentInfo(Map<String, PlayerPaymentInfo> dateToPaymentInfo) {
        this.dateToPaymentInfo = dateToPaymentInfo;
    }

    public Map<String, Integer> getDateToPaymentNum() {
        return dateToPaymentNum;
    }

    public void setDateToPaymentNum(Map<String, Integer> dateToPaymentNum) {
        this.dateToPaymentNum = dateToPaymentNum;
    }
}
