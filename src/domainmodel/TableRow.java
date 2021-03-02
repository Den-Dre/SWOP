package domainmodel;

import java.util.ArrayList;
import java.util.List;

public class TableRow {
    private List<TableCell> cells = new ArrayList<>();

    public TableRow(List<TableCell> cells) {
        this.cells = cells;
    }
}
