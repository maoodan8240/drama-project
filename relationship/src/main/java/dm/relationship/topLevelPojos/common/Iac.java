package dm.relationship.topLevelPojos.common;

import dm.relationship.base.DmCloneable;

import java.io.Serializable;

public class Iac implements Serializable, DmCloneable<Iac> {
    private static final long serialVersionUID = -3108088749683191430L;

    private int id;
    private long count;

    public Iac() {
    }

    public Iac(int id, long count) {
        this.id = id;
        this.count = count;
    }

    public Iac(Iac iac) {
        this.id = iac.getId();
        this.count = iac.getCount();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public Iac clone() {
        return new Iac(id, count);
    }
}
