package dm.relationship.topLevelPojos.paymentOrder;

import com.alibaba.fastjson.annotation.JSONField;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import drama.protos.EnumsProtos.OrderStatusEnum;
import drama.protos.EnumsProtos.PlatformTypeEnum;

import java.util.Date;

/**
 * Created by lee on 17-5-8.
 */
public class PaymentOrder implements TopLevelPojo {
    private static final long serialVersionUID = -360989839665811735L;
    @JSONField(name = "_id")
    private String orderId;                                             // ObjectId
    private String innerOrderId;                                        // 游戏内部订单号 -- UUID 生成
    private String outerOrderId;                                        // 游戏外部订单号 -- 第三方渠道提供
    private String playerId;                                            // 玩家Id
    private int simpleId;                                               // 简单Id
    private int outerRealmId;                                           // 显示服id
    private int goodId;                                                 // 购买物品的Id
    private String goodName;                                            // 购买物品的支付名称
    private int vipMoneyCount;                                          // 游戏内元宝总数
    private int rmb;                                                    // 数需RMB
    private PlatformTypeEnum platformType;                              // 渠道
    private String args;                                                // 额外参数
    private OrderStatusEnum orderStatus = OrderStatusEnum.OD_BEGIN;     // 状态
    private int createDate;                                             // 订单生成的日期          yyyyMMdd
    private int createTime;                                             // 订单生成的时间          HHmmss
    private int endDate;                                                // 订单更改为最终状态的日期  yyyyMMdd
    private int endTime;                                                // 订单更改为最终状态的时间  HHmmss
    private int pyCreateAtDate;                                         // 玩家注册-年月日         yyyyMMdd
    private int pyCreateAtTime;                                         // 玩家注册-时分秒毫秒      HHmmss

    public PaymentOrder() {
        Date date = new Date();
        this.createDate = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.yyyyMMdd));
        this.createTime = Integer.valueOf(WsDateUtils.dateToFormatStr(date, WsDateFormatEnum.HHmmss));
    }

    @Override
    public String getOid() {
        return orderId;
    }

    @Override
    public void setOid(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInnerOrderId() {
        return innerOrderId;
    }

    public void setInnerOrderId(String innerOrderId) {
        this.innerOrderId = innerOrderId;
    }

    public String getOuterOrderId() {
        return outerOrderId;
    }

    public void setOuterOrderId(String outerOrderId) {
        this.outerOrderId = outerOrderId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getVipMoneyCount() {
        return vipMoneyCount;
    }

    public void setVipMoneyCount(int vipMoneyCount) {
        this.vipMoneyCount = vipMoneyCount;
    }

    public int getRmb() {
        return rmb;
    }

    public void setRmb(int rmb) {
        this.rmb = rmb;
    }

    public PlatformTypeEnum getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformTypeEnum platformType) {
        this.platformType = platformType;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public int getSimpleId() {
        return simpleId;
    }

    public void setSimpleId(int simpleId) {
        this.simpleId = simpleId;
    }

    public int getOuterRealmId() {
        return outerRealmId;
    }

    public void setOuterRealmId(int outerRealmId) {
        this.outerRealmId = outerRealmId;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCreateDate() {
        return createDate;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getPyCreateAtDate() {
        return pyCreateAtDate;
    }

    public void setPyCreateAtDate(int pyCreateAtDate) {
        this.pyCreateAtDate = pyCreateAtDate;
    }

    public int getPyCreateAtTime() {
        return pyCreateAtTime;
    }

    public void setPyCreateAtTime(int pyCreateAtTime) {
        this.pyCreateAtTime = pyCreateAtTime;
    }
}
