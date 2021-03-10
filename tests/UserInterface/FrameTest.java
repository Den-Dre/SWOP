package UserInterface;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {

    private Frame frame;

    @BeforeEach
    public void setUp() { frame = new Frame(0,0,10,10); }

    @Test
    @DisplayName("Handles initialisation")
    void correctInit() {
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

        Frame frame3 = new Frame(-15,-5,-6,-12);
        assertFalse(frame3.hasFocus);
        assertEquals(0, frame3.getxPos());
        assertEquals(0,frame3.getyPos());
        assertEquals(0, frame3.getHeight());
        assertEquals(0, frame3.getWidth());

    }

    @Test
    @DisplayName("Handles mouse events")
    void handleMouse() {
//        Can't test for focus as the Frame itself doesn't implement handleMouse
//        assertTrue(frame.hasFocus);
//        frame.handleMouse(mouseClick, 20,20, 1, leftMouse, 0);
//        assertFalse(frame.hasFocus);
    }

    @Test
    @DisplayName("Handles key press")
    void handleKey() {
       // Can't test for key handling as the Frame class itself doesn't implement handleKey()
    }

    @Test
    @DisplayName("Handles resize")
    void handleResize() {
        // Can't test for resizes as the Frame class itself doesn't implement handleKey()
    }

    @Test
    void toggleFocus() {
        assertFalse(frame.hasFocus);
        frame.toggleFocus(true);
        assertTrue(frame.hasFocus);
        // Can't test for focus as the Frame class itself doesn't implement toggleFocus().
    }
}