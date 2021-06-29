package dm.relationship.daos.itemBag;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.itemBag.ItemBag;

public class _ItemBagDao extends AbstractBaseDao<ItemBag> implements ItemBagDao {

    public _ItemBagDao() {
        super(ItemBag.class);
    }
}
