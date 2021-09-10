package drama.gameServer.features.actor.login.utils;

import dm.relationship.base.MagicWords_Mongodb;
import dm.relationship.logServer.base.PlayerLog;
import dm.relationship.logServer.base.RoomLog;
import dm.relationship.logServer.daos.LogDaoContainer;
import dm.relationship.logServer.daos.playerLoginLog.PlayerLoginLogDao;
import dm.relationship.logServer.daos.playerLogoutLog.PlayerLogoutLogDao;
import dm.relationship.logServer.daos.roomCreateLog.RoomCreateLogDao;
import dm.relationship.logServer.daos.roomQuitLog.RoomQuitLogDao;
import dm.relationship.logServer.pojos.PlayerLoginLog;
import dm.relationship.logServer.pojos.PlayerLogoutLog;
import dm.relationship.logServer.pojos.RoomCreateLog;
import dm.relationship.logServer.pojos.RoomOtherLog;
import dm.relationship.logServer.pojos.RoomQuitLog;
import dm.relationship.topLevelPojos.player.Player;
import drama.gameServer.features.actor.room.pojo.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.mongoDB.interfaces.MongoDBClient;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import ws.common.utils.di.GlobalInjector;

import java.util.Date;
import java.util.List;

public class LogHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogHandler.class);
    private static final MongoDBClient MONGO_DB_CLIENT = GlobalInjector.getInstance(MongoDBClient.class);
    private static final PlayerLoginLogDao PLAYER_LOGIN_LOG_DAO = LogDaoContainer.getDao(PlayerLoginLog.class);
    private static final PlayerLogoutLogDao PLAYER_LOGOUT_LOG_DAO = LogDaoContainer.getDao(PlayerLogoutLog.class);
    private static final RoomCreateLogDao ROOM_CREATE_LOG_DAO = LogDaoContainer.getDao(RoomCreateLog.class);
    private static final RoomQuitLogDao ROOM_QUIT_LOG_DAO = LogDaoContainer.getDao(RoomQuitLog.class);


    static {
        PLAYER_LOGIN_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
        PLAYER_LOGOUT_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
        ROOM_CREATE_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
        ROOM_QUIT_LOG_DAO.init(MONGO_DB_CLIENT, MagicWords_Mongodb.TopLevelPojo_All_Logs);
    }

    public static void playerLoginLog(Player player) {
        PlayerLoginLog log = new PlayerLoginLog();
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

    public static void roomCreateLog(Room room) {
        RoomCreateLog roomCreateLog = new RoomCreateLog();
        _setRoomLogInfo(room, roomCreateLog);
        ROOM_CREATE_LOG_DAO.insertIfExistThenReplace(roomCreateLog);
    }

    public static void _setRoomLogInfo(Room room, RoomLog roomLog) {
        roomLog.setRoomId(room.getRoomId());
        roomLog.setPlayerId(room.getMasterId());
        roomLog.setPlayerName(room.getMasterName());
        roomLog.setSimpleId(room.getSimpleRoomId());
        roomLog.setDramaId(room.getDramaId());
        roomLog.setDramaName(room.getDramaName());
        Date date1 = WsDateUtils.dateToFormatDate(room.getCreateAt(), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss);
        roomLog.setCreateAtDate(Integer.valueOf(WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd)));
        roomLog.setCreateAtTime(Integer.valueOf(WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.HHmmss)));
    }


    public static void roomQuitLog(Room room, long qutiTime) {
        RoomQuitLog roomQuitLog = new RoomQuitLog(qutiTime);
        _setRoomLogInfo(room, roomQuitLog);
        ROOM_QUIT_LOG_DAO.insertIfExistThenReplace(roomQuitLog);
    }

    public static void roomOtherLog(Room room, Player player, String action, List<TupleCell<String>> args) {
        RoomOtherLog roomOtherLog = new RoomOtherLog(action, args);
        _setRoomOtherLog(room, player, action, args);
    }

    private static void _setRoomOtherLog(Room room, Player player, String action, List<TupleCell<String>> args) {

    }
}
