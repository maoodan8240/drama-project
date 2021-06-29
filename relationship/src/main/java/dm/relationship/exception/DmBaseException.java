package dm.relationship.exception;

public class DmBaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DmBaseException(Throwable t) {
        super(t);
    }

    public DmBaseException(String msg) {
        super(msg);
    }

    public DmBaseException(String msg, Throwable t) {
        super(msg, t);
    }
}
