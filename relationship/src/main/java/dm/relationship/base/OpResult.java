package dm.relationship.base;

/**
 * Created by lee on 17-6-7.
 */
public class OpResult {
    private long before; // 操作前
    private long after;  // 操作后
    private long change; // 变化的值，为正值

    public OpResult(long before, long after) {
        this.before = before;
        this.after = after;
        this.change = Math.abs(after - before);
    }

    public long getBefore() {
        return before;
    }

    public long getAfter() {
        return after;
    }

    public long getChange() {
        return change;
    }
}
