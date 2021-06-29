package dm.relationship.base;

import dm.relationship.topLevelPojos.common.Iac;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;

import java.util.ArrayList;
import java.util.List;

public class IdAndCount {
    public static final IdAndCount NULL = new IdAndCount(Integer.MIN_VALUE, Integer.MIN_VALUE);

    private Iac iac = new Iac();

    public IdAndCount(int id) {
        this(id, 1);
    }

    public IdAndCount(int id, long count) {
        iac.setId(id);
        setCount(count);
    }

    public IdAndCount(Iac iac) {
        this(iac.getId(), iac.getCount());
    }

    public int getId() {
        return iac.getId();
    }

    public long getCount() {
        return iac.getCount();
    }

    public int getIntCount() {
        return (int) iac.getCount();
    }

    private void setCount(long count) {
        iac.setCount(Math.max(count, 0));
    }

    public Iac getIac() {
        return new Iac(iac);
    }

    @Override
    public String toString() {
        return iac.getId() + ":" + iac.getCount();
    }

    public static IdAndCount parse(TupleCell<Integer> tuple) {
        return new IdAndCount(tuple.get(TupleCell.FIRST), tuple.get(TupleCell.SECOND));
    }

    public static Iac parseIac(TupleCell<Integer> tuple) {
        return new Iac(tuple.get(TupleCell.FIRST), tuple.get(TupleCell.SECOND));
    }

    public static List<IdAndCount> parse(TupleListCell<Integer> tupleList) {
        List<IdAndCount> idAndCounts = new ArrayList<>();
        for (TupleCell<Integer> tuple : tupleList.getAll()) {
            idAndCounts.add(parse(tuple));
        }
        return idAndCounts;
    }

    public static List<Iac> parseIacList(TupleListCell<Integer> tupleList) {
        List<Iac> iacList = new ArrayList<>();
        for (TupleCell<Integer> tuple : tupleList.getAll()) {
            iacList.add(parseIac(tuple));
        }
        return iacList;
    }
}
