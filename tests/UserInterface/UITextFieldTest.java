package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UITextField:")
class UITextFieldTest {

    private UITextField textField1;
    private UITextField textField2;
    private String text1 = "hello world";
    private String text2 = ".";
    private int size1 = 10;
    private int size2 = 100;

    @BeforeEach
    void setUp() throws IllegalDimensionException {
        textField1 = new UITextField(0,0,0,size1, text1);
        textField2 = new UITextField(10,10,0,size2, text2);
    }

    @Test
    @DisplayName("can calculate its maximum width")
    void getMaxWidth() {
        // The maximum width is calculated as follows:
        // -> number of characters * height * height-to-width-ratio
        assertFalse(textField1.calculateActualWidth);
        int height1 = textField1.getHeight();
        double ratio1 = textField1.getHeightToWidthRatio();
        int width1 = (int) (textField1.getText().length() * height1 * ratio1);
        assertEquals(width1, textField1.getMaxWidth());

        assertFalse(textField2.calculateActualWidth);
        int height2 = textField2.getHeight();
        double ratio2 = textField2.getHeightToWidthRatio();
        int width2 = (int) (textField2.getText().length() * height2 * ratio2);
        assertEquals(width2, textField2.getMaxWidth());
    }

    @Test
    @DisplayName("can calculate its maximum height")
    void getMaxHeight() {
        assertEquals(size1, textField1.getMaxHeight());
        assertEquals(size2, textField2.getMaxHeight());
    }

    @Test
    @DisplayName("returns the text correctly")
    void getText() {
        assertEquals(text1, textField1.getText());
        assertEquals(text2, textField2.getText());
    }

    @Test
    void invalidDimension() throws IllegalDimensionException{
        IllegalDimensionException exception = assertThrows(IllegalDimensionException.class, () -> {
            new UITextField(-1,10,10,10,"Test");
        });
    }
}