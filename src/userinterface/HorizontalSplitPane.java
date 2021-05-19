package userinterface;

import java.awt.*;

/**
 * A class extending {@link GenericSplitPane} to handle horizontal splitting
 */
public class HorizontalSplitPane extends GenericSplitPane {
    
	/**
     * Initialize this {@code HorizontalSplitPane} with the given parameters.
     *
     * @param x      : The x coordinate of this HorizontalSplitPane.
     * @param y      : The y coordinate of this HorizontalSplitPane.
     * @param width  : The width of this HorizontalSplitPane
     * @param height : The height of this HorizontalSplitPane
     * @param childPane : the {@link LeafPane} child of this HorizontalSplitPane
     * @param parentPane : the {@link Pane} parent of this HorizontalSplitPane 
     * @throws IllegalDimensionException: When one of the dimensions of this HorizontalSplitPane is negative
     */
    public HorizontalSplitPane(int x, int y, int width, int height, LeafPane childPane, Pane parentPane) throws IllegalDimensionException {
        super(x, y, width, height, childPane, parentPane);

        // Set the sizes & position of the sub-panes
        lowerPane.setxPos(x);
        lowerPane.setyPos(y + height/2);
        lowerPane.setWidth(width);
        lowerPane.setHeight(height/2);
        lowerPane.setParentPane(this);
        lowerPane.setParentHeight(height/2);
        lowerPane.setxReference(x);
        lowerPane.setyReference(y + height/2);
        // Assign focus to one of the `Pane`s
        lowerPane.toggleFocus(true);
        setFocusedPane(lowerPane);

        upperPane.setxPos(x);
        upperPane.setyPos(y);
        upperPane.setWidth(width);
        upperPane.setHeight(height/2);
        upperPane.setParentPane(this);
        upperPane.setParentHeight(height/2);
        upperPane.setxReference(x);
        upperPane.setyReference(y);
        upperPane.toggleFocus(false);
        updateListeners();
    }

    /**
     * Copy constructor: creates a deep copy
     * of this {@code HorizontalSplitPane}.
     *
     * @param pane: the {@code HorizontalSplitPane} to be deep copied.
     */
    public HorizontalSplitPane(HorizontalSplitPane pane) {
        super(pane);
    }

    /**
     * Create a deep copy of this {@code HorizontalSplitPane}
     * object using the copy constructor.
     *
     * @return copy: a deep copy of the given {@code HorizontalSplitPane}
     */
    @Override
    public Pane deepCopy() {
        return new HorizontalSplitPane(this);
    }

    /**
     * This method handles resizes.
     * It makes sure the HorizontalSplitPane is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this HorizontalSplitPane.
     * @param newWindowHeight : parameter containing the new window-height of this HorizontalSplitPane.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        getSecondChild().setyPos(getyPos());

        if (parentPane == null) {
            newWindowWidth -= getBasexPos();
            newWindowHeight -= getBaseyPos();
        }
        getSecondChild().handleResize(newWindowWidth, newWindowHeight/2);
        getFirstChild().setyPos(getyPos() + (newWindowHeight)/2);
        getFirstChild().handleResize(newWindowWidth, (newWindowHeight)/2);
        super.handleResize(newWindowWidth, newWindowHeight);
    }

    /**
     * A method that draws the separator
     * This method is called each time the
     * separator is dragged to a new position.
     *
     * @param g: The graphics to be rendered.
     */
    @Override
    void drawSeparator(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.fillRect(lowerPane.getxPos(), lowerPane.getyPos()-SEPARATOR_THICKNESS, getWidth()-getxPos(), SEPARATOR_THICKNESS);
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Gets the first (lower) childPane of this {@code HorizontalSplitPane}
     * 
     * @return leftPane : the first childPane of this {@code HorizontalSplitPane}
     */
    @Override
    public Pane getFirstChild() {
        return lowerPane;
    }

    /**
     * Gets the second (upper) childPane of this {@code HorizontalSplitPane}
     * 
     * @return rightPane : the second childPane of this {@code HorizontalSplitPane}
     */
    @Override
    public void setFirstChild(Pane pane) {
        this.lowerPane = pane;
    }

    /**
     * Sets the first (lower-side) childPane of this {@code HorizontalSplitPane}
     * 
     * @return leftPane : the first childPane of this {@code HorizontalSplitPane}
     */
    @Override
    public Pane getSecondChild() {
        return upperPane;
    }

    /**
     * Sets the second (upper) childPane of this {@code HorizontalSplitPane}
     * 
     * @return rightPane : the second childPane of this {@code HorizontalSplitPane}
     */
    @Override
    public void setSecondChild(Pane pane) {
        this.upperPane = pane;
    }
    
    
    /**
     * Sets the x-position of this {@code HorizontalSplitPane} and its childPanes
     * ({@code upperPane} and {@code lowerPane})
     * 
     * @param xPos : the new x-position of this {@code HorizontalSplitPane}
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        upperPane.setxPos(xPos);
        lowerPane.setxPos(xPos);
    }

    /**
     * Sets the y-position of this {@code HorizontalSplitPane} and its childPanes
     * ({@code upperPane} and {@code lowerPane})
     * 
     * @param yPos : the new y-position of this {@code HorizontalSplitPane}
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        upperPane.setyPos(yPos);
        lowerPane.setyPos(yPos + getHeight()/2);
    }

//    @Override
//    public void setxReference(int xReference) {
//        super.setxReference(xReference);
//        leftPane.setxReference(xReference);
//        rightPane.setxReference(xReference+getHeight()/2);
//    }
//
//    @Override
//    public void setyReference(int yReference) {
//        super.setyReference(yReference);
//        leftPane.setyReference(yReference);
//        rightPane.setyReference(yReference);
//    }

    /**
     * Sets the width of the parentPane ({@link Pane}) of this {@code HorizontalSplitPane} and its chiledPanes
     * ({@code upperPane} and {@code lowerPane})
     * 
     * @param parentWidth : the new width of the parentPane of this {@code HorizontalSplitPane} 
     */
    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        upperPane.setParentWidth(parentWidth);
        lowerPane.setParentWidth(parentWidth);
    }

    /**
     * Sets the height of the parentPane ({@link Pane}) of this {@code HorizontalSplitPane} and its chiledPanes
     * ({@code upperPane} and {@code lowerPane})
     * 
     * @param parentHeight : the new height of the parentPane of this {@code HorizontalSplitPane} 
     */
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        upperPane.setParentHeight(parentHeight);
        lowerPane.setParentHeight(parentHeight);
    }

    /**
     * A variable of type {@link Pane} to hold the upper childPane of this {@code HorizontalSplitPane} 
     */
    private Pane upperPane;

    /**
     * A variable of type {@link Pane} to hold the lower childPane of this {@code HorizontalSplitPane} 
     */
    private Pane lowerPane;
}
