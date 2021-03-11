package UserInterface;

import UserInterface.Frame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Frame")
class FrameTest {

    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final char undefChar = KeyEvent.CHAR_UNDEFINED;

    @Test
    @DisplayName("can correctly initialize")
    void correctInit() throws Exception {
        Frame frame = new Frame(0,0,10,10);
        assertFalse(frame.hasFocus);
        assertEquals(0, frame.getxPos());
        assertEquals(0, frame.getyPos());
        assertEquals(10, frame.getHeight());
        assertEquals(10, frame.getWidth());

        Frame frame2 = new Frame(12,16,1000,8);
        assertFalse(frame2.hasFocus);
        assertEquals(12,frame2.getxPos());
        assertEquals(16, frame2.getyPos());
        assertEquals(1000, frame2.getWidth());
        assertEquals(8, frame2.getHeight());

//        Frame frame3 = new Frame(-15,-5,-6,-12); // replaced with defensive test below
//        assertFalse(frame3.hasFocus);
//        assertEquals(0, frame3.getxPos());
//        assertEquals(0,frame3.getyPos());
//        assertEquals(0, frame3.getHeight());
//        assertEquals(0, frame3.getWidth());
        
        Exception exception = assertThrows(Exception.class, () -> {
        	new Frame(-15,-5,-6,-12);
        });
    }

    @Test
    void handleKey() throws Exception{
        Frame frame = new Frame(0,0,10,10);
        // Frame should do nothing
        frame.handleKey(keyPress, 0, undefChar, 0);
    }

    @Test
    @DisplayName("can correctly toggle focus")
    void toggleFocus() throws Exception{
        Frame frame = new Frame(0,0,10,10);
        assertFalse(frame.hasFocus);
        frame.toggleFocus(true);
        assertTrue(frame.hasFocus);
        frame.toggleFocus(false);
        assertFalse(frame.hasFocus);
    }

    @Test
    void getBackgroundColor() throws Exception{
        Frame frame = new Frame(0,0,10,10);
        // Standard color should be white
        assertEquals(Color.WHITE, frame.getBackgroundColor());
    }

}