package dm.relationship.topLevelPojos.itemBag;

import java.io.Serializable;

public class PlainCell implements Serializable {
    private static final long serialVersionUID = -6663214987854405761L;
    
    private int itemTemplateId;
    private long stackSize;
    private boolean useCell;

    public PlainCell() {
    }

    public PlainCell(int itemTemplateId, boolean useCell) {
        this.itemTemplateId = itemTemplateId;
        this.stackSize = 0;
        this.useCell = useCell;
    }

    public int getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(int itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public long getStackSize() {
        return stackSize;
    }

    public void setStackSize(long stackSize) {
        this.stackSize = stackSize;
    }

    public boolean isUseCell() {
        return useCell;
    }

    public void setUseCell(boolean useCell) {
        this.useCell = useCell;
    }
}
