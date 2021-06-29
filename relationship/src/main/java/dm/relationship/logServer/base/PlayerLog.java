package dm.relationship.logServer.base;

import drama.protos.EnumsProtos.PlatformTypeEnum;

public abstract class PlayerLog extends DmLog {
    private static final long serialVersionUID = -5608380423951992663L;
    private String pid;                       // playerId
    private int sid;                          // simpleId
    private int orid;                         // outerRealmId
    private PlatformTypeEnum platformType;    // 渠道
    private int createAtDate;                 // 年月日      yyyyMMdd
    private int createAtTime;                 // 时分秒毫秒   HHmmss


    public PlayerLog() {
    }

    /**
     * @param snid 流水号
     */
    public PlayerLog(String snid) {
        super(snid);
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getOrid() {
        return orid;
    }

    public void setOrid(int orid) {
        this.orid = orid;
    }

    public PlatformTypeEnum getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformTypeEnum platformType) {
        this.platformType = platformType;
    }

    public int getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate(int createAtDate) {
        this.createAtDate = createAtDate;
    }

    public int getCreateAtTime() {
        return createAtTime;
    }

    public void setCreateAtTime(int createAtTime) {
        this.createAtTime = createAtTime;
    }
}
