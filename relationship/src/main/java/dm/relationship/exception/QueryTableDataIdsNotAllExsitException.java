package dm.relationship.exception;

import ws.common.table.table.implement.AbstractRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryTableDataIdsNotAllExsitException extends DmBaseException {
    private static final long serialVersionUID = 1L;

    public QueryTableDataIdsNotAllExsitException(Class<? extends AbstractRow> rowClass, List<? extends AbstractRow> rows, Integer... ids) {
        super(rowClass.getPackage() + "." + rowClass.getSimpleName() + "表中存在的rowIds=" + hasIds(rows) + ", 输入的ids=" + Arrays.toString(ids));
    }

    private static List<Integer> hasIds(List<? extends AbstractRow> rows) {
        List<Integer> ids = new ArrayList<>();
        if (rows == null) {
            return ids;
        }
        for (AbstractRow row : rows) {
            ids.add(row.getId());
        }
        return ids;
    }
}
