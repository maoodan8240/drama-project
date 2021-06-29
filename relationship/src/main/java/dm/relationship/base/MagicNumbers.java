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
     * WS-GatewayServer 初始化 个数
     */
    public static final int AKKA_MESSAGE_TRANSFER_COUNT = 200;

    /**
     * WS-MongodbRedisServer 初始化 个数
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
     * 获取itemId的大类型
     * 100010231 / 100000000  -> 1
     * 200011232 / 100000000  -> 20
     */
    public static final int ITEM_ID_PREFIX_DIVISOR = 100000000;

    /**
     * 移除的id缓存的大小
     */
    public static final int REMOVED_ITEM_ID_COUNT = 50;

    /**
     * 用道具抽取忍者或者装备时，一次最大使用量
     */
    public static final int PickCardUseItemMaxCountOneTime = 10;

    /**
     * 关卡默认的最大的星级
     */
    public static final int PVE_STAGE_MAX_STAR_LEVEL = 3;


    /**
     * 默认值：角色默认等级
     */
    public static final int DefaultValue_Role_Level = 1;
    /**
     * 默认值：武将等级
     */
    public static final int DefaultValue_Hero_Level = 1;
    /**
     * 默认值：武将品资等级
     */
    public static final int DefaultValue_Hero_Quality_Level = 1;
    /**
     * 默认值：武将天命等级
     */
    public static final int DefaultValue_Hero_Destiny_Level = 0;

    /**
     * 装备默认等级
     */
    public static final int DefaultValue_Equipment_Level = 1;

    /**
     * 装备默认的星级等级
     */
    public static final int DefaultValue_Equipment_Star_Level = 0;

    /**
     * 装备默认的品级等级
     */
    public static final int DefaultValue_Equipment_Quality_Level = 1;

    /**
     * 默认技能等级
     */
    public static final int DefaultValue_Skill_Level = 1;
    /**
     * 默认战魂等级
     */
    public static final int DefaultValue_Spirit_Level = 1;
    /**
     * 扫荡一次最大的次数
     */
    public static final int PVE_STAGE_MOPUP_MAX_ATTACK_TIMES = 10;

    /**
     * 单抽
     */
    public static final int PICKCARD_ONE_PICK_CARD = 1;
    /**
     * 十连抽
     */
    public static final int PICKCARD_TEN_PICK_CARD = 10;
    /**
     * 抽卡-金币抽卡
     */
    public static final int PICKCARD_MONEY_ID_1 = 1;
    /**
     * 抽卡-钻石抽卡
     */
    public static final int PICKCARD_VIPMONEY_ID_2 = 2;

    /**
     * 100连抽
     */
    public static final int PICKCARD_HUNDRED_PICK_CARD = 100;

    /**
     * 终极试炼战斗完成最高星数
     */
    public static final int ULTIMATE_TEST_MAX_STAR = 3;

    /**
     * 最大次数，用于做无限次数用
     */
    public static final int MAX_TIMES = 99999999;

    /**
     * 14资质
     */
    public static final int APTITUDE_14 = 14;
    /**
     * 13资质
     */
    public static final int APTITUDE_13 = 13;
    /**
     * 12资质
     */
    public static final int APTITUDE_12 = 12;
    /**
     * 11资质
     */
    public static final int APTITUDE_11 = 11;


    /**
     * 好感度阶段1-7
     */
    public static final int FAVOR_CLASS_ONE = 1;
    public static final int FAVOR_CLASS_TWO = 2;
    public static final int FAVOR_CLASS_THREE = 3;
    public static final int FAVOR_CLASS_FOUR = 4;
    public static final int FAVOR_CLASS_FIVE = 5;
    public static final int FAVOR_CLASS_SIX = 6;
    public static final int FAVOR_CLASS_SEVEN = 7;

    /**
     * 星级0-7
     */
    public static final int STAR_NUM_0 = 0;
    public static final int STAR_NUM_1 = 1;
    public static final int STAR_NUM_2 = 2;
    public static final int STAR_NUM_3 = 3;
    public static final int STAR_NUM_4 = 4;
    public static final int STAR_NUM_5 = 5;
    public static final int STAR_NUM_6 = 6;
    public static final int STAR_NUM_7 = 7;

    /**
     * 默认数字1
     */
    public static final int DEFAULT_NEGATIVE_ONE = -1;
    public static final int DEFAULT_ZERO = 0;
    public static final int DEFAULT_ONE = 1;
    public static final int DEFAULT_TWO = 2;
    public static final int DEFAULT_THREE = 3;
    public static final int DEFAULT_FOUR = 4;
    public static final int DEFAULT_FIVE = 5;
    public static final int DEFAULT_TEN = 10;
    public static final int DEFAULT_100 = 100;


    /**
     * 竞技场-开始随机敌人的名次分割点
     */
    public static final int ARENA_BEGIN_RANDOM_RANK = 51;

    /**
     * 积分奖励-竞技场
     */
    public static final int POINTREWARD_TYPE_ARENA_POINT = 1;
    /**
     * 积分奖励-试炼塔
     */
    public static final int POINTREWARD_TYPE_ULTIMATETEST_POINT = 2;

    /**
     * 竞技场排行榜查询前50名
     */
    public static final int ARENA_RANK_QUERY_TOP_50 = 50;

    /**
     * 每日奖励-竞技场排名
     */
    public static final int DAILYREWARD_TYPE_ARENA_RANK = 1;
    /**
     * 每日奖励-竞技场积分
     */
    public static final int DAILYREWARD_TYPE_ARENA_POINT = 2;


    /**
     * 玩家每天产生的数据最大存储天数
     */
    public static final int MAX_STORE_DALIY_DATA_DAYS = 8;

    /**
     * 特殊的时间点
     */
    public static final int SPECIAL_HOUR_TIME_9 = 9;
    public static final int SPECIAL_HOUR_TIME_12 = 12;
    public static final int SPECIAL_HOUR_TIME_18 = 18;
    public static final int SPECIAL_HOUR_TIME_21 = 21;
    public static final int SPECIAL_HOUR_TIME_23 = 23;

    /**
     * 不打折扣
     */
    public static final int WITHOUT_DISCOUNT = 100;
    /**
     * 最低折扣1折
     */
    public static final int LOWEST_DISCOUNT = 10;
    /**
     * 百分比
     */
    public static final int PERCENT_NUMBER = 100;

    /**
     * Pve扫荡星数
     */
    public static final int PVE_MOPUP_STAR = 3;
    /**
     * 战队人数
     */
    public static final int MAX_TEAM_NUMBER = 6;
    /**
     * 爬塔 得星倍率
     */
    public static final int ULTIMATE_TEST_EASY_STAR = 1;
    public static final int ULTIMATE_TEST_NORMAL_STAR = 3;
    public static final int ULTIMATE_TEST_HARD_STAR = 5;

    /**
     * 爬塔 战斗星数算分倍率
     */
    public static final int ULTIMATE_TEST_ONE_STAR_MULTI = 1;
    public static final Double ULTIMATE_TEST_TWO_STAR_MULTI = 1.5;
    public static final Double ULTIMATE_TEST_THREE_STAR_MULTI = 2.5;

    /**
     * 爬塔 战斗关卡难度基础分倍率
     */
    public static final int ULTIMATE_TEST_EASY_MULTI = 1;
    public static final Double ULTIMATE_TEST_NORMAL_MULTI = 1.5;
    public static final int ULTIMATE_TEST_HARD_MULTI = 2;

    /**
     * 试炼塔昨日历史减去的层数，可以得到今日可以跳到的最高层数
     */
    public static final int ULTIMATE_MATH_YESTODAY_HIGHT_LEVEL = 12;
    /**
     * 试炼塔胜利前10个格斗家增加200怒气
     */
    public static final int ULTIMATE_WIN_HERO_COUNT = 10;
    public static final int ULTIMATE_WIN_ANGER = 200;


    /**
     * 一次最大查询排行榜的数量
     */
    public static final int ONE_TIME_QUERY_RANK_COUNT = 20;


    /**
     * 系统邮件的Id
     */
    public static final int SYSTEM_MAIL_ID_1 = 1;
    public static final int SYSTEM_MAIL_ID_2 = 2;
    public static final int SYSTEM_MAIL_ID_ARENA_DALIY_RANK_REWARDS = 3;
    public static final int SYSTEM_MAIL_ID_EQUIPMENT_REDUCESTAR = 4;


    /**
     * 终极试炼开启收费宝箱最大次数
     */
    public static final int ULTIMATE_TEST_OPEN_BOX_MAX_TIMES = 20;

    /**
     * 每个聊天频道最大的存储数量
     */
    public static final int EACH_CHAT_TYPE_MAX_STORE_MSG_COUNT = 40;

    /**
     * 玩家最后登录存储数量
     */
    public static final int PLAYER_LAST_LOGIN_STORE_COUNT = 350;


    /**
     * 月卡可以使用的天数
     */
    public static final int MONTH_CARD_SUM_DAYS = 30;

    /**
     * 一次赠送体力的数量
     */
    public static final int ONE_TIME_GIVE_ENERGY_NUM = 2;

    /**
     * 每夜页好友个数
     */
    public static final int ONE_PAGE_FRIEND_NUM = 10;

    /**
     * 随机推荐好友的个数
     */
    public static final int RANDOM_RECOMMEND_FRIEND_NUM = 10;

    /**
     * 最大好友数量
     */
    public static final int MAX_FRIENDS_NUM = 100;

    /**
     * monster id 不能超过的值
     */
    public static final int MAX_MONSTER_ID = 1000000;

    /**
     * 系统广播模版ID
     */
    public static final int SYSTEM_BROADCAST_ID_1 = 1;
    public static final int SYSTEM_BROADCAST_ID_2 = 2;
    public static final int SYSTEM_BROADCAST_RECRUIT = 10;

    /**
     * 邮件失效天数
     */
    public static final int MAIL_EXPIRE_DAYS = 7;
    /**
     * 终极试炼 历史积分计算百分比
     */
    public static final double ULTIMATE_PERCENTAGE_SCORE = 0.8;
    /**
     * 社团人数研究院每个人每日贡献的经验上限
     */
    public static final int GUILD_MEMBER_BASE_EXP = 250;
    /**
     * pve3扫
     */
    public static final int PVE_THREE_SWEEP = 3;
    /**
     * pve10连扫
     */
    public static final int PVE_TEN_SWEEP = 10;
    /**
     * pve50连扫
     */
    public static final int PVE_FIFTY_SWEEP = 50;

    /**
     * 社团训练所标记人数最大值
     */
    public static int MAX_STAMP_COUNT = 2;
    /**
     * 社团训练所最大加速次数
     */
    public static int ACCELERATETIMES_MAX_TIMES = 6;
    /**
     * 社团训练所最大被动加速次数
     */
    public static int BE_ACCELERATETIMES_MAX_TIMES = 6;
    /**
     * 多字节起始的ASCII
     */
    public static final int CHINA_COUNT = 128;

    /**
     * 魂匣单抽
     */
    public static final int SOULBOX_ONE_PICK_CARD = 1;
    /**
     * 魂匣五连抽
     */
    public static final int SOULBOX_FIVE_PICK_CARD = 5;
}
