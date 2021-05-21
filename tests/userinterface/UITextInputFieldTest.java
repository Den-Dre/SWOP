package userinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UITextInputField")
class UITextInputFieldTest {

    private UITextInputField inputField;
    int x = 0;
    int y = 0;
    int width = 10;
    int height = 10;

    private final char undefChar = KeyEvent.CHAR_UNDEFINED;
    private final int mouseClick = MouseEvent.MOUSE_RELEASED;
    private final int keyPress = KeyEvent.KEY_PRESSED;
    private final int leftMouse = MouseEvent.BUTTON1;
    private final int shiftModifier = KeyEvent.SHIFT_DOWN_MASK;

    String someText = "dit is text";

    @BeforeEach
    void setup() {
        inputField = new UITextInputField(0,0,width,height);
    }

    @Test
    @DisplayName("handles mouse clicks")
    void handleMouse() {
        assertFalse(inputField.hasFocus);
        // Clicking on the inputfield should result in it having focus
        inputField.handleMouse(mouseClick,x+2, y+2, 1, leftMouse, 0);
        assertTrue(inputField.hasFocus);
        // Clicking outside the inputfield should result in it losing focus
        inputField.handleMouse(mouseClick,x+2+width, y+2, 1, leftMouse, 0);
        assertFalse(inputField.hasFocus);
    }

    @Test
    @DisplayName("handles key-presses")
    void handleKey() {
        // Give the inputfield focus by clicking on it
        assertFalse(inputField.hasFocus);
        inputField.handleMouse(mouseClick,x+2, y+2, 1, leftMouse, 0);
        assertTrue(inputField.hasFocus);

        // type some test into it
        char[] chars = someText.toCharArray();
        for (char ch : chars) {
            inputField.handleKey(keyPress, KeyEvent.getExtendedKeyCodeForChar(ch), ch, 0);
        }
        // The field's content should now equal the typed text
        assertEquals(someText, inputField.getText());
    }

    @Test
    @DisplayName("handles simple backspace and delete operation")
    void handleBackspace(){
        String text = "browsr";
        inputField.changeTextTo(text);
        // Click on inputField and check if it has focus
        inputField.handleMouse(mouseClick, 1,1, 1,leftMouse,0);
        assertTrue(inputField.hasFocus);
        // Press Right (all the text is selected when clicking to gain focus, we want no text to be selected for this test)
        inputField.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, 0);
        // Press backspace
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar, 0);
        assertEquals("brows", inputField.getText());

        //Press left
        inputField.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        // Press backspace again
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("bros", inputField.getText());
        // Press delete
        inputField.handleKey(keyPress, KeyEvent.VK_DELETE, undefChar,0);
        assertEquals("bro", inputField.getText());
    }

    @Test
    @DisplayName("deletes all text with Backspace after the inputField gains focus from mouseclick")
    void handleBackSpaceAfterClick() {
        String text = "textcontent";
        inputField.changeTextTo(text);
        // Click on inputField and check if it has focus
        inputField.handleMouse(mouseClick, 1,1, 1,leftMouse,0);
        assertTrue(inputField.hasFocus);
        // Press backspace
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar, 0);
        assertEquals("", inputField.getText());
    }

    @Test
    @DisplayName("deletes all text with Delete after the inputField gains focus from mouseclick")
    void handleDeleteAfterClick() {
        String Text = "helloworld.com";
        inputField.changeTextTo(Text);
        // Click on inputField and check if it has focus
        inputField.handleMouse(mouseClick, 1,1, 1,leftMouse,0);
        assertTrue(inputField.hasFocus);
        // Press delete
        inputField.handleKey(keyPress, KeyEvent.VK_DELETE, undefChar, 0);
        assertEquals("", inputField.getText());
    }

    @Test
    @DisplayName("can handle End, Home, Left and Right + typing at insertion point")
    void handleEndHomeLeftRight() {
        String Text = "helloworld.com";
        inputField.changeTextTo(Text);
        // Click on inputField and check if it has focus
        inputField.handleMouse(mouseClick, 1,1, 1,leftMouse,0);
        assertTrue(inputField.hasFocus);

        // => Press Home + enter letter
        inputField.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        inputField.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.com", inputField.getText());
        // => Press End + enter letter
        inputField.handleKey(keyPress, KeyEvent.VK_END, undefChar, 0);
        inputField.handleKey(keyPress, 65, 'a', 0);
        assertEquals("ahelloworld.coma", inputField.getText());
        // => Press left + enter letter
        inputField.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        inputField.handleKey(keyPress, 66, 'b',0);
        assertEquals("ahelloworld.comba", inputField.getText());
        // => Press Right + enter letter
        inputField.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, 0);
        inputField.handleKey(keyPress, 66, 'b',0);
        assertEquals("ahelloworld.combab", inputField.getText());
    }

    @Test
    @DisplayName("can handle selection+backspace operations")
    void handleSelection() {
        String Text = "helloworld.com";
        inputField.changeTextTo(Text);
        // Click on inputField and check if it has focus
        inputField.toggleFocus(true);
        assertTrue(inputField.hasFocus);

        // => 2 * shift-left + backspace
        for (int i = 0; i < 2; i++)
            inputField.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, shiftModifier);
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld.c", inputField.getText());
        // => 2 * left + 2 * shift-right + backspace
        for (int i = 0; i < 2; i++) inputField.handleKey(keyPress, KeyEvent.VK_LEFT, undefChar, 0);
        for (int i = 0; i < 2; i++) inputField.handleKey(keyPress, KeyEvent.VK_RIGHT, undefChar, shiftModifier);
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("helloworld", inputField.getText());
        // => shift-home + backspace
        inputField.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, shiftModifier);
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("", inputField.getText());
        // set Text back to a string
        inputField.changeTextTo("test");
        inputField.toggleFocus(true);
        // press home
        inputField.handleKey(keyPress, KeyEvent.VK_HOME, undefChar, 0);
        // => shift-end + backspace
        inputField.handleKey(keyPress, KeyEvent.VK_END,undefChar, shiftModifier);
        inputField.handleKey(keyPress, KeyEvent.VK_BACK_SPACE, undefChar,0);
        assertEquals("", inputField.getText());
    }

    @Test
    void setxOffset() {
        int xOffset = -6;
        inputField.setxOffset(xOffset);
        assertEquals(xOffset, inputField.getxOffset());
        assertEquals(inputField.getxPos()+ inputField.getxOffset(),
                inputField.getTextField().getxPos());
        assertEquals(inputField.getxPos()+ inputField.getxOffset(),
                inputField.getTextField().getxReference());
    }

    @Test
    void setyOffset() {
        int yOffset = -7;
        inputField.setyOffset(yOffset);
        assertEquals(yOffset, inputField.getyOffset());
        assertEquals(inputField.getyPos()+ inputField.getyOffset(),
                inputField.getTextField().getyPos());
        assertEquals(inputField.getyPos()+ inputField.getyOffset(),
                inputField.getTextField().getContentWithoutScrollbars().getyReference());
    }
}