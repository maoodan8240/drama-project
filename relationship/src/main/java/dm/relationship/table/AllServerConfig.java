package dm.relationship.table;

public class AllServerConfig {
    /**
     * PlayerInit:默认送的武将-草稚京
     */
    public static final AllServerConfig PlayerInit_Default_Hero_TpId_1 = new AllServerConfig(1001);
    /**
     * PlayerInit:默认送的武将-...
     */
    public static final AllServerConfig PlayerInit_Default_Hero_TpId_2 = new AllServerConfig(1002);
    /**
     * PlayerInit:玩家初始体力
     */
    public static final AllServerConfig PlayerInit_Energy = new AllServerConfig(1003);
    /**
     * PlayerInit:玩家初始金币
     */
    public static final AllServerConfig PlayerInit_Money = new AllServerConfig(1004);
    /**
     * Pve:章节宝箱奖励领取星级需求
     */
    public static final AllServerConfig Pve_Rewards_NeedStarLevel = new AllServerConfig(3001);
    /**
     * Pve:星级判定。此为公式： HeroDieNum死亡武将的个数 EnterHeroNum出场武将的个数
     */
    public static final AllServerConfig Pve_StarLevel_Judge = new AllServerConfig(3002);
    /**
     * Pve:重置关卡攻打的次数
     */
    public static final AllServerConfig Pve_Reset_Stage_Attack_Times = new AllServerConfig(3003);
    /**
     * Pve：扫荡开启等级
     */

