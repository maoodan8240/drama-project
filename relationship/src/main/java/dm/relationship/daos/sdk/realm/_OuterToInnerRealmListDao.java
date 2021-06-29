package dm.relationship.daos.sdk.realm;

import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.sdk.realm.OuterToInnerRealmList;

import java.util.ArrayList;
import java.util.List;

public class _OuterToInnerRealmListDao extends AbstractBaseDao<OuterToInnerRealmList> implements OuterToInnerRealmListDao {

    public _OuterToInnerRealmListDao() {
        super(OuterToInnerRealmList.class);
    }


    @Override
    public OuterToInnerRealmList findByOuterRealmId(int outerRealmId) {
        Document document = new Document("outerRealmId", outerRealmId);
        return findOne(document);
    }

    @Override
    public List<OuterToInnerRealmList> findByInnerRealmId(int innerRealmId) {
        Document document = new Document("innerRealmId", innerRealmId);
        return findAll(document);
    }

    @Override
    public List<Integer> findAllCanUseOuterRealmIdsByOuterRealmId(int outerRealmId) {
        List<Integer> allOuterId = new ArrayList<>();
        OuterToInnerRealmList outerToInnerRealmList = findByOuterRealmId(outerRealmId);
        if (outerToInnerRealmList == null) {
            return allOuterId;
        }
        List<OuterToInnerRealmList> list = findByInnerRealmId(outerToInnerRealmList.getInnerRealmId());
        for (OuterToInnerRealmList realmList : list) {
            allOuterId.add(realmList.getOuterRealmId());
        }
        return allOuterId;
    }


    @Override
    public List<OuterToInnerRealmList> findByGameServerRole(String gameServerRole) {
        Document document = new Document("gameServerRole", gameServerRole);
        return findAll(document);
    }
}
