package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UITable")
class UITableTest {

    private UITable table1;
    private UITextField textField1;
    private UITextField textField2;
    private UIHyperlink link;
    int textSize = 15;
    String href = "/wiki/Java";
    String hrefText = "Java";
    String text = "oefentext";
    String text2 = "aa";

    private final int mouseClick = MouseEvent.MOUSE_CLICKED;
    private final int leftMouse = MouseEvent.BUTTON1;

    @BeforeEach
    void setup() throws Exception {
        int x = 0;
        int y = 0;
        int width = 20;

        // construct Table:
        textField1 = new UITextField(x, y, width, textSize, text);
        textField2 = new UITextField(x, y, width, textSize, text2);
        link = new UIHyperlink(x,y, width, textSize, href,hrefText);
        ArrayList<DocumentCell> row = new ArrayList<>();
        ArrayList<DocumentCell> row2 = new ArrayList<>();
        ArrayList<ArrayList<DocumentCell>> tableContents = new ArrayList<>();
        row.add(textField1);
        row.add(textField2);
        row2.add(link);
        tableContents.add(row);
        tableContents.add(row2);
        table1 = new UITable(x,y,width, width, tableContents);
        assertFalse(table1.calculateActualWidth);
        /*
        table layout:
        ----------------
        | text | text2 |
        | link |       |
        ----------------
         */
    }

    @Test
    void getHandleMouse() {
        int linkX = link.getxPos();
        int linkY = link.getyPos();
        int linkWidth = link.getWidth();
        // Click outside the link-cell
        String result;
        result = table1.getHandleMouse(mouseClick, linkX+linkWidth+10, linkY,1, leftMouse,0);
        assertEquals("", result);
        // Click on the link-cell
        result = table1.getHandleMouse(mouseClick, linkX+linkWidth-1, linkY,1, leftMouse,0);
        assertEquals(href, result);
    }

    @Test
    @DisplayName("can calculate its maximum height")
    void getMaxHeight() {
        assertEquals(2*textSize, table1.getMaxHeight());
    }

    @Test
    @DisplayName("can calculate its maximum width")
    void getMaxWidth() {
        double ratio = table1.getHeightToWidthRatio();
        int width = (int) ((Math.max(text.length(), hrefText.length())+text2.length())*textSize*ratio);
        assertEquals(width, table1.getMaxWidth());
    }

    @Test
    @DisplayName("correctly sets the row heights and y-positions")
    void setRowHeights() {
        // Check if first row has correct height
        assertEquals(textSize, textField1.getHeight());
        assertEquals(textSize, textField2.getHeight());
        // Check if second row has correct height
        assertEquals(textSize, link.getHeight());

        // Check if y-positions are set correctly
        assertEquals(table1.getyPos(), textField1.getyPos());
        assertEquals(table1.getyPos(), textField2.getyPos());
        assertEquals(table1.getyPos()+textSize, link.getyPos());
    }

    @Test
    @DisplayName("correctly sets the column widths and x-positions")
    void setColumnWidths() {
        double ratio = table1.getHeightToWidthRatio();
        // Check if first column has correct width
        int width1 = (int) (Math.max(text.length(), hrefText.length()) * textSize * ratio);
        assertEquals(width1, textField1.getWidth());
        assertEquals(width1, link.getWidth());
        // Check if second column has correct width
        int width2 = (int) (text2.length() * textSize * ratio);
        assertEquals(width2, textField2.getWidth());

        // Check if x-positions are set correctly
        assertEquals(table1.getxPos(), textField1.getxPos());
        assertEquals(table1.getxPos(), link.getxPos());
        assertEquals(table1.getxPos()+width1, textField2.getxPos());
    }

    @Test
    @DisplayName("adjusts the x-position of it and its contents")
    void setxPos() {
        int newpos = 10;
        table1.setxPos(newpos);
        assertEquals(newpos, table1.getxPos());
        setColumnWidths();
    }

    @Test
    void setyPos() {
        int newypos = 10;
        table1.setyPos(newypos);
        assertEquals(newypos, table1.getyPos());
        setRowHeights();
    }
}