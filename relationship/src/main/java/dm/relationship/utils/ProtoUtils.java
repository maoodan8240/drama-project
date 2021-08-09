package dm.relationship.utils;

import dm.relationship.base.IdAndCount;
import dm.relationship.base.IdMaptoCount;
import dm.relationship.topLevelPojos.common.Iac;
import dm.relationship.topLevelPojos.simplePlayer.SimplePlayer;
import drama.protos.CodesProtos.ProtoCodes.Code;
import drama.protos.CommonProtos.Sm_Common_IdAndCount;
import drama.protos.CommonProtos.Sm_Common_IdAndCountList;
import drama.protos.CommonProtos.Sm_Common_IdMaptoCount;
import drama.protos.CommonProtos.Sm_Common_IdMaptoCountList;
import drama.protos.CommonProtos.Sm_Common_Rank;
import drama.protos.CommonProtos.Sm_Common_Round;
import drama.protos.EnumsProtos;
import drama.protos.EnumsProtos.ErrorCodeEnum;
import drama.protos.MessageHandlerProtos.Response;
import drama.protos.PlayerLoginProtos.Sm_NeedReLogin;
import drama.protos.PlayerLoginProtos.Sm_NeedReLogin.Action;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.Connection;
import ws.common.utils.general.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {

    public static Response.Builder create_Response(Code code, Enum<?> action) {
        return create_Response(code, action, ErrorCodeEnum.UNKNOWN);
    }

    public static Response.Builder create_Response(Code code, Enum<?> action, ErrorCodeEnum errorCode) {
        errorCode = errorCode == null ? ErrorCodeEnum.UNKNOWN : errorCode;
        Response.Builder br = Response.newBuilder();
        br.setMsgCode(code);
        br.setResult(false);
        br.setErrorCode(errorCode);
        br.setSmMsgAction(EnumUtils.protoActionToString(action));
        return br;
    }

    public static void needReLogin(Connection connection) {
        Sm_NeedReLogin.Builder builder = Sm_NeedReLogin.newBuilder();
        builder.setAction(Action.RESP_RELOGIN);
        Response.Builder resp = ProtoUtils.create_Response(Code.Sm_NeedReLogin, Action.RESP_RELOGIN);
        resp.setResult(true);
        resp.setErrorCode(EnumsProtos.ErrorCodeEnum.UNKNOWN);
        resp.setSmNeedReLogin(builder);
        connection.send(new MessageSendHolder(resp.build(), resp.getSmMsgAction(), new ArrayList<>()));
    }

    public static Sm_Common_IdMaptoCountList create_Sm_Common_IdMaptoCountList(List<IdMaptoCount> idMaptoCounts) {
//        ExampleProtos.Sm_Example.Builder b = ExampleProtos.Sm_Example.newBuilder();
//        b.setAction(ExampleProtos.Sm_Example.Action.)
        Sm_Common_IdMaptoCountList.Builder b = Sm_Common_IdMaptoCountList.newBuilder();
        for (IdMaptoCount idMaptoCount : idMaptoCounts) {
            b.addIdMaptoCounts(create_Sm_Common_IdMaptoCount(idMaptoCount));
        }
        return b.build();
    }

    public static Sm_Common_IdMaptoCount create_Sm_Common_IdMaptoCount(IdMaptoCount idMaptoCount) {
        Sm_Common_IdMaptoCount.Builder b = Sm_Common_IdMaptoCount.newBuilder();
        for (IdAndCount idAndCount : idMaptoCount.getAll()) {
            b.addIdAndCounts(create_Sm_Common_IdAndCount(idAndCount));
        }
        return b.build();
    }

    public static Sm_Common_IdAndCountList create_Sm_Common_IdAndCountList_UseIac(List<Iac> iacList) {
        Sm_Common_IdAndCountList.Builder bs = Sm_Common_IdAndCountList.newBuilder();
        for (Iac iac : iacList) {
            bs.addIdAndCounts(create_Sm_Common_IdAndCount(iac));
        }
        return bs.build();
    }


    public static Sm_Common_IdAndCountList create_Sm_Common_IdAndCountList(List<IdAndCount> idAndCountList) {
        Sm_Common_IdAndCountList.Builder bs = Sm_Common_IdAndCountList.newBuilder();
        for (IdAndCount idAndCount : idAndCountList) {
            bs.addIdAndCounts(create_Sm_Common_IdAndCount(idAndCount));
        }
        return bs.build();
    }

    public static Sm_Common_IdAndCount create_Sm_Common_IdAndCount(Iac iac) {
        return create_Sm_Common_IdAndCount(new IdAndCount(iac));
    }

    public static Sm_Common_IdAndCount create_Sm_Common_IdAndCount(IdAndCount idAndCount) {
        Sm_Common_IdAndCount.Builder b = Sm_Common_IdAndCount.newBuilder();
        b.setId(idAndCount.getId());
        b.setCount(idAndCount.getCount());
        return b.build();
    }


    /**
     * Sm_Common_IdMaptoCount 转化为 List<IdAndCount>
     *
     * @param map
     * @return
     */
    public static List<IdAndCount> parseSm_Common_IdMaptoCount(Sm_Common_IdMaptoCount map) {
        List<IdAndCount> idAndCountList = new ArrayList<>();
        for (Sm_Common_IdAndCount sidc : map.getIdAndCountsList()) {
            idAndCountList.add(new IdAndCount(sidc.getId(), sidc.getCount()));
        }
        return idAndCountList;
    }


    /**
     * 通用排行榜，适用于展示SimpleGuild信息
     * <p>
     * //     * @param rankType
     * //     * @param idToSimpleGuild
     * //     * @param redisRankAndScores
     *
     * @return
     */
//    public static Sm_Common_RankList create_Sm_Common_Rank_List_ForGuild(CommonRankTypeEnum rankType, Map<String, SimpleGuild> idToSimpleGuild, List<RedisRankAndScore> redisRankAndScores) {
//        Sm_Common_RankList.Builder b = Sm_Common_RankList.newBuilder();
//        b.setRankType(rankType);
//        for (RedisRankAndScore rankAndScore : redisRankAndScores) {
//            if (!idToSimpleGuild.containsKey(rankAndScore.getMember())) {
//                continue;
//            }
//            b.addRanks(create_Sm_Common_Rank(idToSimpleGuild.get(rankAndScore.getMember()), rankAndScore.getRank(), rankAndScore.getScore()));
//        }
//        return b.build();
//    }
    public static Sm_Common_Rank create_Sm_Common_Rank(SimplePlayer simplePlayer, int rank, long score) {
        Sm_Common_Rank.Builder b = Sm_Common_Rank.newBuilder();
//        b.setSimplePlayerBase(create_Sm_Common_SimplePlayer_Base(simplePlayer));
        b.setRank(rank);
        b.setScore(score);
        return b.build();
    }


    public static Sm_Common_Round create_Sm_Common_Round(int min, int max) {
        Sm_Common_Round.Builder b = Sm_Common_Round.newBuilder();
        b.setMax(max);
        b.setMin(min);
        return b.build();
    }


    public static Sm_Common_Round createSm_Common_Round(int min, int max) {
        Sm_Common_Round.Builder b = Sm_Common_Round.newBuilder();
        b.setMax(max);
        b.setMin(min);
        return b.build();
    }


}
