package UserInterface;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UIButton")
class UIButtonTest {

    String returnText = "submit";
    String displayText = "hallo allemaal!";
    UIButton button = new UIButton(0,0,50,25,displayText, returnText);

    private final int mouseClick = MouseEvent.MOUSE_CLICKED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int rightMouse = MouseEvent.BUTTON2;

    @Test
    @DisplayName("Handles mouse clicks.")
    void getHandleMouse() {
        assertEquals(returnText, button.getHandleMouse(mouseClick, 5,5, 1, leftMouse, 0));
        assertEquals("", button.getHandleMouse(mouseClick, 5,100, 1, rightMouse, 0));
    }

    @Test
    @DisplayName("Stores correct displayText.")
    void getDisplayText() {
        assertEquals(displayText, button.getDisplayText());
    }
}