import userinterface.*;
import domainlayer.Document;
import domainlayer.TextSpan;
import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.*;

@DisplayName("total program flow when typing in addressbar")
public class flowTest {

    private ContentFrame area;
    private AddressBar bar;
    private UIController controller;
    private final String goodUrl = "https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html";
    private final String badUrl = "www.fout.be";
    private final int id = 0;

    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final int mouseClick = MouseEvent.MOUSE_RELEASED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int shiftModifier = KeyEvent.SHIFT_DOWN_MASK;

    @BeforeEach
    void setup(){
        // Make a total browsr without the gui class (=browsr.java)
        area = new ContentFrame(0,20,100,100);
        bar = new AddressBar(0,0,100,20,0);
        controller = new UIController(); // The document is created within uicontroller
        // Couple the uicontoller to the documentarea and addressbar
        area.setController(controller);
        bar.setUiController(controller);
        // Couple the document with the documentarea and addressbar
        controller.addDocumentListener(area);
        controller.addUrlListener(bar);
    }

    @Test
    @DisplayName("Shows correct welcome message")
    void startMessage() {
        TextSpan welcome = (TextSpan) Document.getWelcomeDocument();
        UITextField areaText = (UITextField) ((DocumentCellDecorator) area.getContent()).getContentWithoutScrollbars();
        assertEquals(welcome.getText(), areaText.getText());
    }

    @Test
    @DisplayName("good flow when typing good url")
    void typeGoodUrl() {
        //1. Click on addressBar to gain focus
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 5, 5, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        //2. Type in a correct url
        char[] chars = goodUrl.toCharArray();
        for (char ch : chars) {
            bar.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        assertEquals(goodUrl, bar.getURL());
        //3. press enter
        bar.handleKey(keyPress, KeyEvent.VK_ENTER, undefChar, 0);
        assertFalse(bar.hasFocus);
        assertEquals(goodUrl, bar.getURL());
        assertEquals(goodUrl, controller.getUrlString(id));
        //4. Document should be loaded
//        ContentSpan expectedSpan = controller.getCurrentDocument().composeDocument(new URL(goodUrl));
//        assertEquals(area.translateToUIElements(expectedSpan), area.getContent());
    }

    @Test
    @DisplayName("good flow when typing bad url")
    void typeBadUrl() {
        //1. Click on addressBar to gain focus
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 5, 5, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // 2. Type a bad url
        char[] chars = badUrl.toCharArray();
        for (char ch : chars) {
            bar.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        assertEquals(badUrl, bar.getURL());
        // 3. Click out of addressbar
        bar.handleMouse(mouseClick, 100,100, 1,leftMouse, 0);
        assertFalse(bar.hasFocus);
        assertEquals(badUrl, bar.getURL());
        // 4. Show error document
        TextSpan error = (TextSpan) Document.getErrorDocument();
        UITextField areaText = (UITextField) ((DocumentCellDecorator) area.getContent()).getContentWithoutScrollbars();
        assertEquals(error.getText(), areaText.getText());
    }

    @Test
    @DisplayName("good flow when discarding editing addressbar")
    void discardEditing() {
        // 1. Click on addressBar to gain focus
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 5, 5, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        //2. Type in a correct url
        char[] chars = goodUrl.toCharArray();
        for (char ch : chars) {
            bar.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        assertEquals(goodUrl, bar.getURL());
        //3. press enter
        bar.handleKey(keyPress, KeyEvent.VK_ENTER, undefChar, 0);
        assertFalse(bar.hasFocus);
        assertEquals(goodUrl, bar.getURL());
        assertEquals(goodUrl, controller.getUrlString(0));
        // 5. Click on addressBar to gain focus
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 5, 5, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // 6. Type a bad url
        char[] chars2 = badUrl.toCharArray();
        for (char ch : chars2) {
            bar.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        assertEquals(badUrl, bar.getURL());
        // 7. You realise the url is wrong and discard by pressing escape
        bar.handleKey(keyPress, KeyEvent.VK_ESCAPE, undefChar, 0);
        // 8. The addressbar should now be restored to the correct previous url
        assertEquals(goodUrl, bar.getURL());
        assertEquals(goodUrl, controller.getUrlString(id));
    }

    @Test
    @DisplayName("good flow when clicking a bad hyperlink")
    void clickLink() {

        /////////////////////
        // Load a good document with a hyperlink
        //1. Click on addressBar to gain focus
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 5, 5, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        //2. Type in a correct url
        char[] chars = goodUrl.toCharArray();
        for (char ch : chars) {
            bar.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        assertEquals(goodUrl, bar.getURL());
        //3. press enter
        bar.handleKey(keyPress, KeyEvent.VK_ENTER, undefChar, 0);
        assertFalse(bar.hasFocus);
        assertEquals(goodUrl, bar.getURL());
        assertEquals(goodUrl, controller.getUrlString(id));
        ///////////////////////

        // 4. Click on a hyperlink 'a' -> 'a.html'
        area.handleMouse(mouseClick, 5, 40, 1, leftMouse, 0);
        // 5. The link leads nowhere so show error document
        TextSpan error = (TextSpan) Document.getErrorDocument();
        UITextField areaText = (UITextField) ((DocumentCellDecorator) area.getContent()).getContentWithoutScrollbars();
        assertEquals(error.getText(), areaText.getText());
        // 6. The bar should show the new url, although it is bad
        String badLink = "https://people.cs.kuleuven.be/bart.jacobs/a.html";
        assertEquals(badLink, bar.getURL());
        assertEquals(badLink, controller.getUrlString(id));
    }
}
