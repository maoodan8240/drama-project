package dm.relationship.logServer.daos;

import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.mongoDB.interfaces.BaseDao;
import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.classProcess.ClassFinder;
import dm.relationship.exception.BusinessLogicMismatchConditionException;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class AllLogDaoClassHolder {
    private static List<Class<? extends BaseDao>> allDaoClass = null;
    // dao的接口 对应的 实现类
    private static Map<Class<? extends BaseDao>, Class<? extends BaseDao>> intefaceClassToInstanceClass = new HashMap<>();
    // TopLevelPojo 对应的 dao
    private static Map<Class<? extends TopLevelPojo>, Class<? extends BaseDao>> topLevelPojoClassToInstanceClass = new HashMap<>();

    static {
        allDaoClass = ClassFinder.getAllAssignedClass(BaseDao.class, LogDaoPackageHolder.class);
        init();
    }

    private static void init() {
        intefaceClassToInstanceClass.clear();
        for (Class<? extends BaseDao> clzz1 : allDaoClass) {
            if (clzz1.isInterface()) {
                for (Class<? extends BaseDao> clzz2 : allDaoClass) {
                    if (!clzz2.isInterface() && clzz1.isAssignableFrom(clzz2)) { // 具体的实现类
                        if (!Modifier.isAbstract(clzz2.getModifiers())) { // 过滤抽象的实现类
                            intefaceClassToInstanceClass.put(clzz1, clzz2);
                            try {
                                AbstractBaseDao dao = (AbstractBaseDao) clzz2.newInstance();
                                topLevelPojoClassToInstanceClass.put(dao.getCls(), clzz1); // 获得dao对应的pojo
                            } catch (Exception e) {
                                String msg = String.format("实例化dao出错！interfaceClass=%s, instanceClass=%s !", clzz1, clzz2);
                                throw new BusinessLogicMismatchConditionException(msg);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public static Map<Class<? extends BaseDao>, Class<? extends BaseDao>> getIntefaceClassToInstanceClass() {
        return intefaceClassToInstanceClass;
    }

    public static Map<Class<? extends TopLevelPojo>, Class<? extends BaseDao>> getTopLevelPojoClassToInstanceClass
            () {
        return topLevelPojoClassToInstanceClass;
    }

    public static boolean containsTopLevelPojoClass(Class<? extends TopLevelPojo> topLevelPojoClass) {
        return topLevelPojoClassToInstanceClass.containsKey(topLevelPojoClass);
    }

    public static void play() {
        for (Entry<Class<? extends BaseDao>, Class<? extends BaseDao>> e : intefaceClassToInstanceClass.entrySet()) {
            System.out.println(e.getKey() + "  -->  " + e.getValue());
        }
    }

    public static void main(String[] args) {
        play();
    }
}
