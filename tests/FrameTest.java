import UserInterface.Frame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {


    @Test
    void correctInit() {
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

        Frame frame3 = new Frame(-15,-5,-6,-12);
        assertFalse(frame3.hasFocus);
        assertEquals(0, frame3.getxPos());
        assertEquals(0,frame3.getyPos());
        assertEquals(0, frame3.getHeight());
        assertEquals(0, frame3.getWidth());

    }

    @Test
    void handleMouse() {

    }

    @Test
    void handleKey() {
    }

    @Test
    void handleResize() {
    }

    @Test
    void toggleFocus() {
    }
}