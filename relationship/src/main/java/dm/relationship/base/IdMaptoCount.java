package dm.relationship.base;

import dm.relationship.topLevelPojos.common.Iac;
import ws.common.table.table.interfaces.cell.TupleCell;
import ws.common.table.table.interfaces.cell.TupleListCell;
import ws.common.table.table.utils.CellUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdMaptoCount {
    private Map<Integer, Long> map = new HashMap<>();

    public IdMaptoCount() {
    }

    public IdMaptoCount(IdAndCount idAndCount, IdAndCount... idAndCounts) {
        add(idAndCount);
        for (IdAndCount ic : idAndCounts) {
            add(ic);
        }
    }

    public IdMaptoCount(List<IdAndCount> idAndCounts) {
        addAll(idAndCounts);
    }

    public IdMaptoCount(Iac iac, Iac... iacs) {
        add(new IdAndCount(iac));
        for (Iac ic : iacs) {
            add(new IdAndCount(ic));
        }
    }

    public IdMaptoCount(List<Iac> iacs, int placeholder) {
        for (Iac iac : iacs) {
            add(new IdAndCount(iac));
        }
    }

    public long getCount(int id) {
        return map.containsKey(id) ? map.get(id) : 0;
    }

    public int getIntCount(int id) {
        return (int) getCount(id);
    }


    public IdMaptoCount remove(int id) {
        this.map.remove(id);
        return this;
    }


    private void _addCount(int id, long count) {
        if (id > 0 && count > 0) {
            map.put(id, count + getCount(id));
        }
    }

    private void _reduceCount(int id, long count) {
        if (count <= 0) {
            return;
        }
        long oldCount = getCount(id);
        if (oldCount <= count) {
            map.remove(id);
        } else {
            map.put(id, oldCount - count);
        }
    }

    public IdMaptoCount add(IdAndCount idAndCount) {
        _addCount(idAndCount.getId(), idAndCount.getCount());
        return this;
    }

    public IdMaptoCount add(Iac iac) {
        _addCount(iac.getId(), iac.getCount());
        return this;
    }

    public IdMaptoCount reduce(IdAndCount idAndCount) {
        _reduceCount(idAndCount.getId(), idAndCount.getCount());
        return this;
    }

    public IdMaptoCount reduce(Iac iac) {
        _reduceCount(iac.getId(), iac.getCount());
        return this;
    }

    public IdMaptoCount addAll(IdMaptoCount map) {
        return addAll(map.getAll());
    }

    public IdMaptoCount addAll(List<IdAndCount> list) {
        for (IdAndCount x : list) {
            _addCount(x.getId(), x.getCount());
        }
        return this;
    }

    public IdMaptoCount addAll(List<Iac> iacs, int placeholder) {
        for (Iac iac : iacs) {
            _addCount(iac.getId(), iac.getCount());
        }
        return this;
    }

    public IdMaptoCount reduceAll(List<IdAndCount> list) {
        for (IdAndCount x : list) {
            _reduceCount(x.getId(), x.getCount());
        }
        return this;
    }

    public IdMaptoCount reduceAll(List<Iac> iacs, int placeholder) {
        for (Iac iac : iacs) {
            _reduceCount(iac.getId(), iac.getCount());
        }
        return this;
    }

    public List<IdAndCount> getAll() {
        List<IdAndCount> list = new ArrayList<>();
        map.forEach((id, count) -> {
            list.add(new IdAndCount(id, count));
        });
        return list;
    }

    public List<Iac> getAll(int placeholder) {
        List<Iac> list = new ArrayList<>();
        map.forEach((id, count) -> {
            list.add(new Iac(id, count));
        });
        return list;
    }

    public IdMaptoCount replaceAll(IdMaptoCount idMaptoCount) {
        if (idMaptoCount == null) {
            return this;
        }
        for (IdAndCount idAndCount : idMaptoCount.getAll()) {
            replace(idAndCount);
        }
        return this;
    }

    public IdMaptoCount replace(IdAndCount idAndCount) {
        if (idAndCount == null || idAndCount.getId() <= 0) {
            return this;
        }
        if (idAndCount.getCount() <= 0) {
            this.remove(idAndCount.getId());
        }
        this.map.put(idAndCount.getId(), idAndCount.getCount());
        return this;
    }

    public List<Integer> getAllKeys() {
        return new ArrayList<>(map.keySet());
    }

    @Override
    public String toString() {
        return getAll().toString();
    }

    public static IdMaptoCount parse(TupleListCell<Integer> tupleList) {
        IdMaptoCount idMaptoCount = new IdMaptoCount();
        if (CellUtils.isEmptyCell(tupleList)) {
            return idMaptoCount;
        }
        for (TupleCell<Integer> tuple : tupleList.getAll()) {
            idMaptoCount.add(IdAndCount.parse(tuple));
        }
        return idMaptoCount;
    }

    public static IdMaptoCount parse(List<IdMaptoCount> idMaptoCounts) {
        IdMaptoCount idMaptoCount = new IdMaptoCount();
        for (IdMaptoCount idmtc : idMaptoCounts) {
            idMaptoCount.addAll(idmtc.getAll());
        }
        return idMaptoCount;
    }
}
