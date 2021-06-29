package dm.relationship.topLevelPojos.player;

import com.alibaba.fastjson.annotation.JSONField;
import dm.relationship.topLevelPojos.PlayerTopLevelPojo;
import ws.common.network.server.interfaces.Connection;


public class Player extends PlayerTopLevelPojo {
    private static final long serialVersionUID = 2630217257496628198L;
    private String mobileNum; // 电话
    private String roomId;

    private PlayerBase base = new PlayerBase(); // 基本信息
    private PlayerAccount account = new PlayerAccount();// 账户信息
    private PlayerPayment payment = new PlayerPayment();// 支付信息
    private PlayerOther other = new PlayerOther();// 其他信息

    @JSONField(serialize = false)
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PlayerBase getBase() {
        return base;
    }

    public void setBase(PlayerBase base) {
        this.base = base;
    }

    public PlayerAccount getAccount() {
        return account;
    }

    public void setAccount(PlayerAccount account) {
        this.account = account;
    }

    public PlayerPayment getPayment() {
        return payment;
    }

    public void setPayment(PlayerPayment payment) {
        this.payment = payment;
    }

    public PlayerOther getOther() {
        return other;
    }

    public void setOther(PlayerOther other) {
        this.other = other;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
