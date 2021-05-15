package userinterface;

import java.awt.*;

public class VerticalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public VerticalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
        double ratio = (double) cell.getMaxHeight()/cell.getHeight();
        length = cell.parentHeight;
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
        double ratio = (double) cellToBeDecorated.getMaxHeight()/parentHeight;
        if (ratio < 1.0)
            ratio = 1.0;
        setLength(parentHeight);
        innerBarLength = (int) Math.round(length/ratio);
        g.setColor(Color.BLACK);
        g.drawRect(getxPos() + getxOffset(), getyPos()+offset + getyOffset(), thicknessOuterBar, length - (4 * offset));
        g.setColor(currentColor);
        g.fillRoundRect(getxPos()+(thicknessOuterBar)/4, (int) (getyPos() + (2 * offset) + fraction * (length - innerBarLength) + getyOffset()),
                thicknessInnerBar, innerBarLength - (8 * offset), 2, 2);
    }

    void init() {
        setHeight(length);
        setWidth(thicknessOuterBar);
    }

    @Override
    void moved() {
        cellToBeDecorated.setyOffset(- (int) Math.round(fraction*Math.abs(cellToBeDecorated.getMaxHeight() - length)));
    }

    @Override
    void dragged(int dx, int dy) {
        setFraction((double) dy / (length-innerBarLength) + fraction);
    }
}
