package dm.relationship.utils;

import dm.relationship.base.IdAndCount;
import dm.relationship.base.IdMaptoCount;
import dm.relationship.base.MagicNumbers;
import dm.relationship.base.msg.db.getter.In_DbResponse;
import dm.relationship.base.resultCode.ResultCodeEnum;
import dm.relationship.topLevelPojos.mails.Mail;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by lee on 17-4-28.
 */
public class RelationshipCommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipCommonUtils.class);


    /**
     * 设置发送时间和过期时间
     *
     * @param m
     */
    public static void setSendTimeAndExpiredTime(Mail m) {
        Date expireDate = DateUtils.addDays(new Date(), MagicNumbers.MAIL_EXPIRE_DAYS);
        m.setExpireTime(WsDateUtils.dateToFormatStr(expireDate, WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
        m.setSendTime(WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyy_MM_dd$HH_mm_ss));
    }


    /**
     * 检查db回的消息
     *
     * @param response
     */
    public static void checkDbResponse(In_DbResponse response) {
        if (response == null) {
            throw new NullPointerException("DbResponse 返回对象不能为空！");
        }
        if (response.getResultCode() != ResultCodeEnum.SUCCESS) {
            throw response.getException();
        }
    }

    /**
     * IdMaptoCount 中的所有value翻N倍
     *
     * @param idMaptoCount
     * @param n
     * @return
     */
    public static IdMaptoCount severalTimesIdMaptoCount(IdMaptoCount idMaptoCount, int n) {
        Objects.requireNonNull(idMaptoCount);
        if (n <= 0) {
            throw new IllegalArgumentException("n 必须 >= 1 n=" + n);
        }
        if (n == 1) {
            return idMaptoCount;
        }
        for (IdAndCount idAndCount : idMaptoCount.getAll()) {
            IdAndCount newIdAndCount = new IdAndCount(idAndCount.getId(), idAndCount.getCount() * n);
            idMaptoCount.replace(newIdAndCount);
        }
        return idMaptoCount;
    }

    /**
     * 当前语言环境是否是简体中文 language=zh  country=CN
     *
     * @param locale
     * @return
     */
    public static boolean isSimplifiedChinese(Locale locale) {
        if (locale.getLanguage().equals(Locale.SIMPLIFIED_CHINESE.getLanguage()) && locale.getCountry().equals(Locale.SIMPLIFIED_CHINESE.getCountry())) {
            return true;
        }
        return false;
    }

    /**
     * null 转空字符
     *
     * @param str
     * @return
     */
    public static String converNullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /**
     * 移除空的K值或者V值
     *
     * @param ori
     * @param <K>
     * @param <V>
     */
    public static <K, V> void removeNullElements(Map<K, V> ori) {
        Iterator<K> it = ori.keySet().iterator();
        while (it.hasNext()) {
            K k = it.next();
            V v = ori.get(k);
            if (k == null || v == null) {
                it.remove();
            }
        }
    }

    /**
     * 移除空的K值或者V值
     *
     * @param ori
     * @param <V>
     */
    public static <V> void removeNullElements(List<V> ori) {
        Iterator<V> it = ori.iterator();
        while (it.hasNext()) {
            V v = it.next();
            if (v == null) {
                it.remove();
            }
        }
    }



    /**
     * 日期比较 (date1 - date2 的结果)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(String date1, String date2) {
        Date d1 = WsDateUtils.dateToFormatDate(date1, WsDateFormatEnum.yyyyMMdd);
        Date d2 = WsDateUtils.dateToFormatDate(date2, WsDateFormatEnum.yyyyMMdd);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(d2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (DateUtils.MILLIS_PER_DAY);
        return Long.valueOf(between_days).intValue();
    }


    /**
     * for Map<K, List<T>>
     *
     * @param idToTLis
     * @param key
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<T> getListByKey(Map<K, List<T>> idToTLis, K key) {
        if (!idToTLis.containsKey(key)) {
            idToTLis.put(key, new ArrayList<>());
        }
        return idToTLis.get(key);
    }


    /**
     * Map<K1, Map<K2, V>>
     *
     * @param kToMap
     * @param key
     * @param <K1>
     * @param <K2>
     * @param <V>
     * @return
     */
    public static <K1, K2, V> Map<K2, V> getMapByKey(Map<K1, Map<K2, V>> kToMap, K1 key) {
        if (!kToMap.containsKey(key)) {
            kToMap.put(key, new HashMap<>());
        }
        return kToMap.get(key);
    }


    /**
     * 获取Map的value，如果不存在，则存入默认的值
     *
     * @param kTov
     * @param key
     * @param defaultValue
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V getValueByKeyFromMap(Map<K, V> kTov, K key, V defaultValue) {
        try {
            if (!kTov.containsKey(key)) {
                kTov.put(key, defaultValue);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
        return kTov.get(key);
    }


    /**
     * Map按照Value的大小排序，从小到大排序
     *
     * @param source
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueASC(Map<K, V> source) {
        return sortMapByValue(source, SortRuleEnum.ASC);
    }

    /**
     * Map按照Value的大小排序，从大到小排序
     *
     * @param source
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueESC(Map<K, V> source) {
        return sortMapByValue(source, SortRuleEnum.ESC);
    }

    /**
     * Map按照Value的大小排序
     *
     * @param source
     * @param sortRule
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> source, SortRuleEnum sortRule) {
        int resultSymbol = sortRule == SortRuleEnum.ESC ? -1 : 1;
        List<Entry<K, V>> list = new LinkedList<>(source.entrySet());
        Collections.sort(list, (o1, o2) -> {
            long[] rs = _firstCompareNullValue(o1.getValue(), o2.getValue(), new SortConditionValues<V>() {
                @Override
                public long[] compareValues(V o1, V o2) {
                    return new long[]{o1.compareTo(o2)};
                }
            });
            int compareValue = convetToCompareValue(rs[0]);
            return resultSymbol * compareValue;
        });
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }


    /**
     * Map按照Value的多条件排序
     *
     * @param source
     * @param scvs
     * @param sortRule
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> sortMapByValue(Map<K, V> source, SortConditionValues scvs, SortRuleEnum sortRule) {
        int resultSymbol = sortRule == SortRuleEnum.ESC ? -1 : 1;
        List<Entry<K, V>> list = new LinkedList<>(source.entrySet());
        Collections.sort(list, (o1, o2) -> {
            long[] values = _firstCompareNullValue(o1.getValue(), o2.getValue(), scvs);
            for (long v : values) {
                int v1 = convetToCompareValue(v);
                if (v1 == 0) {
                    continue;
                }
                return resultSymbol * v1;
            }
            return 0;
        });
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }


    /**
     * list 多条件排序
     *
     * @param tList
     * @param scvs
     * @param <T>
     */
    public static <T> void mutliConditionSort(List<T> tList, SortConditionValues scvs, SortRuleEnum sortRule) {
        int resultSymbol = sortRule == SortRuleEnum.ESC ? -1 : 1;
        Collections.sort(tList, (o1, o2) -> {
                    long[] values = _firstCompareNullValue(o1, o2, scvs);
                    for (long v : values) {
                        int v1 = convetToCompareValue(v);
                        if (v1 == 0) {
                            continue;
                        }
                        return resultSymbol * v1;
                    }
                    return 0;
                }
        );
    }

    /**
     * 先处理比较的对象有Null的情况，null 为最小的值
     * [1, 10, null, 5, 3, null, 8, null]
     * 从小到大: [null, null, null, 1, 3, 5, 8, 10]
     * 从大到小: [10, 8, 5, 3, 1, null, null, null]
     *
     * @param o1
     * @param o2
     * @param scvs
     * @param <T>
     * @return
     */
    private static <T> long[] _firstCompareNullValue(T o1, T o2, SortConditionValues scvs) {
        // o1 为排序扫描在原列表中的后者， o1 为排序扫描在原列表中的前者，
        if (o1 == null && o2 != null) {
            return new long[]{-1};
        } else if (o1 == null) { // 此时 o2 == null
            return new long[]{-1};
        } else if (o2 == null) { // 此时 o1 != null
            return new long[]{1};
        }
        // o1 != null && o2 != null
        return scvs.compareValues(o1, o2);
    }

    /**
     * 正>1 , 负>-1, 0>0
     *
     * @param a
     * @return
     */
    private static int convetToCompareValue(long a) {
        if (a > 0) {
            return 1;
        } else if (a < 0) {
            return -1;
        }
        return 0;
    }

    /**
     * 升序（从小到大） OR 降序（从大到小）
     */
    public enum SortRuleEnum {
        /**
         * 升序（从小到大）
         */
        ASC, //
        /**
         * 降序（从大到小）
         */
        ESC  //
    }

    public interface SortConditionValues<T> {

        long[] compareValues(T o1, T o2);

    }
}
