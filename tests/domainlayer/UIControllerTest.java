package domainlayer;

import org.junit.jupiter.api.BeforeEach;
import userinterface.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UIControllerTest {
    private ContentFrame cf;
    private UIController controller;
    private final int id = 0;
    private Pane rootPane;

    @BeforeEach
    void setUp() {
        cf = new ContentFrame(0, 0, 100, 100);
        controller = new UIController();
        
        rootPane = new LeafPane(cf, controller);
        controller.setCurrentDocument(rootPane.getId());
        controller.addDocumentListener(id, cf);
    }

    @Test
    @DisplayName("Correct URL document loading")
    void loadURL() {
        String url = "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html";
        controller.loadDocument(id, url);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan(id);
        browsrhtml.tests.ContentSpanBuilderTest.verifyContents(contentSpan);
    }

    @Test
    @DisplayName("Malformed URL")
    void malformedURL() {
        String malformedURL = "ww.www.test.com";
        controller.loadDocument(id, malformedURL);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan(id);
        TextSpan textSpan = (TextSpan) contentSpan;
        assertEquals("Error: malformed URL.", textSpan.getText());
    }

    @Test
    @DisplayName("correct loading of hyperlink")
    void loadHyperlink() {
        String url = "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html";
        controller.loadDocument(id, url);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan(id);
        browsrhtml.tests.ContentSpanBuilderTest.verifyContents(contentSpan);

        // Load the actual href
        String href = "fout.html";
        controller.loadDocumentFromHref(id, href);
        // should show errortext
        TextSpan span = (TextSpan) controller.getContentSpan(id);
        assertEquals("Error: malformed URL.", span.getText());
        // check if url changed
        assertEquals("https://people.cs.kuleuven.be/~bart.jacobs/fout.html", controller.getUrlString(id));
    }

    @Test
    void newDocument() {
        AddressBar bar = new AddressBar(0,0,10,10,0);
        ContentFrame area = new ContentFrame(0,10,10,10);
//        Document document = new Document();
//        document.setUrlString("www.test.be");
//        document.addDocumentListener(area);
//        document.addURLListener(bar);
        Document document = new Document("www.test.be", area, bar);

        // set the document of controller to be the new document
        controller.setCurrentDocument(id);
        assertEquals(document, controller.getCurrentDocument());
    }

    @Test
    void newDocument2() {
        // Setup
        AddressBar bar = new AddressBar(0,0,10,10,0);
        ContentFrame area = new ContentFrame(0,10,10,10);
        bar.setUiController(controller);
        area.setController(controller);
        int id = controller.addPaneDocument();
        // Normally the id gets set in the LeafPane constructor.
        // Since we only use a ContentFrame, we set it manually.
        area.setId(id);

        // set the document of controller to be the new document
        controller.setCurrentDocument(id);
        assertEquals(controller.getDocument(id), controller.getCurrentDocument());

        controller.addDocumentListener(id, area);
        controller.addUrlListener(bar);

        String link = "www.test.be";
        bar.setId(id);
        controller.loadDocument(id, "www.test.be");
        assertEquals(link, bar.getURL());

        TextSpan errorText = (TextSpan) Document.getErrorDocument();
        UITextField areaText = (UITextField) ((DocumentCellDecorator) area.getContent()).getContentWithoutScrollbars();
        assertEquals(errorText.getText(), areaText.getText());

    }
}
