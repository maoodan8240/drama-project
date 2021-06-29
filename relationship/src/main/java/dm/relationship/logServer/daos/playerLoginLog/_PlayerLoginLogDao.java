package dm.relationship.logServer.daos.playerLoginLog;


import com.mongodb.client.DistinctIterable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.Document;
import ws.common.mongoDB.implement.AbstractBaseDao;
import ws.common.utils.date.WsDateFormatEnum;
import ws.common.utils.date.WsDateUtils;
import dm.relationship.base.MagicNumbers;
import dm.relationship.daos.utils.DaoUtils;
import dm.relationship.logServer.pojos.PlayerLoginLog;
import dm.relationship.topLevelPojos.data.DayRemain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zww on 8/10/16.
 */
public class _PlayerLoginLogDao extends AbstractBaseDao<PlayerLoginLog> implements PlayerLoginLogDao {
    private static final int REMAINDAY_7 = 7; //七日留存
    private static final int REMAINDAY_30 = 30; // 30日留存
    private static final String DECIMAL = "0.00";//小数点精确位数

    public _PlayerLoginLogDao() {
        super(PlayerLoginLog.class);
    }


    @Override
    public int findNewPlayer() {
        String data = WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyyMMdd);
        Document document = new Document();
        document.append("createAtDate", Integer.valueOf(data));
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "pid", document);
    }


    @Override
    public int findActiveDevice() {
        String data = WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyyMMdd);
        Document document = new Document();
        document.append("date", Integer.valueOf(data));
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "deviceUid", document);
    }

    @Override
    public int findActiveDeviceByDate(String date, String platformType, int orid) {
        Document document = new Document();
        document.append("date", Integer.valueOf(date));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid != 0) {
            document.append("orid", orid);
        }
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "deviceUid", document);
    }

    @Override
    public int findActiveAccount() {
        String data = WsDateUtils.dateToFormatStr(new Date(), WsDateFormatEnum.yyyyMMdd);
        Document document = new Document();
        document.append("date", Integer.valueOf(data));
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "pid", document);
    }

    @Override
    public int findActiveAccountByDate(String date, String platformType, int orid) {
        Document document = new Document();
        document.append("date", Integer.valueOf(date));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid != 0) {
            document.append("orid", orid);
        }
        return DaoUtils.findDistinctFieldAndCondition(this.getMongoCollection(), "pid", document);
    }

    @Override
    public DayRemain findRemainByDate(String date, String platformType, int orid) {
        List<String> day = findNewPlayerByDate(date, platformType, orid);//新增
        int dayNewly = day.size();
        if (dayNewly == 0) {
            return new DayRemain();
        }
        String date1 = _getNextDate(date, MagicNumbers.DEFAULT_ONE);
        List<String> day1 = findLoginByCreate(date, date1, platformType, orid);//新增
        double day1Remain = _matchRemain(day, day1);
        String date2 = _getNextDate(date1, MagicNumbers.DEFAULT_ONE);
        List<String> day2 = findLoginByCreate(date, date2, platformType, orid);//新增
        double day2Remain = _matchRemain(day, day2);
        String date3 = _getNextDate(date2, MagicNumbers.DEFAULT_ONE);
        List<String> day3 = findLoginByCreate(date, date3, platformType, orid);//新增
        double day3Remain = _matchRemain(day, day3);
        String date4 = _getNextDate(date3, MagicNumbers.DEFAULT_ONE);
        List<String> day4 = findLoginByCreate(date, date4, platformType, orid);//新增
        double day4Remain = _matchRemain(day, day4);
        String date5 = _getNextDate(date4, MagicNumbers.DEFAULT_ONE);
        List<String> day5 = findLoginByCreate(date, date5, platformType, orid);//新增
        double day5Remain = _matchRemain(day, day5);
        String date6 = _getNextDate(date5, MagicNumbers.DEFAULT_ONE);
        List<String> day6 = findLoginByCreate(date, date6, platformType, orid);//新增
        double day6Remain = _matchRemain(day, day6);
        String date7 = _getNextDate(date6, MagicNumbers.DEFAULT_ONE);
        List<String> day7 = findLoginByCreate(date, date7, platformType, orid);//新增
        double day7Remain = _matchRemain(day, day7);
        //第七天加7天 14日留存
        String date14 = _getNextDate(date7, REMAINDAY_7);
        List<String> day14 = findLoginByCreate(date, date14, platformType, orid);//新增
        double day14Remain = _matchRemain(day, day14);
        //从第一天开始算
        String date30 = _getNextDate(date, REMAINDAY_30);
        List<String> day30 = findLoginByCreate(date, date30, platformType, orid);//新增
        double day30Remain = _matchRemain(day, day30);
        DayRemain dayRemain = new DayRemain(date, dayNewly, day1Remain, day2Remain, day3Remain, day4Remain, day5Remain, day6Remain, day7Remain, day14Remain, day30Remain);
        return dayRemain;
    }

    private Double _matchRemain(List<String> playerIds, List<String> playerIds1) {
        if (playerIds1.size() == 0 || playerIds.size() == 0) {
            return Double.valueOf(0);
        }
        float f = (playerIds1.size() * 100) / playerIds.size();
        DecimalFormat df = new DecimalFormat(DECIMAL);
        String str = df.format(f);
        return Double.valueOf(str);
    }


    private String _getNextDate(String dateStr, int amount) {
        Date date = WsDateUtils.dateToFormatDate(dateStr, WsDateFormatEnum.yyyyMMdd);
        Date date1 = DateUtils.addDays(date, amount);
        return WsDateUtils.dateToFormatStr(date1, WsDateFormatEnum.yyyyMMdd);
    }

    /**
     * 指定日期新增用户
     *
     * @param date
     * @return
     */
    public List<String> findNewPlayerByDate(String date, String platformType, int orid) {
        Document document = new Document();
        document.append("createAtDate", Integer.valueOf(date));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid != 0) {
            document.append("orid", orid);
        }
        DistinctIterable<String> result = this.getMongoCollection().distinct("pid", document, String.class);
        Iterator<String> it = result.iterator();
        List<String> playerIds = new ArrayList<>();
        while (it.hasNext()) {
            String pid = it.next();
            playerIds.add(pid);
        }
        return playerIds;
    }

    /**
     * 查询指定注册时间，并且当日的登陆的用户
     *
     * @return
     */
    private List<String> findLoginByCreate(String createAtDate, String date, String platformType, int orid) {
        Document document = new Document();
        document.append("createAtDate", Integer.valueOf(createAtDate));
        document.append("date", Integer.valueOf(date));
        if (!StringUtils.isBlank(platformType) && !platformType.equals("0")) {
            document.append("platformType", platformType);
        }
        if (orid > 0) {
            document.append("orid", orid);
        }
        DistinctIterable<String> result = this.getMongoCollection().distinct("pid", document, String.class);
        Iterator<String> it = result.iterator();
        List<String> playerIds = new ArrayList<>();
        while (it.hasNext()) {
            String pid = it.next();
            playerIds.add(pid);
        }
        return playerIds;
    }
}




