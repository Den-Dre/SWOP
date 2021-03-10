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
            AddressBar bar = new AddressBar(10, 100, 50, 60, 5);
            UIController contr = new UIController();
            contr.changeURL(url);
            contr.addUrlListener(bar);
            URL newUrl = new URL("http://www.ditiseenneiuweurl.be");
            contr.changeURL(url);

            assertEquals(newUrl.toString(),bar.getURL());
        }
        catch(Exception e) { System.out.println(e);}
    }
}
