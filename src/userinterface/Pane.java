package userinterface;

import domainlayer.DocumentListener;
import domainlayer.UIController;

public abstract class Pane extends AbstractFrame implements DocumentListener {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param x      : The x coordinate of this AbstractFrame.
     * @param y      : The y coordinate of this AbstractFrame.
     * @param width  : The width of this AbstractFrame
     * @param height : The height of this AbstractFrame
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public Pane(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
    }

    /**
     * A copy constructor in order
     * to create a deep copy of
     * {@code Pane} objects.
     *
     * @param p: the {@code Pane} to be copied.
     */
    public Pane(Pane p) {
        super(p.getxPos(), p.getyPos(), p.getWidth(), p.getHeight());
        this.parentPane = p.parentPane.deepCopy();
        this.subPane = p.subPane.deepCopy();
        this.focusedPane = p.focusedPane.deepCopy();
    }

    public abstract Pane deepCopy();

    /**
     * Set the {@link ContentFrame} object that currently has focus.
     *
     * @param pane: The {@code ContentFrame} object to be set.
     */
    public void setFocusedPane(Pane pane) {
        this.focusedPane = pane;
        if (parentPane != null)
            parentPane.setFocusedPane(pane);
    }

    public UIController getController() {
        return this.subPane.getController();
    }

    public DocumentCell getContent() {
        return this.subPane.getContent();
    }

    public Pane getRootPane() {
        if (this.parentPane == null)
            return this;
        return parentPane.getRootPane();
    }

    /**
     * Define what the class that implements
     * this {@link DocumentListener} Interface
     * should do when the contents of the
     * linked {@link domainlayer.Document} change.
     */
    @Override
    public abstract void contentChanged();

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    public abstract Pane getHorizontalSplit();

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    public abstract Pane getVerticalSplit();

    public abstract void replaceChildPane(Pane oldPane, Pane newPane);

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
    public abstract void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx);

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id          : The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode     : The KeyEvent code (Determines the involved key)
     * @param keyChar     : The character representation of the involved key
     * @param modifiersEx : Specifies other keys that were involved in the event
     */
    @Override
    public abstract void handleKey(int id, int keyCode, char keyChar, int modifiersEx);

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
    public abstract void handleResize(int newWindowWidth, int newWindowHeight);

    /**
     * Check whether this {@code Pane} currently has focus.
     *
     * @return focus: True iff. this {@code Pane} currently has focus.
     */
    public boolean hasFocus() {
        return this.hasFocus;
    }


    public abstract void setController(UIController controller);

    protected abstract void setParentPane(Pane pane);

    private Pane subPane;

    protected Pane focusedPane;

    protected Pane parentPane;

    public Pane getFocusedPane() {
        return this.focusedPane;
    };

}
