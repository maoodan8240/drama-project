package dm.relationship.exception;

import dm.relationship.base.ServerRoleEnum;

public class ServerRoleAddressNotFoundException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public ServerRoleAddressNotFoundException(ServerRoleEnum serverRoleEnum) {
        super("未获取到[" + serverRoleEnum + "]在集群中的地址!");
    }
}
