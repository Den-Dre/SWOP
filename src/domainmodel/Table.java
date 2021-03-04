package domainmodel;

import java.util.ArrayList;
import java.util.List;

public class Table extends ContentSpan {
    private List<TableRow> rows = new ArrayList<>();

    public Table(List<TableRow> rows) {
        this.rows = rows;
    }

    public void addTableRow(TableRow row) {
        this.rows.add(row);
    }

    public List<TableRow> getRows() {
        return rows;
    }
}
