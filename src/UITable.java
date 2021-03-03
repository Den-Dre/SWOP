import javax.print.Doc;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class UITable extends DocumentCell{
    public UITable(int x, int y, int width, int height, ArrayList<ArrayList<DocumentCell>> rows) {
        super(x, y, width, height);

        // => 1. Initialise the grid
        this.grid = rows;

        // => 2. Set the dimensions of the table contents
        setRowHeights();
        setColumnWidths();
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
    }

    @Override
    /*
    Render every cell in the grid.
     */
    public void Render(Graphics g) {
        resetWidthsHeights();
        setColumnWidths();
        setRowHeights();
        for (ArrayList<DocumentCell> row : grid) {
            for (DocumentCell cell : row) {
                cell.Render(g);
            }
        }
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
        resetWidthsHeights();
        setColumnWidths();
        setRowHeights();
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
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
        int i = 0;
        for (ArrayList<DocumentCell> row : grid) {
            int max = 0;
            for (DocumentCell cell : row) {
                int height = cell.getMaxHeight();
                if (height > max) max = height;
            }
            rowHeights.add(max);
            for (DocumentCell cell : row) {
                int offset = 0;
                for (int j = 0; j < i; j++) offset += rowHeights.get(j);
                cell.setyPos(getyPos()+offset);
                cell.setHeight(max);
            }
            i++;
        }
    }

    /*
    This methods calculates the needed width of each column.
    It also sets the desired width and x-position of the cells in the table.
     */
    public void setColumnWidths() {
        for (ArrayList<DocumentCell> row : grid) {
            for (int i = 0; i < row.size(); i++) {
                DocumentCell cell = row.get(i);
                int width = cell.getMaxWidth();
                if (columnWidths.size() <= i)
                    columnWidths.add(width);
                else {
                    if (columnWidths.get(i) < width)
                        columnWidths.remove(i);
                        columnWidths.add(i, width);
                }
            }
        }
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

    private void resetWidthsHeights() {
        rowHeights = new ArrayList<>(); // Contains the height for each row
        columnWidths = new ArrayList<>(); // Contains the width for each column
    }

    private ArrayList<ArrayList<DocumentCell>> grid = new ArrayList<>();
    private ArrayList<Integer> rowHeights = new ArrayList<>(); // Contains the height for each row
    private ArrayList<Integer> columnWidths = new ArrayList<>(); // Contains the width for each column

}
