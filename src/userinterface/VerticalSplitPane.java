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
        // Assign focus to one of the child `Pane`s
        leftPane.toggleFocus(true);

        rightPane.setxPos(x + width/2);
        rightPane.setyPos(y);
        rightPane.setWidth(width/2);
        rightPane.setHeight(height);
        rightPane.setParentPane(this);
        rightPane.toggleFocus(false);

        updateListener();
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

    private Pane leftPane;

    private Pane rightPane;
}
