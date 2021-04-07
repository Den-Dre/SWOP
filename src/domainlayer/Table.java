package domainlayer;

import java.util.List;

/**
 * A class to represent tables in the domain layer.
 */
public class Table extends ContentSpan {

    /**
     * The rows of this Table.
     */
    private final List<TableRow> rows;

    /**
     * Initialise this Table with the given rows.
     *
     * @param rows: The rows that should be placed in this Table.
     */
    public Table(List<TableRow> rows) {
        this.rows = rows;
    }

    /**
     * Add a {@link TableRow} to this Table.
     *
     * @param row:
     *          the TableRow to be added.
     */
    public void addTableRow(TableRow row) {
        this.rows.add(row);
    }

    /**
     * Retrieve the {@link TableRow}'s that are present in this Table.
     *
     * @return rows:
     *             the TableRow's present in this Table.
     */
    public List<TableRow> getRows() {
        return rows;
    }
}
