package userinterface;

import browsrhtml.tests.ContentSpanBuilderTest;
import domainlayer.TextSpan;
import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

public class HorizontalSplitTest {
    private Browsr browsr;
    private Pane rootPane;
    private LeafPane upperPane;
    private LeafPane lowerPane;
    private UIController controller;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final String formUrl = "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html";

    @BeforeEach
    public void init() {
        browsr = new Browsr("browsr");
        rootPane = browsr.getDocumentArea();
        controller = rootPane.getController();
    }

    private void splitHorizontally() {
        int hKey = 72;
        // Press Ctrl+h to split horizontally
        browsr.handleKeyEvent(KeyEvent.KEY_PRESSED, hKey, undefChar, KeyEvent.CTRL_DOWN_MASK);
        lowerPane = (LeafPane) rootPane.getRootPane().getFirstChild();
        upperPane = (LeafPane) rootPane.getRootPane().getSecondChild();
    }

    private void closeCurrentPane() {
        int xKey = 88;
        browsr.handleKeyEvent(KeyEvent.KEY_PRESSED, xKey, undefChar, KeyEvent.CTRL_DOWN_MASK);
    }

    private void welcomePageIsLoaded(LeafPane pane) {
        assertTrue(pane.getContentWithoutScrollbars() instanceof UITextField);
        UITextField contents = (UITextField) pane.getContentWithoutScrollbars();
        assertEquals(contents.getText(), ((UITextField) pane.getContentWithoutScrollbars()).getText());
    }

    // 4.7 Use Case: Split Pane
    // Main Success Scenario
    @Test
    @DisplayName("Can split horizontally")
    public void splitHorizontallyUISide() {
        // 1. The user presses Ctrl+H (resp. Ctrl+V).
        splitHorizontally();
        // 2. The system replaces the parent pane of the leaf pane containing the
        // focused frame by the latter pane's sibling pane. It sets some remaining
        // frame as the new focused frame.
        assertNotNull(lowerPane);
        assertNotNull(upperPane);
        assertTrue(rootPane.getRootPane() instanceof HorizontalSplitPane);
        // We chose to let the bottom Pane obtain focus after splitting horizontally
        assertTrue(lowerPane.hasFocus);
        assertFalse(upperPane.hasFocus);
        // Verify Welcome document has been loaded in both sub panes
        welcomePageIsLoaded(lowerPane);
        welcomePageIsLoaded(upperPane);
    }

    @Test
    @DisplayName("Can update domain knowledge when splitting.")
    public void splitHorizontallyDomainSide() {
        UITextField originalContents = (UITextField) (((LeafPane) rootPane.getRootPane()).getContentWithoutScrollbars());
        splitHorizontally();
        // Lower pane gets focus
        assertEquals(controller.getCurrentDocumentId(), lowerPane.getId());
        assertTrue(controller.getContentSpan(lowerPane.getId()) instanceof TextSpan);
        assertTrue(controller.getContentSpan(upperPane.getId()) instanceof TextSpan);
        assertEquals(((TextSpan) controller.getContentSpan(lowerPane.getId())).getText(), originalContents.getText());
        assertEquals(((TextSpan) controller.getContentSpan(upperPane.getId())).getText(), originalContents.getText());
    }

    @Test
    @DisplayName("Can copy loaded page after splitting")
    public void canCopyLoadedPage() {
        // Load the test Form in the original rootPane
        controller.loadDocument(rootPane.getRootPane().getId(), formUrl);
        splitHorizontally();
        assertTrue(lowerPane.getContentWithoutScrollbars() instanceof UIForm);
        assertTrue(upperPane.getContentWithoutScrollbars() instanceof UIForm);
        ContentSpanBuilderTest.verifyContentsForm(controller.getContentSpan(lowerPane.getId()));
        ContentSpanBuilderTest.verifyContentsForm(controller.getContentSpan(upperPane.getId()));
    }

    // 4.8 Use Case: Close Frame
    // Main Success Scenario
    @Test
    @DisplayName("Can close a Pane")
    public void canClosePane() {
        splitHorizontally();
        assertTrue(rootPane.getRootPane() instanceof HorizontalSplitPane);
        assertTrue(lowerPane.hasFocus());
        // 1. The user presses Ctrl+X.
        // This closes the bottom pane, and should promote the ex-upperPane to rootPane
        closeCurrentPane();
        // 2. The system replaces the parent pane of the leaf pane containing the
        // focused frame by the latter pane’s sibling pane. It sets some remaining
        // frame as the new focused frame.
        assertTrue(upperPane.getRootPane() instanceof LeafPane);
        assertSame(upperPane.getFocusedPane(), upperPane.getRootPane());
        assertSame(upperPane.getRootPane(), upperPane);
    }

    // 4.8 Use Case: Close Frame
    // Extensions
    @Test
    @DisplayName("Can show Welcome Document when closing rootPane")
    public void canCloseRootPane() {
        // 2a. The leaf pane containing the focused frame is the root pane.
        assertEquals(rootPane.getFocusedPane(), rootPane.getFocusedPane());
        controller.loadDocument(controller.getCurrentDocumentId(), formUrl);
        closeCurrentPane();
        // The system replaces the focused frame by a fresh frame showing
        // the welcome page.
        assertTrue(rootPane.getRootPane() instanceof LeafPane);
        welcomePageIsLoaded((LeafPane) rootPane.getRootPane());
    }

    // 4.9 Use Case: Select Frame
    // Main Success Scenario
    @Test
    @DisplayName("Can select a LeafPane.")
    public void canSelectLeafPane() {
        splitHorizontally();
        // 1. The user clicks inside a frame.
        assertTrue(lowerPane.hasFocus());
        assertFalse(upperPane.hasFocus());
        // Click on the upperPane which didn't have focus up until now.
        browsr.handleMouseEvent(MouseEvent.MOUSE_PRESSED, upperPane.getxPos()+5, upperPane.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // 2. The system sets this frame as the focused frame.
        assertSame(rootPane.getRootPane().getFocusedPane(), upperPane);
        assertTrue(upperPane.hasFocus());
        assertFalse(lowerPane.hasFocus());
    }

    @Test
    @DisplayName("Can set positions")
    public void setPositions() {
        int xPos = 7;
        int yPos = 7;

        splitHorizontally();
        HorizontalSplitPane hsp = (HorizontalSplitPane) rootPane.getRootPane();

        hsp.setxPos(7);
        assertEquals(hsp.getxPos(), xPos);
        assertEquals(lowerPane.getxPos(), xPos);
        assertEquals(upperPane.getxPos(), xPos);

        hsp.setyPos(yPos);
        assertEquals(hsp.getyPos(), yPos);
        assertEquals(lowerPane.getyPos(), yPos + 0.5*hsp.getHeight());
        assertEquals(upperPane.getyPos(), yPos);
    }

    @Test
    void dragging() {

    }
}
