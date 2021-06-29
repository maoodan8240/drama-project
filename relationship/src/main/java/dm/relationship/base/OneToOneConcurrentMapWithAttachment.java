package dm.relationship.base;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 一一对应的Map
 */
public class OneToOneConcurrentMapWithAttachment<K, V, A> extends OneToOneAbstractMap<K, V, A> {
    private static final long serialVersionUID = -4619838488459070883L;

    public OneToOneConcurrentMapWithAttachment() {
        KToV = new ConcurrentHashMap<>();
        VToK = new ConcurrentHashMap<>();
        KToAttachment = new ConcurrentHashMap<>();
    }
}
