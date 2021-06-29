package dm.relationship.base;

public class MagicWords_Redis {

    public static final String TopLevelPojo_Game_Prefix = "Game_";// 数据实体名字的前缀
    public static final String TopLevelPojo_All_Common_Prefix = "AllCommon_";// 数据实体名字的前缀，使用Redis中的 Strings.set 和 Strings.get

    public static final String TopLevelPojo_All_Common_KeyXX1 = "AllCommon_KeyXX1";// 数据实体名字 使用Redis中的 Hashes.hset 和 Hashes.hget
    public static final String TopLevelPojo_All_Common_KeyXX2 = "AllCommon_KeyXX2";// 数据实体名字 使用Redis中的 Hashes.hset 和 Hashes.hget
    public static final String TopLevelPojo_All_Common_KeyXX3 = "AllCommon_KeyXX3";// 数据实体名字 使用Redis中的 Hashes.hset 和 Hashes.hget


    public static final String GameServerPlayerCount = "GameServerPlayerCount";// 每个服务器上面玩家的数量

    public static final String PlayerOnWitchServerRole = "PlayerOnWitchServerRole";// 玩家在哪个服务器上面

    public static final String CenterPlayerAutoId = "CenterPlayerAutoId";// CenterPlayer自增id

    public static final String PlayerIds_Updated = "PlayerIds_Updated"; // 数据发生变动的玩家的id集合

    public static final String PlayerLogoutTime = "PlayerLogoutTime"; // 玩家最后推出游戏的时间点,登录的时候需要移除

    public static final String Rank_Challenge_Money = "Rank_Challenge_Money";//金币挑战排行

    public static final String Rank_Challenge_Exp = "Rank_Challenge_Exp";//经验挑战排行

    public static final String Rank_Challenge_Women = "Rank_Challenge_Women";//女子格斗家挑战排行

    public static final String Rank_Challenge_Ghots = "Rank_Challenge_Ghots";// 幻象挑战排行

    public static final String Rank_UltimateTest = "Rank_UltimateTest";// 幻象挑战排行
}
