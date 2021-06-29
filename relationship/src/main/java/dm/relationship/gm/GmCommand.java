package dm.relationship.gm;

import dm.relationship.enums.GmCommandFromTypeEnum;

public interface GmCommand {
    void exec(GmCommandFromTypeEnum fromTypeEnum, String commandName, String[] args);
}
