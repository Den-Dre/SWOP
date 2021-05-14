package userinterface;

import java.awt.*;

/**
 * A class to represent the portion of Broswr that renders the document
 */
public class LeafPane extends Pane {
    /**
     * Construct a {@code ContentFrame} with the given parameters.
     *
     * @param x      : The x coordinate of the {@code ContentFrame}.
     * @param y      : The y coordinate of the {@code ContentFrame}.
     * @param width  : The width of the {@code ContentFrame}.
     * @param height : The height of the {@code ContentFrame}.
     * @throws IllegalDimensionException : When one of the dimensions is negative.
     */
    public LeafPane(ContentFrame contentFrame, Pane parentPane) throws IllegalDimensionException {
        super(contentFrame.getxPos(), contentFrame.getyPos(), contentFrame.getWidth(), contentFrame.getHeight());
        this.contentFrame = contentFrame;
        this.parentPane = parentPane;
    }

    /**
     * A copy constructor in order
     * to create a deep copy of
     * {@code Pane} objects.
     *
     * @param p : the {@code Pane} to be copied.
     */
    public LeafPane(LeafPane p) {
        super(p.getxPos(), p.getyPos(), p.getWidth(), p.getHeight());
        this.contentFrame = p.contentFrame.deepCopy();
    }

    @Override
    public Pane deepCopy() {
        return null;
    }

    /**
     * render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        contentFrame.render(g);
    }

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    @Override
    public void handleHorizontalSplit() {
        HorizontalSplitPane hsPane = new HorizontalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this);
        parentPane.replaceChildPane(this, hsPane);
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public void handleVerticalSplit() {
        VerticalSplitPane hsPane = new VerticalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this);
        parentPane.replaceChildPane(this, hsPane);
    }

    @Override
    public void replaceChildPane(Pane oldPane, Pane newPane) {
        throw new UnsupportedOperationException("Can't call replaceChildPane on a LeafPane object.");
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
        contentFrame.handleMouse(id, x, y, clickCount, button, modifiersEx);
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
        contentFrame.handleKey(id, keyCode, keyChar, modifiersEx);
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
        contentFrame.handleResize(newWindowWidth, newWindowHeight);
    }

    protected void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public ContentFrame getContentFrame() {
        return contentFrame;
    }


    /**
     * The content that is represented by this ContentFrame.
     */
    private ContentFrame contentFrame;

    private Pane parentPane;
}


