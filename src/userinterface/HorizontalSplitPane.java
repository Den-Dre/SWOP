package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HorizontalSplitPane extends GenericSplitPane {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param x      : The x coordinate of this AbstractFrame.
     * @param y      : The y coordinate of this AbstractFrame.
     * @param width  : The width of this AbstractFrame
     * @param height : The height of this AbstractFrame
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
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
     * It makes sure the AbstractFrame is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this AbstractFrame.
     * @param newWindowHeight : parameter containing the new window-height of this AbstractFrame.
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
    void drawSeparator(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.fillRect(lowerPane.getxPos(), lowerPane.getyPos()-SEPARATOR_THICKNESS, lowerPane.getWidth(), SEPARATOR_THICKNESS);
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Retruns true if and only if the coordinates are in the separator
     * @param x : The x coordinate
     * @param y : The y coordinate
     * @return  : True iff the click was in the separator
     */
    boolean isInSeperator(int x, int y) {
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
        if (id == MouseEvent.MOUSE_PRESSED && isInSeperator(x,y)) dragging = true;
        else if((id == MouseEvent.MOUSE_DRAGGED) && dragging) {
            int oldY = lowerPane.getyPos();
            int deltaY = Math.abs(oldY-y);
            if(y < lowerPane.getyPos()) {
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
        }
        else
            handleMouseNotDragged(id, x, y, clickCount, button, modifiersEx);
    }

    @Override
    public Pane getFirstChild() {
        return lowerPane;
    }

    @Override
    public void setFirstChild(Pane pane) {
        this.lowerPane = pane;
    }

    @Override
    public Pane getSecondChild() {
        return upperPane;
    }

    @Override
    public void setSecondChild(Pane pane) {
        this.upperPane = pane;
    }

    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        upperPane.setxPos(xPos);
        lowerPane.setxPos(xPos);
    }

    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        upperPane.setyPos(yPos);
        lowerPane.setyPos((int) (yPos + getHeight()*upperFraction));
    }

    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        upperPane.setParentWidth(parentWidth);
        lowerPane.setParentWidth(parentWidth);
    }

    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        upperPane.setParentHeight(parentHeight);
        lowerPane.setParentHeight(parentHeight);
    }

    private Pane upperPane;

    private Pane lowerPane;

    private double upperFraction = 0.5;
}
