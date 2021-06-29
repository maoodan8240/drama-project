package dm.relationship.daos.paymentOrder;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.topLevelPojos.paymentOrder.PaymentOrder;

import java.util.List;

public interface PaymentOrderDao extends BaseDao<PaymentOrder> {

    PaymentOrder findOneByInnerOrderId(String innerOrderId);

    /**
     * 今日新增玩家中充值的玩家数量
     *
     * @return
     */
    int findNewPlayerNewPaymentCount();

    /**
     * 充值总人数
     *
     * @return
     */
    int findPaymentCount();

    /**
     * 充值总流水
     *
     * @return
     */
    int findPaymentSum();


    /**
     * 根据条件查询充值记录
     *
     * @param endDate
     * @param platformType
     * @param orid
     * @return
     */
    List<PaymentOrder> findRechargeRecordByCondition(String endDate, String platformType, int orid);

    /**
     * 根据simpleId查询充值记录
     *
     * @param simpleId
     * @return
     */
    List<PaymentOrder> findReChargeRecordBySimpleId(int simpleId);


    /**
     * 今日新增玩家中充值的玩家数量
     *
     * @param date
     * @return
     */
    int findNewPlayerNewPaymentCountByDate(String date, String platformType, int orid);

    /**
     * 充值总人数
     *
     * @param date
     * @return
     */
    int findPaymentCountByDate(String date, String platformType, int orid);

    /**
     * 充值总流水
     *
     * @param date
     * @return
     */
    int findPaymentSumByDate(String date, String platformType, int orid);


}
