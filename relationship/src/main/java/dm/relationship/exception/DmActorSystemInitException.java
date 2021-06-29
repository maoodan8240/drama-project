package dm.relationship.exception;

public class DmActorSystemInitException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public DmActorSystemInitException(String message) {
        super(message, null);
    }

    public DmActorSystemInitException(String message, Throwable cause) {
        super(message, cause);
    }

}
