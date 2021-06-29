package dm.relationship.daos.sdk.realm;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.topLevelPojos.sdk.realm.OuterToInnerRealmList;

import java.util.List;

public interface OuterToInnerRealmListDao extends BaseDao<OuterToInnerRealmList> {

    OuterToInnerRealmList findByOuterRealmId(int outerRealmId);

    List<OuterToInnerRealmList> findByInnerRealmId(int innerRealmId);

    List<Integer> findAllCanUseOuterRealmIdsByOuterRealmId(int outerRealmId);

    List<OuterToInnerRealmList> findByGameServerRole(String gameServerRole);
}
