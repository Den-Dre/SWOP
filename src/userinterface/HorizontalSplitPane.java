package userinterface;

import java.awt.*;

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
     * It makes sure the AbstractFrame is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this AbstractFrame.
     * @param newWindowHeight : parameter containing the new window-height of this AbstractFrame.
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
}
