package dm.relationship.appServers.thirdPartyServer;

import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * 由thirdPartyServer发往GameServer验证
 */
public class In_ProcessPayment {
    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = -2350729225473038187L;

        private String outerOrderId;       // 第三方返回 游戏外部订单号 | 第三方游戏平台订单ID
        private String innerOrderId;       // 第三方返回 游戏内部订单号
        private Float platformMoney;       // 第三方返回 渠道货币的总数

        private String playerId;           // cpUserInfo 玩家据色ObjectId
        private Integer goodId;            // cpUserInfo 购买物品的Id
        private String goodName;           // cpUserInfo 购买物品的Id
        private Long serverTime;           // cpUserInfo 服务器时间

        private String requestContent; // 第三方返回信息
        private boolean rs; // 交易是否成功

        public Request(String outerOrderId, String innerOrderId, Float platformMoney, String playerId, Integer goodId, String goodName, Long serverTime, String requestContent, boolean rs) {
            this.outerOrderId = outerOrderId;
            this.innerOrderId = innerOrderId;
            this.platformMoney = platformMoney;
            this.playerId = playerId;
            this.goodId = goodId;
            this.goodName = goodName;
            this.serverTime = serverTime;
            this.requestContent = requestContent;
            this.rs = rs;
        }

        public Integer getGoodId() {
            return goodId;
        }

        public void setGoodId(Integer goodId) {
            this.goodId = goodId;
        }

        public String getOuterOrderId() {
            return outerOrderId;
        }

        public String getInnerOrderId() {
            return innerOrderId;
        }

        public Float getPlatformMoney() {
            return platformMoney;
        }

        public String getPlayerId() {
            return playerId;
        }

        public Long getServerTime() {
            return serverTime;
        }

        public String getRequestContent() {
            return requestContent;
        }

        public boolean isRs() {
            return rs;
        }

        public String getGoodName() {
            return goodName;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("rs=").append(rs).append("\n");
            sb.append("outerOrderId=").append(outerOrderId).append("\n");
            sb.append("innerOrderId=").append(innerOrderId).append("\n");
            sb.append("platformMoney=").append(platformMoney).append("\n");
            sb.append("playerId=").append(playerId).append("\n");
            sb.append("goodId=").append(goodId).append("\n");
            sb.append("goodName=").append(goodName).append("\n");
            sb.append("serverTime=").append(serverTime).append("\n");
            sb.append("requestContent=").append(requestContent).append("\n");
            return sb.toString();
        }
    }

    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = -4504532046635507254L;
        private Request request;
        private boolean rs;// GameServer是否处理完毕

        public Response(Request request, boolean rs) {
            this.request = request;
            this.rs = rs;
        }

        public Request getRequest() {
            return request;
        }

        public boolean isRs() {
            return rs;
        }
    }

}