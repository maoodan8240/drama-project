package drama.gameServer.features.actor.message;

import akka.actor.Address;
import dm.relationship.base.OneToOneConcurrentMapWithAttachment;
import dm.relationship.base.OneToOneConcurrentMapWithoutAttachment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.interfaces.Connection;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class ConnectionContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionContainer.class);
    private static final OneToOneConcurrentMapWithAttachment<String, Connection, ConnectionAttachment> FLAG_TO_CONN = new OneToOneConcurrentMapWithAttachment<>();
    private static final OneToOneConcurrentMapWithoutAttachment<String, String> GAMEID_TO_FLAG = new OneToOneConcurrentMapWithoutAttachment<>();
    private static final ConcurrentHashMap<String, Connection> PLAYERID_CONNECTION = new ConcurrentHashMap<>();

    /**
     * flag 和 conn 必须一一对应
     *
     * @param flag
     * @param conn
     */
    public static void put(String flag, Connection conn) {
        LOGGER.debug("添加新的连接对应关系 flag={}  <--> conn={} .", flag, conn);
        FLAG_TO_CONN.removeKV(flag, conn);
        FLAG_TO_CONN.put(flag, conn);
    }


    public static String getPlayerIdByConnection(Connection connection) {
        String playerId = null;
        for (Entry<String, Connection> entries : PLAYERID_CONNECTION.entrySet()) {
            if (entries.getValue().toString().equals(connection.toString())) {
                playerId = entries.getKey();
                break;
            }
        }
        return playerId;
    }

    /**
     * 覆盖GameId的数据
     *
     * @param gameId
     * @param flagNew
     */
    public static void put(String flagNew, String gameId, Address address) {
        LOGGER.debug("添加新的GameId={} flagNew={} .", gameId, flagNew);
        if (containsGameIdInGameIdToFlag(gameId)) {
            String flagOld = getFlagByGameIdInGameIdToFlag(gameId);
            Connection connNew = getConnByFlagInFlagToConn(flagNew);
            if (!flagNew.equals(flagOld)) {
                Connection connOld = FLAG_TO_CONN.removeByK(flagOld);
                LOGGER.debug("flagOld={} flagNew={} 不一样, 新的连接->新的登录, 移除connOld={} connNew={} .", flagOld, flagNew, connOld, connNew);
                if (connOld != null) {
                    connOld.close();
                }
            } else {
                LOGGER.debug("flagOld={} connNew={} flagNew={} 相同, 老的连接->新的登录 .", flagOld, connNew, flagNew);
            }
        }
        GAMEID_TO_FLAG.removeKV(gameId, flagNew);
        GAMEID_TO_FLAG.put(gameId, flagNew);
        FLAG_TO_CONN.addAttachment(flagNew, new ConnectionAttachment(address, gameId, flagNew));
    }


    public static void putPlayerIdToConn(String playerId, Connection conn) {
        LOGGER.debug("添加新的连接对应关系  playerId={}  <--> conn={} .", playerId, conn);
        PLAYERID_CONNECTION.put(playerId, conn);
    }


    public static boolean containsConnByPlayerId(String playerId) {
        return PLAYERID_CONNECTION.containsKey(playerId);
    }


    public static boolean containsConnection(Connection connection) {
        return PLAYERID_CONNECTION.containsValue(connection);
    }

   
    public static void removeConnByPlayerId(String playerId) {
        if (!StringUtils.isEmpty(playerId)) {
            LOGGER.debug("playerId={},断开了, 移除连接", playerId);
            if (containsConnByPlayerId(playerId)) {
                Connection conn = PLAYERID_CONNECTION.remove(playerId);
                LOGGER.debug("remove playerId ={} <--> conn={} ", playerId, conn);
            } else {
                LOGGER.debug("remove playerId ={} conn is not exist", playerId);
            }
        }

    }

    public static ConnectionAttachment remove(Connection connOld) {
        LOGGER.debug("connOld={} 断开了,移除相关信息.", connOld);
        if (containsConnInFlagToConn(connOld)) {
            String flagOld = getFlagByConnInFlagToConn(connOld);
            ConnectionAttachment oldAttachment = getAttachmentByConnInFlagToConn(connOld);
            FLAG_TO_CONN.removeByV(connOld);
            if (oldAttachment == null) { // 没有登录成功
                LOGGER.debug("remove connOld={} <---> flagOld={}, 不存在gameIdOld，connOld上面玩家没有成功登录 ", connOld, flagOld);
                return null;
            }
            String gameIdOld = oldAttachment.getGameId();
            String flagNew = getFlagByGameIdInGameIdToFlag(gameIdOld);
            LOGGER.debug("remove connOld={} <---> flagOld={} <---> gameIdOld={}  <---> flagNew={} ", connOld, flagOld, gameIdOld, flagNew);
            if (flagOld.equals(flagNew)) {  // 同一个GameId相同的Conn
                LOGGER.debug("移除的Conn={} flag={} 对应的玩家不在线了.", connOld, flagOld);
                return oldAttachment.clone();
            }
        }
        return null;
    }

    // ============================== FLAG_TO_CONN start ================================
    public static boolean containsFlagInFlagToConn(String flag) {
        return FLAG_TO_CONN.containsK(flag);
    }

    public static boolean containsConnInFlagToConn(Connection conn) {
        return FLAG_TO_CONN.containsV(conn);
    }


    public static String getFlagByConnInFlagToConn(Connection conn) {
        return FLAG_TO_CONN.getKByV(conn);
    }

    public static Connection getConnByFlagInFlagToConn(String flag) {
        return FLAG_TO_CONN.getVByK(flag);
    }

    public static ConnectionAttachment getAttachmentByFlagInFlagToConn(String flag) {
        return FLAG_TO_CONN.getAttachmentByK(flag);
    }

    public static ConnectionAttachment getAttachmentByConnInFlagToConn(Connection conn) {
        return FLAG_TO_CONN.getAttachmentByV(conn);
    }


    public static boolean containsAttachmentByConnInFlagToConn(Connection conn) {
        return FLAG_TO_CONN.containsAttachmentByV(conn);
    }
    // ============================== FLAG_TO_CONN end ================================

    //

    // ============================== GAMEID_TO_FLAG start ================================

    public static boolean containsGameIdInGameIdToFlag(String gameId) {
        return GAMEID_TO_FLAG.containsK(gameId);
    }

    public static boolean containsFlagInGameIdToFlag(String flag) {
        return GAMEID_TO_FLAG.containsV(flag);
    }


    public static String getGameIdByFlagInGameIdToFlag(String flag) {
        return GAMEID_TO_FLAG.getKByV(flag);
    }

    public static String getFlagByGameIdInGameIdToFlag(String gameId) {
        return GAMEID_TO_FLAG.getVByK(gameId);
    }
// ============================== GAMEID_TO_FLAG end ================================
}