package UserInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UIHyperlink")
class UIHyperlinkTest {

    private UIHyperlink link1;
    private UIHyperlink link2;
    private int height1 = 10;
    private int height2 = 100;
    private String href1 = "/wiki/SWOP";
    private String text1 = "klik hier voor swop";
    private String href2 = "heeeel lange href!!!!!";
    private String text2 = "kort.";

    private final int mouseClick = MouseEvent.MOUSE_CLICKED;
    private final int leftMouse = MouseEvent.BUTTON1;

    @BeforeEach
    void init() throws Exception {

        link1 = new UIHyperlink(0,0,0,height1, href1, text1);

        link2 = new UIHyperlink(10,15,0,height2, href2, text2);
    }

    @Test
    @DisplayName("can handle mouse-clicks")
    void getHandleMouse() {
        // Not possible to calculate the actual with by hand, depends on the font etc.
        assertFalse(link1.calculateActualWidth);
        assertFalse(link2.calculateActualWidth);
        // LINK 1
        int link1Width = link1.getWidth();
        // Click not on link1
        String result = link1.getHandleMouse(mouseClick,link1Width+1, 5, 0, leftMouse, 0);
        assertEquals("", result);
        // Click on link1
        String result2 = link1.getHandleMouse(mouseClick,link1Width-1, 5, 0, leftMouse, 0);
        assertEquals(this.href1, result2);

        // LINK 2
        int link2Width = link2.getWidth();
        // Click not on link2
        String result3 = link2.getHandleMouse(mouseClick,link2Width+1+10, 17, 0, leftMouse, 0);
        assertEquals("", result3);
        // Click on link2
        String result4 = link2.getHandleMouse(mouseClick,link2Width-1+10, 17, 0, leftMouse, 0);
        assertEquals(this.href2, result4);
    }

    @Test
    @DisplayName("can calculate its maximum height")
    void getMaxHeight() {
        // Should return the height of the text.
        assertEquals(height1, link1.getHeight());
        assertEquals(height2, link2.getHeight());
    }

    @Test
    @DisplayName("can calculate its maximum width")
    void getMaxWidth() {
        // Should return the width of the text, calculated by the number of letters and the height-to-width ratio
        double ratio1 = link1.getHeightToWidthRatio();
        int width1 = (int) (link1.getText().length() * height1 * ratio1);
        assertEquals(width1, link1.getWidth());

        double ratio2 = link2.getHeightToWidthRatio();
        int width2 = (int) (link2.getText().length() * height2 * ratio2);
        assertEquals(width2, link2.getWidth());
    }

    @Test
    void invalidDimensions() throws IllegalDimensionException {
        IllegalDimensionException exception = assertThrows(IllegalDimensionException.class, () -> {
            new UITextField(-1,10,10,10,"Test");
        });
    }
}