package userinterface;

import java.awt.*;

/**
 * A class extending {@link GenericSplitPane} to handle vertical splitting 
 */
public class VerticalSplitPane extends GenericSplitPane {
	
    /**
     * Initialize this {@code VerticalSplitPane} with the given parameters.
     *
     * @param x      : The x coordinate of this VerticalSplitPane.
     * @param y      : The y coordinate of this VerticalSplitPane.
     * @param width  : The width of this VerticalSplitPane
     * @param height : The height of this VerticalSplitPane
     * @param childPane : the {@link LeafPane} child of this VerticalSplitPane
     * @param parentPane : the {@link Pane} parent of this VerticalSplitPane 
     * @throws IllegalDimensionException: When one of the dimensions of this VerticalSplitPane is negative
     */
    public VerticalSplitPane(int x, int y, int width, int height, LeafPane childPane, Pane parentPane) throws IllegalDimensionException {
        super(x, y, width, height, childPane, parentPane);

        // Set the sizes & position of the sub-panes
        leftPane.setxPos(x);
        leftPane.setyPos(y);
        leftPane.setWidth(width/2);
        leftPane.setHeight(height);
        leftPane.setParentPane(this);
        leftPane.setParentHeight(height);
        leftPane.setxReference(x);
        leftPane.setyReference(y);
        // Assign focus to one of the child `Pane`s
        leftPane.toggleFocus(true);

        rightPane.setxPos(x + width/2);
        rightPane.setyPos(y);
        rightPane.setWidth(width/2);
        rightPane.setHeight(height);
        rightPane.setParentPane(this);
        rightPane.setParentHeight(height);
        rightPane.setxReference(x + width/2);
        rightPane.setyReference(y);
        rightPane.toggleFocus(false);

        updateListeners();
    }

    /**
     * Copy constructor: creates a deep copy
     * of this {@code VerticalSplitPane}.
     *
     * @param pane: the {@code VerticalSplitPane} to be deep copied.
     */
    public VerticalSplitPane(VerticalSplitPane pane) {
        super(pane);
    }

    /**
     * Create a deep copy of this {@code VerticalSplitPane}
     * object using the copy constructor.
     *
     * @return copy: a deep copy of the given {@code VerticalSplitPane}.
     */
    @Override
    public Pane deepCopy() {
        return new VerticalSplitPane(this);
    }

    /**
     * This method handles resizes.
     * It makes sure the VerticalSplitPane is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this VerticalSplitPane.
     * @param newWindowHeight : parameter containing the new window-height of this VerticalSplitPane.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if (parentPane == null) {
            newWindowWidth -= getBasexPos();
            newWindowHeight -= getBaseyPos();
        }
        getFirstChild().setxPos(getxPos());
        getSecondChild().setxPos(getxPos() + newWindowWidth/2);
        getFirstChild().handleResize(newWindowWidth/2, newWindowHeight);
        getSecondChild().handleResize(newWindowWidth/2, newWindowHeight);
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
        g2.fillRect(rightPane.getxPos()-SEPARATOR_THICKNESS, rightPane.getyPos(), SEPARATOR_THICKNESS, rightPane.getHeight()-getxPos());
        g2.setStroke(new BasicStroke(1));
    }
    
    /**
     * Gets the first (left-side) childPane of this {@code VerticalSplitPane}
     * @return leftPane : the first childPane of this {@code VerticalSplitPane}
     */
    @Override
    public Pane getFirstChild() {
        return leftPane;
    }

    /**
     * Gets the second (right-side) childPane of this {@code VerticalSplitPane}
     * @return rightPane : the second childPane of this {@code VerticalSplitPane}
     */
    @Override
    public Pane getSecondChild() {
        return rightPane;
    }

    /**
     * Sets the first (left-side) childPane of this {@code VerticalSplitPane}
     * @return leftPane : the first childPane of this {@code VerticalSplitPane}
     */
    @Override
    public void setFirstChild(Pane pane) {
        this.leftPane = pane;
    }

    /**
     * Sets the second (right-side) childPane of this {@code VerticalSplitPane}
     * @return rightPane : the second childPane of this {@code VerticalSplitPane}
     */
    @Override
    public void setSecondChild(Pane pane) {
        this.rightPane = pane;
    }
    
    /**
     * Sets the x-position of this {@code VerticalSplitPane} and its childPanes
     * ({@code leftPane} and {@code rightPane})
     * @param xPos : the new x-position of this {@code VerticalSplitPane}
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        leftPane.setxPos(xPos);
        rightPane.setxPos(xPos + getWidth()/2);
    }

    /**
     * Sets the y-position of this {@code VerticalSplitPane} and its childPanes
     * ({@code leftPane} and {@code rightPane})
     * @param yPos : the new y-position of this {@code VerticalSplitPane}
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        leftPane.setyPos(yPos);
        rightPane.setyPos(yPos);
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
     * Sets the width of the parentPane ({@link Pane}) of this {@code VerticalSplitPane} and its chiledPanes
     * ({@code leftPane} and {@code rightPane})
     * @param parentWidth : the new width of the parentPane of this {@code VerticalSplitPane} 
     */
    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        leftPane.setParentWidth(parentWidth);
        rightPane.setParentWidth(parentWidth);
    }

    /**
     * Sets the height of the parentPane ({@link Pane}) of this {@code VerticalSplitPane} and its chiledPanes
     * ({@code leftPane} and {@code rightPane})
     * @param parentHeight: the new height of the parentPane of this {@code VerticalSplitPane} 
     */
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        leftPane.setParentHeight(parentHeight);
        rightPane.setParentHeight(parentHeight);
    }

    /**
     * A variable of type {@link Pane} to hold the left childPane of this {@code VerticalSplitPane} 
     */
    private Pane leftPane;

    /**
     * A variable of type {@link Pane} to hold the right childPane of this {@code VerticalSplitPane} 
     */
    private Pane rightPane;
}
