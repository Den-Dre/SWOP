import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressBarTest {

    AddressBar bar = new AddressBar(0,0,10,10);

    @Test
    void handleMouse() {
        assertFalse(bar.hasFocus);
        // click inside Frame
        bar.handleMouse(500, 5,5,1, 0,0);
        assertTrue(bar.hasFocus);
        // click outside addressbar
        bar.handleMouse(500, 15,5,1,0,0);
        assertFalse(bar.hasFocus);
        bar.handleMouse(500, 5,15,1,0,0);
        assertFalse(bar.hasFocus);
        // click on the edge
        bar.handleMouse(500, 10,10,1,0,0);
        assertTrue(bar.hasFocus);
    }

    @Test
    void handleKey() {
    }

    @Test
    void handleResize() {
        // When the window is resized, the bar has to adjust its width... has it to adjust its height?
        int newWindowWidth = 100;
        int newWindowHeight = 100;
        bar.handleResize(newWindowWidth,newWindowHeight);
        assertEquals(bar.getWidth(), newWindowWidth);
    }

    @Test
    void getURL() {
        assertEquals("", bar.getURL());
        String url = "https://nieuweurl.be";
        bar.setURL(url);
        assertEquals(url, bar.getURL());
    }
}