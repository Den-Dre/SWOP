package domainmodel;

import java.util.ArrayList;
import java.util.List;

public class Table extends ContentSpan {
    private List<TableRow> rows = new ArrayList<>();

    public Table(List<TableRow> rows) {
        this.rows = rows;
    }
}
