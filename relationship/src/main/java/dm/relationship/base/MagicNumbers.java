package dm.relationship.base;

public class MagicNumbers {

    /**
     * 每个服最小的SimpleId
     */
    public static final int MIN_SIMPLEID = 1000000;
    /**
     * 随机的基数值
     */
    public static final int RANDOM_BASE_VALUE = 10000;

    /**
     * 玩家操作的中间状态超时时间 单位： 秒
     */
    public static final int PLAYERIO_INTERMEDIATE_STATUS_INTERVAL_TIME = 30 * 1000;

    /**
     * akka 消息请求超时时间， 单位： 秒
     */
    public static final int AKKA_TIME_OUT = 15;

    /**
     * DM-GatewayServer 初始化 个数
     */
    public static final int AKKA_MESSAGE_TRANSFER_COUNT = 200;

    /**
     * DM-MongodbRedisServer 初始化 个数
     */
    public static final int AkkaPojoGetterActorCount = 100;

    /**
     * 玩家下线N小时后，从redis中移除玩家的数据
     */
    public static final int RemovePlayerDataFromRedisAfterLogoutNHour = 24 * 2;


    /**
     * gm邮件最大cache数量
     */
    public static final int GMMAIL_MAX_CACHE = 50;

    /**
     * 模拟充值的固定OrderId
     */
    public static final String SIMULATE_RECHARGE_ORDER_ID = "SIMULATERECHARGE";

    /**
     * 获取itemId的大类型 舍去前4位
     * 100010231 / 100000000  -> 1
     * 200011232 / 100000000  -> 20
     */
    public static final int ITEM_ID_PREFIX_DIVISOR = 1000;


    /**
     * 最大次数，用于做无限次数用
     */
    public static final int MAX_TIMES = 99999999;

    /**
     * 默认数字1
     */
    public static final int DEFAULT_NEGATIVE_ONE = -1;
    public static final int DEFAULT_ZERO = 0;
    public static final int DEFAULT_ONE = 1;


    public static final int MONEY_ID = 1;
    /**
     * 特殊的时间点
     */
    public static final int SPECIAL_HOUR_TIME_9 = 9;
    public static final int SPECIAL_HOUR_TIME_12 = 12;
    public static final int SPECIAL_HOUR_TIME_18 = 18;
    public static final int SPECIAL_HOUR_TIME_21 = 21;
    public static final int SPECIAL_HOUR_TIME_23 = 23;

    /**
     * 多字节起始的ASCII
     */
    public static final int CHINA_COUNT = 128;


    /**
     * 耳环ID 写死
     */
    public static final int ERARINGS_ID = 51;

    /**
     * 98kItemId
     */
    public static final int WEAPON_98K_ID = 21;
    /**
     * 勃朗宁ItemId
     */
    public static final int WEAPON_BROWNING_ID = 25;
    /**
     * 一级甲ItemId
     */
    public static final int ONE_LV_ARMOR_ID = 27;
    /**
     * 二级甲ItemId
     */
    public static final int TWO_LV_ARMOR_ID = 28;
    /**
     * 孤城剧本ID
     */
    public static final int DRMAAID_103 = 103;
    /**
     * 98K子弹
     */
    public static final int WEAPON_98K_BULLET_ID = 22;
   
}
