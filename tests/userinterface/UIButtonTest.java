package userinterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UIButton")
class UIButtonTest {

    String returnText = "submit";
    String displayText = "hallo allemaal!";
    UIButton button = new UIButton(0,0,50,25,displayText, returnText);

    private final int mouseClick = MouseEvent.MOUSE_PRESSED;
    private final int mouseReleased = MouseEvent.MOUSE_RELEASED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int rightMouse = MouseEvent.BUTTON2;

    @Test
    @DisplayName("Handles mouse clicks.")
    void getHandleMouse() {
        // click and release
        assertEquals("", button.getHandleMouse(mouseClick, 5,5, 1, leftMouse, 0).getContent());
        assertEquals(returnText, button.getHandleMouse(mouseReleased, 5,5, 1, leftMouse, 0).getContent());
        // wrong button
        assertEquals("", button.getHandleMouse(mouseClick, 5,100, 1, rightMouse, 0).getContent());
        // click but release out of button
        assertEquals("", button.getHandleMouse(mouseClick, 5,5, 1, leftMouse, 0).getContent());
        assertEquals("", button.getHandleMouse(mouseReleased, 5,100, 1, leftMouse, 0).getContent());
    }

    @Test
    @DisplayName("Stores correct displayText.")
    void getDisplayText() {
        assertEquals(displayText, button.getDisplayText());
    }
}