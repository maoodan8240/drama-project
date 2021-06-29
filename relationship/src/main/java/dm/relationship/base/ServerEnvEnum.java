package dm.relationship.base;

import org.apache.commons.lang3.StringUtils;

public enum ServerEnvEnum {

    /**
     * 本地开发环境
     */
    DEV("<DEV>"),

    /**
     * 本地开发环境-1
     */
    DEV_1("<DEV-1>"),

    /**
     * 本地开发环境-2
     */
    DEV_2("<DEV-2>"),

    /**
     * 测试
     */
    TEST("<TEST>"),

    /**
     * 准生产
     */
    PRE("<PRE>"),

    /**
     * 生产
     */
    PRO("<PRO>"),

    //
    NULL("");
    private String env;

    private ServerEnvEnum(String env) {
        this.env = env;
    }

    public String getEnv() {
        return env;
    }

    public static ServerEnvEnum parse(String env) {
        if (StringUtils.isBlank(env)) {
            return NULL;
        }
        for (ServerEnvEnum serverEnvEnum : values()) {
            if (serverEnvEnum == NULL) {
                continue;
            }
            if (serverEnvEnum.env.equals(env)) {
                return serverEnvEnum;
            }
        }
        return NULL;
    }
}
