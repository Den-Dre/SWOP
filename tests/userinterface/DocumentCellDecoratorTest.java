package userinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.*;

class DocumentCellDecoratorTest {

    private DocumentCellDecorator decorator;
    int x = 10;
    int y = 15;
    int width = 100;
    int height = 10;
    String text = "hallo";

    final int mousePress = MouseEvent.MOUSE_PRESSED;
    final int mouseDrag = MouseEvent.MOUSE_DRAGGED;
    final int mouseRelease = MouseEvent.MOUSE_RELEASED;
    final int leftMouse = MouseEvent.BUTTON1;


    @BeforeEach
    void setUp() {
        decorator = new HorizontalScrollBarDecorator(new UITextField(x, y, width, height, "lolololololçlolololollllllllllllllllllll"));
    }

    @Test
    void setLength() {
        decorator.setLength(200);
        assertEquals(200, decorator.length);
    }

    @Test
    void getFraction() {
        decorator.setFraction(0.5);
        assertEquals(0.5, decorator.getFraction());
    }

    @Test
    void handleMouse() {
        decorator.setParentWidth(50);
        decorator.setLength(10);
        decorator.innerBarLength = 5;
        int x1 = 1;
        int x2 = 5;
        int delta = x2-x1;
        double beforeFraction = decorator.getFraction();
        decorator.handleMouse(mousePress, decorator.getxPos()+1, decorator.getyPos()+decorator.getHorizontalBarYOffset()+1, 1, leftMouse, 0);
        decorator.handleMouse(mouseDrag, decorator.getxPos()+5, decorator.getyPos()+decorator.getHorizontalBarYOffset()+1, 1, leftMouse, 0);
        double afterFraction = decorator.getFraction();
        System.out.println("afterfraction: "+ afterFraction);
        System.out.println("max width: " + decorator.cellToBeDecorated.getMaxWidth());
        System.out.println("offset: " + decorator.getxOffset());
        assertTrue(decorator.getxOffset() < 0);
        assertTrue(afterFraction > beforeFraction);
    }

    @Test
    void handleResize() {
        //decorator.handleResize();
    }

    @Test
    void setxPos() {
    }

    @Test
    void setyPos() {
    }

    @Test
    void setxOffset() {
    }

    @Test
    void setyOffset() {
    }

    @Test
    void setWidth() {
    }

    @Test
    void setHeight() {
    }

    @Test
    void setParentHeight() {
    }

    @Test
    void setParentWidth() {
    }

    @Test
    void getMaxWidth() {
    }

    @Test
    void getMaxHeight() {
    }

    @Test
    void setyReference() {
    }
}