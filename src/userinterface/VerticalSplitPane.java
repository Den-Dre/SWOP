package userinterface;

import java.awt.*;

public class VerticalSplitPane extends GenericSplitPane {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param x      : The x coordinate of this AbstractFrame.
     * @param y      : The y coordinate of this AbstractFrame.
     * @param width  : The width of this AbstractFrame
     * @param height : The height of this AbstractFrame
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
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
     * It makes sure the AbstractFrame is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this AbstractFrame.
     * @param newWindowHeight : parameter containing the new window-height of this AbstractFrame.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
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
    @Override
    public Pane getFirstChild() {
        return leftPane;
    }

    @Override
    public Pane getSecondChild() {
        return rightPane;
    }

    @Override
    public void setFirstChild(Pane pane) {
        this.leftPane = pane;
    }

    @Override
    public void setSecondChild(Pane pane) {
        this.rightPane = pane;
    }

    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        leftPane.setxPos(xPos);
        rightPane.setxPos(xPos + getWidth()/2);
    }

    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        leftPane.setyPos(yPos);
        rightPane.setyPos(yPos);
    }

    @Override
    public void setxReference(int xReference) {
        super.setxReference(xReference);
        leftPane.setxReference(xReference);
        rightPane.setxReference(xReference+getHeight()/2);
    }

    @Override
    public void setyReference(int yReference) {
        super.setyReference(yReference);
        leftPane.setyReference(yReference);
        rightPane.setyReference(yReference);
    }

    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
    }

    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
    }

    private Pane leftPane;

    private Pane rightPane;
}
