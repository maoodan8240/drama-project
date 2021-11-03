package drama.gameServer.features.extp.itemBag.enums;

/**
 * Created by lee on 2021/10/22
 */
public enum EquipItemEnum {
    WEAPONE(1),

    ARMOR(2);


    private int type;

    EquipItemEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
