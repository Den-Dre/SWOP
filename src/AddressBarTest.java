import UserInterface.AddressBar;
import domainmodel.*;
import org.junit.jupiter.api.Test;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressBarTest {

    @Test
    void testUrlChange() {
        try {
            URL url = new URL("http://www.ditiseentest.com");
            // Observable
            Document doc = new Document(url);
            // Observer
            AddressBar bar = new AddressBar(10, 100, 50, 60, 5);
            doc.addURLListener(bar);

            URL newUrl = new URL("http://www.ditiseenneiuweurl.be");
            doc.setUrl(newUrl);

            assertEquals(newUrl.toString(),bar.getURL());
        }
        catch(Exception ignored) { }
    }
}
