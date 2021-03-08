package domainmodel;

import java.util.ArrayList;
import java.util.List;

public class TableRow {

    private List<TableCell> cells = new ArrayList<>();

    public TableRow(List<TableCell> cells) {
        this.cells = cells;
    }

    public void addTableCell(TableCell cell) {
        this.cells.add(cell);
    }

    public List<TableCell> getCells() {
        return cells;
    }
}
