package UserInterface;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class to represent tables in the UI layer.
 */
public class UITable extends DocumentCell{

    /**
     * Create a {@code UITable} based on the given parameters.
     *
     * @param x: The x coordinate of this {@code UITable}.
     * @param y: The y coordinate of this {@code UITable}.
     * @param width: The width of this {@code UITable}.
     * @param height: The height of this {@code UITable}.
     * @param rows: The number of rows of this {@code UITable}.
     * @throws Exception 
     */
    public UITable(int x, int y, int width, int height, ArrayList<ArrayList<DocumentCell>> rows) throws Exception {
        super(x, y, width, height);

        // => 1. Initialise the grid
        this.grid = rows;

        // => 2. Set the dimensions of the table contents
        setColumnWidths();
        setRowHeights();
    }

    /**
     * Render every cell in the grid.
     *
     * @param g: The graphics to be updated.
     */
    @Override
    public void Render(Graphics g) {
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                cell.Render(g);
            }
        }
        if (calculateActualWidth) {
            setColumnWidths();
            setRowHeights();
        }
        // Draw a rectangle around the table for debugging purposes
        g.setColor(Color.BLACK);
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * Handle mouse events by forwarding the click to each cell.
     * If a cell returns something (=href) this method returns this.
     * Else, it returns the empty string (= "")
     *
     * @param id: The type of mouse action
     * @param x: The x coordinate of the mouse action.
     * @param y: The y coordinate of the mouse action.
     * @param clickCount: The number of times the mouse has clicked.
     * @param button: The mouse button that was clicked.
     * @param modifier: Possible other keys that were pressed during this mouse action.
     */
    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        String result;
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                // Let all the cells handle their click, and if the click ended up on a hyperlink, the href is passed into result
                result = cell.getHandleMouse(id, x, y, clickCount, button, modifier);
                if (!result.equals("")) return result;
            }
        }
        return "";
    }

    /**
     * Re-calculates the necessary widths and heights of the DocumentCells
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setColumnWidths();
        setRowHeights();
    }

    /**
     * Returns the maximum height of the table.
     * Only returns a useful answer after calling setRowHeights() to set the used ArrayList
     */
    @Override
    public int getMaxHeight() {
        int maxheight = 0;
        for (int height :  rowHeights)
            maxheight += height;
        return maxheight;
//        return rowHeights.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Returns the maximum width of the table.
     * Only returns a useful answer after colling setColumnWidths() to set the used ArrayList
     */
    @Override
    public int getMaxWidth() {
        int maxwidth = 0;
        for (int width :  columnWidths)
            maxwidth += width;
        return maxwidth;
//        return columnWidths.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * This methods calculates the needed height of each row.
     * It also sets the desired height and y-position of the cells in {@code UITable}.
     */
    public void setRowHeights() {
        // Reset the array with the heights
        rowHeights = new ArrayList<>();
        // Iterate over the rows to find the highest cell in each row
        int i = 0;
        // Calculate the maximum height of the grid
        for (ArrayList<DocumentCell> row : grid) {
            int max = 0;
            for (DocumentCell cell : row) {
                int height = cell.getMaxHeight();
                if (height > max) max = height;
            }
            // Store the height if the highest cell in the row into an array
            rowHeights.add(max);
            // Set the height of each cell in the current row to this calculated height
            for (DocumentCell cell : row) {
                cell.setHeight(max);
                // Also, the y-position needs to be updated. This is the y-position of the table + the heights of all the above cells
                int offset = 0;
                for (int j = 0; j < i; j++) offset += rowHeights.get(j);
                cell.setyPos(getyPos()+offset);
            }
            i++;
        }
        // Set the height of the table to the max height of the table
        setHeight(getMaxHeight());
    }

    /**
     * This methods calculates the needed width of each column.
     * It also sets the desired width and x-position of the cells in this {@code UITable}.
     */
    public void setColumnWidths() {
        // Reset the array with the widths
        columnWidths = new ArrayList<>();
        // Iterate over the rows to update the array containing the widest cell per column
        for (ArrayList<DocumentCell> row : grid) {
            for (int i = 0; i < row.size(); i++) {
                DocumentCell cell = row.get(i);
                int width = cell.getMaxWidth();
                // If there isn't already a max width for this column, just append it to the array
                if (columnWidths.size() <= i)
                    columnWidths.add(width);
                // If there is, check to see whether this new cell is wider or not
                else {
                    if (columnWidths.get(i) < width) {
                        // If it is wider, replace with newly found (wider) width
                        columnWidths.remove(i);
                        columnWidths.add(i, width);
                    }
                }
            }
        }
        // Set the width of each cell to the maximum width in its column
        for (ArrayList<DocumentCell> row : grid) {
            for (int i = 0; i < row.size(); i++) {
                DocumentCell cell = row.get(i);
                int width = columnWidths.get(i);
                cell.setWidth(width);
                // The x-position needs to be updated too. This is the x-pos of the table + the widths of all the cells to this cell's left
                int offset = 0;
                for (int j = 0; j < i; j++) offset += columnWidths.get(j);
                cell.setxPos(getxPos()+offset);

            }
        }
        // Set the width of the table to the max width of the table
        setWidth(getMaxWidth());
    }

    /**
     * Set the x position of this Table to the given value
     *
     * @param xPos:
     *            The new x position this Table will be set to.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        setColumnWidths();
    }

//    /**
//     * Reset the {@code rowHeights} and {@code columnWidths} to empty lists.
//     */
//    private void resetWidthsHeights() {
//        rowHeights = new ArrayList<>(); // Contains the height for each row
//        columnWidths = new ArrayList<>(); // Contains the width for each column
//    }

    /**
     * Set the y position of this Table to the given value
     *
     * @param yPos:
     *            The new y position this Table will be set to.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        setRowHeights();
    }

    /**
     * simple getter, only for debug purposes
     *
     * @return grid:
     *          The grid that holds the {@link DocumentCell}'s of this UITable.
     *
     */
    public ArrayList<ArrayList<DocumentCell>> getContent() {
    	return this.grid;
    }

    /**
     * An {@link ArrayList} that contains {@link ArrayList}'s
     * that on their turn contain {@link DocumentCell}'s to
     * denote the cells of this table.
     */
    private ArrayList<ArrayList<DocumentCell>> grid;

    /**
     * An {@link ArrayList} that contains integers that
     * represent the heights of the rows in this Table.
     */
    private ArrayList<Integer> rowHeights = new ArrayList<>(); // Contains the height for each row

    /**
     * An {@link ArrayList} that contains integers that
     * represent the widths of the columns of this Table.
     */
    private ArrayList<Integer> columnWidths = new ArrayList<>(); // Contains the width for each column
}
