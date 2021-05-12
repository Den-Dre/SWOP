package userinterface;

import domainlayer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarksBarTest {

    private LeafPane area;
    private AddressBar addressBar;
    private BookmarksBar bookmarksBar;
    private UIController controller;
    private final String tableUrl = "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html";
    private final String badUrl = "www.fout.be";

    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final int mouseClick = MouseEvent.MOUSE_RELEASED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int shiftModifier = KeyEvent.SHIFT_DOWN_MASK;

    @BeforeEach
    void setup(){
        int bookmarksBarOffset = 5;
        int addressBarOffset = 5;
        int addressBarHeight = 20;
        int bookmarksBarHeight = 20;

        // Make a total browsr without the gui class (=browsr.java)
        addressBar = new AddressBar(addressBarOffset, addressBarOffset, 100, addressBarHeight, addressBarOffset);
        bookmarksBar = new BookmarksBar(bookmarksBarOffset, addressBarHeight + 2 * bookmarksBarOffset, 100, bookmarksBarHeight, bookmarksBarOffset);
        area = new LeafPane(addressBarOffset, 2 * (addressBarHeight + 2 * addressBarOffset), 100, 100);
        controller = new UIController(); // The document is created within uicontroller
        // Couple the uicontoller to the documentarea and addressbar
        area.setController(controller);
        addressBar.setUiController(controller);
        bookmarksBar.setUIController(controller);
        // Couple the document with the documentarea and addressbar
        controller.addDocumentListener(area);
        controller.addUrlListener(addressBar);
    }

    @Test
    @DisplayName("Can add bookmark")
    void addBookmark() {
        String name = "Test";
        String url = "https://www.test.be";
        bookmarksBar.addBookmark(name, url);
        assertEquals(1, bookmarksBar.getTextHyperLinks().size());
        assertEquals(url, controller.getURLFromBookmark(name));
    }

    @Test
    @DisplayName("Can click on correct bookmark")
    void handleBookmarkClick() {
        String name = "Table";
        bookmarksBar.addBookmark(name, tableUrl);
        UITextHyperlink bookmark = bookmarksBar.getTextHyperLinks().get(0);
        // Click on newly added bookmark
        bookmarksBar.handleMouse(mouseClick, bookmark.getxPos()+1, bookmark.getyPos()+1, 1, leftMouse, 0);
        // Table from example should be loaded
        assertEquals(tableUrl, addressBar.getText());
        verifyUIContents(area.getContent());
    }

    @Test
    @DisplayName("Can click on incorrect bookmark")
    void handleIncorrectBookmarkClick() {
        String name = "Incorrect";
        bookmarksBar.addBookmark(name, badUrl);
        UITextHyperlink bookmark = bookmarksBar.getTextHyperLinks().get(0);
        assertEquals(bookmark.getText(), name);
        assertEquals(badUrl, controller.getURLFromBookmark(name));
        // Click on newly added bookmark
        bookmarksBar.handleMouse(mouseClick, bookmark.getxPos()+1, bookmark.getyPos()+1, 1, leftMouse, 0);
        // Error document should be loaded
        assertEquals(badUrl, addressBar.getText());
        errorDocumentIsLoaded();
    }

    public void verifyUIContents(DocumentCell cell) {

        assertNotNull(cell);
        UITable outerTable = ((UITable) ((HorizontalScrollBarDecorator) cell).getContent());
        List<DocumentCell> outerRow1 = outerTable.getContent().get(0);
        assertEquals("HTML elements partially supported by Browsr:", ((UITextField) outerRow1.get(0)).getText());

        List<DocumentCell> innerRow1 = outerTable.getContent().get(1);
        UITable innerTable = (UITable) innerRow1.get(0);
        ArrayList<ArrayList<DocumentCell>> grid = innerTable.getContent();
        ArrayList<DocumentCell> aElementRow = grid.get(0);
        ArrayList<DocumentCell> tableElementRow = grid.get(1);
        ArrayList<DocumentCell> trElementRow = grid.get(2);
        ArrayList<DocumentCell> tdElementRow = grid.get(3);

        assertEquals("a", ((UIHyperlink) aElementRow.get(0)).getText());
        assertEquals("a.html", ((UIHyperlink) aElementRow.get(0)).getHref());
        assertEquals("Hyperlink anchors", ((UITextField) aElementRow.get(1)).getText());

        assertEquals("table", ((UIHyperlink) tableElementRow.get(0)).getText());
        assertEquals("table.html", ((UIHyperlink) tableElementRow.get(0)).getHref());
        assertEquals("Tables", ((UITextField) tableElementRow.get(1)).getText());

        assertEquals("tr", ((UIHyperlink) trElementRow.get(0)).getText());
        assertEquals("tr.html", ((UIHyperlink) trElementRow.get(0)).getHref());
        assertEquals("Table rows", ((UITextField) trElementRow.get(1)).getText());

        assertEquals("td", ((UIHyperlink) tdElementRow.get(0)).getText());
        assertEquals("td.html", ((UIHyperlink) tdElementRow.get(0)).getHref());
        assertEquals("Table cells containing table data", ((UITextField) tdElementRow.get(1)).getText());
    }

    private void errorDocumentIsLoaded() {
        TextSpan error = (TextSpan) Document.getErrorDocument();
        UITable table = (UITable) ((DocumentCellDecorator) area.getContent()).getContentWithoutScrollbars();
        UITextField areaText = (UITextField) table.getContent().get(0).get(0);
        assertEquals(error.getText(), areaText.getText());
    }
}
