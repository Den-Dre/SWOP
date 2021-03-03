import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.*;
import java.security.Key;

class AddressBarTest {

    private AddressBar bar;
    private int offset = 5;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final int mouseClick = MouseEvent.MOUSE_CLICKED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int shiftModifier = KeyEvent.SHIFT_DOWN_MASK;

    @BeforeEach
    public void setUp() {

        bar = new AddressBar(offset,offset,500,50, offset);
    }

    @Test
    void handleMouse() {
        assertFalse(bar.hasFocus);
        // click inside Frame
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
    void handleEscape() {
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
    @DisplayName("handles simple backspace and delete operation")
    void handleBackspace(){
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // Press Right (all the text is selected when clicking to gain focus, we want no text to be selected for this test)
        bar.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, 0);
        // Press backspace
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar, 0);
        assertEquals("helloworld.co", bar.getURL());

        //Press left
        bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        // Press backspace again
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld.o", bar.getURL());
        // Press delete
        bar.handleKey(keyPress, KeyEvent.VK_DELETE, undefChar,0);
        assertEquals("helloworld.", bar.getURL());
    }

    @Test
    @DisplayName("deletes all text with Backspace after the bar gains focus from mouseclick")
    void handleBackSpaceAfterClick() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // Press backspace
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar, 0);
        assertEquals("", bar.getURL());
    }

    @Test
    @DisplayName("deletes all text with Delete after the bar gains focus from mouseclick")
    void handleDeleteAfterClick() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);
        // Press backspace
        bar.handleKey(keyPress, KeyEvent.VK_DELETE, undefChar, 0);
        assertEquals("", bar.getURL());
    }

    @Test
    @DisplayName("can handle End, Home, Left and Right + typing at insertion point")
    void handleEndHomeLeftRight() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.handleMouse(mouseClick, 10,10, 1,leftMouse,0);
        assertTrue(bar.hasFocus);

        // => Press Home + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        bar.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.com", bar.getURL());
        // => Press End + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_END, undefChar, 0);
        bar.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.coma", bar.getURL());
        // => Press left + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        bar.handleKey(keyPress, 66, 'b',0);
        assertEquals("ahelloworld.comba", bar.getURL());
        // => Press Right + enter letter
        bar.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, 0);
        bar.handleKey(keyPress, 66, 'b',0);
        assertEquals("ahelloworld.combab", bar.getURL());
    }

    @Test
    @DisplayName("Can handle selection+backspace operations")
    void handleSelection() {
        String url = "helloworld.com";
        bar.changeURLto(url);
        // Click on bar and check if it has focus
        bar.toggleFocus(true);
        assertTrue(bar.hasFocus);

        // => 2 * shift-left + backspace
        for (int i = 0; i < 2; i++)
            bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld.c", bar.getURL());
        // => 2 * left + 2 * shift-right + backspace
        for (int i = 0; i < 2; i++)
            bar.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        for (int i = 0; i < 2; i++)
            bar.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld", bar.getURL());
        // => shift-home + backspace
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("", bar.getURL());
        // set url back to a string
        bar.changeURLto("test");
        bar.toggleFocus(true);
        // press home
        bar.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        // => shift-end + backspace
        bar.handleKey(keyPress, KeyEvent.VK_END,undefChar, shiftModifier);
        bar.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("", bar.getURL());
    }



    @Test
    @DisplayName("handles resizes")
    void handleResize() {
        // When the window is resized, the bar has to adjust its width... has it to adjust its height?
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
    void getURL() {
        assertEquals("", bar.getURL());
        String url = "https://nieuweurl.be";
        bar.changeURLto(url);
        assertEquals(url, bar.getURL());
    }
}