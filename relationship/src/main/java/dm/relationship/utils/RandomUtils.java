package dm.relationship.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellUtils;
import ws.common.utils.random.MersenneTwister;
import dm.relationship.base.IdAndCount;
import dm.relationship.exception.RandomDropNumIllegalArgumentException;
import dm.relationship.topLevelPojos.common.Iac;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtils.class);
    private static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();

    /**
     * 将TupleListCell转化为权重掉落对象
     * 格式：id:num:weight,id:num:weight,id:num:weight...
     *
     * @param tupleList
     * @return
     */
    public static List<RandomUtils.Entity<Iac>> parseTupleList(TupleListCell<Integer> tupleList) {
        List<RandomUtils.Entity<Iac>> list = new ArrayList<>();
        if (CellUtils.isEmptyCell(tupleList)) {
            return list;
        }
        for (TupleCell<Integer> tuple : tupleList.getAll()) {
            int weight = tuple.get(TupleCell.THIRD);
            if (weight <= 0) {
                continue;
            }
            Iac iac = IdAndCount.parse(tuple).getIac();
            list.add(new RandomUtils.Entity(weight, iac));
        }
        return list;
    }

    public static <T extends Serializable> Entity<T> random(List<Entity<T>> list) {
        if (!isValid(list)) {
            return null;
        }
        int count = list.size();
        List<Integer> rateLadder = _parseRateLadder(list);
        int maxRandomNumber = rateLadder.get(count - 1);

        int r = 0;
        try {
            r = SecureRandom.getInstance("SHA1PRNG").nextInt(maxRandomNumber);
        } catch (Exception e) {// 捕获异常
            LOGGER.error("SecureRandom 随机算法执行异常!", e);
            r = new Random().nextInt(maxRandomNumber);
        }
        for (int i = 0; i < count; i++) {
            if (r < rateLadder.get(i)) {
                return list.get(i);
            }
        }
        return null;
    }

    public static <T extends Serializable> boolean isValid(List<Entity<T>> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        return list.stream().allMatch(x -> {
            return x.weight > 0;
        });
    }

    /**
     * <table>
     * <tr>
     * <td>Entity Weight</td>
     * <td>10</td>
     * <td>20</td>
     * <td>100</td>
     * </tr>
     * <tr>
     * <td>Rate Ladder</td>
     * <td>10</td>
     * <td>30</td>
     * <td>130</td>
     * </tr>
     * <tr>
     * <td>Selected Condition</td>
     * <td>[0,10)</td>
     * <td>[10,30)</td>
     * <td>[30,130)</td>
     * </tr>
     * </table>
     *
     * @param list
     * @return
     */
    private static <T extends Serializable> List<Integer> _parseRateLadder(List<Entity<T>> list) {
        List<Integer> rateLadder = new ArrayList<>();
        int base = 0;
        for (Entity<?> entity : list) {
            int weight = entity.weight;
            rateLadder.add(base + weight);
            base += weight;
        }
        return rateLadder;
    }

    public static class Entity<T extends Serializable> implements Serializable {
        private static final long serialVersionUID = 3274286963641165807L;
        private int weight;
        private T object;
        private Object args;

        public Entity() {
        }

        public Entity(int weight, T object) {
            this.weight = weight;
            this.object = object;
        }

        public Entity(int weight, T object, Object args) {
            this.weight = weight;
            this.object = object;
            this.args = args;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }

        public void setArgs(Object args) {
            this.args = args;
        }

        public Object getArgs() {
            return args;
        }
    }

    /**
     * 百分之几的概率
     *
     * @param prob
     * @return
     */
    public static boolean isDropByPercent(int prob) {
        int rs = MERSENNE_TWISTER.nextInt(100) + 1;
        if (rs <= prob) {
            return true;
        }
        return false;
    }

    /**
     * [min,max] 输出两个区间的随机值
     *
     * @param min >=0 且 <= max
     * @param max >=0
     * @return
     */
    public static int dropBetweenTowNum(int min, int max) {
        if (min > max) {
            throw new RandomDropNumIllegalArgumentException("min=" + min + "  >  max=" + max + " , 非法！");
        }
        if (min < 0) {
            throw new RandomDropNumIllegalArgumentException("min=" + min + " < 0 , 非法！");
        }
        if (max < 0) {
            throw new RandomDropNumIllegalArgumentException("max=" + max + " < 0 , 非法！");
        }
        return MERSENNE_TWISTER.nextInt(max + 1 - min) + min;
    }

    /**
     * 几分之一的概率是否发生
     *
     * @param base
     */
    public static boolean isDropAFractionOfBase(int base) {
        return isDropPartsFractionOfBase(1, base);
    }

    /**
     * 几分之几的概率是否发生，如：1/5 7/9
     *
     * @param fraction
     * @param base
     * @return
     */
    public static boolean isDropPartsFractionOfBase(long fraction, long base) {
        if (base <= 0) {
            throw new RuntimeException("几分之几概率参数错误，base 不能<=0 !");
        }
        if (fraction <= 0) {
            return false;
        }
        if (fraction >= base) {
            return true;
        }
        return MERSENNE_TWISTER.nextLong(base) < fraction;
    }

    /**
     * 几分之几的概率是否发生，如：1/5 7/9
     *
     * @param fraction
     * @param base
     * @return
     */
    public static boolean isDropPartsFractionOfBase(int fraction, int base) {
        return isDropPartsFractionOfBase((long) fraction, (long) base);
    }
}
