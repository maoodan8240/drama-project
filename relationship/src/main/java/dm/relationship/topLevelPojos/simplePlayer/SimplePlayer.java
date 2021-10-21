package dm.relationship.topLevelPojos.simplePlayer;

import drama.protos.EnumsProtos.SexEnum;


/**
 * Created by leetony on 16-8-25.
 */
public class SimplePlayer {
    private static final long serialVersionUID = -6148875855249139783L;

    private String playerId;
    private int simplePlayerId;    // 玩家短Id
    private String playerName;     // 玩家名字
    private SexEnum sex;           // 性别

    private String icon;              // 玩家头像

    private long lastLoginTime;    // 最近一次登录时间
    private long lastLogoutTime;   // 最近一次登出时间

    private String roomId;         // 房间Id


    /**
     * @param playerId
     * @param simplePlayerId
     * @param playerName
     * @param sex
     * @param icon
     * @param lastLoginTime
     * @param lastLogoutTime
     * @param roomId
     */
    public SimplePlayer(String playerId, int simplePlayerId, String playerName, SexEnum sex, String icon, long lastLoginTime, long lastLogoutTime, String roomId) {
        this.playerId = playerId;
        this.simplePlayerId = simplePlayerId;
        this.playerName = playerName;
        this.sex = sex;
        this.icon = icon;
        this.lastLoginTime = lastLoginTime;
        this.lastLogoutTime = lastLogoutTime;
        this.roomId = roomId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSimplePlayerId() {
        return simplePlayerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public SexEnum getSex() {
        return sex;
    }

    public String getIcon() {
        return icon;
    }

    public String getPlayerId() {
        return playerId;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }

    public String getRoomId() {
        return roomId;
    }


    @Override
    public String toString() {
        return "SimplePlayer{" +
                "playerId='" + playerId + '\'' +
                ", simplePlayerId=" + simplePlayerId +
                ", playerName='" + playerName + '\'' +
                ", sex=" + sex +
                ", icon='" + icon + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLogoutTime=" + lastLogoutTime +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
