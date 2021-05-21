package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;

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
        setFocusedPane(leftPane);

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
     * This method handles resizes.
     * It makes sure the VerticalSplitPane is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this VerticalSplitPane.
     * @param newWindowHeight : parameter containing the new window-height of this VerticalSplitPane.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setWidth(newWindowWidth);
        setHeight(newWindowHeight);
        getFirstChild().setxPos(getxPos());
        getFirstChild().handleResize((int) (newWindowWidth* leftFraction), newWindowHeight);
        getSecondChild().setxPos((int) (getxPos() + newWindowWidth* leftFraction));
        getSecondChild().handleResize((int) (newWindowWidth*(1- leftFraction)),newWindowHeight);
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
        g2.setColor(Color.black);
        g2.fillRect(rightPane.getxPos()-SEPARATOR_THICKNESS, rightPane.getyPos(), SEPARATOR_THICKNESS, rightPane.getHeight());
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Retruns true if and only if the coordinates are in the separator
     * @param x : The x coordinate
     * @param y : The y coordinate
     * @return  : True iff the click was in the separator
     */
    private boolean isInSeperator(int x, int y) {
        return ((x < rightPane.getxPos() + SEPARATOR_THICKNESS / 2) && (x > rightPane.getxPos() - SEPARATOR_THICKNESS) &&
                (y >= this.getyPos() && y < this.getyPos() + this.getHeight()));
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
        else if (id == MouseEvent.MOUSE_DRAGGED && dragging){
            if (x < rightPane.getxPos()) {
                int oldX = rightPane.getxPos();
                int deltaX = Math.abs(oldX - x);
                if (leftPane.getWidth()-deltaX <= SEPARATOR_THICKNESS) return;
                rightPane.setxPos(x);
                rightPane.setWidth(rightPane.getWidth() + deltaX);
                leftPane.setWidth(leftPane.getWidth() - deltaX);
                leftFraction = (double) (leftPane.getWidth())/getWidth();
                rightPane.handleResize(rightPane.getWidth(),this.getHeight());
                leftPane.handleResize(leftPane.getWidth(),this.getHeight());
            } else {
                int oldX = rightPane.getxPos();
                int deltaX = Math.abs(oldX - x);
                if (rightPane.getWidth()-deltaX <= SEPARATOR_THICKNESS) return;
                rightPane.setxPos(x);
                rightPane.setWidth(rightPane.getWidth() - deltaX);
                leftPane.setWidth(leftPane.getWidth() + deltaX);
                leftFraction = (double) (leftPane.getWidth())/getWidth();
                rightPane.handleResize(rightPane.getWidth(),this.getHeight());
                leftPane.handleResize(leftPane.getWidth(),this.getHeight());
            }
        } else
            handleMouseNotDragged(id, x, y, clickCount, button, modifiersEx);
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
     *
     * @param pane : the first childPane of this {@code VerticalSplitPane}
     */
    @Override
    public void setFirstChild(Pane pane) {
        this.leftPane = pane;
    }

    /**
     * Sets the second (right-side) childPane of this {@code VerticalSplitPane}
     * @param pane : the second childPane of this {@code VerticalSplitPane}
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
        rightPane.setxPos((int) (xPos + getWidth()* leftFraction));
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

    /**
     * A variable of type {@link Pane} to hold the left childPane of this {@code VerticalSplitPane} 
     */
    private Pane leftPane;

    /**
     * A variable of type {@link Pane} to hold the right childPane of this {@code VerticalSplitPane} 
     */
    private Pane rightPane;

    private double leftFraction = 0.5;
}
