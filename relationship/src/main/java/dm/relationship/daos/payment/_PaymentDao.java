package dm.relationship.daos.payment;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.payment.Payment;

public class _PaymentDao extends AbstractBaseDao<Payment> implements PaymentDao {

    public _PaymentDao() {
        super(Payment.class);
    }
}
