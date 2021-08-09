package drama.gameServer.features.actor.world.msg;

import ws.common.utils.message.implement.AbstractInnerMsg;

public class In_NoticeToKillOverTimeCachePlayerActorMsg extends AbstractInnerMsg {
    private static final long serialVersionUID = 1L;
    private int overTime; // 单位为分钟

    public In_NoticeToKillOverTimeCachePlayerActorMsg(int overTime) {
        this.overTime = overTime;
    }

    public int getOverTime() {
        return overTime;
    }
}
