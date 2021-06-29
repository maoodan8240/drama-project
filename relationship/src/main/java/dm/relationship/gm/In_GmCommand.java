package dm.relationship.gm;

import ws.common.utils.message.implement.AbstractInnerMsg;
import dm.relationship.base.resultCode.ResultCodeEnum;
import dm.relationship.enums.GmCommandFromTypeEnum;

public class In_GmCommand {

    public static class Request extends AbstractInnerMsg {
        private static final long serialVersionUID = 511718462493456915L;

        private String groupName;
        private String commandName;
        private String[] args;
        private GmCommandFromTypeEnum fromType;

        public Request(String groupName, String commandName, String[] args) {
            this.groupName = groupName;
            this.commandName = commandName;
            this.args = args;
            fromType = GmCommandFromTypeEnum.CLIENT;
        }

        public GmCommandFromTypeEnum getFromType() {
            return fromType;
        }

        public void setFromType(GmCommandFromTypeEnum fromType) {
            this.fromType = fromType;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getCommandName() {
            return commandName;
        }

        public String[] getArgs() {
            return args;
        }
    }

    public static class Response extends AbstractInnerMsg {
        private static final long serialVersionUID = -5541981953128900544L;

        public Response(ResultCodeEnum resultCode) {
            super(resultCode);
        }
    }
}
