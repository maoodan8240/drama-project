package dm.relationship.enums;

public enum DropTypeEnum {
    /**
     * 掉落组的形式掉落
     */
    DropGroup(1),
    /**
     * 物品的形式掉落
     */
    Item(2),
    //
    ;

    private int value;

    private DropTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DropTypeEnum parse(int code) {
        for (DropTypeEnum resourceType : values()) {
            if (resourceType.value == code) {
                return resourceType;
            }
        }
        return null;
    }
}
