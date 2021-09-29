package drama.gameServer.features.actor.config.enums;

import dm.relationship.exception.BusinessLogicMismatchConditionException;
import drama.protos.CommonProtos;

public enum ConfigTableNameEnums {
    STAGE(CommonProtos.Cm_Common_Config.Action.STAGE, "Table_Stage"),                   //      小剧场
    ACTER(CommonProtos.Cm_Common_Config.Action.ACTER, "Table_Acter"),                   //      角色表
    SUBJECT(CommonProtos.Cm_Common_Config.Action.SUBJECT, "Table_Subject"),             //      选角题目
    RESULT(CommonProtos.Cm_Common_Config.Action.RESULT, "Table_Result"),                //      选角答案对应角色ID
    SCENELIST(CommonProtos.Cm_Common_Config.Action.SCENELIST, "Table_SceneList"),       //      剧本列表以及详情
    RUNDOWN(CommonProtos.Cm_Common_Config.Action.RUNDOWN, "Table_RunDown"),             //      剧本流程
    MURDER(CommonProtos.Cm_Common_Config.Action.MURDER, "Table_Murder"),                //      投凶表
    SOLO(CommonProtos.Cm_Common_Config.Action.SOLO, "Table_Solo"),                      //      独白
    SOLODRAMA(CommonProtos.Cm_Common_Config.Action.SOLODRAMA, "Table_SoloDrama"),       //      独白剧本
    DRAFT(CommonProtos.Cm_Common_Config.Action.DRAFT, "Table_Draft"),                   //      轮抽
    SUBACTER(CommonProtos.Cm_Common_Config.Action.SUBACTER, "Table_SubActer"),          //      子角色
    SECRETTALK(CommonProtos.Cm_Common_Config.Action.SECRETTALK, "Table_SecretTalk"),    //      私聊
    SUBMURDER(CommonProtos.Cm_Common_Config.Action.SUBMURDER, "Table_SubMurder"),       //      子剧本投凶
    ITEM(CommonProtos.Cm_Common_Config.Action.ITEM, "Table_Item"),                      //      道具
    AUCTION(CommonProtos.Cm_Common_Config.Action.AUCTION, "Table_Auction"),             //      拍卖


    NULL(null, "");

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

    public static String getTableNameByAction(CommonProtos.Cm_Common_Config.Action action, int dramaId) {
        for (ConfigTableNameEnums value : ConfigTableNameEnums.values()) {
            if (value.action == action) {
                if (value.action == CommonProtos.Cm_Common_Config.Action.SCENELIST) {
                    return value.getTableName();
                } else {
                    return value.getTableName() + "_" + dramaId;
                }
            }
        }
        String msg = String.format("TableName 解析失败 action=%s", action);
        throw new BusinessLogicMismatchConditionException(msg);
    }
}
