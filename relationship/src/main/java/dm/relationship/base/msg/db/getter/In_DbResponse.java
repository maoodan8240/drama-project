package dm.relationship.base.msg.db.getter;

import dm.relationship.base.resultCode.ResultCodeEnum;
import dm.relationship.exception.PlayerPojosGetterException;
import ws.common.utils.message.implement.AbstractInnerMsg;

/**
 * Created by lee on 17-6-13.
 */
public abstract class In_DbResponse extends AbstractInnerMsg {
    private static final long serialVersionUID = 8999006897263903698L;
    private PlayerPojosGetterException exception;

    public In_DbResponse() {
        super(ResultCodeEnum.SUCCESS);
        this.exception = null;
    }

    public In_DbResponse(PlayerPojosGetterException exception) {
        super(ResultCodeEnum.ERR_UNKNOWN);
        this.exception = exception;
    }

    public PlayerPojosGetterException getException() {
        return exception;
    }
}
