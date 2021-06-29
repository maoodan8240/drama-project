package dm.relationship.base.cluster;

public class ActorSystemPath {
    /***
     * selection用于接收广播和发送,不带Selection的用于创建
     */
    /**
     * Common
     */
    public static final String DM_Common_System = "dmsystem";

    public static final String DM_Common_DMRoot = "DMRoot";
    public static final String DM_Common_Selection_DMRoot = "/user/DMRoot";

    public static final String DM_Common_ClusterListener = "clusterListener";
    public static final String DM_Common_Selection_ClusterListener = "/user/clusterListener";

    /**
     * DM-ParticularFunctionServer
     */
    public static final String DM_ThirdPartyServer_MailsCenterActor = "mailsCenterActor";
    public static final String DM_ThirdPartyServer_Selection_MailsCenterActor = DM_Common_Selection_DMRoot + "/mailsCenterActor";

    /**
     * DM_ThirdPartyServer
     */
    public static final String DM_ThirdPartyServer_PaymentCenter = "paymentCenter";
    public static final String DM_ThirdPartyServer_Selection_PaymentCenter = DM_Common_Selection_DMRoot + "/paymentCenter";

    public static final String DM_ThirdPartyServer_Payment = "payment-";
    public static final String DM_ThirdPartyServer_Selection_Payment = DM_ThirdPartyServer_Selection_PaymentCenter + "/payment-";

    public static final String DM_ThirdPartyServer_LoginCheckCenter = "loginCheckCenter";
    public static final String DM_ThirdPartyServer_Selection_LoginCheckCenter = DM_Common_Selection_DMRoot + "/loginCheckCenter";

    public static final String DM_ThirdPartyServer_LoginCheck = "loginCheck-";
    public static final String DM_ThirdPartyServer_Selection_LoginCheck = DM_ThirdPartyServer_Selection_LoginCheckCenter + "/loginCheck-";

    /**
     * DM-LoginServer
     */
    public static final String DM_LoginServer_Login = "login";
    public static final String DM_LoginServer_Selection_Login = DM_Common_Selection_DMRoot + "/login";

    /**
     * DM-GatewayServer
     */

    /**
     * DM_MongodbRedisServer
     */
    public static final String DM_MongodbRedisServer_PojoGetterManager = "pojoGetterManager";
    public static final String DM_MongodbRedisServer_Selection_PojoGetterManager = DM_Common_Selection_DMRoot + "/pojoGetterManager";
    public static final String DM_MongodbRedisServer_PojoGetter = "pojoGetter-";
    public static final String DM_MongodbRedisServer_Selection_PojoGetter = DM_MongodbRedisServer_Selection_PojoGetterManager + "/pojoGetter-";

    public static final String DM_MongodbRedisServer_PojoSaver = "pojoSaver";
    public static final String DM_MongodbRedisServer_Selection_PojoSaver = DM_Common_Selection_DMRoot + "/pojoSaver";

    public static final String DM_MongodbRedisServer_PojoRemover = "pojoRemover";
    public static final String DM_MongodbRedisServer_Selection_PojoRemover = DM_Common_Selection_DMRoot + "/pojoRemover";

    /**
     * DM-GameServer
     */
    //分发器
    public static final String DM_GameServer_MessageTransfer = "messageTransfer";
    public static final String DM_GameServer_Selection_MessageTransfer = DM_Common_Selection_DMRoot + "/messageTransfer";

    //回复器
    public static final String DM_GameServer_MessageTransferForSend = "messageTransferForSend-";
    public static final String Dm_GameServer_Selection_MessageTransferForSend = DM_GameServer_Selection_MessageTransfer + "/messageTransferForSend-";

    //接收器
    public static final String DM_GameServer_MessageTransferForReceive = "messageTransferForReceive-";
    public static final String DM_GameServer_Selection_MessageTransferForReceive = DM_GameServer_Selection_MessageTransfer + "/messageTransferForReceive-";


    public static final String DM_GameServer_Login = "login";
    public static final String DM_GameServer_Selection_Login = DM_Common_Selection_DMRoot + "/login";

    public static final String DM_GameServer_World = "world";
    public static final String DM_GameServer_Selection_World = DM_Common_Selection_DMRoot + "/world";

    //all player manager 暂未启用
    public static final String DM_GameServer_PlayerIO = "playerIO-";
    public static final String DM_GameServer_Selection_PlayerIO = DM_GameServer_Selection_World + "/playerIO-*";

    public static final String DM_GameServer_Player = "player-";
    public static final String DM_GameServer_Selection_Player = DM_GameServer_Selection_PlayerIO + "/player-*";

    //all room manager 暂未启用
    public static final String DM_GameServer_RoomCenter = "roomCenter";
    public static final String DM_GameServer_Selection_RoomCenter = DM_GameServer_Selection_World + "/roomCenter";

    //剧本房间
    public static final String DM_GameServer_Room = "room-";
    public static final String DM_GameServer_Selection_Room = DM_GameServer_Selection_World + "/room-*";

    //配置文件请求
    public static final String DM_GameServer_Config = "config";
    public static final String DM_GameServer_Selection_Config = DM_GameServer_Selection_World + "/config";

    public static final String[] DM_GameServer_Selection_All = {
            // DM_GameServer_Selection_Register, 没有业务需求不需要广播
            // DM_GameServer_Selection_PlayerIO, 没有业务需求不需要广播
            DM_GameServer_Selection_World,
            DM_GameServer_Selection_Player
    };


    /**
     * DM-ChatServer
     */
    public static final String DM_ChatServer_ChatManager = "chatManager";
    public static final String DM_ChatServer_Selection_ChatManager = DM_Common_Selection_DMRoot + "/chatManager";

    public static final String DM_ChatServer_InnerRealmChat = "innerRealmChat-";
    public static final String DM_ChatServer_Selection_InnerRealmChat = DM_ChatServer_Selection_ChatManager + "/innerRealmChat-*";

    public static final String DM_ChatServer_ChatMsgSender = "chatMsgSender-";

    /**
     * DM-LogServer
     */
    public static final String DM_LogServer_SaveLogsManager = "saveLogsManager";
    public static final String DM_LogServer_Selection_SaveLogsManager = DM_Common_Selection_DMRoot + "/saveLogsManager";
    public static final String DM_LogServer_SaveLogs = "saveLogs-";
    public static final String DM_LogServer_Selection_SaveLogs = DM_LogServer_Selection_SaveLogsManager + "/saveLogs-*";
}
