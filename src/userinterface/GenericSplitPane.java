package userinterface;

import domainlayer.UIController;

import java.awt.*;

public abstract class GenericSplitPane extends Pane {
    /**
     * Initialise this {@code GenericSplitPane} with the given parameters.
     *
     * @param x      : The x coordinate of this AbstractFrame.
     * @param y      : The y coordinate of this AbstractFrame.
     * @param width  : The width of this AbstractFrame
     * @param height : The height of this AbstractFrame
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public GenericSplitPane(int x, int y, int width, int height, LeafPane childPane, Pane parentPane) throws IllegalDimensionException {
        super(x, y, width, height);
        setParentPane(parentPane);
        setFirstChild(childPane);
        ContentFrame cf = new ContentFrame(x, (height-y)/2, width, height);
        setSecondChild(new LeafPane(cf, getFirstChild().getController()));

    }

    protected void updateListener() {
        // Update domain layer knowledge
        Pane secondChild = getSecondChild();
        UIController controller = secondChild.getController();
        controller.addDocumentListener(secondChild.getId(), secondChild);
    }

    public GenericSplitPane(GenericSplitPane pane) {
        super(pane.getxPos(), pane.getyPos(), pane.getWidth(), pane.getHeight());
    }

    /**
     * render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        drawSeparator(g);
        getFirstChild().render(g);
        getSecondChild().render(g);
    }

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getHorizontalSplit() {
        if (getFirstChild().hasFocus())
            return getFirstChild().getHorizontalSplit();
        else if (getSecondChild().hasFocus())
            return getSecondChild().getHorizontalSplit();
        throw new UnsupportedOperationException("Can't split a Pane that doesn't have focus.");
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getVerticalSplit() {
        if (getFirstChild().hasFocus())
            return getFirstChild().getVerticalSplit();
        else if (getSecondChild().hasFocus())
            return getSecondChild().getVerticalSplit();
        throw new UnsupportedOperationException("Can't split a Pane that doesn't have focus.");
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
        getFirstChild().handleMouse(id, x, y, clickCount, button, modifiersEx);
        getSecondChild().handleMouse(id, x, y, clickCount, button, modifiersEx);
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
        if (getFirstChild().hasFocus())
            getFirstChild().handleKey(id, keyCode, keyChar, modifiersEx);
        else if (getSecondChild().hasFocus())
            getSecondChild().handleKey(id, keyCode, keyChar, modifiersEx);
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
        getFirstChild().handleResize(newWindowWidth, newWindowHeight/2);
        getSecondChild().handleResize(newWindowWidth, newWindowHeight/2);
    }

    @Override
    public void setController(UIController controller) {
        getFirstChild().setController(controller);
        getSecondChild().setController(controller);
    }

    /**
     * Define what the class that implements
     * this {@link domainlayer.DocumentListener} Interface
     * should do when the contents of the
     * child {@link Pane}s change.
     */
    @Override
    public void contentChanged() {
        getFirstChild().contentChanged();
        getSecondChild().contentChanged();
    }

    /**
     * Check whether this {@code Pane} currently has focus.
     *
     * @return focus: True iff. this {@code Pane} currently has focus.
     */
    @Override
    public boolean hasFocus() {
        return (getSecondChild().hasFocus() || getFirstChild().hasFocus());
    }

    /**
     * Set the {@link ContentFrame} object that currently has focus.
     *
     * @param pane : The {@code ContentFrame} object to be set.
     */
    @Override
    public void setFocusedPane(Pane pane) {
        this.focusedPane = pane;
    }

    public abstract Pane getFirstChild();

    public abstract Pane getSecondChild();

    public abstract void setFirstChild(Pane pane);

    public abstract void setSecondChild(Pane pane);

    abstract void drawSeparator(Graphics g);

    /**
     * A constant that denotes the thickness
     * of the separator drawn between split windows.
     */
    protected final int SEPARATOR_THICKNESS = 5;
}