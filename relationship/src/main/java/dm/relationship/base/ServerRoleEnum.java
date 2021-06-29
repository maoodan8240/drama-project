package dm.relationship.base;

import org.apache.commons.lang3.StringUtils;

public enum ServerRoleEnum {
    DM_ClusterCenterServer("DM-ClusterCenterServer"), //
    DM_GatewayServer("DM-GatewayServer"), //
    DM_LoginServer("DM-LoginServer"), //
    DM_ThirdPartyServer("DM-ThirdPartyServer"), //
    DM_GameServer("DM-GameServer"), //
    DM_ParticularFunctionServer("DM-ParticularFunctionServer"), //
    DM_MongodbRedisServer("DM-MongodbRedisServer"), //
    DM_ChatServer("DM-ChatServer"), //
    DM_LogServer("DM-LogServer"), //
    NULL("");

    private String roleName;

    ServerRoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean isDevServer() {
        return roleName.indexOf("Server-DEV-") > 0;
    }

    public static ServerRoleEnum parse(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            return NULL;
        }
        for (ServerRoleEnum serverRole : values()) {
            if (serverRole == NULL) {
                continue;
            }
            if (roleName.startsWith(serverRole.roleName)) {
                return serverRole;
            }
        }
        return NULL;
    }
}
