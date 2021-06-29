package dm.relationship.utils;

import dm.relationship.base.DmCloneable;
import dm.relationship.exception.DmBaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Created by lee on 17-4-5.
 */
public class CloneUtils {

    /**
     * clone 对象
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T cloneObject(T t) {
        if (t == null) {
            return null;
        } else if (isWrapClass(t) || t instanceof Enum || t instanceof String) { // 基本类型 或者 字符串 或者 枚举
            return t;
        } else if (t instanceof DmCloneable) {
            DmCloneable<T> tmp = (DmCloneable<T>) t;
            return tmp.clone();
        } else {
            throw new NotDmCloneableClassException(t.getClass());
        }
    }

    /**
     * clone list
     *
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> List<T> cloneList(List<T> tList) {
        List<T> ts = new ArrayList<>();
        for (T t : tList) {
            ts.add(cloneObject(t));
        }
        return ts;
    }

    /**
     * clone HashMap
     *
     * @param kvMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> cloneHashMap(Map<K, V> kvMap) {
        Map<K, V> map = new HashMap<>();
        for (Entry<K, V> entry : kvMap.entrySet()) {
            map.put(cloneObject(entry.getKey()), cloneObject(entry.getValue()));
        }
        return map;
    }

    /**
     * clone HashMap
     *
     * @param kvMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, List<V>> cloneKListVHashMap(Map<K, List<V>> kvMap) {
        Map<K, List<V>> map = new HashMap<>();
        for (Entry<K, List<V>> entry : kvMap.entrySet()) {
            map.put(cloneObject(entry.getKey()), cloneList(entry.getValue()));
        }
        return map;
    }

    /**
     * clone LinkedHashMap
     *
     * @param kvMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> LinkedHashMap<K, V> cloneLinkedHashMap(LinkedHashMap<K, V> kvMap) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        for (Entry<K, V> entry : kvMap.entrySet()) {
            map.put(cloneObject(entry.getKey()), cloneObject(entry.getValue()));
        }
        return map;
    }

    public static List<String> cloneStringList(List<String> tList) {
        return cloneList(tList);
    }

    public static List<Integer> cloneIntegerList(List<Integer> tList) {
        return cloneList(tList);
    }

    public static <T extends Enum> List<T> cloneEnumList(List<T> tList) {
        return cloneList(tList);
    }

    public static <T extends DmCloneable<T>> List<T> cloneWsCloneableList(List<T> tList) {
        return cloneList(tList);
    }

    /**
     * 只能clone value
     *
     * @param kvMap
     * @param <K>   K 为基本类型 包括 Enum
     * @param <V>
     * @return
     */
    public static <K, V extends DmCloneable<V>> Map<K, V> cloneWsCloneableMap(HashMap<K, V> kvMap) {
        return cloneHashMap(kvMap);
    }


    /**
     * 只能clone value
     *
     * @param kvMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends DmCloneable<V>> LinkedHashMap<K, V> cloneWsCloneableLinkedHashMap(LinkedHashMap<K, V> kvMap) {
        return cloneLinkedHashMap(kvMap);
    }


    /**
     * 是否是包装类型
     *
     * @param value
     * @return
     */
    public static boolean isWrapClass(Object value) {
        Objects.requireNonNull(value);
        return isWrapClass(value.getClass());
    }

    /**
     * 是否是包装类型
     *
     * @param clzz
     * @return
     */
    public static boolean isWrapClass(Class<?> clzz) {
        Objects.requireNonNull(clzz);
        try {
            Class<?> clzz1 = (Class<?>) clzz.getField("TYPE").get(null);
            return clzz1.isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static class NotDmCloneableClassException extends DmBaseException {
        private static final long serialVersionUID = 2569704983776101353L;

        public NotDmCloneableClassException(Class<?> clzz) {
            super("类:[" + clzz + "] 未实现接口:" + DmCloneable.class.getName());
        }
    }
}
