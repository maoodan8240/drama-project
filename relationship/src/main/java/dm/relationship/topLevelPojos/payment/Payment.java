package dm.relationship.topLevelPojos.payment;

import dm.relationship.topLevelPojos.PlayerTopLevelPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 17-5-8.
 */
public class Payment extends PlayerTopLevelPojo {
    private static final long serialVersionUID = -2394148713369879351L;
    private Map<Integer, Integer> paymentIdToTimes = new HashMap<>();          // paymentId -- 购买的次数
    private List<Integer> hasBuyVipLvGift = new ArrayList<>();                 // hasBuyVipLvGift
    private int payTs;                                                         // 累计充值的次数

    public Payment() {
    }

    public Payment(String playerId) {
        super(playerId);
    }


    public Map<Integer, Integer> getPaymentIdToTimes() {
        return paymentIdToTimes;
    }

    public void setPaymentIdToTimes(Map<Integer, Integer> paymentIdToTimes) {
        this.paymentIdToTimes = paymentIdToTimes;
    }

  
    public List<Integer> getHasBuyVipLvGift() {
        return hasBuyVipLvGift;
    }

    public void setHasBuyVipLvGift(List<Integer> hasBuyVipLvGift) {
        this.hasBuyVipLvGift = hasBuyVipLvGift;
    }

    public int getPayTs() {
        return payTs;
    }

    public void setPayTs(int payTs) {
        this.payTs = payTs;
    }
}
