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
    public HorizontalSplitPane(int x, int y, int width, int height, Pane pane) throws IllegalDimensionException {
        super(x, y, width, height);

        // Associate the original `Pane` object to the `lowerPane` attribute...
        lowerPane = pane;
        // ... and associate a deep copy of the original `Pane` object to the `upperPane` attribute
        // This way we ensure that changing one `Pane` doesn't change the other ´Pane` object, too.
        upperPane = pane.deepCopy();

        // Set the sizes & position of the sub-panes
        lowerPane.setxPos(x);
        lowerPane.setyPos(y + height/2);
        lowerPane.setWidth(width);
        lowerPane.setHeight(height/2);
        lowerPane.setParentPane(this);

        upperPane.setxPos(x);
        upperPane.setyPos(y);
        upperPane.setWidth(width);
        upperPane.setHeight(height/2);
    }

    /**
     * Copy constructor: creates a deep copy
     * of this {@code HorizontalSplitPane}.
     *
     * @param pane: the {@code HorizontalSplitPane} to be deep copied.
     */
    public HorizontalSplitPane(HorizontalSplitPane pane) {
        super(pane.getxPos(), pane.getyPos(), pane.getWidth(), pane.getHeight());
        this.upperPane = pane.upperPane.deepCopy();
        this.lowerPane = pane.lowerPane.deepCopy();
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
     * render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        drawSeparator(g);
        lowerPane.render(g);
        upperPane.render(g);
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
        g.fillRect(lowerPane.getxPos(), lowerPane.getyPos(), getWidth(), SEPARATOR_THICKNESS);
    }


    /**
     * Set the {@link ContentFrame} object that currently has focus.
     *
     * @param contentFrame : The {@code ContentFrame} object to be set.
     */
    @Override
    public void setFocusedPane(ContentFrame contentFrame) {
        super.setFocusedPane(contentFrame);
    }

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    @Override
    public void handleHorizontalSplit() {
        lowerPane.handleHorizontalSplit();
        upperPane.handleHorizontalSplit();
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public void handleVerticalSplit() {
        lowerPane.handleVerticalSplit();
        upperPane.handleVerticalSplit();
    }

    @Override
    public void replaceChildPane(Pane oldPane, Pane newPane) {
        if (lowerPane == oldPane)
            lowerPane = newPane;
        else
            upperPane = newPane;
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
        // Delegate to the sub-pane that has focus, if any.
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

    @Override
    protected void setParentPane(Pane pane) {

    }

    private Pane upperPane;

    private Pane lowerPane;
}
