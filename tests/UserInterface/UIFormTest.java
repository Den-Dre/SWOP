package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UIForm")
class UIFormTest {

    private UITextInputField formContent;
    private UIForm form;
    int x = 10;
    int y = 10;

    private final int mouseClick = MouseEvent.MOUSE_RELEASED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int rightMouse = MouseEvent.BUTTON2;

    @BeforeEach
    void setUp() {
        formContent = new UITextInputField(0,0,100,20);
        form = new UIForm(x,y,"action", formContent);
    }

    @Test
    @DisplayName("Initialises correctly.")
    void goodInit() {
        assertEquals(formContent, form.getFormContent());
        assertEquals(x, form.getxPos());
        assertEquals(y, form.getyPos());
        assertEquals(x, formContent.getxPos());
        assertEquals(y, formContent.getyPos());
        assertEquals(formContent.getWidth(), form.getWidth());
        assertEquals(formContent.getHeight(), form.getHeight());
    }

    @Test
    @DisplayName("Handles mouse-clicks.")
    void getHandleMouse() {
        // Click on the UIForm.
        form.getHandleMouse(mouseClick, x + 5, y + 5, 1, leftMouse, 0);
        assertTrue(form.getFormContent().hasFocus);
        // Click out of the UIForm.
        form.getHandleMouse(mouseClick, x + 5, y + 25, 1, leftMouse, 0);
        assertFalse(form.getFormContent().hasFocus);
        // Click on the form, but with the wrong mouse-button.
        form.getHandleMouse(mouseClick, x + 25, y + 25, 1, rightMouse, 0);
        assertFalse(form.getFormContent().hasFocus);
    }

    @Test
    @DisplayName("Handles key-presses.")
    void handleKey() {
        // Click on the UIForm.
        form.getHandleMouse(mouseClick, x + 5, y + 5, 1, leftMouse, 0);
        assertTrue(form.getFormContent().hasFocus);
        String toEnter = "hallo";
        // type some test into it
        char[] chars = toEnter.toCharArray();
        for (char ch : chars) {
            form.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        assertEquals(toEnter, ((UITextInputField) form.getFormContent()).getText());
    }

    @Test
    @DisplayName("Sets it and it's content y-pos correctly.")
    void setyPos() {
        int newY = 25;
        form.setyPos(newY);
        assertEquals(newY, form.getyPos());
        assertEquals(newY, form.getFormContent().getyPos());
    }

    @Test
    @DisplayName("Sets it and it's content x-pos correctly.")
    void setxPos() {
        int newX = 25;
        form.setxPos(newX);
        assertEquals(newX, form.getxPos());
        assertEquals(newX, form.getFormContent().getxPos());
    }
}