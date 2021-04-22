package userinterface;

import domainlayer.UIController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.*;
import java.net.URL;

class AddressBarTest {

    private AddressBar bar;
    private int offset = 5;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final int mouseClick = MouseEvent.MOUSE_RELEASED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int shiftModifier = KeyEvent.SHIFT_DOWN_MASK;

    @BeforeEach
    public void setUp() throws Exception {
        bar = new AddressBar(offset,offset,500,50, offset);
    }

    @Test
    @DisplayName("Handles mouse events")
    void handleMouse() {
        assertFalse(bar.hasFocus);
        // click inside UserInterface.Frame
        bar.handleMouse(mouseClick, 10,15,1, leftMouse,0);
        assertTrue(bar.hasFocus);
        // click outside addressbar
        bar.handleMouse(mouseClick, 0,0,1,leftMouse,0);
        assertFalse(bar.hasFocus);
        bar.handleMouse(mouseClick, 6,500,1,leftMouse,0);
        assertFalse(bar.hasFocus);
        // click on the edge
        bar.handleMouse(mouseClick, 490,55,1,leftMouse,0);
        assertTrue(bar.hasFocus);
    }

    @Test
    @DisplayName("Can handle normal key presses with no focus")
    void handleNormalKeyNoFocus() {
        //Normalkeys = [44,111], [512,523]
        assertFalse(bar.hasFocus);
        int[] keyCodes = new int[] {65, 66, 67};
        for (int code : keyCodes) {
            char[] chars = KeyEvent.getKeyText(code).toCharArray();
            bar.handleKey(keyPress, code, chars[0], 0);
            // Characters shouldn't appear in AddressBar:
            assertEquals("", bar.getURL());
        }
    }

    @Test
    @DisplayName("Can handle normal key presses with focus")
    void handleNormalKeysFocus() {
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        int[] keyCodes = new int[] {65, 66, 67};
        StringBuilder url = new StringBuilder();
        for (int code : keyCodes) {
            char[] chars = KeyEvent.getKeyText(code).toCharArray();
            bar.handleKey(keyPress, code, chars[0], 0);
            url.append(chars[0]);
            assertEquals(url.toString(), bar.getURL());
        }
    }

    @Test
    @DisplayName("Can handle escape and enter")
    void handleEscapeEnter() {
        String first = "helloworld.com";
        String second = "loremipsum.be";
        bar.changeURLto(first);
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        assertEquals(first, bar.getURL());

        bar.handleKey(keyPress, KeyEvent.VK_ESCAPE, undefChar, 0);

        // Is the url reset to the url before editing?
        assertFalse(bar.hasFocus);
        assertEquals("", bar.getURL());

        // Set URL to to first then Enter then to second and escape. The url should be first.
        bar.changeURLto(first);
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        assertEquals(first, bar.getURL());
        // Press enter
        bar.handleKey(keyPress, KeyEvent.VK_ENTER, undefChar, 0);
        assertEquals(first, bar.getURL());
        assertFalse(bar.hasFocus);

        // Click on bar again to gain focus
        bar.changeURLto(second);
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // url is changed to second url
        assertEquals(second, bar.getURL());
        // but operation is cancelled with Escape
        bar.handleKey(keyPress, KeyEvent.VK_ESCAPE, undefChar, 0);
        // bar should have no focus and contain the first url
        assertFalse(bar.hasFocus);
        assertEquals(first, bar.getURL());
    }

    @Test
    @DisplayName("Handles resizes")
    void handleResize() {
        // When the window is resized, the bar has to adjust its width... Does it need to adjust its height, too?
        int newWindowWidth = 100;
        int newWindowHeight = 100;
        bar.handleResize(newWindowWidth,newWindowHeight);
        assertEquals(bar.getWidth(), newWindowWidth-2*offset);

        int newWindowWidth2 = 1000;
        int newWindowHeight2 = 5000;
        bar.handleResize(newWindowWidth2,newWindowHeight2);
        assertEquals(bar.getWidth(), newWindowWidth2-2*offset);
    }

    @Test
    @DisplayName("Changes URL")
    void getURL() {
        assertEquals("", bar.getURL());
        String url = "https://nieuweurl.be";
        bar.changeURLto(url);
        assertEquals(url, bar.getURL());
    }

    @Test
    void testUrlChange() {
        try {
            URL url = new URL("http://www.ditiseentest.com");
            AddressBar bar = new AddressBar(10, 100, 50, 60, 5);
            UIController contr = new UIController();
            contr.changeURL(url.toString());
            contr.addUrlListener(bar);
            URL newUrl = new URL("http://www.ditiseenneiuweurl.be");
            contr.changeURL(url.toString());

            assertEquals(newUrl.toString(),bar.getURL());
        }
        catch(Exception e) { System.out.println(e);}
    }
}