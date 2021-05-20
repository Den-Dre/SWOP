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

public class VerticalSplitTest {
    private Browsr browsr;
    private Pane rootPane;
    private LeafPane rightPane;
    private LeafPane leftPane;
    private UIController controller;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final String formUrl = "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html";

    @BeforeEach
    public void init() {
        browsr = new Browsr("browsr");
        rootPane = browsr.getDocumentArea();
        controller = rootPane.getController();
    }

    private void splitVertically() {
        int vKey = 86;
        // Press Ctrl+v to split vertically
        browsr.handleKeyEvent(KeyEvent.KEY_PRESSED, vKey, undefChar, KeyEvent.CTRL_DOWN_MASK);
        leftPane = (LeafPane) rootPane.getRootPane().getFirstChild();
        rightPane = (LeafPane) rootPane.getRootPane().getSecondChild();
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
    @DisplayName("Can split vertically")
    public void splitVerticallyUISide() {
        // 1. The user presses Ctrl+H (resp. Ctrl+V).
        splitVertically();
        // 2. The system replaces the parent pane of the leaf pane containing the
        // focused frame by the latter pane's sibling pane. It sets some remaining
        // frame as the new focused frame.
        assertNotNull(leftPane);
        assertNotNull(rightPane);
        assertTrue(rootPane.getRootPane() instanceof VerticalSplitPane);
        // We chose to let the bottom Pane obtain focus after splitting vertically
        assertTrue(leftPane.hasFocus);
        assertFalse(rightPane.hasFocus);
        // Verify Welcome document has been loaded in both sub panes
        welcomePageIsLoaded(leftPane);
        welcomePageIsLoaded(rightPane);
    }

    @Test
    @DisplayName("Can update domain knowledge when splitting.")
    public void splitHorizontallyDomainSide() {
        UITextField originalContents = (UITextField) (((LeafPane) rootPane.getRootPane()).getContentWithoutScrollbars());
        splitVertically();
        // Left pane gets focus
        assertEquals(controller.getCurrentDocumentId(), leftPane.getId());
        assertTrue(controller.getContentSpan(leftPane.getId()) instanceof TextSpan);
        assertTrue(controller.getContentSpan(rightPane.getId()) instanceof TextSpan);
        assertEquals(((TextSpan) controller.getContentSpan(leftPane.getId())).getText(), originalContents.getText());
        assertEquals(((TextSpan) controller.getContentSpan(rightPane.getId())).getText(), originalContents.getText());
    }

    @Test
    @DisplayName("Can copy loaded page after splitting")
    public void canCopyLoadedPage() {
        // Load the test Form in the original rootPane
        controller.loadDocument(rootPane.getRootPane().getId(), formUrl);
        splitVertically();
        assertTrue(leftPane.getContentWithoutScrollbars() instanceof UIForm);
        assertTrue(rightPane.getContentWithoutScrollbars() instanceof UIForm);
        ContentSpanBuilderTest.verifyContentsForm(controller.getContentSpan(leftPane.getId()));
        ContentSpanBuilderTest.verifyContentsForm(controller.getContentSpan(rightPane.getId()));
    }

    // 4.8 Use Case: Close Frame
    // Main Success Scenario
    @Test
    @DisplayName("Can close a Pane")
    public void canClosePane() {
        splitVertically();
        assertTrue(rootPane.getRootPane() instanceof VerticalSplitPane);
        assertTrue(leftPane.hasFocus());
        // 1. The user presses Ctrl+X.
        // This closes the bottom pane, and should promote the ex-rightPane to rootPane
        closeCurrentPane();
        // 2. The system replaces the parent pane of the leaf pane containing the
        // focused frame by the latter pane’s sibling pane. It sets some remaining
        // frame as the new focused frame.
        assertTrue(rootPane.getRootPane() instanceof LeafPane);
        assertSame(rightPane.getFocusedPane(), rightPane.getRootPane());
        assertSame(rightPane.getRootPane(), rightPane);
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
        splitVertically();
        // 1. The user clicks inside a frame.
        assertTrue(leftPane.hasFocus());
        assertFalse(rightPane.hasFocus());
        // Click on the rightPane which didn't have focus up until now.
        browsr.handleMouseEvent(MouseEvent.MOUSE_PRESSED, rightPane.getxPos()+5, rightPane.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // 2. The system sets this frame as the focused frame.
        assertSame(rootPane.getRootPane().getFocusedPane(), rightPane);
        assertTrue(rightPane.hasFocus());
        assertFalse(leftPane.hasFocus());
    }

    @Test
    @DisplayName("Can set positions")
    public void setPositions() {
        int xPos = 7;
        int yPos = 7;

        splitVertically();
        VerticalSplitPane hsp = (VerticalSplitPane) rootPane.getRootPane();

        hsp.setxPos(7);
        assertEquals(hsp.getxPos(), xPos);
        assertEquals(leftPane.getxPos(), xPos);
        assertEquals(rightPane.getxPos(), xPos + 0.5*hsp.getWidth());

        hsp.setyPos(yPos);
        assertEquals(hsp.getyPos(), yPos);
        assertEquals(leftPane.getyPos(), yPos);
        assertEquals(rightPane.getyPos(), yPos);
    }

    @Test
    void dragging() {
        final int mousePressed = MouseEvent.MOUSE_PRESSED;
        final int mouseDragged = MouseEvent.MOUSE_DRAGGED;
        int leftMouse = MouseEvent.BUTTON1;

        splitVertically();
        int separatorX = rightPane.getxPos();
        int separatorY = rightPane.getyPos();
        int x1 = 1;
        int x2 = 5;
        browsr.handleMouseEvent(mousePressed, separatorX+x1, separatorY+1, 1, leftMouse, 0);
        browsr.handleMouseEvent(mouseDragged, separatorX+x2, separatorY+1, 1, leftMouse, 0);
        assertEquals(separatorX + x2 ,rightPane.getxPos());

        separatorX = rightPane.getxPos();
        separatorY = rightPane.getyPos();
        browsr.handleMouseEvent(mousePressed, separatorX+x1, separatorY+1, 1, leftMouse, 0);
        browsr.handleMouseEvent(mouseDragged, separatorX-x2, separatorY+1, 1, leftMouse, 0);
        assertEquals(separatorX - x2 ,rightPane.getxPos());


    }
}
