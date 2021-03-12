package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DocumentCell")
class DocumentCellTest {

    int x = 0;
    int y = 0;
    int width = 100;
    int height = 100;
    private DocumentCell cell;
    private int mousePressed = MouseEvent.MOUSE_PRESSED;
    private int leftmouse = MouseEvent.BUTTON1;

    @BeforeEach
    void setup() throws Exception {
        cell = new DocumentCell(x,y,width,height);
    }

    @Test
    @DisplayName("can determine if it was clicked")
    void wasClicked() {
        // no click on the cell
        assertFalse(cell.wasClicked(10,110));
        assertFalse(cell.wasClicked(110,10));
        // click on the cell
        assertTrue(cell.wasClicked(10,10));
    }

    @Test
    @DisplayName("returns standard empty string when clicked")
    void getHandleMouse() {
        assertEquals("", cell.getHandleMouse(mousePressed, x+1, y+1, 1, leftmouse,0));
    }

    @Test
    @DisplayName("returns standard 1 as max-height")
    void getMaxHeight() {
        assertEquals(1, cell.getMaxHeight());
    }

    @Test
    @DisplayName("returns standard 1 as max-width")
    void getMaxWidth() {
        assertEquals(1, cell.getMaxWidth());
    }

    @Test
    @DisplayName("height-to-width ratio is not 0 or negative")
    void getHeightToWidthRatio() {
        assertTrue(cell.getHeightToWidthRatio() >= 0);
    }

    @Test
    void handleMouse() {
        cell.handleMouse(mousePressed, x+1, y+1, 1, leftmouse,0);
        // it should do nothing, this behaviour must be specified in subclasses
    }

    @Test
    void handleResize() {
        cell.handleResize(100,100);
        // it should do nothing, this behaviour must be specified in subclasses
    }
}