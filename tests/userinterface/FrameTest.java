//package userinterface;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.awt.*;
//import java.awt.event.KeyEvent;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DisplayName("AbstractFrame")
//class FrameTest {
//
//    private final int keyPress = KeyEvent.KEY_PRESSED;
//    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
//
//    @Test
//    @DisplayName("can correctly initialize")
//    void correctInit() throws Exception {
//        AbstractFrame frame = new AbstractFrame(0,0,10,10);
//        assertFalse(frame.hasFocus);
//        assertEquals(0, frame.getxPos());
//        assertEquals(0, frame.getyPos());
//        assertEquals(10, frame.getHeight());
//        assertEquals(10, frame.getWidth());
//
//        AbstractFrame frame2 = new AbstractFrame(12,16,1000,8);
//        assertFalse(frame2.hasFocus);
//        assertEquals(12,frame2.getxPos());
//        assertEquals(16, frame2.getyPos());
//        assertEquals(1000, frame2.getWidth());
//        assertEquals(8, frame2.getHeight());
//
////        AbstractFrame frame3 = new AbstractFrame(-15,-5,-6,-12); // replaced with defensive test below
////        assertFalse(frame3.hasFocus);
////        assertEquals(0, frame3.getxPos());
////        assertEquals(0,frame3.getyPos());
////        assertEquals(0, frame3.getHeight());
////        assertEquals(0, frame3.getWidth());
//
//        Exception exception = assertThrows(Exception.class, () -> {
//        	new AbstractFrame(-15,-5,-6,-12);
//        });
//    }
//
//    @Test
//    void handleKey() throws Exception{
//        AbstractFrame frame = new AbstractFrame(0,0,10,10);
//        // AbstractFrame should do nothing
//        frame.handleKey(keyPress, 0, undefChar, 0);
//    }
//
//    @Test
//    @DisplayName("can correctly toggle focus")
//    void toggleFocus() throws Exception{
//        AbstractFrame frame = new AbstractFrame(0,0,10,10);
//        assertFalse(frame.hasFocus);
//        frame.toggleFocus(true);
//        assertTrue(frame.hasFocus);
//        frame.toggleFocus(false);
//        assertFalse(frame.hasFocus);
//    }
//
//    @Test
//    void getBackgroundColor() throws Exception{
//        AbstractFrame frame = new AbstractFrame(0,0,10,10);
//        // Standard color should be white
//        assertEquals(Color.WHITE, frame.getBackgroundColor());
//    }
//
//    @Test
//    void wrongDimensionsFrame() throws IllegalDimensionException{
//        IllegalDimensionException exception = assertThrows(IllegalDimensionException.class, () -> {
//            new AbstractFrame(-1,10,10,10);
//        });
//    }
//}