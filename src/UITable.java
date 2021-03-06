import javax.print.Doc;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UITable extends DocumentCell{

    /**
     * Create a {@code UITable} based on the given parameters.
     *
     * @param x: The x coordinate of this {@code UITable}.
     * @param y: The y coordinate of this {@code UITable}.
     * @param width: The width of this {@code UITable}.
     * @param height: The height of this {@code UITable}.
     * @param rows: The number of rows of this {@code UITable}.
     */
    public UITable(int x, int y, int width, int height, ArrayList<ArrayList<DocumentCell>> rows) {
        super(x, y, width, height);

        // => 1. Initialise the grid
        this.grid = rows;

        // => 2. Set the dimensions of the table contents
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
        setRowHeights();
        setColumnWidths();
    }

    /**
     * Render every cell in the grid.
     *
     * @param g: The graphics to be updated.
     */
    @Override
    public void Render(Graphics g) {
        resetWidthsHeights();
        setColumnWidths();
        setRowHeights();
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                cell.Render(g);
            }
        }
        resetWidthsHeights();
        setColumnWidths();
        setRowHeights();
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
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
        resetWidthsHeights();
        setColumnWidths();
        setRowHeights();
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
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
        int i = 0;
        // Calculate the maximum height of the grid
        for (ArrayList<DocumentCell> row : grid) {
            int max = 0;
            for (DocumentCell cell : row) {
                int height = cell.getMaxHeight();
                if (height > max) max = height;
            }
            rowHeights.add(max);

            // Place each cell at the correct y position by summing the heights of the cells underneath
            for (DocumentCell cell : row) {
                int offset = 0;
                for (int j = 0; j < i; j++) offset += rowHeights.get(j);
                cell.setyPos(getyPos()+offset);
                cell.setHeight(max);
            }
            i++;
        }
    }

    /**
     * This methods calculates the needed width of each column.
     * It also sets the desired width and x-position of the cells in this {@code UITable}.
     */
    public void setColumnWidths() {
        // Add and update cell widths.
        for (ArrayList<DocumentCell> row : grid) {
            for (int i = 0; i < row.size(); i++) {
                DocumentCell cell = row.get(i);
                int width = cell.getMaxWidth();
                if (columnWidths.size() <= i) // Add cell width.
                    columnWidths.add(width);
                else { // Update cell width
                    if (columnWidths.get(i) < width)
                        columnWidths.remove(i);
                        columnWidths.add(i, width);
                }
            }
        }

        // Place each cell of the grid at the correct x position by summing the widths of the preceding cells.
        for (ArrayList<DocumentCell> row : grid) {
            for (int i = 0; i < row.size(); i++) {
                int offset = 0;
                for (int j = 0; j < i; j++) offset += columnWidths.get(j);
                int width = columnWidths.get(i);
                DocumentCell cell = row.get(i);
                cell.setxPos(getxPos()+offset);
                cell.setWidth(width);
            }
        }
    }

    /**
     * Reset the {@code rowHeights} and {@code columnWidths} to empty lists.
     */
    private void resetWidthsHeights() {
        rowHeights = new ArrayList<>(); // Contains the height for each row
        columnWidths = new ArrayList<>(); // Contains the width for each column
    }

    private ArrayList<ArrayList<DocumentCell>> grid;
    private ArrayList<Integer> rowHeights = new ArrayList<>(); // Contains the height for each row
    private ArrayList<Integer> columnWidths = new ArrayList<>(); // Contains the width for each column

}
