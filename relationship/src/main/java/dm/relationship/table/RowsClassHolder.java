package dm.relationship.table;

import dm.relationship.table.tableRows.Table_Result_Row;
import ws.common.table.table.implement.AbstractRow;
import ws.common.utils.classProcess.ClassFinder;

import java.util.ArrayList;
import java.util.List;

public class RowsClassHolder {

    private static List<Class<? extends AbstractRow>> abstractRowClasses = null;

    static {
        abstractRowClasses = ClassFinder.getAllAssignedClass(AbstractRow.class, Table_Result_Row.class);
    }

    public static List<Class<? extends AbstractRow>> getAbstractRowClasses() {
        return new ArrayList<>(abstractRowClasses);
    }
}
