package drama.gameServer.features.manager;

/**
 * Created by lee on 2021/9/8
 */

@FunctionalInterface
public interface Action {

    String handle(String funcName, String args);
}
