package dm.relationship.exception;

/**
 * Created by lee on 17-2-23.
 */
public class Dm_HttpServerException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public Dm_HttpServerException(String message) {
        super(message, null);
    }

    public Dm_HttpServerException(String message, Throwable t) {
        super(message, t);
    }

}
