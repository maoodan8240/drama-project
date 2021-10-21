package drama.gameServer.utils;

import drama.gameServer.GameServerPackageHolder;
import ws.common.utils.classProcess.ClassFinder;
import ws.common.utils.mc.controler.Controler;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCtrlClassHolder {
    private static List<Class<? extends Controler>> allCtrlClass = null;
    // 接口 对应的 实现类
    private static Map<Class<? extends Controler>, Class<? extends Controler>> intefaceClassToInstanceClass = new HashMap<>();

    static {
        allCtrlClass = ClassFinder.getAllAssignedClass(Controler.class, GameServerPackageHolder.class);
        init();
    }

    private static void init() {
        intefaceClassToInstanceClass.clear();
        for (Class<? extends Controler> clzz1 : allCtrlClass) {
            if (clzz1.isInterface()) {
                for (Class<? extends Controler> clzz2 : allCtrlClass) {
                    if (!clzz2.isInterface() && clzz1.isAssignableFrom(clzz2)) { // 具体的实现类
                        if (!Modifier.isAbstract(clzz2.getModifiers())) { // 过滤抽象的实现类
                            intefaceClassToInstanceClass.put(clzz1, clzz2);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static Map<Class<? extends Controler>, Class<? extends Controler>> getIntefaceClassToInstanceClass() {
        return intefaceClassToInstanceClass;
    }

    public static void play() {
        System.out.println("\n--------------------------------------------------------------");
        for (Map.Entry<Class<? extends Controler>, Class<? extends Controler>> e : intefaceClassToInstanceClass.entrySet()) {
            System.out.println(e.getKey() + "  -->  " + e.getValue());
        }
        System.out.println("--------------------------------------------------------------\n");
    }

    public static void main(String[] args) {
        play();
    }
}
