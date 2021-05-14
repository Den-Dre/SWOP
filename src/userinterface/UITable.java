package userinterface;

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
     * @throws IllegalDimensionException: When negative dimensions are supplied.
     */
    public UITable(int x, int y, int width, int height, ArrayList<ArrayList<DocumentCell>> rows) throws IllegalDimensionException {
        super(x, y, width, height);

        // => 1. Initialise the grid
        this.grid = rows;

        // => 2. Set the dimensions of the table contents
        setColumnWidths();
        setRowHeights();

        setxReference(x);
        setyReference(y);
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
        if (isCalculateActualWidth()) {
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
     *
     * @return result: A {@link ReturnMessage} containing the appropriate result based on
     *                  which {@link DocumentCell} contained in this {@code UITable} was clicked.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        ReturnMessage result;
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                // Let all the cells handle their click, and if the click ended up on a hyperlink, the href is passed into result
                result = cell.getHandleMouse(id, x, y, clickCount, button, modifier);
                if (result.getType() != ReturnMessage.Type.Empty) return result;
            }
        }
        return new ReturnMessage(ReturnMessage.Type.Empty);
    }

    /**
     * Send the given KeyEvent to the {@code DocumentCells} of this {@code UITable}.
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode: The KeyEvent code (Determines the involved key)
     * @param keyChar: The character representation of the involved key
     * @param modifiersEx: Specifies other keys that were involved in the event
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                cell.handleKey(id, keyCode, keyChar, modifiersEx);
            }
        }
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
        int maxHeight = 0;
        for (int height : rowHeights)
            maxHeight += height + verticalOffset;
        return maxHeight;
    }

    /**
     * Returns the maximum width of the table.
     * Only returns a useful answer after colling setColumnWidths() to set the used ArrayList
     */
    @Override
    public int getMaxWidth() {
        int maxWidth = 0;
        for (int width : columnWidths)
            maxWidth += width + horizontalOffset;
        return maxWidth;
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
                for (int j = 0; j < i; j++) offset += rowHeights.get(j)+verticalOffset;
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
                else if (columnWidths.get(i) < width)
                    // If it is wider, replace with newly found (wider) width
                    columnWidths.set(i, width);
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
                for (int j = 0; j < i; j++) offset += columnWidths.get(j)+horizontalOffset;
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
        //setxReference(xPos);
    }

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
        //setyReference(yPos);
    }

    @Override
    public void setxReference(int xReference) {
        //super.setxReference(xReference);
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                cell.setxReference(xReference);
            }
        }
    }

    @Override
    public void setyReference(int yReference) {
        //super.setyReference(yReference);
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                cell.setyReference(yReference);
            }
        }
    }

//    @Override
//    public void setxOffset(int xOffset) {
//        super.setxOffset(xOffset);
//        for (ArrayList<DocumentCell> row : grid) {
//            for (DocumentCell cell : row) {
//                cell.setxOffset(xOffset);
//            }
//        }
//    }
//
//    @Override
//    public void setyOffset(int yOffset) {
//        super.setyOffset(yOffset);
//        for (ArrayList<DocumentCell> row : grid) {
//            for (DocumentCell cell : row) {
//                cell.setyOffset(yOffset);
//            }
//        }
//    }

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
     * Get a list of the names/value pairs of the {@code DocumentCells} contained
     * in this {@code UITable}.
     *
     * @return An ArrayList with the name-value pairs of the {@code DocumentCells}.
     */
    @Override
    public ArrayList<String> getNamesAndValues() {
        ArrayList<String> namesAndValues = new ArrayList<>();
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                namesAndValues.addAll(cell.getNamesAndValues());
            }
        }
        return namesAndValues;
    }


    /**
     * An integer variable to denote a horizontal
     * offset that is added to each width of each
     * {@link DocumentCell} in this {@code UITable}.
     *
     * <p>
     * This is needed to properly separate
     * {@link UITextInputField}'s from adjacent
     * {@link UITextField}'s, such as presented in
     * a {@link BookmarksDialog}.
     * </p>
     */
    int verticalOffset = 3;

    /**
     * An integer variable to denote a vertical
     * offset that is added to each width of each
     * {@link DocumentCell} in this {@code UITable}.
     *
     * <p>
     * This is needed to properly separate
     * {@link UITextInputField}'s from adjacent
     * {@link UITextField}'s, such as presented in
     * a {@link BookmarksDialog}.
     * </p>
     */
    int horizontalOffset = 3;

    /**
     * An {@link ArrayList} that contains {@link ArrayList}'s
     * that on their turn contain {@link DocumentCell}'s to
     * denote the cells of this table.
     */
    private final ArrayList<ArrayList<DocumentCell>> grid;

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
