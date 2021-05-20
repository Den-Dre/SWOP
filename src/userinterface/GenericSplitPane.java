package userinterface;

import domainlayer.UIController;

import java.awt.*;
import java.awt.event.MouseEvent;

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
        setFirstChild(childPane); // horizontal split -> = lower pane; vertical split -> = left pane
        ContentFrame cf = new ContentFrame(x, y, width, height);
        setSecondChild(new LeafPane(cf, getFirstChild().getController())); // horizontal split -> = upper pane; vertical split -> = right pane
        this.lastResize = childPane.lastResize;
    }

    protected void updateListeners() {
        // Update domain layer knowledge
        UIController controller = getController();
        controller.addDocumentListener(getFirstChild().getId(), getFirstChild());
        controller.addDocumentListener(getSecondChild().getId(), getSecondChild(), getFirstChild().getId());
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
     * @param newWindowWidth  : parameter containing the new window-width of this AbstractFrame.
     * @param newWindowHeight : parameter containing the new window-height of this AbstractFrame.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setWidth(getFirstChild().getWidth() + getSecondChild().getWidth());
        setHeight(getFirstChild().getHeight() + getSecondChild().getHeight());
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
     * Handle a MouseEvent in a SplitPane when no dragging action was performed.
     *
     * @param id          : The type of mouse activity
     * @param x           : The x coordinate of the mouse activity
     * @param y           : The y coordinate of the mouse activity
     * @param clickCount  : The number of clicks
     * @param button      : The mouse button that was clicked
     * @param modifiersEx : The control keys that were held on the click
     */
    public void handleMouseNotDragged(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (id == MouseEvent.MOUSE_RELEASED && dragging){
            dragging = false;
            getFocusedPane().handleMouse(id, x, y, clickCount, button, modifiersEx);
        }
        else {
            dragging = false;
            getFirstChild().handleMouse(id, x, y, clickCount, button, modifiersEx);
            getSecondChild().handleMouse(id, x, y, clickCount, button, modifiersEx);
        }

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
     * Get the {@link Pane} object that currently has focus.
     */
    @Override
    public Pane getFocusedPane() {
        return parentPane != null ? parentPane.getFocusedPane() : focusedPane;
    }

    public abstract Pane getFirstChild();

    public abstract Pane getSecondChild();

    abstract void drawSeparator(Graphics g);

    /**
     * A constant that denotes the thickness
     * of the separator drawn between split windows.
     */
    protected final int SEPARATOR_THICKNESS = 10;

    public boolean dragging = false;
}
