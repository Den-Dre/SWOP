package domainmodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UIControllerTest {
    @Test
    @DisplayName("Correct URL document loading")
    void loadURL() {
        String url = "https://people.cs.kuleuven.be/~bart.jacobs/browsrtest.html";
        UIController controller = new UIController();
        controller.loadDocument2(url);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
        browsrhtml.tests.ContentSpanBuilderTest.verifyContents(contentSpan);
    }

    @Test
    @DisplayName("Malformed URL")
    void malformedURL() {
        String malformedURL = "ww.www.test.com";
        UIController controller = new UIController();
        controller.loadDocument2(malformedURL);

        // Verify contents of returned URL
        ContentSpan contentSpan = controller.getContentSpan();
        TextSpan textSpan = (TextSpan) contentSpan;
        assertEquals("Error: malformed URL.", textSpan.getText());
    }
}
