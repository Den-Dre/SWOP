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
        // Assign focus to one of the `Pane`s
        lowerPane.toggleFocus(true);
        setFocusedPane(lowerPane);

        upperPane.setxPos(x);
        upperPane.setyPos(y);
        upperPane.setWidth(width);
        upperPane.setHeight(height/2);
        upperPane.setParentPane(this);
        upperPane.toggleFocus(false);

        updateListener();
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

    private Pane upperPane;

    private Pane lowerPane;
}
