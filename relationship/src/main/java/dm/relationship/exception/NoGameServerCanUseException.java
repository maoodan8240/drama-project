package dm.relationship.exception;

public class NoGameServerCanUseException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public NoGameServerCanUseException() {
        super("集群中没有GameServer可以使用！");
    }
}
