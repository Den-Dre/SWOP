package domainmodel;

import java.util.List;

/**
 * A class of the domain layer to represent rows contained in {@link Table}'s.
 */
public class TableRow {

    /**
     * A list to hold all {@link TableCell}'s contained in this TableRow.
     */
    private final List<TableCell> cells;

    /**
     * Initialise this TableRow with the given cells.
     *
     * @param cells:
     *             The cells to be placed in this TableRow.
     */
    public TableRow(List<TableCell> cells) {
        this.cells = cells;
    }

    /**
     * Add a {@link TableCell} to this TableRow.
     *
     * @param cell:
     *            the {@link TableCell} to be added to this TableRow.
     */
    public void addTableCell(TableCell cell) {
        this.cells.add(cell);
    }

    /**
     * Retrieve a list of the {@link TableCell's} contained in this TableRow.
     *
     * @return cells:
     *              a list of the {@link TableCell}'s contained in this TableRow.
     */
    public List<TableCell> getCells() {
        return cells;
    }
}
