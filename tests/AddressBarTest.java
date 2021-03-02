import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.*;

class AddressBarTest {

    private AddressBar bar;
    private int offset = 5;

    @BeforeEach
    public void setUp() {

        bar = new AddressBar(offset,offset,500,50, offset);
    }

    @Test
    void handleMouse() {
        assertFalse(bar.hasFocus);
        // click inside Frame
        bar.handleMouse(500, 10,15,1, 0,0);
        assertTrue(bar.hasFocus);
        // click outside addressbar
        bar.handleMouse(500, 0,0,1,0,0);
        assertFalse(bar.hasFocus);
        bar.handleMouse(500, 6,500,1,0,0);
        assertFalse(bar.hasFocus);
        // click on the edge
        bar.handleMouse(500, 490,55,1,0,0);
        assertTrue(bar.hasFocus);
    }

    @Test
    @DisplayName("Can handle normal key presses when no focus")
    void handleNormalKeyNoFocus() {
        //Normalkeys = [44,111], [512,523]
        assertFalse(bar.hasFocus);
        int[] keyCodes = new int[] {65, 66, 67};
        for (int code : keyCodes) {
            char[] chars = KeyEvent.getKeyText(code).toCharArray();
            bar.handleKey(KeyEvent.KEY_PRESSED, code, chars[0], 0);
            assertEquals("", bar.getURL());
        }
    }

    @Test
    void handleNormalKeysFocus() {
        bar.handleMouse(MouseEvent.MOUSE_CLICKED, 10,10, 1,0,0);
        assertTrue(bar.hasFocus);
        int[] keyCodes = new int[] {65, 66, 67};
        StringBuilder url = new StringBuilder();
        for (int code : keyCodes) {
            char[] chars = KeyEvent.getKeyText(code).toCharArray();
            bar.handleKey(KeyEvent.KEY_PRESSED, code, chars[0], 0);
            url.append(chars[0]);
            assertEquals(url.toString(), bar.getURL());
        }
    }

    @Test
    void handleResize() {
        // When the window is resized, the bar has to adjust its width... has it to adjust its height?
        int newWindowWidth = 100;
        int newWindowHeight = 100;
        bar.handleResize(newWindowWidth,newWindowHeight);
        assertEquals(bar.getWidth(), newWindowWidth-2*offset);
    }

    @Test
    void getURL() {
        assertEquals("", bar.getURL());
        String url = "https://nieuweurl.be";
        bar.setURL(url);
        assertEquals(url, bar.getURL());
    }
}