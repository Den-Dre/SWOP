package userinterface;

import domainlayer.UIController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AbstractFrame")
class AbstractFrameTest {

    @Test
    @DisplayName("can correctly initialize")
    void correctInit() {
        AbstractFrame pane = new LeafPane(new ContentFrame(0, 0, 10, 10), new UIController());
        assertFalse(pane.hasFocus);
        assertEquals(0, pane.getxPos());
        assertEquals(0, pane.getyPos());
        assertEquals(10, pane.getHeight());
        assertEquals(10, pane.getWidth());
    }

    @Test
    @DisplayName("can correctly toggle focus")
    void toggleFocus() {
        ContentFrame cf = new ContentFrame(0, 0, 10, 10);
        cf.setContent(new UITextField(0, 0, 10, 10, "Test"));
        AbstractFrame pane = new LeafPane(cf, new UIController());
        assertFalse(pane.hasFocus);
        pane.handleMouse(MouseEvent.MOUSE_CLICKED, pane.getxPos()+5, pane.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        assertTrue(pane.hasFocus);
    }

    @Test
    void getBackgroundColor() {
        AbstractFrame frame = new LeafPane(new ContentFrame(0, 0, 10, 10), new UIController());
        // Standard color should be white
        assertEquals(Color.WHITE, frame.getBackgroundColor());
    }

    @Test
    void wrongDimensionsFrame() {
        assertThrows(IllegalDimensionException.class, () -> new LeafPane(new ContentFrame(-1, 0, 10, 10), new UIController()));
    }

    @Test
    void outOfHorizontalBounds() {
        UITextField pane = new UITextField(5,5, 50, 15, "hallo");
        pane.setxReference(0);
        int textWidth = pane.getMaxWidth();
        pane.setWidth(pane.getMaxWidth());
        pane.setParentWidth(textWidth-10);
        assertTrue(pane.outOfHorizontalBounds());

        pane.setParentWidth(pane.getxPos()+textWidth);
        assertFalse(pane.outOfHorizontalBounds());

        pane.setxOffset(-10);
        assertTrue(pane.outOfHorizontalBounds());
    }

    @Test
    void outOfVerticalBounds() {
        UITextField pane = new UITextField(5,5, 50, 15, "hallo");
        pane.setyReference(0);
        int textHeight = pane.getMaxHeight();
        pane.setParentHeight(textHeight-5);
        assertTrue(pane.outOfVerticalBounds());

        pane.setParentHeight(pane.getyPos()+pane.getMaxHeight());
        assertFalse(pane.outOfVerticalBounds());

        pane.setyOffset(-10);
        assertTrue(pane.outOfVerticalBounds());
    }
}