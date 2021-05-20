package userinterface;

import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

public class GenericSplitPaneTest {

    private Browsr browsr;
    private Pane rootPane;
    private UIController controller;
    private Pane lowerPane;
    private Pane upperPane;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final String formUrl = "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html";

    @BeforeEach
    public void init() {
        browsr = new Browsr("browsr");
        rootPane = browsr.getDocumentArea();
        controller = rootPane.getController();
    }

    @Test
    @DisplayName("Get horizontal split")
    public void getHorizontalSplit() {
        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        assertTrue(hsp instanceof HorizontalSplitPane);
        assertTrue(hsp.getFirstChild().hasFocus());
        assertFalse(hsp.getSecondChild().hasFocus());
    }

    @Test
    @DisplayName("Get vertical split")
    public void getVerticalSplit() {
        Pane vsp = browsr.getDocumentArea().getVerticalSplit();
        assertTrue(vsp instanceof VerticalSplitPane);
        assertTrue(vsp.getFirstChild().hasFocus());
        assertFalse(vsp.getSecondChild().hasFocus());
    }

    @Test
    @DisplayName("Can handle mouse after horizontal split")
    public void handleMouseHorizontalSplit() {
        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        Pane secondChild = hsp.getSecondChild();
        assertFalse(secondChild.hasFocus());
        hsp.handleMouse(MouseEvent.MOUSE_CLICKED, secondChild.getxPos()+5, secondChild.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        assertTrue(secondChild.hasFocus());
    }

    @Test
    @DisplayName("Can handle mouse after vertical split")
    public void handleMouseVerticalSplit() {
        Pane vsp = browsr.getDocumentArea().getVerticalSplit();
        Pane secondChild = vsp.getSecondChild();
        assertFalse(secondChild.hasFocus());
        vsp.handleMouse(MouseEvent.MOUSE_CLICKED, secondChild.getxPos()+5, secondChild.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        assertTrue(secondChild.hasFocus());
    }

    @Test
    @DisplayName("Can set Controller")
    public void canSetController() {
        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        assertSame(hsp.getFirstChild().getController(), controller);
        assertSame(hsp.getSecondChild().getController(), controller);
        UIController controller2 = new UIController();
        hsp.setController(controller2);
        assertSame(hsp.getFirstChild().getController(), controller2);
        assertSame(hsp.getSecondChild().getController(), controller2);
    }

    @Test
    @DisplayName("Can set parent heights and widths")
    public void setParentDimensions() {
        int newValue = 7;
        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        hsp.setParentHeight(newValue);
        assertEquals(hsp.parentHeight, newValue);
        assertEquals(hsp.getFirstChild().parentHeight, newValue);
        assertEquals(hsp.getSecondChild().parentHeight, newValue);
        hsp.setParentWidth(newValue);
        assertEquals(hsp.parentWidth, newValue);
        assertEquals(hsp.getFirstChild().parentWidth, newValue);
        assertEquals(hsp.getSecondChild().parentWidth, newValue);
    }

    @Test
    @DisplayName("Can handles resizes")
    public void canHandleResizes() {
        int newWidth = 10;
        int newHeight = 10;

        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        hsp.handleResize(10, 10);
        assertEquals(hsp.getFirstChild().getWidth(), newWidth);
        assertEquals(hsp.getFirstChild().getHeight(), newHeight/2);
        assertEquals(hsp.getSecondChild().getWidth(), newWidth);
        assertEquals(hsp.getSecondChild().getHeight(), newHeight/2);
        assertEquals(hsp.getWidth(), newWidth);
        assertEquals(hsp.getHeight(), newHeight);
    }

}
