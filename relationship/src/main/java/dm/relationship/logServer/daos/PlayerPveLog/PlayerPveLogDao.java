package dm.relationship.logServer.daos.PlayerPveLog;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.logServer.pojos.PlayerPveLog;

import java.util.List;

/**
 * Created by zww on 8/10/16.
 */
public interface PlayerPveLogDao extends BaseDao<PlayerPveLog> {
    /**
     * 查询时间范围内Pve分布情况
     *
     * @param createAtTime
     * @param endDate
     * @return
     */
    List<Integer> findPveByDate(String createAtTime, String endDate, String platformType, int orid);
}
