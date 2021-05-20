package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;

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
     * This method handles resizes.
     * It makes sure the HorizontalSplitPane is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this HorizontalSplitPane.
     * @param newWindowHeight : parameter containing the new window-height of this HorizontalSplitPane.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setWidth(newWindowWidth);
        setHeight(newWindowHeight);
        getSecondChild().setyPos(getyPos());
        getSecondChild().handleResize(newWindowWidth, (int) (newWindowHeight*upperFraction));
        getFirstChild().setyPos(getyPos() + (int) (newWindowHeight*upperFraction));
        getFirstChild().handleResize(newWindowWidth, (int) (newWindowHeight*(1-upperFraction)));
    }

    /**
     * A method that draws the separator
     * This method is called each time the
     * separator is dragged to a new position.
     *
     * @param g: The graphics to be rendered.
     */
    @Override
    protected void drawSeparator(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.fillRect(lowerPane.getxPos(), lowerPane.getyPos()-SEPARATOR_THICKNESS, lowerPane.getWidth(), SEPARATOR_THICKNESS);
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Returns true if and only if the coordinates are in the separator
     * @param x : The x coordinate
     * @param y : The y coordinate
     * @return  : True iff the click was in the separator
     */
    private boolean isInSeperator(int x, int y) {
        return ((y < lowerPane.getyPos() + SEPARATOR_THICKNESS / 2) && (y > lowerPane.getyPos() - SEPARATOR_THICKNESS) &&
                (x >= this.getxPos() && x < this.getxPos() + this.getWidth()));
    }

    /**
     * Handle mouseEvents.
     *
     * @param id          : The type of mouse activity
     * @param x           : The x coordinate of the mouse activity
     * @param y           : The y coordinate of the mouse activity
     * @param clickCount  : The number of clicks
     * @param button      : The mouse button that was clicked
     * @param modifiersEx : The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (id == MouseEvent.MOUSE_PRESSED && isInSeperator(x,y))
            dragging = true;
        else if ((id == MouseEvent.MOUSE_DRAGGED) && dragging) {
            int oldY = lowerPane.getyPos();
            int deltaY = Math.abs(oldY-y);
            if (y < lowerPane.getyPos()) {
                int newUpperHeight = upperPane.getHeight() - deltaY;
                int newLowerHeight = lowerPane.getHeight() + deltaY;
                lowerPane.setyPos(y);
                lowerPane.setHeight(newLowerHeight);
                upperPane.setHeight(newUpperHeight);
                upperFraction = (double) (newUpperHeight)/getHeight();
                lowerPane.handleResize(this.getWidth(),newLowerHeight);
                upperPane.handleResize(this.getWidth(),newUpperHeight);
            } else {
                lowerPane.setyPos(y);
                lowerPane.setHeight(lowerPane.getHeight() - deltaY);
                upperPane.setHeight(upperPane.getHeight() + deltaY);
                upperFraction = (double) (upperPane.getHeight())/getHeight();
                lowerPane.handleResize(this.getWidth(),lowerPane.getHeight());
                upperPane.handleResize(this.getWidth(),upperPane.getHeight());
            }
        } else
            handleMouseNotDragged(id, x, y, clickCount, button, modifiersEx);
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
     * Set the first child of this {@code HorizontalSplitPane}.
     *
     * @param pane : the new first child (of type {@code Pane}) of this {@code Pane}
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
     * @param pane : the second childPane of this {@code HorizontalSplitPane}
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
        lowerPane.setyPos((int) (yPos + getHeight()*upperFraction));
    }

    /**
     * A variable of type {@link Pane} to hold the upper childPane of this {@code HorizontalSplitPane} 
     */
    private Pane upperPane;

    /**
     * A variable of type {@link Pane} to hold the lower childPane of this {@code HorizontalSplitPane} 
     */
    private Pane lowerPane;

    private double upperFraction = 0.5;
}
