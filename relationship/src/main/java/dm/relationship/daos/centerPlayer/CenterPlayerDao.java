package dm.relationship.daos.centerPlayer;

import ws.common.mongoDB.interfaces.BaseDao;
import drama.protos.EnumsProtos.PlatformTypeEnum;
import dm.relationship.topLevelPojos.centerPlayer.CenterPlayer;

import java.util.List;

public interface CenterPlayerDao extends BaseDao<CenterPlayer> {

    /**
     * 查询CenterPlayer
     *
     * @param platformType 平台
     * @param outerRealmId 服
     * @param platformUid  第三方uid
     * @return
     */
    CenterPlayer findCenterPlayer(PlatformTypeEnum platformType, int outerRealmId, String platformUid);

    /**
     * 查询CenterPlayer
     *
     * @param gameId 游戏Id
     * @return
     */
    CenterPlayer findCenterPlayer(String gameId);

    /**
     * 查询CenterPlayer
     *
     * @param simpleId 简单Id
     * @return
     */
    CenterPlayer findCenterPlayer(Integer simpleId);


    /**
     * 添加到白名单
     *
     * @param simpleId
     * @return
     */
    boolean addToWhiteList(Integer simpleId);

    /**
     * 添加到黑名单
     *
     * @param simpleId
     * @return
     */
    boolean addToBlackList(Integer simpleId);

    /**
     * 从白名单移除
     *
     * @param simpleId
     * @return
     */
    boolean removeFromWhiteList(Integer simpleId);

    /**
     * 从黑名单移除
     *
     * @param simpleId
     * @return
     */
    boolean removeFromBlackList(Integer simpleId);

    /**
     * 踢出内存
     *
     * @param simpleId time
     * @return
     */
    boolean lockPlayer(Integer simpleId, int time);


    /**
     * 所有白名单中的玩家
     *
     * @return
     */
    List<CenterPlayer> queryAllWhiteList();

    /**
     * 所有黑名单中的玩家
     *
     * @return
     */
    List<CenterPlayer> queryAllBlackList();

    List<CenterPlayer> queryAllLockPlayerList();
}
