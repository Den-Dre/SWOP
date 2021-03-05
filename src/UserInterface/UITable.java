package UserInterface;

import java.awt.*;
import java.util.ArrayList;

public class UITable extends DocumentCell{
    public UITable(int x, int y, int width, int height, ArrayList<ArrayList<DocumentCell>> rows) {
        super(x, y, width, height);

        // => 1. Initialise the grid
        this.grid = rows;

        // => 2. Set the dimensions of the table contents
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
    }

    @Override
    /*
    Render every cell in the grid.
     */
    public void Render(Graphics g) {
        setColumnWidths();
        setRowHeights();
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
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    @Override
    /*
    Handle mouse events by forwarding the click to each cell.
    If a cell returns something (=href) this method returns this.
    Else, it returns the empty string (= "")
     */
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        String result = "";
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                // Let all the cells handle their click, and if the click ended up on a hyperlink, the href is passed into result
                result = cell.getHandleMouse(id, x, y, clickCount, button, modifier);
                if (!result.equals("")) return result;
            }
        }
        return result;
    }

    @Override
    /*
    Re-calculates the necessary widths and heights of the DocumentCells
     */
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setColumnWidths();
        setRowHeights();
    }

    @Override
    /*
    Returns the maximum height of the table.
    Only returns a usefull answer after calling setRowHeights() to set the used ArrayList
     */
    public int getMaxHeight() {
        int maxheight = 0;
        for (int height :  rowHeights)
            maxheight += height;
        return maxheight;
    }

    @Override
    /*
    Returns the maximum width of the table.
    Only returns a useful answer after colling setColumnWidths() to set the used ArrayList
     */
    public int getMaxWidth() {
        int maxwidth = 0;
        for (int width :  columnWidths)
            maxwidth += width;
        return maxwidth;
    }

    /*
    This methods calculates the needed height of each row.
    It also sets the desired height and y-position of the cells in the table.
     */
    public void setRowHeights() {
        // Reset the array with the heights
        rowHeights = new ArrayList<>();
        // Iterate over the rows to find the highest cell in each row
        int i = 0;
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

    /*
    This methods calculates the needed width of each column.
    It also sets the desired width and x-position of the cells in the table.
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
                    if (columnWidths.get(i) < width)
                        // If it is wider, replace with newly found (wider) width
                        columnWidths.remove(i);
                        columnWidths.add(i, width);
                }
            }
        }
        // Set the width of each cell to the maximum width in its column
        for (ArrayList<DocumentCell> row : grid) {
            for (int i = 0; i < row.size(); i++) {
                DocumentCell cell = row.get(i);
                int width = columnWidths.get(i);
                cell.setWidth(width);
                // The x-position needs to be updated too. This is the x-pos of the table + the widths of all the cells to this cells left
                int offset = 0;
                for (int j = 0; j < i; j++) offset += columnWidths.get(j);
                cell.setxPos(getxPos()+offset);

            }
        }
        // Set the width of the table to the max width of the table
        setWidth(getMaxWidth());
    }

    private ArrayList<ArrayList<DocumentCell>> grid = new ArrayList<>();
    private ArrayList<Integer> rowHeights = new ArrayList<>(); // Contains the height for each row
    private ArrayList<Integer> columnWidths = new ArrayList<>(); // Contains the width for each column

}
