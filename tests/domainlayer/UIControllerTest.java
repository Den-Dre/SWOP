package domainlayer;

import userinterface.AddressBar;
import userinterface.HorizontalScrollBarDecorator;
import userinterface.LeafPane;
import userinterface.UITextField;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UIControllerTest {
    @Test
    @DisplayName("Correct URL document loading")
    void loadURL() {
        String url = "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html";
        UIController controller = new UIController();
        controller.loadDocument(url);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
        browsrhtml.tests.ContentSpanBuilderTest.verifyContents(contentSpan);
    }

    @Test
    @DisplayName("Malformed URL")
    void malformedURL() {
        String malformedURL = "ww.www.test.com";
        UIController controller = new UIController();
        controller.loadDocument(malformedURL);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
        TextSpan textSpan = (TextSpan) contentSpan;
        assertEquals("Error: malformed URL.", textSpan.getText());
    }

    @Test
    @DisplayName("correct loading of hyperlink")
    void loadHyperlink() {
        String url = "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html";
        UIController controller = new UIController();
        controller.loadDocument(url);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
        browsrhtml.tests.ContentSpanBuilderTest.verifyContents(contentSpan);

        // Load the actual href
        String href = "fout.html";
        controller.loadDocumentFromHref(href);
        // should show errortext
        TextSpan span = (TextSpan) controller.getContentSpan();
        assertEquals("Error: malformed URL.", span.getText());
        // check if url changed
        assertEquals("https://people.cs.kuleuven.be/~bart.jacobs/fout.html", controller.getUrlString());
    }

    @Test
    void newDocument() {
        UIController controller = new UIController();
        AddressBar bar = new AddressBar(0,0,10,10,0);
        LeafPane area = new LeafPane(0,10,10,10);
        Document document = new Document("www.test.be", area, bar);

        // set the document of controller to be the new document
        controller.setDocument(document);
        assertEquals(document, controller.getDocument());
    }

    @Test
    void newDocument2() {
        // Setup
        UIController controller = new UIController();
        AddressBar bar = new AddressBar(0,0,10,10,0);
        LeafPane area = new LeafPane(0,10,10,10);
        bar.setUiController(controller);
        area.setController(controller);
        Document document = new Document();

        // set the document of controller to be the new document
        controller.setDocument(document);
        assertEquals(document, controller.getDocument());

        controller.addDocumentListener(area);
        controller.addUrlListener(bar);

        String link = "www.test.be";
        controller.loadDocument("www.test.be");
        assertEquals(link, bar.getURL());

        TextSpan errorText = (TextSpan) Document.getErrorDocument();
        UITextField areaText = (UITextField) ((HorizontalScrollBarDecorator) area.getContent()).getContent();
        assertEquals(errorText.getText(), areaText.getText());

    }
}
