package dm.relationship.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.redis.RedisOpration;
import ws.common.redis.operation.In_RedisOperation;
import ws.common.redis.operation.RedisOprationEnum.SortedSets;
import ws.common.redis.operation.bean.RedisTuple;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import ws.common.utils.di.GlobalInjector;
import drama.protos.EnumsProtos.CommonRankTypeEnum;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.RedisRankAndScore;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ws.common.redis.operation.RedisOprationEnum.SortedSets.zrevrangeWithScores;


public class RedisRankUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRankUtils.class);
    private static final String REDIS_SCORE_TIME_YEAR = "2017";
    public static final int REDIS_SCORE_TIME_PRECISION = 1000000000;
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);

    /**
     * 加入排行
     *
     * @param score
     * @param memberId
     * @param outerRealmId
     * @param rankTypeEnum
     */
    public static void insertRank(long score, String memberId, int outerRealmId, CommonRankTypeEnum rankTypeEnum) {
        score = createRankScore(score);
        REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zrem.newParmBuilder().build(createRankKey(rankTypeEnum, outerRealmId), memberId)));
        REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zadd.newParmBuilder().build(createRankKey(rankTypeEnum, outerRealmId), score, memberId)));
    }


    /**
     * 删除指定的排名成员
     *
     * @param outerRealmId
     * @param rankTypeEnum
     * @param rankMin
     * @param rankMax
     */
    public static void removeFromRankByRange(int outerRealmId, CommonRankTypeEnum rankTypeEnum, int rankMin, int rankMax) {
        REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zremrangeByRank.newParmBuilder().build(createRankKey(rankTypeEnum, outerRealmId), rankMin, rankMax)));
    }


    /**
     * 删除指定的member
     *
     * @param outerRealmId
     * @param rankTypeEnum
     * @param member
     */
    public static void removeFromRankByMember(int outerRealmId, CommonRankTypeEnum rankTypeEnum, String member) {
        REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zrem.newParmBuilder().build(createRankKey(rankTypeEnum, outerRealmId), member)));
    }


    /**
     * 创建排行的RedisKey值
     *
     * @param rankTypeEnum
     * @param outReamlId
     * @return
     */
    private static String createRankKey(CommonRankTypeEnum rankTypeEnum, int outReamlId) {
        return rankTypeEnum.toString() + "_" + outReamlId;
    }

    /**
     * 获取玩家当前排名
     *
     * @param playerId
     * @param outRealmId
     * @param rankTypeEnum
     * @return 名次，0代表没有每次，1代表第一名
     */
    public static int getRank(String playerId, int outRealmId, CommonRankTypeEnum rankTypeEnum) {
        Object objResult;
        long rank = 0;
        try {
            objResult = REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zrevrank.newParmBuilder().build(createRankKey(rankTypeEnum, outRealmId), playerId)));
            rank = SortedSets.zrevrank.parseResult(objResult);
            rank = rank + 1; // redis 返回是从0开始的
        } catch (Exception e) {
            rank = 0;
            LOGGER.error("获取玩家当前排名失败！ playerId={}  outRealmId={} rankTypeEnum={} ", playerId, outRealmId, rankTypeEnum, e);
        } finally {
            return (int) rank;
        }
    }

    /**
     * 获取玩家分数
     *
     * @param playerId
     * @param outRealmId
     * @param rankTypeEnum
     * @return
     */
    public static long getScore(String playerId, int outRealmId, CommonRankTypeEnum rankTypeEnum) {
        long score = 0;
        try {
            Object objResult = REDIS_OPRATION.execute(new In_RedisOperation(SortedSets.zscore.newParmBuilder().build(createRankKey(rankTypeEnum, outRealmId), playerId)));
            score = SortedSets.zscore.parseResult(objResult).longValue();
            score = convertToRealRankScore(score);
        } catch (Exception e) {
            score = 0;
            LOGGER.error("获取玩家分数失败！ playerId={}  outRealmId={} rankTypeEnum={} ", playerId, outRealmId, rankTypeEnum, e);
        } finally {
            return score;
        }
    }


    /**
     * 获取一些玩家的分数
     *
     * @param playerIds
     * @param outRealmId
     * @param rankTypeEnum
     * @return
     */
    public static Map<String, Long> getSomePlayerIdScore(Set<String> playerIds, int outRealmId, CommonRankTypeEnum rankTypeEnum) {
        Map<String, Long> playerIdToRankValue = new LinkedHashMap<>();
        for (String playerId : playerIds) {
            long rankValue = getScore(playerId, outRealmId, rankTypeEnum);
            playerIdToRankValue.put(playerId, rankValue);
        }
        return playerIdToRankValue;
    }


    /**
     * 获取榜单上范围 [min, min+count) 的playerId & score
     *
     * @param outRealmId
     * @param rankTypeEnum
     * @param min          名次，从1开始
     * @param count
     * @return
     */
    public static List<RedisRankAndScore> getRankRangeWithScores(int outRealmId, CommonRankTypeEnum rankTypeEnum, int min, int count) {
        min = Math.max(min - 1, 0); // 从第一名开始
        int max = min + count;
        Object objResult1 = REDIS_OPRATION.execute(new In_RedisOperation(zrevrangeWithScores.newParmBuilder().build(createRankKey(rankTypeEnum, outRealmId), min, max - MagicNumbers.DEFAULT_ONE)));
        Set<RedisTuple> set = SortedSets.zrevrangeWithScores.parseResult(objResult1);
        return convertToRealRankScore(set, min);
    }


    /**
     * 组装以秒为精度的分数(sorce+秒数),目的相同分数的时间早的排名大,可以使用30年，否则会溢出
     *
     * @param score
     * @return
     */
    private static long createRankScore(long score) {
        Date date1 = WsDateUtils.dateToFormatDate(REDIS_SCORE_TIME_YEAR, WsDateFormatEnum.yyyy);
        long second = ((System.currentTimeMillis() - date1.getTime()) / 1000);
        return score * REDIS_SCORE_TIME_PRECISION + (REDIS_SCORE_TIME_PRECISION - second);
    }

    /**
     * 获取真正的分数
     *
     * @param score
     * @return
     */
    private static long convertToRealRankScore(long score) {
        return score / REDIS_SCORE_TIME_PRECISION;
    }

    /**
     * 转换集合内的排名分数为真实分数
     *
     * @param set
     * @param minRank 为redis的rank，实际排名从1开始，即实际排名为(minRank+1)
     * @return
     */
    private static List<RedisRankAndScore> convertToRealRankScore(Set<RedisTuple> set, int minRank) {
        List<RedisRankAndScore> newSet = new ArrayList<>();
        for (RedisTuple tuple : set) {
            newSet.add(new RedisRankAndScore(tuple.getMember(), (minRank + 1), convertToRealRankScore(tuple.getScore().longValue())));
            minRank++;
        }
        return newSet;
    }
}
