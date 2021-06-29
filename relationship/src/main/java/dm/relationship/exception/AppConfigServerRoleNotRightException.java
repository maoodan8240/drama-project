package dm.relationship.exception;

import java.util.List;

public class AppConfigServerRoleNotRightException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public AppConfigServerRoleNotRightException(List<String> roles) {
        super("Appconfig配置不正确！roles长度不为2! roles=" + roles);
    }

}
