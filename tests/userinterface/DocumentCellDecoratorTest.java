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

    @BeforeEach
    void setUp() {
        decorator = new DocumentCellDecorator(new UITextField(x, y, width, height, text)) {
            @Override
            void init() { }

            @Override
            void moved() { }

            @Override
            void dragged(int dx, int dy) { }
        };
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
        decorator.setFraction(-1.3);
        assertEquals(0.0, decorator.getFraction());
        decorator.setFraction(15);
        assertEquals(1.0, decorator.getFraction());
    }

    @Test
    void getRatio() {
        // A generic decorator should return 1.0 as ratio.
        assertEquals(1.0, decorator.getRatio());
    }

    @Test
    void ratioChanged() {
        decorator.ratioChanged(0.5);
        assertEquals(decorator.length, decorator.innerBarLength);
    }

    @Test
    void handleResize() {
        //decorator.handleResize();
    }

    @Test
    void setxPos() {
        int newX = decorator.getxPos()+20;
        decorator.setxPos(newX);
        assertEquals(newX, decorator.getxPos());
        assertEquals(newX, decorator.getContent().getxPos());
        assertEquals(newX, decorator.getContentWithoutScrollbars().getxPos());
    }

    @Test
    void setyPos() {
        int newY = decorator.getyPos()+20;
        decorator.setyPos(newY);
        assertEquals(newY, decorator.getyPos());
        assertEquals(newY, decorator.getContent().getyPos());
        assertEquals(newY, decorator.getContentWithoutScrollbars().getyPos());
    }

    @Test
    void setxOffset() {
        int xOffset = -3;
        decorator.setxOffset(xOffset);
        assertEquals(xOffset, decorator.getContent().getxOffset());
        assertEquals(xOffset, decorator.getContentWithoutScrollbars().getxOffset());
    }

    @Test
    void setyOffset() {
        int yOffset = -8;
        decorator.setyOffset(yOffset);
        assertEquals(yOffset, decorator.getContent().getyOffset());
        assertEquals(yOffset, decorator.getContentWithoutScrollbars().getyOffset());
    }

    @Test
    void setWidth() {
        int width = 23;
        decorator.setWidth(width);
        assertEquals(width, decorator.getWidth());
        assertEquals(width, decorator.getContent().getWidth());
        assertEquals(width, decorator.getContentWithoutScrollbars().getWidth());
    }

    @Test
    void setHeight() {
        int height = 21;
        decorator.setHeight(height);
        assertEquals(height, decorator.getHeight());
        assertEquals(height, decorator.getContent().getHeight());
        assertEquals(height, decorator.getContentWithoutScrollbars().getHeight());
    }

    @Test
    void setParentHeight() {
        int parentHeight = 12;
        decorator.setParentHeight(parentHeight);
        assertEquals(parentHeight, decorator.parentHeight);
        assertEquals(parentHeight, decorator.getContent().parentHeight);
        assertEquals(parentHeight, decorator.getContentWithoutScrollbars().parentHeight);
    }

    @Test
    void setParentWidth() {
        int parentWidth = 14;
        decorator.setParentWidth(parentWidth);
        assertEquals(parentWidth, decorator.parentWidth);
        assertEquals(parentWidth, decorator.getContent().parentWidth);
        assertEquals(parentWidth, decorator.getContentWithoutScrollbars().parentWidth);
    }

    @Test
    void getMaxWidth() {
        int expectedWidth = decorator.getContentWithoutScrollbars().getMaxWidth();
        assertEquals(expectedWidth, decorator.getMaxWidth());
    }

    @Test
    void getMaxHeight() {
        int expectedHeight = decorator.getContentWithoutScrollbars().getMaxHeight();
        assertEquals(expectedHeight, decorator.getMaxHeight());
    }

    @Test
    void setyReference() {
    }
}