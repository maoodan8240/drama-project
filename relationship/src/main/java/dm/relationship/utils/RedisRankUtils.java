package dm.relationship.utils;

import dm.relationship.base.RedisRankAndScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.redis.RedisOpration;
import ws.common.redis.operation.bean.RedisTuple;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import ws.common.utils.di.GlobalInjector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class RedisRankUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRankUtils.class);
    private static final String REDIS_SCORE_TIME_YEAR = "2017";
    public static final int REDIS_SCORE_TIME_PRECISION = 1000000000;
    private static final RedisOpration REDIS_OPRATION = GlobalInjector.getInstance(RedisOpration.class);

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
