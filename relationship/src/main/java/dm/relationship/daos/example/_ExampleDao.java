package dm.relationship.daos.example;

import ws.common.mongoDB.implement.AbstractBaseDao;
import dm.relationship.topLevelPojos.example.Example;

public class _ExampleDao extends AbstractBaseDao<Example> implements ExampleDao {

    public _ExampleDao() {
        super(Example.class);
    }
}
