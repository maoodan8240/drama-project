package dm.relationship.exception;

public class JmxJolokiaServerStartException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public JmxJolokiaServerStartException(String message) {
        super(message, null);
    }

    public JmxJolokiaServerStartException(String message, Throwable cause) {
        super(message, cause);
    }

}
