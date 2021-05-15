package userinterface;

import domainlayer.UIController;

import java.awt.*;

/**
 * A class to represent the portion of Broswr that renders the document
 */
public class LeafPane extends Pane {
    /**
     * Construct a {@code ContentFrame} with the given parameters.
     * The coordinates and dimensions are copied from the given {@link ContentFrame}.
     *
     * @param contentFrame: The contents of this {@code LeafPane} in a {@link ContentFrame} object.
     * @param parentPane: The parent {@link Pane} of this {@code LeafPane}.
     * @throws IllegalDimensionException : When one of the dimensions is negative.
     */
    public LeafPane(ContentFrame contentFrame, Pane parentPane) throws IllegalDimensionException {
        super(contentFrame.getxPos(), contentFrame.getyPos(), contentFrame.getWidth(), contentFrame.getHeight());
        this.contentFrame = contentFrame;
        this.parentPane = parentPane;
        this.id = getController().addPaneDocument();
    }


    /**
     * Construct a {@code ContentFrame} with the given parameters.
     *
     * @param contentFrame: The contents of this {@code LeafPane} in a {@link ContentFrame} object.
     * @throws IllegalDimensionException : When one of the dimensions is negative.
     */
    public LeafPane(ContentFrame contentFrame, UIController controller) throws IllegalDimensionException {
        super(contentFrame.getxPos(), contentFrame.getyPos(), contentFrame.getWidth(), contentFrame.getHeight());
        this.contentFrame = contentFrame;
        this.parentPane = null;
        this.contentFrame.setController(controller);
        this.id = getController().addPaneDocument();
        setFocusedPane(this);
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
        this.id = getController().addPaneDocument();
        if (p.parentPane == null)
            this.parentPane = null;
        else
            this.parentPane = p.parentPane.deepCopy();
    }

    @Override
    public LeafPane deepCopy() {
        return new LeafPane(this);
    }

    /**
     * Render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        contentFrame.render(g);
        setHeight(contentFrame.getHeight());
        setWidth(contentFrame.getWidth());
        if (hasFocus())
            drawFocusedBorder(g);
    }

    private void drawFocusedBorder(Graphics g) {
        int offset = 5;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLUE);
        g2.drawRect(getxPos(), getyPos(), getWidth()-offset, getHeight()-offset);
    }

    /**
     * Handle a horizontal split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getHorizontalSplit() {
        return new HorizontalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this, parentPane);
//        parentPane.replaceChildPane(this, hsPane);
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getVerticalSplit() {
        return new VerticalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this, parentPane);
//        parentPane.replaceChildPane(this, hsPane);
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
        if (!contentFrame.wasClicked(x, y))
            toggleFocus(false);
        else {
            setFocusedPane(this);
            toggleFocus(true);
            contentFrame.handleMouse(id, x, y, clickCount, button, modifiersEx);
        }
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
        setWidth(newWindowWidth);
        setHeight(newWindowHeight);
        contentFrame.handleResize(newWindowWidth, newWindowHeight);
    }

    /**
     * Define what the class that implements
     * this {@link domainlayer.DocumentListener} Interface
     * should do when the contents of the
     * linked {@link ContentFrame} changes.
     */
    @Override
    public void contentChanged() {
        this.contentFrame.contentChanged();
    }

    public UIController getController() {
        return contentFrame.getController();
    }

    public void setController(UIController controller) {
        this.contentFrame.setController(controller);
    }

    public ContentFrame getContentFrame() {
        return contentFrame;
    }

    /**
     * Set the x position of this LeafPane and its contents to the given value
     *
     * @param xPos :
     *             The value this LeafPane's x position should be set to.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        contentFrame.setxPos(xPos);
    }

    /**
     * Set the y position of this LeafPane and its contents to the given value
     *
     * @param yPos :
     *             The value this LeafPane and its contents' y position should be set to.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        contentFrame.setyPos(yPos);
    }

    /**
     * Set the width of this LeafPane and its contents to the given value.
     *
     * @param newWidth :
     *                 The new value of this LeafPane and its contents" width should be set to.
     */
    @Override
    public void setWidth(int newWidth) {
        super.setWidth(newWidth);
        contentFrame.setWidth(newWidth);
    }

    /**
     * Set the height of this LeafPane and its contents to the given value.
     *
     * @param newHeight :
     *                  The new value of this LeafPane and its contents' height should be set to.
     */
    @Override
    public void setHeight(int newHeight) {
        super.setHeight(newHeight);
        contentFrame.setHeight(newHeight);
    }

    /**
     * The content that is represented by this ContentFrame.
     */
    private final ContentFrame contentFrame;

    private final Pane parentPane;

    protected int id;
}


