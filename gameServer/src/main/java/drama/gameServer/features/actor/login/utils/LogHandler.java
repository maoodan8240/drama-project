package drama.gameServer.features.actor.login.utils;

import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.logServer.base.PlayerLog;
import dm.relationship.logServer.daos.LogDaoContainer;
import dm.relationship.logServer.daos.playerLoginLog.PlayerLoginLogDao;
import dm.relationship.logServer.daos.playerLogoutLog.PlayerLogoutLogDao;
import dm.relationship.logServer.pojos.PlayerLoginLog;
import dm.relationship.logServer.pojos.PlayerLogoutLog;
import dm.relationship.topLevelPojos.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import ws.common.utils.di.GlobalInjector;

import java.util.Date;

public class LogHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogHandler.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final PlayerLoginLogDao PLAYER_LOGIN_LOG_DAO = LogDaoContainer.getDao(PlayerLoginLog.class);
    private static final PlayerLogoutLogDao PLAYER_LOGOUT_LOG_DAO = LogDaoContainer.getDao(PlayerLogoutLog.class);


    static {
        PLAYER_LOGIN_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
        PLAYER_LOGOUT_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
    }

    public static void playerLoginLog(Player player) {
        PlayerLoginLog log = new PlayerLoginLog("");
        _setPlayerLogInfo(player, log);
        PLAYER_LOGIN_LOG_DAO.insertIfExistThenReplace(log);
    }

    private static void _setPlayerLogInfo(Player player, PlayerLog playerLog) {
        playerLog.setPid(player.getPlayerId());
        playerLog.setSid(player.getBase().getSimpleId());
        Date date1 = WsDateUtils.dateToFormatDate(player.getAccount().getCreateAt(), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss);
        playerLog.setCreateAtDate(Integer.valueOf(WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd)));
        playerLog.setCreateAtTime(Integer.valueOf(WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.HHmmss)));
    }

    /**
     * 玩家登出
     *
     * @param player
     * @param lsinTime
     */
    public static void playerLogoutLog(Player player, long lsinTime) {
        PlayerLogoutLog log = new PlayerLogoutLog(lsinTime);
        _setPlayerLogInfo(player, log);
        PLAYER_LOGOUT_LOG_DAO.insertIfExistThenReplace(log);
    }
}
