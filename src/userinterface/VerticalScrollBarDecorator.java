package userinterface;

import java.awt.*;

/**
 * A class extending from {@link DocumentCellDecorator} that decorates a 
 * {@link DocumentCell} with a vertical scroll bar.
 */
public class VerticalScrollBarDecorator extends DocumentCellDecorator {
    
	/**
     * Initialize this {@code VerticalScrollBarDecorator} with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this {@code VerticalScrollBarDecorator} is negative
     */
    public VerticalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
        double ratio = (double) cell.getMaxHeight()/parentHeight;
        length = parentHeight;
        innerBarLength = (int) Math.round(length/ratio);
    }

//    /**
//     * Initialise this AbstractFrame with the given parameters.
//     *
//     * @param frame: The {@link ContentFrame} to be decorated.
//     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
//     */
//    public VerticalScrollBarDecorator(ContentFrame frame) throws IllegalDimensionException {
//        super(frame);
//    }
    
    /**
     * Initialize this {@code VerticalScrollBarDecorator} with the given 
     * {@code VerticalScrollBarDecorator}. Used to achieve a deep-copy of a given
     * {@code VerticalScrollBarDecorator}.
     * 
     * @param decorator : the {@code VerticalScrollBarDecorator} that needs to be copied.
     */
    public VerticalScrollBarDecorator(VerticalScrollBarDecorator decorator) {
        super(decorator.getContent().deepCopy());
    }

    /**
     * Create a deep copy of this {@code VerticalScrollBarDecorator} object.
     *
     * @return copy: a deep copied version of this {@code VerticalScrollBarDecorator}
     * object which thus does not point to the original object.
     */
    @Override
    protected VerticalScrollBarDecorator deepCopy() {
        return new VerticalScrollBarDecorator(this);
    }

    /**
     * render the contents of this {@code VerticalScrollBarDecorator}.
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

    /**
     * Gets the horizontal scroll bar y offset of this {@code VerticalScrollBarDecorator} 
     * 
     * @return parentHeight : 
     * 						the {@code parentWidth} of this {@code VerticalScrollBarDecorator} - 
     * 						its {@code thicknessOuterBar} - 5.
     */
    @Override
    public int getVerticalBarXOffset() {
        return parentWidth - thicknessOuterBar-5;
    }

    /**
     * Initialize the {@code VerticalScrollBarDecorator} by setting the height to its
     * own length and its width to the {@code thicknessOuterBar}.
     */
    void init() {
        setHeight(length);
        setWidth(thicknessOuterBar);
    }
    
    /**
     * Set the height of the parent of this {@code VerticalScrollBarDecorator} to the given value. Does the 
     * same for the {@code length} of this {@code VerticalScrollBarDecorator}.
     *
     * @param parentHeight:
     *             The value the height of the parent of this {@code DocumentCellDecorator} should be set to.
     */
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        if (parentHeight < length)
            setLength(parentHeight);
    }

    /**
     * Move the y offset of the {@code cellToBeDecorated} of this {@code VerticalScrollBarDecorator} when the 
     * scroll bar is moved
     */
    @Override
    void moved() {
        if (length >= cellToBeDecorated.getMaxHeight()) return;
        cellToBeDecorated.setyOffset(- (int) Math.round(fraction*Math.abs(cellToBeDecorated.getMaxHeight() - length)));
    }

    /**
     * set the fraction of this {@code VerticalScrollBarDecorator} based on the distant in which the mouse
     * is dragged along the screen.
     * 
     * @param dx : the x distance along which the mouse is dragged
     * @param dy : the y distance along which the mouse is dragged
     */
    @Override
    void dragged(int dx, int dy) {
        setFraction((double) dy / (length-innerBarLength) + fraction);
    }

    /**
     * Get the ratio of this {@code VerticalScrollBarDecorator}.
     * 
     * @return ratio : the ratio of type {@link Double} of this {@code VerticalScrollBarDecorator}.
     */
    @Override
    public double getRatio() {
        return (double) cellToBeDecorated.getMaxHeight()/parentHeight;
    }
}
