package dm.relationship.base;

import java.util.Map.Entry;
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

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");

        for (Entry<K, V> kvEntry : KToV.entrySet()) {
            buffer.append(kvEntry.getKey().toString() + "=");
            buffer.append(kvEntry.getValue().toString() + "=");
            buffer.append(getAttachmentByK(kvEntry.getKey()).toString() + ",");
        }
        buffer.append("}");
        int i = buffer.toString().lastIndexOf(",");
        buffer.replace(i, i + 1, "");
        return buffer.toString();
    }
}
