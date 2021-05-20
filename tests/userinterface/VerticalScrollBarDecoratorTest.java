package userinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VerticalScrollBarDecoratorTest {

    private VerticalScrollBarDecorator decorator;
    private UITable table;

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
        ArrayList<DocumentCell> row1 = new ArrayList<>();
        row1.add(new UITextField(x, y, width, height, text));
        ArrayList<DocumentCell> row2 = new ArrayList<>();
        row2.add(new UITextField(x, y, width, height,  text + "2"));
        ArrayList<ArrayList<DocumentCell>> grid = new ArrayList<>();
        grid.add(row1);
        grid.add(row2);
        table = new UITable(x, y, width, height, grid);
        decorator = new VerticalScrollBarDecorator(table);
    }

    @Test
    void handleMouseHorizontal() {
        int newParentHeight = 10;
        decorator.setParentHeight(newParentHeight);
        decorator.setLength(newParentHeight);
        int maxHeight = (decorator.getContentWithoutScrollbars()).getMaxHeight();
        decorator.ratioChanged((double) maxHeight/newParentHeight);
        int y1 = 1;
        int y2 = 5;

        decorator.handleMouse(mousePress, decorator.getxPos()+decorator.getVerticalBarXOffset()+1, decorator.getyPos()+y1, 1, leftMouse, 0);
        decorator.handleMouse(mouseDrag, decorator.getxPos()+decorator.getVerticalBarXOffset()+1, decorator.getyPos()+y2, 1, leftMouse, 0);

        double afterFraction = decorator.getFraction();
        int expectedOffset = - (int) Math.round(afterFraction*Math.abs(maxHeight - newParentHeight));
        assertEquals(expectedOffset, decorator.getContentWithoutScrollbars().getyOffset());

        double expectedAfterFraction = (double) (y2-y1) / (newParentHeight- decorator.innerBarLength);
        assertEquals(expectedAfterFraction, afterFraction);
    }

    @Test
    void getRatio() {
        int maxHeight = decorator.getContentWithoutScrollbars().getMaxHeight();
        int parentHeight = 10;
        decorator.setParentHeight(parentHeight);
        double ratio = (double) maxHeight / parentHeight;
        assertEquals(ratio, decorator.getRatio());
    }
}