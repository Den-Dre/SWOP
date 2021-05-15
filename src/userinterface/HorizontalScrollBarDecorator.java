package userinterface;

import java.awt.*;

public class HorizontalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public HorizontalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
        double ratio = (double) cell.getMaxWidth()/cell.getWidth();
        length = cell.parentWidth;
        innerBarLength = (int) Math.round(length/ratio);
    }

    /**
     * render the contents of this Frame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        double ratio = (double) cellToBeDecorated.getMaxWidth()/parentWidth;
        if (ratio < 1.0)
            ratio = 1.0;
        setLength(parentWidth);
        innerBarLength = (int) Math.round(length/ratio);
        g.setColor(Color.BLACK);
        g.drawRect(getxPos() + offset + getxOffset(), getyPos(), length - (4 * offset), thicknessOuterBar);
        g.setColor(currentColor);
        g.fillRoundRect((int) (getxPos() + (2 * offset) + fraction * (length - innerBarLength) + getxOffset()),
                getyPos()+(thicknessOuterBar)/4, innerBarLength - (8 * offset), thicknessInnerBar, 2, 2);
    }

    void init() {
        setHeight(thicknessOuterBar);
        setWidth(length);
    }

    @Override
    void moved() {
        System.out.println("moved");
        cellToBeDecorated.setxOffset(- (int) Math.round(fraction*Math.abs(cellToBeDecorated.getMaxWidth() - length)));
    }

//    @Override
//    public void setWidth(int newWidth) {
//        super.setWidth(newWidth);
//        setLength(newWidth);
//    }

    @Override
    void dragged(int dx, int dy) {
        setFraction((double) dx / (length-innerBarLength) + fraction);
    }

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id          : The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode     : The KeyEvent code (Determines the involved key)
     * @param keyChar     : The character representation of the involved key
     * @param modifiersEx : Specifies other keys that were involved in the event
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        this.cellToBeDecorated.handleKey(id, keyCode, keyChar, modifiersEx);
        ratioChanged((double) parentWidth/cellToBeDecorated.getMaxWidth());
    }
}
