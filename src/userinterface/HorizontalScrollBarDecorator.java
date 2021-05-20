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
        g.drawRect(getxPos() + offset + getxOffset(), getyPos()+getHorizontalBarYOffset(), length - (4 * offset), thicknessOuterBar);
        g.setColor(currentColor);
        g.fillRoundRect((int) (getxPos() + (2 * offset) + fraction * (length - innerBarLength) + getxOffset()),
                getyPos()+(thicknessOuterBar)/4+getHorizontalBarYOffset(), innerBarLength - (8 * offset), thicknessInnerBar, 2, 2);
    }

    void init() {
        setHeight(thicknessOuterBar);
        setWidth(length);
    }

    @Override
    public double getRatio() {
        return (double) cellToBeDecorated.getMaxWidth()/parentWidth;
    }

    @Override
    void moved() {
        if (length >= cellToBeDecorated.getMaxWidth()) return;
        cellToBeDecorated.setxOffset(- (int) Math.round(fraction*Math.abs(cellToBeDecorated.getMaxWidth() - length)));
    }

    @Override
    void dragged(int dx, int dy) {
        setFraction((double) dx / (length-innerBarLength) + fraction);
    }

    @Override
    public int getHorizontalBarYOffset() {
        if (cellToBeDecorated instanceof UITextField)
            return parentHeight;
        else
            return parentHeight-17; // if the decorated object is not a UITextField,
                                    // the scrollbar has to be higher.
    }
}
