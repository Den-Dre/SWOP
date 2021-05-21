package userinterface;

import java.awt.*;

/**
 * A class extending from {@link DocumentCellDecorator} that decorates a 
 * {@link DocumentCell} with a horizontal scroll bar.
 */
public class HorizontalScrollBarDecorator extends DocumentCellDecorator {
	
    /**
     * Initialize this {@code HorizontalScrollBarDecorator} with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this {@code HorizontalScrollBarDecorator} is negative
     */
    public HorizontalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
        double ratio = (double) cell.getMaxWidth()/cell.getWidth();
        length = cell.parentWidth;
        innerBarLength = (int) Math.round(length/ratio);
    }

    /**
     * render the contents of this {@code HorizontalScrollBarDecorator}.
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

    /**
     * Initialize the {@code HorizontalScrollBarDecorator} by setting the width to its
     * own length.
     */
    void init() {
        setHeight(thicknessOuterBar);
        setWidth(length);
    }

    /**
     * Get the ratio of this {@code HorizontalScrollBarDecorator}.
     * 
     * @return ratio : the ratio of type {@link Double} of this {@code HorizontalScrollBarDecorator}.
     */
    @Override
    public double getRatio() {
        return (double) cellToBeDecorated.getMaxWidth()/parentWidth;
    }

    /**
     * Move the x offset of the {@code cellToBeDecorated} of this {@code HorizontalScrollBarDecorator} when the 
     * scroll bar is moved
     */
    @Override
    void moved() {
        if (length >= cellToBeDecorated.getMaxWidth()) return;
        cellToBeDecorated.setxOffset(- (int) Math.round(fraction*Math.abs(cellToBeDecorated.getMaxWidth() - length)));
    }

    /**
     * set the fraction of this {@code HorizontalScrollBarDecorator} based on the distant in which the mouse
     * is dragged along the screen.
     * 
     * @param dx : the x distance along which the mouse is dragged
     * @param dy : the y distance along which the mouse is dragged
     */
    @Override
    void dragged(int dx, int dy) {
        setFraction((double) dx / (length-innerBarLength) + fraction);
    }

    /**
     * Gets the horizontal scroll bar y offset based on whether the {@code cellToBeDecorated} 
     * is a {@link UITextField} or not.
     * 
     * @return parentHeight : 
     * 						the parentHeight of this {@code HorizontalScrollBarDecorator} 
     * 						or the (parentHeight - 15) when the {@code cellToBeDecorated} is a
     * 						{@link UITextField}.
     */
    @Override
    public int getHorizontalBarYOffset() {
        if (cellToBeDecorated instanceof UITextField)
            return parentHeight;
        else
            return parentHeight-17; // if the decorated object is not a UITextField,
                                    // the scrollbar has to be higher.
    }
}
