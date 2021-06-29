package dm.relationship.base.resultCode;

import ws.common.utils.message.interfaces.ResultCode;

public class ResultMsg implements ResultCode {
    public static final ResultMsg DEFAULT_UNKNOW = new ResultMsg();

    private String message;

    @Override
    public int getCode() {
        return -1000;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ResultMsg setMessage(String message) {
        this.message = message;
        return this;
    }
}
