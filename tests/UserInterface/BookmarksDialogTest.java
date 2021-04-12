package UserInterface;

import browsrhtml.ContentSpanBuilder;
import browsrhtml.tests.ContentSpanBuilderTest;
import domainmodel.ContentSpan;
import domainmodel.Document;
import domainmodel.TextSpan;
import domainmodel.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import javax.swing.plaf.synth.Region;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static org.junit.jupiter.api.Assertions.*;

// Unfortunately, we can't run Browsr in test files. So the tests for BookmarksDialog can't cover
// the entire use case of pressing CTRL+D etc. The tests provided underneath do test the internal
// functionality for the most part, however.
public class BookmarksDialogTest {

    private Browsr browsr;
    private AddressBar bar;
    private DocumentArea area;
    private BookmarksBar bookmarksBar;
    private UIController controller;

    private final String tableUrl = "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html";

    @BeforeEach
    void setUp() {
        int bookmarksBarOffset = 5;
        int addressBarOffset = 5;
        int addressBarHeight = 20;
        int bookmarksBarHeight = 20;

        bar = new AddressBar(addressBarOffset, addressBarOffset, 100, addressBarHeight, addressBarOffset);
        bookmarksBar = new BookmarksBar(bookmarksBarOffset, addressBarHeight + 2 * bookmarksBarOffset, 100, bookmarksBarHeight, bookmarksBarOffset);
        area = new DocumentArea(addressBarOffset, 2 * (addressBarHeight + 2 * addressBarOffset), 100, 100);

        // Couple the uicontoller to the documentarea and addressbar
        controller = new UIController(); // The document is created within uicontroller
        area.setController(controller);
        bar.setUiController(controller);
        bookmarksBar.setUIController(controller);

        // Couple the document with the documentarea and addressbar
        controller.addDocumentListener(area);
        controller.addUrlListener(bar);
        browsr = new Browsr("Browsr");
        bar = browsr.getAddressBar();
        area = browsr.getDocumentArea();
    }

    @Test
    @DisplayName("Handles CTRL+D correctly")
    void handleCtrlD() {
        bar.setText(tableUrl);
        assertTrue(browsr.getBrowsrLayout() instanceof Browsr.RegularLayout);
        assertNull(browsr.getBookmarksDialog());

        // Press CTRL+D to open Bookmarks dialog
//        browsr.handleKeyEvent(keyPress, 68, 'd', ctrlModifier); // Can't simulate a Browsr object in tests...
        BookmarksDialog dialog = new BookmarksDialog(100, 100, tableUrl, bookmarksBar, browsr);
        // URL should be pre-filled
        assertEquals(tableUrl, dialog.getURLInput());
        assertEquals("", dialog.getNameInput());

//        UIButton cancelButton = (UIButton) ((UITable) dialog.getForm().getFormContent()).getContent().get(1).get(1);
    }

    @Test
    @DisplayName("Can add bookmarks")
    void addBookmarkTest() {
        bookmarksBar.addBookmark("test", "http://www.test.be");
        assertEquals("http://www.test.be", controller.getURLFromBookmark("test"));
    }

    @Test
    @DisplayName("Can load incorrectly formed bookmarks")
    void loadIncorrectBookmarkTest() {
        bookmarksBar.addBookmark("test", "http://wwww.test.be");
        bookmarksBar.loadTextHyperlink("test");
        TextSpan error = (TextSpan) Document.getErrorDocument();
        TextSpan text = (TextSpan) controller.getDocument().getContentSpan();
        UITextField areaText = (UITextField) area.getContent();
        assertEquals(error.getText(), text.getText());
    }

    @Test
    @DisplayName("Can load correctly formed bookmarks")
    void loadCorrectBookmarkTest() {
        bookmarksBar.addBookmark("test", tableUrl);
        bookmarksBar.loadTextHyperlink("test");
        ContentSpan resultContentSpan = controller.getDocument().getContentSpan();
        ContentSpanBuilderTest.verifyContents(resultContentSpan);
    }
}
