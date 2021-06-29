package dm.relationship.exception;

/**
 * CmMessage参数不对
 */
public class CmMessageIllegalArgumentException extends DmBaseException {

    private static final long serialVersionUID = 1L;

    public CmMessageIllegalArgumentException(String msg) {
        super(msg);
    }
}
