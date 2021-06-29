package dm.relationship.topLevelPojos;

import ws.common.mongoDB.interfaces.TopLevelPojo;
import ws.common.utils.classProcess.ClassFinder;
import dm.relationship.topLevelPojos.common.TopLevelHolder;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


public class AllTopLevelClassHolder {
    private static List<Class<? extends TopLevelPojo>> allTopLevelClass = null;
    private static List<Class<? extends TopLevelPojo>> playerAllTopLevelClass = new ArrayList<>();

    static {
        allTopLevelClass = ClassFinder.getAllAssignedClass(TopLevelPojo.class, TopLevelPackageHolder.class);
        init();
    }

    private static void init() {
        playerAllTopLevelClass.clear();
        for (Class<? extends TopLevelPojo> clzz : allTopLevelClass) {
            if (IPlayerTopLevelPojo.class.isAssignableFrom(clzz)) {
                if (!Modifier.isAbstract(clzz.getModifiers())) { // 过滤抽象的实现类
                    if (skip(clzz)) {
                        continue;
                    }
                    playerAllTopLevelClass.add(clzz);
                }
            }
        }
    }

    private static boolean skip(Class<? extends TopLevelPojo> clzz) {
        if (clzz.equals(TopLevelHolder.class) || clzz.equals(PlayerTopLevelPojo.class)) {
            return true;
        }
        return false;
    }


    public static List<Class<? extends TopLevelPojo>> getPlayerAllTopLevelClass() {
        return playerAllTopLevelClass;
    }

    public static void play() {
        System.out.println("------------------------------");
        for (Class<? extends TopLevelPojo> p : playerAllTopLevelClass) {
            System.out.println(p);
        }
        System.out.println("------------------------------");
    }

    public static void main(String[] args) {
        play();
    }
}
