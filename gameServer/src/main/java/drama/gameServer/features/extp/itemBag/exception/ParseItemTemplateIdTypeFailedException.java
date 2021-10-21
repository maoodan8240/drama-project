package drama.gameServer.features.extp.itemBag.exception;


import dm.relationship.exception.DmBaseException;

public class ParseItemTemplateIdTypeFailedException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public ParseItemTemplateIdTypeFailedException(String IdItemTypeEnums, int itemTemplateId) {
        super("在IdItemTypeEnums=" + IdItemTypeEnums + "中，未找到itemTemplateId=" + itemTemplateId + "所属的类型！");
    }
}
