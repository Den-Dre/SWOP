package userinterface;

import domainlayer.UIController;

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
    public VerticalSplitPane(int x, int y, int width, int height, Pane childPane, Pane parentPane) throws IllegalDimensionException {
        super(x, y, width, height);
        this.parentPane = parentPane;

        // Associate the original `Pane` object to the `leftPane` attribute...
        leftPane = childPane;
        // ... and associate a deep copy of the original `Pane` object to the `rightPane` attribute
        // This way we ensure that changing one `Pane` doesn't change the other ´Pane` object, too.
        rightPane = childPane.deepCopy();

        // Set the sizes & position of the sub-panes
        leftPane.setxPos(x);
        leftPane.setyPos(y);
        leftPane.setWidth(width/2);
        leftPane.setHeight(height);

        rightPane.setxPos(x + width/2);
        rightPane.setyPos(y);
        rightPane.setWidth(width/2);
        rightPane.setHeight(height);
    }

    /**
     * Copy constructor: creates a deep copy
     * of this {@code VerticalSplitPane}.
     *
     * @param pane: the {@code VerticalSplitPane} to be deep copied.
     */
    public VerticalSplitPane(VerticalSplitPane pane) {
        super(pane.getxPos(), pane.getyPos(), pane.getWidth(), pane.getHeight());
        this.leftPane = pane.leftPane.deepCopy();
        this.rightPane = pane.rightPane.deepCopy();
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
     * Render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        drawSeparator(g);
        leftPane.render(g);
        rightPane.render(g);
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
        g.fillRect(rightPane.getxPos(), rightPane.getyPos(), SEPARATOR_THICKNESS, getHeight());
    }

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getHorizontalSplit() {
        if (leftPane.hasFocus())
            return leftPane.getHorizontalSplit();
        else if (rightPane.hasFocus())
            return rightPane.getHorizontalSplit();
        return null;
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getVerticalSplit() {
        if (leftPane.hasFocus())
            return leftPane.getHorizontalSplit();
        else if (rightPane.hasFocus())
            return rightPane.getHorizontalSplit();
        return null;
    }

    @Override
    public void replaceChildPane(Pane oldPane, Pane newPane) {
        if (leftPane == oldPane)
            leftPane = newPane;
        else
            rightPane = newPane;
    }

    /**
     * Handle mouseEvents. Determine if this AbstractFrame was pressed and do the right actions.
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
        leftPane.handleMouse(id, x, y, clickCount, button, modifiersEx);
        rightPane.handleMouse(id, x, y, clickCount, button, modifiersEx);
    }

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id          : The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode     : The KeyEvent code (Determines the involved key)
     * @param keyChar     : The character representation of the involved key
     * @param modifiersEx : Specifies other keys that were involved in the event
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {

    }

    /**
     * This method handles resizes.
     * It makes sure the AbstractFrame is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmakrBar} would be rendered with
     * the given absolute width, and thus one would need to guess the
     * correct initial size of the window. Using this method, widths are
     * automatically adjusted: both at initialization and at runtime.</p>
     *
     * @param newWindowWidth  : parameter containing the new window-width of this AbstractFrame.
     * @param newWindowHeight : parameter containing the new window-height of this AbstractFrame.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
    }


    /**
     * Set the {@link UIController} of the sub panes
     * of this split Pane.
     *
     * @param controller: the {@link UIController} to be set.
     */
    @Override
    public void setController(UIController controller) {
        rightPane.setController(controller);
        leftPane.setController(controller);
    }

    /**
     * Define what the class that implements
     * this {@link domainlayer.DocumentListener} Interface
     * should do when the contents of the
     * linked child {@link Pane}s change.
     */
    @Override
    public void contentChanged() {
        leftPane.contentChanged();
        rightPane.contentChanged();
    }

    @Override
    protected void setParentPane(Pane pane) {
    }

    /**
     * Check whether this {@code Pane} currently has focus.
     *
     * @return focus: True iff. this {@code Pane} currently has focus.
     */
    @Override
    public boolean hasFocus() {
        return (leftPane.hasFocus() || rightPane.hasFocus());
    }

    private Pane leftPane;

    private Pane rightPane;
}
