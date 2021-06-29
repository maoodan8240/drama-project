package drama.gameServer.features.actor.config.enums;

import dm.relationship.exception.BusinessLogicMismatchConditionException;
import drama.protos.CommonProtos;

public enum ConfigTableNameEnums {
    STAGE(CommonProtos.Cm_Common_Config.Action.STAGE, "Table_Stage"),                   //	小剧场
    ACTER(CommonProtos.Cm_Common_Config.Action.ACTER, "Table_Acter"),                   //	角色表
    SUBJECT(CommonProtos.Cm_Common_Config.Action.SUBJECT, "Table_Subject"),             //	选角题目
    RESULT(CommonProtos.Cm_Common_Config.Action.RESULT, "Table_Result"),                //	选角答案对应角色ID
    SCENELIST(CommonProtos.Cm_Common_Config.Action.SCENELIST, "Table_SceneList"),       //    剧本列表以及详情
    RUNDOWN(CommonProtos.Cm_Common_Config.Action.RUNDOWN, "Table_RunDown"),             //    剧本流程
    ;


    private String tableName;
    private CommonProtos.Cm_Common_Config.Action action;

    ConfigTableNameEnums(CommonProtos.Cm_Common_Config.Action action, String tableName) {
        this.action = action;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public CommonProtos.Cm_Common_Config.Action getAction() {
        return action;
    }

    public static String getTableNameByAction(CommonProtos.Cm_Common_Config.Action action) {
        for (ConfigTableNameEnums value : ConfigTableNameEnums.values()) {
            if (value.action == action) {
                return value.getTableName();
            }
        }
        String msg = String.format("TableName 解析失败 aciton=%s", action);
        throw new BusinessLogicMismatchConditionException(msg);
    }
}
