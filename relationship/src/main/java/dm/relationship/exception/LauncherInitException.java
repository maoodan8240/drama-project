package dm.relationship.exception;

public class LauncherInitException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public LauncherInitException(String message) {
        super(message, null);
    }

    public LauncherInitException(String message, Throwable cause) {
        super(message, cause);
    }

}
