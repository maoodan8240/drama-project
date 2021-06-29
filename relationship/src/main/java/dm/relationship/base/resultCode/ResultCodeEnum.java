package dm.relationship.base.resultCode;

import ws.common.utils.message.interfaces.ResultCode;

public enum ResultCodeEnum implements ResultCode {
    /**
     * 失败：未知错误
     */
    ERR_UNKNOWN(1, "失败"),
    /**
     * 成功
     */
    SUCCESS(0, "成功");


    private int value;
    private String message;

    ResultCodeEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getCode() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
