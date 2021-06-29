package dm.relationship.daos.commonCreatedTargets;

import ws.common.mongoDB.interfaces.BaseDao;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import dm.relationship.topLevelPojos.common.CommonCreatedTargets;

public interface CommonCreatedTargetsDao extends BaseDao<CommonCreatedTargets> {

    /**
     * targetNames中是否包含topLevelPojo.getClass().getSimpleName()
     *
     * @param topLevelPojo
     * @return
     */
    boolean containsTopLevelPojo(TopLevelPojo topLevelPojo);


    /**
     * targetNames中是否包含topLevelPojoSimpleName
     *
     * @param topLevelPojoSimpleName
     * @return
     */
    boolean containsTopLevelPojo(String topLevelPojoSimpleName);


    /**
     * targetNames中添加topLevelPojo.getClass().getSimpleName()
     *
     * @param topLevelPojo
     */
    boolean addTopLevelPojo(TopLevelPojo topLevelPojo);

    /**
     * targetNames中添加topLevelPojoSimpleName
     *
     * @param topLevelPojoSimpleName
     * @return
     */
    boolean addTopLevelPojo(String topLevelPojoSimpleName);
}
