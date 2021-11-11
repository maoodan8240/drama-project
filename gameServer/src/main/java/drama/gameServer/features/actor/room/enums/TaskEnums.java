package drama.gameServer.features.actor.room.enums;

import dm.relationship.exception.BusinessLogicMismatchConditionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 2021/11/3
 */
public enum TaskEnums {
    HAVEITEM("HAVEITEM"),                   //任务目标角色持有任务道具
    DEAD("DEAD"),                           //任务目标角色死亡(无论是否是自己击杀)
    KILL("KILL"),                           //任务目标角色被自己亲手击杀
    VOTEMURDER("VOTEMURDER"),               //投凶投中真凶
    ESCAPE("ESCAPE"),                       //如果自己是凶手成功脱逃
    CHOICE("CHOICE"),                       //获取任务目标角色的选择
    ALIVE("ALIVE"),                         //保证任务目标角色存活
    ;//

    private String name;

    TaskEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TaskEnums getTaskTypeByName(String name) {
        for (TaskEnums taskEnums : TaskEnums.values()) {
            if (taskEnums.getName().equals(name)) {
                return taskEnums;
            }
        }
        String msg = String.format("TaskEnums解析失败! name=%s", name);
        throw new BusinessLogicMismatchConditionException(msg);
    }

    public static List<TaskEnums> getTaskTypeListByNames(List<String> names) {
        List<TaskEnums> arr = new ArrayList<>();
        for (String name : names) {
            arr.add(getTaskTypeByName(name));
        }
        return arr;
    }
}
