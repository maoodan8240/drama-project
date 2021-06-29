package dm.relationship.exception;


import drama.protos.EnumsProtos.ErrorCodeEnum;

/**
 * 业务逻辑条件不符合要求
 */
public class BusinessLogicMismatchConditionException extends DmBaseException {
    private ErrorCodeEnum errorCodeEnum;
    private static final long serialVersionUID = 1L;


    public BusinessLogicMismatchConditionException(String msg) {
        super(msg);
        this.errorCodeEnum = ErrorCodeEnum.UNKNOWN;
    }

    public BusinessLogicMismatchConditionException(String msg, Throwable t) {
        super(msg, t);
        this.errorCodeEnum = ErrorCodeEnum.UNKNOWN;
    }


    public BusinessLogicMismatchConditionException(String msg, ErrorCodeEnum errorCodeEnum) {
        super(msg);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
