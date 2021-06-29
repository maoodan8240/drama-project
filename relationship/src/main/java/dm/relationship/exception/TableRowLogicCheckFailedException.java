package dm.relationship.exception;

/**
 * 数据表逻辑检查失败
 */
public class TableRowLogicCheckFailedException extends DmBaseException {

    private static final long serialVersionUID = 1L;

    public TableRowLogicCheckFailedException(Class<?> tableClass, int id, String msg) {
        super("[" + tableClass + "] -> id:" + id + ", msg=" + msg);
    }
}
