package userinterface;

import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenericSplitPaneTest {

    private Browsr browsr;
    private UIController controller;

    @BeforeEach
    public void init() {
        browsr = new Browsr("browsr");
        Pane rootPane = browsr.getDocumentArea();
        controller = rootPane.getController();
    }

    @Test
    @DisplayName("Get horizontal split")
    public void getHorizontalSplit() {
        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        Pane hsp2 = hsp.getHorizontalSplit();
        assertTrue(hsp instanceof HorizontalSplitPane);
        assertTrue(hsp2 instanceof HorizontalSplitPane);
        assertTrue(hsp2.hasFocus());
        assertTrue(hsp2.getFirstChild().hasFocus());
        assertFalse(hsp2.getSecondChild().hasFocus());
    }

    @Test
    @DisplayName("Get vertical split")
    public void getVerticalSplit() {
        Pane vsp = browsr.getDocumentArea().getVerticalSplit();
        Pane vsp2 = vsp.getVerticalSplit();
        assertTrue(vsp instanceof VerticalSplitPane);
        assertTrue(vsp2 instanceof VerticalSplitPane);
        assertTrue(vsp2.hasFocus());
        assertTrue(vsp2.getFirstChild().hasFocus());
        assertFalse(vsp2.getSecondChild().hasFocus());
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
    @DisplayName("Can handle contentChanged")
    public void canChangeContents() {
        Pane hsp = browsr.getDocumentArea().getHorizontalSplit();
        String url = "https://people.cs.kuleuven.be/~bart.jacobs/swop/browsrformtest.html";
        controller.loadDocument(hsp.getFirstChild().getId(), url);
        hsp.contentChanged();
        UITable table = (UITable) ((UIActionForm) ((DocumentCellDecorator) hsp.getFirstChild().getContent().getContent()).getContentWithoutScrollbars()).getFormContent();
        assertEquals(((UITextField) table.getContent().get(0).get(0)).getText(), "List words from the Woordenlijst Nederlandse Taal");
//        System.out.println(form);
    }
}
