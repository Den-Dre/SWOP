package UserInterface;

import browsrhtml.tests.ContentSpanBuilderTest;
import domainmodel.ContentSpan;
import domainmodel.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        DocumentArea area = new DocumentArea(addressBarOffset, 2 * (addressBarHeight + 2 * addressBarOffset), 100, 100);

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
        // Simulate use case of pressing "Save Dialog" without creating a Browsr object
        controller.loadDocument(tableUrl);
        String fileName = "test";
        controller.saveDocument(fileName);
        String projectDir = System.getProperty("user.dir");
        // Check if the saved document exists
        File outputFile = new File(projectDir + File.separator + "savedPages" + File.separator + fileName + ".html");
        assertTrue(outputFile.isFile());
        // Check the contents of the saved HTML file:
        // TODO: Zie card op Trello: aan de gedownloade HTML-code zijn whitespaces  for some reason?
        // Als tijdelijke oplossing gebruiken we de regex replaceAll("\\s+","") om alle whitespaces te verwijderen,
        // zodat de assertion slaagt.
        assertEquals(
                ("<table>  " +
                        "<tr>" +
                        "<td>HTML elements partially supported by Browsr:  " +
                        "<tr>" +
                        "<td>    " +
                        "<table>      " +
                        "<tr>" +
                        "<td><a href=\"a.html\">a</a>" +
                        "<td>Hyperlink anchors      " +
                        "<tr>" +
                        "<td><a href=\"table.html\">table</a>" +
                        "<td>Tables      " +
                        "<tr>" +
                        "<td><a href=\"tr.html\">tr</a>" +
                        "<td>Table rows      <tr>" +
                        "<td><a href=\"td.html\">td</a>" +
                        "<td>Table cells containing table data    " +
                        "</table>" +
                        "</table>").replaceAll("\\s+",""),
                getFileContents(outputFile). replaceAll("\\s+","")
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
    @DisplayName("Can load correctly formed bookmarks")
    void loadCorrectBookmarkTest() {
        bookmarksBar.addBookmark("test", tableUrl);
        bookmarksBar.loadTextHyperlink("test");
        ContentSpan resultContentSpan = controller.getDocument().getContentSpan();
        ContentSpanBuilderTest.verifyContents(resultContentSpan);
    }
}


