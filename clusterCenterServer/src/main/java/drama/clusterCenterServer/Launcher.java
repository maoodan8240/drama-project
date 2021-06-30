package drama.clusterCenterServer;

import drama.clusterCenterServer.system.actor.DmActorSystem;
import drama.clusterCenterServer.system.config.AppConfig;
import ws.common.utils.schedule.GlobalScheduler;

public class Launcher {
    public static void main(String[] args) {
        System.out.println(Launcher.class.getResource("/").getFile().toString());
        System.out.println("java.version > " + System.getProperty("java.version"));
        System.out.println("java.home > " + System.getProperty("java.home"));
        System.out.println("java.class.path > " + System.getProperty("java.class.path"));
        System.out.println("java.library.path > " + System.getProperty("java.library.path"));
        System.out.println("os.name > " + System.getProperty("os.name"));
        System.out.println("os.arch > " + System.getProperty("os.arch"));
        System.out.println("os.version > " + System.getProperty("os.version"));
        System.out.println("user.name > " + System.getProperty("user.name"));
        System.out.println("user.home > " + System.getProperty("user.home"));
        System.out.println("user.dir > " + System.getProperty("user.dir"));

//        System.setProperty("user.dir", "");
        _init();
        _startActorSystem();
    }

    private static void _init() {
        try {
            AppConfig.init();
            GlobalScheduler.init();
        } catch (Exception e) {
            System.out.println("初始化异常！" + e);
        }
    }

    private static void _startActorSystem() {
        DmActorSystem.init();
    }
}
