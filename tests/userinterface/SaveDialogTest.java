package userinterface;

import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

// Unfortunately, we can't run Browsr in test files. So the tests for BookmarksDialog can't cover
// the entire use case of pressing CTRL+D etc. The tests provided underneath do test the internal
// functionality for the most part, however.
public class SaveDialogTest {

    private Browsr browsr;
    private AddressBar bar;
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
        ContentFrame area = new ContentFrame(addressBarOffset, 2 * (addressBarHeight + 2 * addressBarOffset), 100, 100);

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
    }

    @Test
    @DisplayName("Handles CTRL+D correctly")
    void handleCtrlD() {
        bar.setText(tableUrl);
        assertTrue(browsr.getBrowsrLayout() instanceof Browsr.RegularLayout);
        assertNull(browsr.getSaveDialog());

        SaveDialog dialog = new SaveDialog(100,100, tableUrl, browsr);
        // Name field should be empty
        assertEquals("", dialog.getNameInput());
    }

    @Test
    @DisplayName("Can correctly save a page")
    void savePageTest() throws IOException {
        // Page should only be saved when it's loaded, not when only the URL is typed into the AddressBar
        bar.changeTextTo(tableUrl);
        assertThrows(Exception.class, () -> controller.getDocument().saveDocument("test"));
        // Simulate use case of pressing "Save Dialog" without creating a Browsr object
        controller.loadDocument(tableUrl);
        // Now the document is loaded and it should be saved when pressing the save button
        String fileName = "test";
        controller.saveDocument(fileName);
        String projectDir = System.getProperty("user.dir");
        // Check if the saved document exists
        File outputFile = new File(projectDir + File.separator + fileName + ".html");
        assertTrue(outputFile.isFile());
        // Check the contents of the saved HTML file:
        // In de demo op "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html" staan er spaties als indentatie
        // Deze worden ook mee gedownload door URL.openStream().
        // Als tijdelijke oplossing gebruiken we de regex replaceAll("\\s+","") om alle whitespaces te verwijderen,
        // zodat de assertion slaagt.
        assertEquals(
                (
                        "<table>" +
                        "  <tr>" +
                        "<td>HTML elements partially supported by Browsr:" +
                        "  <tr><td>" +
                        "    <table>" +
                        "      <tr><td><a href=\"a.html\">a</a><td>Hyperlink anchors" +
                        "      <tr><td><a href=\"table.html\">table</a><td>Tables" +
                        "      <tr><td><a href=\"tr.html\">tr</a><td>Table rows" +
                        "      <tr><td><a href=\"td.html\">td</a><td>Table cells containing table data" +
                        "    </table>" +
                        "</table>"
                ).replaceAll("\\s+",""),
                getFileContents(outputFile).replaceAll("\\s+","")
        );
    }

    String getFileContents(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null)
            stringBuilder.append(line);
        return stringBuilder.toString();
    }

    @Test
    @DisplayName("Throws exception when trying to save Welcome document")
    void saveWelcomeDocumentTest() {
        // No URL is set, so the welcome document is loaded by default
        assertThrows(Exception.class, () -> controller.getDocument().saveDocument("test"));
    }

    @Test
    @DisplayName("Can save pages from within the SaveDialog")
    void canSavePageFromDialog() {
        String name = "TableURL";
        // Load in the document to be saved
        controller.loadDocument(tableUrl);
        // Now the document is loaded and it should be saved when pressing the save button
        controller.saveDocument(name);
        // Simulate opening a BookmarkDialog
        SaveDialog dialog = new SaveDialog(100, 100, tableUrl, browsr);
        UITextInputField nameInput = (UITextInputField) ((HorizontalScrollBarDecorator) ((UITable) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(1).get(0)).getContent().get(0).get(1)).getContent();
        UIButton saveButton = (UIButton) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(2).get(0);

        // Select the Name input field
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED, nameInput.getxPos()+5, nameInput.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        assertTrue(nameInput.hasFocus);

        // And type in the name of the Bookmark
        char[] chars = name.toCharArray();
        for (char ch : chars)
            dialog.handleKey(KeyEvent.KEY_PRESSED, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);

        // Finally, click the Add Bookmark button to add the bookmark
        // First off, press in the button
        dialog.handleMouse(MouseEvent.MOUSE_PRESSED,  saveButton.getxPos()+5, saveButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Secondly, release the button which triggers its action
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED,  saveButton.getxPos()+5, saveButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Check whether the page is saved succesfully
        String projectDir = System.getProperty("user.dir");
        File outputFile = new File(projectDir + File.separator + name + ".html");
        assertTrue(outputFile.isFile());
    }


    @Test
    @DisplayName("Can cancel saving action in SaveDialog")
    void canCancelDialog() {
        String name = "TableURL";
        // Load in the document to be saved
        controller.loadDocument(tableUrl);
        // Now the document is loaded and it should be saved when pressing the save button
        controller.saveDocument(name);
        // Simulate opening a BookmarkDialog
        SaveDialog dialog = new SaveDialog(100, 100, tableUrl, browsr);
        // Set the correct layout as we can't emulate keyEvent's without having a running Browsr object
        browsr.setBrowsrLayout(browsr.new SaveDialogLayout());
        UIButton saveButton = (UIButton) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(2).get(0);
        UIButton cancelButton = (UIButton) ((UITable) dialog.getForm(tableUrl).getFormContent()).getContent().get(2).get(1);

        // Pressing "save" shouldn't do anything as there's no page loaded
        // First off, press in the button
        dialog.handleMouse(MouseEvent.MOUSE_PRESSED,  saveButton.getxPos()+5, saveButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Secondly, release the button which triggers its action
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED,  saveButton.getxPos()+5, saveButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        System.out.println(browsr.getBrowsrLayout());
        assertTrue(dialog.getBrowsr().getBrowsrLayout() instanceof Browsr.SaveDialogLayout);

        // Finally, click the Cancel button to cancel the saving action
        // First off, press in the button
        dialog.handleMouse(MouseEvent.MOUSE_PRESSED,  cancelButton.getxPos()+5, cancelButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Secondly, release the button which triggers its action
        dialog.handleMouse(MouseEvent.MOUSE_RELEASED,  cancelButton.getxPos()+5, cancelButton.getyPos()+5, 1, MouseEvent.BUTTON1, 0);
        // Check whether we're back at the original layout
        assertTrue(dialog.getBrowsr().getBrowsrLayout() instanceof Browsr.RegularLayout);
    }
}


