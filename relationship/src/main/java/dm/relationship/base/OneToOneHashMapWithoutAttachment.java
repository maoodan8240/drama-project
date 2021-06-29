package dm.relationship.base;

import java.util.HashMap;

/**
 * 一一对应的Map
 */
public class OneToOneHashMapWithoutAttachment<K, V> extends OneToOneAbstractMap<K, V, Object> {
    private static final long serialVersionUID = -4619838488459070883L;

    public OneToOneHashMapWithoutAttachment() {
        KToV = new HashMap<>();
        VToK = new HashMap<>();
        KToAttachment = new HashMap<>();
    }
}
