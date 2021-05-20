package userinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalScrollBarDecoratorTest {

    private HorizontalScrollBarDecorator decorator;

    final int mousePress = MouseEvent.MOUSE_PRESSED;
    final int mouseDrag = MouseEvent.MOUSE_DRAGGED;
    final int mouseRelease = MouseEvent.MOUSE_RELEASED;
    final int leftMouse = MouseEvent.BUTTON1;

    int x = 10;
    int y = 15;
    int width = 100;
    int height = 10;
    String text = "hallo";

    @BeforeEach
    void setUp() {
        decorator = new HorizontalScrollBarDecorator(new UITextField(x, y, width, height, text));
    }

    @Test
    void handleMouseHorizontal() {
        int newParentWidth = 10;
        decorator.setParentWidth(newParentWidth);
        decorator.setLength(newParentWidth);
        int textWidth = decorator.getContentWithoutScrollbars().getMaxWidth();
        decorator.ratioChanged((double) textWidth/newParentWidth);
        int x1 = 1;
        int x2 = 5;

        decorator.handleMouse(mousePress, decorator.getxPos()+x1, decorator.getyPos()+ decorator.getHorizontalBarYOffset()+1, 1, leftMouse, 0);
        decorator.handleMouse(mouseDrag, decorator.getxPos()+x2, decorator.getyPos()+ decorator.getHorizontalBarYOffset()+1, 1, leftMouse, 0);

        double afterFraction = decorator.getFraction();
        int expectedOffset = - (int) Math.round(afterFraction*Math.abs(textWidth - newParentWidth));
        assertEquals(expectedOffset, decorator.getContentWithoutScrollbars().getxOffset());

        double expectedAfterFraction = (double) (x2-x1) / (newParentWidth- decorator.innerBarLength);
        assertEquals(expectedAfterFraction, afterFraction);
    }

    @Test
    void getRatio() {
        int textWidth = decorator.getContentWithoutScrollbars().getMaxWidth();
        int parentWidth = 10;
        decorator.setParentWidth(parentWidth);
        double ratio = (double) textWidth/parentWidth;
        assertEquals(ratio, decorator.getRatio());
    }

}