package dm.relationship.gm;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GmCommandUtils {
    private static final Logger logger = LoggerFactory.getLogger(GmCommandUtils.class);

    private static final String COMMAND_ARGS_DELIMITER = " ";
    private static final int COMMAND_ARGS_ATLEAST_LEN = 2;


    public static In_GmCommand.Request convertGmConentTo_In_GmCommand(String content) {
        String[] params = GmCommandUtils.getCommandParams(content);
        if (params.length > 0) {
            return GmCommandUtils.createIm_GmCommand(params);
        }
        return null;
    }

    /**
     * 检查客户端传来的消息
     *
     * @param content
     * @return
     */
    public static String[] getCommandParams(String content) {
        String[] params = new String[0];
        if (!isProductEnvironment()) {
            return isCommandLegal(content);
        }
        return params;
    }

    /**
     * 检查是否是生成环境
     *
     * @return
     */
    private static boolean isProductEnvironment() {
        return false;
    }

    /**
     * 检查command的格式是否正确
     *
     * @param content
     * @return
     */
    private static String[] isCommandLegal(String content) {
        content = StringUtils.strip(content);
        String[] params = content.split(COMMAND_ARGS_DELIMITER);
        if (params.length < COMMAND_ARGS_ATLEAST_LEN) {
            logger.info("GMCommandActor msgHandler GMCommand={} 不合法!", content);
            return new String[0];
        }
        if (StringUtils.isEmpty(params[0]) || StringUtils.isEmpty(params[1])) {
            logger.info("CommandManager  execute 输入的CommandRunner={} 不存在对应要执行的Command！ {}-{}", params[0], params[1]);
            return new String[0];
        }
        logger.info("GMCommandActor msgHandler GMCommand={} !", content);
        return params;
    }

    /**
     * 封装内部的GMCommand
     *
     * @param params
     * @return
     */
    public static In_GmCommand.Request createIm_GmCommand(String[] params) {
        int argsLen = params.length - COMMAND_ARGS_ATLEAST_LEN;
        String[] args = new String[argsLen];
        if (argsLen > 0) {
            System.arraycopy(params, COMMAND_ARGS_ATLEAST_LEN, args, 0, argsLen);
        }
        return new In_GmCommand.Request(params[0], params[1], args);
    }

    /**
     * 解析为int[]
     *
     * @param params
     * @return
     */
    public static int[] parseInt(String[] params) {
        int len = params.length;
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.parseInt(params[i]);
        }
        return result;
    }

}
