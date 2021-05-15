package userinterface;

import domainlayer.UIController;

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
        super(x, y, width, height);
        this.parentPane = parentPane;

        // Associate the original `Pane` object to the `lowerPane` attribute...
        lowerPane = childPane;
        // ... and associate a deep copy of the original `Pane` object to the `upperPane` attribute
        // This way we ensure that changing one `Pane` doesn't change the other `Pane` object.
//        upperPane = childPane.deepCopy();
        ContentFrame c = new ContentFrame(lowerPane.getxPos(), lowerPane.getyPos(), lowerPane.getWidth(), lowerPane.getHeight());
        upperPane = new LeafPane(c, lowerPane.getController());

        // Set the sizes & position of the sub-panes
        lowerPane.setxPos(x);
        lowerPane.setyPos(y + height/2);
        lowerPane.setWidth(width);
        lowerPane.setHeight(height/2);
        lowerPane.setParentPane(this);
        // Assign focus to one of the `Pane`s
        lowerPane.toggleFocus(true);

        upperPane.setxPos(x);
        upperPane.setyPos(y);
        upperPane.setWidth(width);
        upperPane.setHeight(height/2);
        upperPane.setParentPane(this);
        upperPane.toggleFocus(false);

        // Update domain layer knowledge
        UIController controller = upperPane.getController();
        controller.addDocumentListener(upperPane);
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.fillRect(lowerPane.getxPos(), lowerPane.getyPos()-SEPARATOR_THICKNESS-3, getWidth()-getxPos(), SEPARATOR_THICKNESS);
    }

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getHorizontalSplit() {
        if (lowerPane.hasFocus())
            return lowerPane.getHorizontalSplit();
        else if (upperPane.hasFocus())
            return upperPane.getHorizontalSplit();
        return null;
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getVerticalSplit() {
        if (lowerPane.hasFocus())
            return lowerPane.getVerticalSplit();
        else if (upperPane.hasFocus())
            return upperPane.getVerticalSplit();
        return null;
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
        lowerPane.handleMouse(id, x, y, clickCount, button, modifiersEx);
        upperPane.handleMouse(id, x, y, clickCount, button, modifiersEx);
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
        if (lowerPane.hasFocus())
            lowerPane.handleKey(id, keyCode, keyChar, modifiersEx);
        else if (upperPane.hasFocus())
            upperPane.handleKey(id, keyCode, keyChar, modifiersEx);
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
        this.lowerPane.handleResize(newWindowWidth, newWindowHeight/2);
        this.upperPane.handleResize(newWindowWidth, newWindowHeight/2);
    }

    @Override
    public void setController(UIController controller) {
        lowerPane.setController(controller);
        upperPane.setController(controller);
    }

    /**
     * Define what the class that implements
     * this {@link domainlayer.DocumentListener} Interface
     * should do when the contents of the
     * child {@link Pane}s change.
     */
    @Override
    public void contentChanged() {
        lowerPane.contentChanged();
        upperPane.contentChanged();
    }

    /**
     * Check whether this {@code Pane} currently has focus.
     *
     * @return focus: True iff. this {@code Pane} currently has focus.
     */
    @Override
    public boolean hasFocus() {
        return (upperPane.hasFocus() || lowerPane.hasFocus());
    }

    private Pane upperPane;

    private Pane lowerPane;
}
