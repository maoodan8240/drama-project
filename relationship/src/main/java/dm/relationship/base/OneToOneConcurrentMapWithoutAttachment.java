package dm.relationship.base;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 一一对应的Map
 */
public class OneToOneConcurrentMapWithoutAttachment<K, V> extends OneToOneAbstractMap<K, V, Object> {
    private static final long serialVersionUID = -4619838488459070883L;

    public OneToOneConcurrentMapWithoutAttachment() {
        KToV = new ConcurrentHashMap<>();
        VToK = new ConcurrentHashMap<>();
        KToAttachment = new ConcurrentHashMap<>();
    }
}
