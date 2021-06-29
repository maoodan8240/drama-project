package dm.relationship.logServer.pojos;

import com.alibaba.fastjson.annotation.JSONField;
import org.bson.types.ObjectId;
import dm.relationship.enums.ItemOpEnum;
import dm.relationship.enums.item.IdItemTypeEnum;
import dm.relationship.logServer.base.PlayerLog;

public class ItemLog extends PlayerLog {
    private static final long serialVersionUID = -2481565357132513349L;

    @JSONField(serialize = false)
    private IdItemTypeEnum type; // 物品类型
    private int id;              // Id
    private long count;          // 数量
    private ItemOpEnum op;       // 变化的类型
    private String msgac;        // 造成变化的Action

    public ItemLog() {
    }

    public ItemLog(IdItemTypeEnum type, int id, long count, ItemOpEnum op, String msgac) {
        super(ObjectId.get().toString());
        this.type = type;
        this.id = id;
        this.count = count;
        this.op = op;
        this.msgac = msgac;
    }

    public IdItemTypeEnum getType() {
        return type;
    }

    public void setType(IdItemTypeEnum type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public ItemOpEnum getOp() {
        return op;
    }

    public void setOp(ItemOpEnum op) {
        this.op = op;
    }

    public String getMsgac() {
        return msgac;
    }

    public void setMsgac(String msgac) {
        this.msgac = msgac;
    }
}
