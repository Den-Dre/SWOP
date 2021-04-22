package userinterface;

import browsrhtml.tests.ContentSpanBuilderTest;
import domainlayer.ContentSpan;
import domainlayer.Document;
import domainlayer.TextSpan;
import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("Can add bookmarks from within the BookmarksDialog")
    void addBookmarkTest() {
        String name = "TableURL";
        // Simulate opening a BookmarkDialog
        BookmarksDialog dialog = new BookmarksDialog(100, 100, tableUrl, bookmarksBar, browsr);
        UITextInputField nameInput = (UITextInputField) ((UITable) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(1).get(0)).getContent().get(0).get(1);
        UIButton addBookmarkButton = (UIButton) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(2).get(0);

        // Select the Name input field
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED, nameInput.getxPos()+5, nameInput.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        assertTrue(nameInput.hasFocus);

        // And type in the name of the Bookmark
        char[] chars = name.toCharArray();
        for (char ch : chars)
            dialog.handleKey(KeyEvent.KEY_PRESSED, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);

        // Finally, click the Add Bookmark button to add the bookmark
        // First off, press in the button
        dialog.handleMouse(MouseEvent.MOUSE_PRESSED,  addBookmarkButton.getxPos()+5, addBookmarkButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Secondly, release the button which triggers its action
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED,  addBookmarkButton.getxPos()+5, addBookmarkButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Check whether the bookmark is added successfully
        assertEquals(tableUrl, controller.getURLFromBookmark(name));
    }

    @Test
    @DisplayName("Can cancel saving action in BookmarksDialog")
    void canCancelDialog() {
        String name = "TableURL";
        // Load in the document to be saved
        controller.loadDocument(tableUrl);
        // Now the document is loaded and it should be saved when pressing the save button
        controller.saveDocument(name);
        // Simulate opening a BookmarkDialog
        BookmarksDialog dialog = new BookmarksDialog(100, 100, tableUrl, bookmarksBar, browsr);
        // Set the correct layout as we can't emulate keyEvent's without having a running Browsr object
        browsr.setBrowsrLayout(browsr.new BookmarksDialogLayout());
        UIButton addBookmarkButton = (UIButton) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(2).get(0);
        UIButton cancelButton = (UIButton) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(2).get(1);

        // Pressing "save" shouldn't do anything as there's no name input in the name UITextInputField.
        // First off, press in the button
        dialog.handleMouse(MouseEvent.MOUSE_PRESSED,  addBookmarkButton.getxPos()+5, addBookmarkButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Secondly, release the button which triggers its action
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED,  addBookmarkButton.getxPos()+5, addBookmarkButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        System.out.println(browsr.getBrowsrLayout());
        assertTrue(dialog.getBrowsr().getBrowsrLayout() instanceof Browsr.BookmarksDialogLayout);

        // Finally, click the Cancel button to cancel the saving action
        // First off, press in the button
        dialog.handleMouse(MouseEvent.MOUSE_PRESSED,  cancelButton.getxPos()+5, cancelButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Secondly, release the button which triggers its action
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED,  cancelButton.getxPos()+5, cancelButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Check whether we're back at the original layout
        assertTrue(dialog.getBrowsr().getBrowsrLayout() instanceof Browsr.RegularLayout);
    }

    @Test
    @DisplayName("Can load incorrectly formed bookmarks")
    void loadIncorrectBookmarkTest() {
        bookmarksBar.addBookmark("test", "http://wwww.test.be");
        bookmarksBar.loadTextHyperlink("test");
        TextSpan error = (TextSpan) Document.getErrorDocument();
        TextSpan text = (TextSpan) controller.getDocument().getContentSpan();
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

    @Test
    @DisplayName("Can handle clicks")
    void canHandleClicks() {

    }
}
