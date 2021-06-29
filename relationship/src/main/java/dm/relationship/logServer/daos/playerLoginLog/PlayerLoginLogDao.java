package dm.relationship.logServer.daos.playerLoginLog;

import ws.common.mongoDB.interfaces.BaseDao;
import dm.relationship.logServer.pojos.PlayerLoginLog;
import dm.relationship.topLevelPojos.data.DayRemain;

import java.util.List;

/**
 * Created by zww on 8/10/16.
 */
public interface PlayerLoginLogDao extends BaseDao<PlayerLoginLog> {
    /**
     * 查询今日新增
     */
    int findNewPlayer();

    /**
     * 查询今日活跃设备
     *
     * @return
     */
    int findActiveDevice();

    /**
     * 查询今日活跃帐号
     *
     * @return
     */
    int findActiveAccount();


    DayRemain findRemainByDate(String date, String platformType, int orid);


    /**
     * 按日期查询今日活跃设备
     *
     * @return
     */
    int findActiveDeviceByDate(String date, String platformType, int orid);

    /**
     * 按日期查询今日活跃帐号
     *
     * @return
     */
    int findActiveAccountByDate(String date, String platformType, int orid);


    List<String> findNewPlayerByDate(String date, String platformType, int orid);
}
