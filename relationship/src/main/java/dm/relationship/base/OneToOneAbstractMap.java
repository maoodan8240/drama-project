package dm.relationship.base;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * 一一对应的Map，可以添加附件
 * 操作时，K V A 都不能为空
 */
public abstract class OneToOneAbstractMap<K, V, A> implements Serializable {
    private static final long serialVersionUID = 8294688538354994234L;
    protected Map<K, V> KToV;              // key   ----> value
    protected Map<V, K> VToK;              // value ----> key
    protected Map<K, A> KToAttachment;     // key   ----> 附件


    public boolean put(K k, V v) {
        if (_isNotNull(k, v)) {
            KToV.put(k, v);
            VToK.put(v, k);
            return true;
        }
        return false;
    }


    public boolean put(K k, V v, A a) {
        if (_isNotNull(k, v, a)) {
            KToV.put(k, v);
            VToK.put(v, k);
            KToAttachment.put(k, a);
            return true;
        }
        return false;
    }

    public void clear() {
        KToV.clear();
        VToK.clear();
        KToAttachment.clear();
    }

    public boolean addAttachment(K k, A a) {
        if (containsK(k) && _isNotNull(a)) {
            KToAttachment.put(k, a);
            return true;
        }
        return false;
    }

    public void removeKV(K k, V v) {
        removeByK(k);
        removeByV(v);
    }

    public V getVByK(K k) {
        return KToV.get(k);
    }

    public K getKByV(V v) {
        return VToK.get(v);
    }


    public A getAttachmentByK(K k) {
        return KToAttachment.get(k);
    }


    public A getAttachmentByV(V v) {
        return KToAttachment.get(getKByV(v));
    }

    public boolean containsAttachmentByV(V v) {
        if (containsV(v)) {
            return KToAttachment.containsKey(getKByV(v));
        }
        return false;
    }

    public boolean containsAttachmentByK(K k) {
        if (containsK(k)) {
            return KToAttachment.containsKey(k);
        }
        return false;
    }

    public boolean containsK(K k) {
        if (_isNotNull(k)) {
            return KToV.containsKey(k);
        }
        return false;
    }

    public boolean containsV(V v) {
        if (_isNotNull(v)) {
            return VToK.containsKey(v);
        }
        return false;
    }


    public V removeByK(K k) {
        if (_isNotNull(k)) {
            V v = KToV.remove(k);
            KToAttachment.remove(k);
            if (v != null) {
                VToK.remove(v);
            }
            return v;
        }
        return null;
    }


    public K removeByV(V v) {
        if (_isNotNull(v)) {
            K k = VToK.remove(v);
            if (k != null) {
                KToV.remove(k);
                KToAttachment.remove(k);
            }
            return k;
        }
        return null;
    }


    private static <T> boolean _isNotNull(T t) {
        if (t == null) {
            return false;
        }
        return true;
    }

    private static boolean _isNotNull(Object o1, Object o2) {
        return _isNotNull(o1) && _isNotNull(o2);
    }

    private static boolean _isNotNull(Object o1, Object o2, Object o3) {
        return _isNotNull(o1) && _isNotNull(o2) && _isNotNull(o3);
    }


    public Map<K, V> getKToV() {
        return Collections.unmodifiableMap(KToV);
    }

    public Map<V, K> getVToK() {
        return Collections.unmodifiableMap(VToK);
    }

    public Map<K, A> getKToAttachment() {
        return Collections.unmodifiableMap(KToAttachment);
    }

   
}
