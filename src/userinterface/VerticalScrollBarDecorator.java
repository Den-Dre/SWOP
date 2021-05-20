package userinterface;

import java.awt.*;

public class VerticalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public VerticalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
        double ratio = (double) cell.getMaxHeight()/parentHeight;
        length = parentHeight;
        innerBarLength = (int) Math.round(length/ratio);
    }

    /**
     * render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        double ratio = (double) cellToBeDecorated.getMaxHeight()/parentHeight;
        if (ratio < 1.0)
            ratio = 1.0;
        setLength(parentHeight);
        innerBarLength = (int) Math.round(length/ratio);
        g.setColor(Color.BLACK);
        g.drawRect(getxPos() + getxOffset()+getVerticalBarXOffset(), getyPos()+offset + getyOffset(), thicknessOuterBar, length - (4 * offset));
        g.setColor(currentColor);
        g.fillRoundRect(getxPos()+(thicknessOuterBar)/4+getVerticalBarXOffset(), (int) (getyPos() + (2 * offset) + fraction * (length - innerBarLength) + getyOffset()),
                thicknessInnerBar, innerBarLength - (8 * offset), 2, 2);
    }

    @Override
    public int getVerticalBarXOffset() {
        return parentWidth - thicknessOuterBar-10;
    }

    void init() {
        setHeight(length);
        setWidth(thicknessOuterBar);
    }

    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        setLength(parentHeight);
    }

    @Override
    void moved() {
        if (length >= cellToBeDecorated.getMaxHeight()) return;
        cellToBeDecorated.setyOffset(- (int) Math.round(fraction*Math.abs(cellToBeDecorated.getMaxHeight() - length)));
    }

    @Override
    void dragged(int dx, int dy) {
        setFraction((double) dy / (length-innerBarLength) + fraction);
    }

    @Override
    public double getRatio() {
        return (double) cellToBeDecorated.getMaxHeight()/parentHeight;
    }
}