    public static final AllServerConfig Pve_Mopup_OpenLevel = new AllServerConfig(3004);
    /**
     * Pve：开始普通战斗扣除体力
     */
    public static final AllServerConfig Pve_Begin_Normal_DeductionEnergy = new AllServerConfig(3006);
    /**
     * Pve：开始精英战斗扣除体力
     */
    public static final AllServerConfig Pve_Begin_Elite_DeductionEnergy = new AllServerConfig(3007);
    /**
     * 名人堂：开启等级
     */
    public static final AllServerConfig HOF_OpenLevel = new AllServerConfig(3008);
    /**
     * 名人堂：好感度初始经验
     */
    public static final AllServerConfig HOF_Favor_Exp_Defualt = new AllServerConfig(3009);
    /**
     * 名人堂：好感度等级步长
     */
    public static final AllServerConfig HOF_Favor_Level_Step = new AllServerConfig(3010);
    /**
     * 名人堂：好感度经验步长
     */
    public static final AllServerConfig HOF_Favor_Exp_Step = new AllServerConfig(3011);
    /**
     * 天赋功能开启等级
     */
    public static final AllServerConfig Talen_OpenLevel = new AllServerConfig(3100);
    /**
     * 竞技场开启等级
     */
    public static final AllServerConfig Pvp_Open_Level = new AllServerConfig(3101);
    /**
     * 竞技场战后冷却时间（战后时间戳+这个时间）单位：毫秒
     */
    public static final AllServerConfig Pvp_CD = new AllServerConfig(3102);
    /**
     * 竞技场免费刷新次数
     */
    public static final AllServerConfig Pvp_Free_Fresh_Times = new AllServerConfig(3103);
    /**
     * 竞技场每日挑战次数
     */
    public static final AllServerConfig Pvp_Challenge_Times = new AllServerConfig(3104);
    /**
     * 竞技场清除CD的消耗
     */
    public static final AllServerConfig Pvp_Clean_CD_Money = new AllServerConfig(3105);
    /**
     * 竞技场挑战获得积分
     */
    public static final AllServerConfig Pvp_Integral_Max = new AllServerConfig(3106);
    /**
     * 竞技场挑战获得积分
     */
    public static final AllServerConfig Pvp_Integral_Min = new AllServerConfig(3107);
    /**
     * 竞技场挑战获得积分的规则（第一个是玩家等级，第二个是vip等级，满足了就可以获得max的积分奖励）
     */
    public static final AllServerConfig Pvp_Integral_Role = new AllServerConfig(3108);
    /**
     * 竞技场刷新排名规则（每个Tuple代表随机百分比上下限，71:79之间随机一个数字，然后当做百分比乘上当前排名，出来的排名就是刷新出来第一个位置的排名）
     */
    public static final AllServerConfig Pvp_Refresh_Role = new AllServerConfig(3109);
    /**
     * 竞技场膜拜奖励
     */
    public static final AllServerConfig Pvp_Worship_Rewards = new AllServerConfig(3110);
    /**
     * 竞技场：每日结算的最大排名
     */
    public static final AllServerConfig Pvp_DaliyCalcuMaxRank = new AllServerConfig(3111);
    /**
     * 终极试炼扫荡玩家等级可以扫荡到的层数
     */
    public static final AllServerConfig UltimateTest_MopUp_PlayerLvToLevel = new AllServerConfig(3200);
    /**
     * 终极试炼玩家通关后可以扫荡到的层数，Vip开启这个功能的等级在Vip表中
     */
    public static final AllServerConfig UltimateTest_MopUp_VipLevel = new AllServerConfig(3201);
    /**
     * 终极试炼战斗关卡基础分（公式：试炼基础积分=70+战队等级*层数/10）
     */
    public static final AllServerConfig UltimateTest_Base_Score = new AllServerConfig(3202);
    /**
     * 天赋重置消耗钻石
     */
    public static final AllServerConfig Talent_Rest_Vip_Money = new AllServerConfig(3300);
    /**
     * Equipment:装备最大的精炼等级
     */
    public static final AllServerConfig Equipment_Max_Refining_Level = new AllServerConfig(4000);
    /**
     * Heros:所有的阶级
     */
    public static final AllServerConfig Heros_All_Class_Level = new AllServerConfig(5000);
    /**
     * Player:当前开放的最大等级
     */
    public static final AllServerConfig Player_CurMaxLv = new AllServerConfig(6010);
    /**
     * Player:当前开放的最大VIP等级
     */
    public static final AllServerConfig Player_Max_VIPLv = new AllServerConfig(6011);
    /**
     * Player:重命名消耗（钻石）
     */
    public static final AllServerConfig Player_Rename_Consume = new AllServerConfig(6012);
    /**
     * Player:重命名免费的次数
     */
    public static final AllServerConfig Player_Rename_FreeTimes = new AllServerConfig(6013);
    /**
     * 抽卡系统-免费抽卡的时间间隔-单位为秒
     */
    public static final AllServerConfig PickCards_FreeTime_Gap = new AllServerConfig(10300);
    /**
     * 抽卡系统-单次普通抽卡赠送的金币
     */
    public static final AllServerConfig PickCards_AddMoney_OneSimplePick = new AllServerConfig(10301);
    /**
     * 抽卡系统-单次高级抽卡赠送的金币
     */
    public static final AllServerConfig PickCards_AddMoney_OneAdvancedPick = new AllServerConfig(10302);
    /**
     * 抽卡系统-高级抽卡-有折扣的每日单次抽取次数点:折扣
     */
    public static final AllServerConfig PickCards_AdvancedPick_Discount_Times_Point = new AllServerConfig(10303);
    /**
     * 抽卡系统-免费抽卡间隔CD消失的等级
     */
    public static final AllServerConfig PickCards_NoCD_NeedPlayerLv = new AllServerConfig(10304);
    /**
     * 装备系统-升品的等级点
     */
    public static final AllServerConfig Equipment_QualityLevelPoint = new AllServerConfig(20100);
    /**
     * 战斗系统-斩杀
     */
    public static final AllServerConfig Battle_FeatureValue_Behead = new AllServerConfig(50001);
    /**
     * 战斗系统-致命
     */
    public static final AllServerConfig Battle_FeatureValue_Fatal = new AllServerConfig(50002);
    /**
     * 战斗系统-不屈
     */
    public static final AllServerConfig Battle_FeatureValue_Unyielding = new AllServerConfig(50003);
    /**
     * 武将系统-最大技能点
     */
    public static final AllServerConfig Heros_Max_Skill_Point = new AllServerConfig(50100);
    /**
     * 武将系统-每恢复一个技能点需要的时间，单位：毫秒
     */
    public static final AllServerConfig Heros_Skill_Point_Regain_Time = new AllServerConfig(50101);
    /**
     * 武将系统-战魂位置解锁需要的品级：紫色:位置,紫色+2:位置,紫色+4:位置,橙色+1:位置。其中位置的值引用于WarSoulPositionEnum
     */
    public static final AllServerConfig Heros_Spirit_Unlock_Need_Color = new AllServerConfig(50102);
    /**
     * 武将系统-战魂升级消耗的金币比例，一点经验消耗的金币
     */
    public static final AllServerConfig Heros_WarSoul_Lv_Up_Consume_Ratio = new AllServerConfig(50103);
    /**
     * 武将系统-最大战魂等级
     */
    public static final AllServerConfig Heros_Max_WarSoul_Lv = new AllServerConfig(50104);
    /**
     * 武将系统-最大星级
     */
    public static final AllServerConfig Heros_Max_Star_Lv = new AllServerConfig(50105);
    /**
     * 武将系统-技能升级需要的星级
     */
    public static final AllServerConfig Heros_Skill_LvUp_NeedStar = new AllServerConfig(50106);
    /**
     * 武将系统-技能解锁需要的星级
     */
    public static final AllServerConfig Heros_Skill_UnlocSkill_NeedStar = new AllServerConfig(50107);
    /**
     * 武将系统-购买技能点的消耗（技能点数量:总的价格）
     */
    public static final AllServerConfig Heros_Skill_BuySkillPointConsume = new AllServerConfig(50108);
    /**
     * 武将系统-购买战魂经验需要的钻石（经验数量:总的价格）
     */
    public static final AllServerConfig Heros_WarSoul_BuyExpRatio = new AllServerConfig(50109);
    /**
     * 武将系统-一次购买获得的技能点
     */
    public static final AllServerConfig Heros_OneTimeBuy_GetSkillPoint = new AllServerConfig(50110);
    /**
     * 装备系统-装备位置ABCD升品需要的装备等级
     */
    public static final AllServerConfig Equipment_ABCD_UpQuality_Need_Lv = new AllServerConfig(50200);
    /**
     * 装备系统-装备位置EF升品需要的装备等级
     */
    public static final AllServerConfig Equipment_EF_UpQuality_Need_Lv = new AllServerConfig(50201);
    /**
     * 装备系统-徽章/秘籍升级消耗的金币比例，一点经验消耗的金币
     */
    public static final AllServerConfig Equipment_EF_Lv_Up_Consume_Ratio = new AllServerConfig(50202);
    /**
     * 装备系统-最大星级
     */
    public static final AllServerConfig Equipment_Max_Star_Lv = new AllServerConfig(50203);
    /**
     * 装备系统-将星消耗的固定VipMoney
     */
    public static final AllServerConfig Equipment_Reduce_Star_Lv_VipMoney = new AllServerConfig(50204);
    /**
     * 装备系统-N个竞技场币:N钻石数量
     */
    public static final AllServerConfig Equipment_Buy_RES_ARENA_MONEY = new AllServerConfig(50205);
    /**
     * 装备系统-N个试炼币:N钻石数量
     */
    public static final AllServerConfig Equipment_Buy_RES_TEST_MONEY = new AllServerConfig(50206);
    /**
     * 任务系统-体力任务的id和结束时间点，（上午11点）
     */
    public static final AllServerConfig Missions_Energy_IdAndEndTime_9 = new AllServerConfig(50300);
    /**
     * 任务系统-体力任务的id和结束时间点，（下午5点）
     */
    public static final AllServerConfig Missions_Energy_IdAndEndTime_12 = new AllServerConfig(50301);
    /**
     * 任务系统-体力任务的id和结束时间点，（下午20点）
     */
    public static final AllServerConfig Missions_Energy_IdAndEndTime_18 = new AllServerConfig(50302);
    /**
     * 任务系统-体力任务的id和结束时间点，（下午23点）
     */
    public static final AllServerConfig Missions_Energy_IdAndEndTime_21 = new AllServerConfig(50303);
    /**
     * 任务系统-体力任务的所有ids
     */
    public static final AllServerConfig Missions_Energy_Ids = new AllServerConfig(50304);
    /**
     * 商店系统-商店自动刷新的时间的整小时点
     */
    public static final AllServerConfig Shops_AutoRefeshTimeHour = new AllServerConfig(50400);
    /**
     * 商店系统-装备碎片刷新的时间间隔（单位秒）
     */
    public static final AllServerConfig Shops_AwakeFragmentRefeshInterval = new AllServerConfig(50401);
    /**
     * 商店系统-神秘商店持续时间（单位秒）
     */
    public static final AllServerConfig Shops_Mysterious_DurationTime = new AllServerConfig(50402);
    /**
     * 好友系统-每日可以领取体力的次数
     */
    public static final AllServerConfig Friends_GetEnergy_DaliyTimes = new AllServerConfig(50500);
    /**
     * 社团开启等级
     */
    public static final AllServerConfig Open_Guild_Lv = new AllServerConfig(60001);
    /**
     * 创建社团功能开启等级
     */
    public static final AllServerConfig Create_Guild_Lv = new AllServerConfig(60002);
    /**
     * 创建社团消耗钻石
     */
    public static final AllServerConfig Create_Guild_Vip_Money = new AllServerConfig(60003);
    /**
     * 加入社团等级限制设置
     */
    public static final AllServerConfig Guild_Join_Limit_Lv = new AllServerConfig(60004);
    /**
     * 社团研究所升级消耗钻石(钻石:得到经验:社团币)
     */
    public static final AllServerConfig Guild_Research_Vip_Money_Consume = new AllServerConfig(60005);
    /**
     * 社团研究所升级消耗金币(金币:得到经验:社团币)
     */
    public static final AllServerConfig Guild_Research_Money_Consume = new AllServerConfig(60006);
    /**
     * 社团研究所每日最大研究升级次数
     */
    public static final AllServerConfig Guild_Max_Research_Times = new AllServerConfig(60007);
    /**
     * 社团每日邮件数量
     */
    public static final AllServerConfig Guild_Mails_Count = new AllServerConfig(60008);
    /**
     * 社团每日抢红包最大次数
     */
    public static final AllServerConfig Guild_Grab_RedBag_Day_Times = new AllServerConfig(60009);
    /**
     * 社团成员每日发红包次数
     */
    public static final AllServerConfig Guild_Player_Send_RedBag_Count = new AllServerConfig(60010);
    /**
     * 社团训练所主动加速奖励
     */
    public static final AllServerConfig Guild_Tainer_Accelerate_Reward = new AllServerConfig(60011);
    /**
     * 体力-一次购买可以获得的体力
     */
    public static final AllServerConfig Energy_OneTimesBuyCanGet = new AllServerConfig(60100);
    /**
     * 体力-每X分钟涨Y点体力(单位分钟)
     */
    public static final AllServerConfig Energy_Auto_IncreaseSpeed = new AllServerConfig(60101);
    /**
     * 体力-最大体力
     */
    public static final AllServerConfig Energy_MaxEnergy = new AllServerConfig(60102);
    /**
     * 活动-购买基金的消耗(花费钻石)
     */
    public static final AllServerConfig Activity_BuyFund = new AllServerConfig(70001);
    /**
     * 活动-购买基金，需要的玩家等级
     */
    public static final AllServerConfig Activity_BuyFund_NeedPlayerLv = new AllServerConfig(70002);
    /**
     * 活动-购买基金，需要的玩家VIP等级
     */
    public static final AllServerConfig Activity_BuyFund_NeedPlayerVipLv = new AllServerConfig(70003);


    private int id;

    public AllServerConfig(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}