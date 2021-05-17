package userinterface;

import domainlayer.UIController;

import java.awt.*;
import java.util.Arrays;

/**
 * A class to represent the portion of Browsr that renders the document
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
        this.contentFrame.setId(id);
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
        // Add this LeafPane to the controller and retrieve its `id` generated by the controller
        this.id = getController().addPaneDocument();
        if (p.parentPane == null)
            this.parentPane = null;
        else // this LeafPane's parentPane remains `null` as its the root of the `Pane` tree structure
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
    }

    /**
     * Handle a vertical split of the contents of this {@code Pane}.
     */
    @Override
    public Pane getVerticalSplit() {
        return new VerticalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this, parentPane);
    }

    public Pane closeLeafPane() {
        Pane sibling = parentPane.getFirstChild() == this ?
                       parentPane.getSecondChild() : parentPane.getFirstChild();
        sibling.handleResize(parentPane.getWidth(), parentPane.getHeight());
//        sibling.handleResize(getLastResize()[0], getLastResize()[1]);
        sibling.setParentPane(parentPane.getParentPane());
        setFocusedPane(sibling);
        setParentPane(null);
        return sibling;
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
        if (!contentFrame.wasClicked(x, y) && getRootPane().wasClicked(x, y))
            // Then a different LeafPane must have been clicked
            toggleFocus(false);
        else if (contentFrame.wasClicked(x, y)) {
            setFocusedPane(this);
            toggleFocus(true);
            getController().setCurrentDocument(this.id);
            contentFrame.handleMouse(id, x, y, clickCount, button, modifiersEx);
        }
        System.out.println("[Clicked on " + this + ": (" + getWidth() + ", " + getHeight() + ")]");
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
        setWidth(contentFrame.getWidth());
        setHeight(contentFrame.getHeight());
        setLastResize(newWindowWidth, newWindowHeight);
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
     * Get the id associated to this {@code LeafPane}.
     *
     * @return id: the id associated to this {@code LeafPane}.
     */
    // Note: if this method is not overridden and instead
    // taken from Pane.java, incorrect behaviour occurs due
    // to the id of the parentPane being returned.
    @Override
    public int getId() {
        return this.id;
    }


    @Override
    public Pane getFirstChild() {
        throw new UnsupportedOperationException("Can't request child of a LeafPane.");
    }

    @Override
    public Pane getSecondChild() {
        throw new UnsupportedOperationException("Can't request child of a LeafPane.");
    }

    @Override
    public void setFirstChild(Pane pane) {
        throw new UnsupportedOperationException("Can't set child Pane of a LeafPane.");
    }

    @Override
    public void setSecondChild(Pane pane) {
        throw new UnsupportedOperationException("Can't set child Pane of a LeafPane.");
    }

    @Override
    public void replacePaneWith(Pane oldPane, Pane newPane) { }

    /**
     * The content that is represented by this ContentFrame.
     */
    private final ContentFrame contentFrame;
}


