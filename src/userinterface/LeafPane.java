package userinterface;

import domainlayer.Document;
import domainlayer.DocumentListener;
import domainlayer.UIController;

import java.awt.*;
import java.awt.event.MouseEvent;
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
        System.out.println("made a leaf pane! " +  this);
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
     * Render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        contentFrame.render(g);
        if (hasFocus())
            drawFocusedBorder(g);
    }

    private void drawFocusedBorder(Graphics g) {
        int offset = 5;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLUE);
        g2.drawRect(getxPos(), getyPos(), getWidth()-offset, getHeight()-offset);
        // Reset the stroke for future drawing
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * If the new window dimensions are legal, the UserInterface.LeafPane gets resized.
     * It also resizes its content.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setWidth(newWindowWidth);
        setHeight(newWindowHeight);
        contentFrame.setxPos(getxPos());
        contentFrame.setyPos(getyPos());
        contentFrame.handleResize(newWindowWidth, newWindowHeight);
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
        if (this == getRootPane()) {
            contentFrame.setWelcomeDocument();
            return this;
        }
        Pane sibling = parentPane.getFirstChild() == this ?
                       parentPane.getSecondChild() : parentPane.getFirstChild();
        updateParents(sibling);
        sibling.setxPos(parentPane.getxPos());
        sibling.setyPos(parentPane.getyPos());
        sibling.handleResize(parentPane.getWidth(), parentPane.getHeight());
        sibling.setParentWidth(parentPane.getWidth());
        sibling.setParentHeight(parentPane.getHeight());
        sibling.setParentPane(parentPane.getParentPane());
        setFocusedPane(sibling);
        setParentPane(null);
        return sibling;
    }

    /**
     * Update the parent of the parent of the given {@link Pane}.
     * @param sibling : the {@link Pane} whose parents will be updated.
     */
    private void updateParents(Pane sibling) {
        if (parentPane.getParentPane() == null)
            return;
        if (parentPane == parentPane.getParentPane().getFirstChild())
            parentPane.getParentPane().setFirstChild(sibling);
        else
            parentPane.getParentPane().setSecondChild(sibling);
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
        if (id == MouseEvent.MOUSE_PRESSED)
            System.out.printf("[Clicked on %s: (%d, %d; %d, %d)]\n", this.toString(), x, y, getWidth(), getHeight());
    }

//    /**
//     * Returns true if and only if (x,y) is in this UserInterface.LeafPane.
//     *
//     * @param x: The x coordinate to check
//     * @param y: the y coordinate to check
//     */
//    private boolean wasClicked(int x, int y) {
////    	System.out.println("DocArea: on: "+x+","+y);
////    	System.out.println("getX: "+this.getxPos()+", getY: "+this.getyPos());
////    	System.out.println("width: "+this.getWidth()+", height: "+this.getHeight());
//        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
//    }

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
     * Define what the class that implements
     * this {@link DocumentListener} Interface
     * should do when the contents of the
     * linked {@link ContentFrame} changes.
     */
    @Override
    public void contentChanged() {
        this.contentFrame.contentChanged();
        this.contentFrame.setyReference(getyPos());
    }

    public UIController getController() {
        return contentFrame.getController();
    }


//    /**
//     * Wrap the given {@link DocumentCell} in a
//     * {@link UITable} s.t. it can be easily decorated
//     * by means of the decorator pattern which is implemented by
//     * {@link VerticalScrollBarDecorator} and {@link HorizontalScrollBarDecorator}.
//     *
//     *
//     * @param cell: The {@link DocumentCell} to be wrapped.
//     * @return wrapped: The given {@link DocumentCell} wrapped in a {@link UITable}.
//     */
//    private DocumentCell wrapInDocumentCell(DocumentCell cell) {
//        ArrayList<ArrayList<DocumentCell>> overlayContents =
//                new ArrayList<>(Collections.singletonList(new ArrayList<>(Collections.singletonList(cell))));
//        return new UITable(cell.getxPos(), cell.getyPos(), cell.getWidth(), cell.getHeight(), overlayContents);
//    }

//    /**
//     * Set the content of this LeafPane
//     * to the provided {@link ContentSpan}.
//     *
//     * The content to be set is wrapped in a
//     * {@link UITable} for easy decoration using the
//     * {@link HorizontalScrollBarDecorator} and
//     * {@link VerticalScrollBarDecorator} decorators.
//     *
//     * @param content:
//     *               The content that should be set.
//     */
//    public void setContent(DocumentCell content) {
//        DocumentCell wrapped = wrapInDocumentCell(content);
//        this.content = new HorizontalScrollBarDecorator(new VerticalScrollBarDecorator(wrapped));
//        this.content.setParentWidth(getWidth());
//        this.content.setParentHeight(getHeight());
//    }

    /**
     * Set the LeafPane's controller to a given controller
     *
     * @param controller
     *        The new controller
     */
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
        //contentFrame.handleResize(getWidth(),newHeight);
    }

    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        contentFrame.setParentWidth(parentWidth);
    }

    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        contentFrame.setParentHeight(parentHeight);
    }

    @Override
    public void setxReference(int xReference) {
        super.setxReference(xReference);
        contentFrame.setxReference(xReference);
    }

    @Override
    public void setyReference(int yReference) {
        super.setyReference(yReference);
        contentFrame.setyReference(yReference);
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

    public DocumentCell getContentWithoutScrollbars() {
        return ((DocumentCellDecorator) contentFrame.getContent()).getContentWithoutScrollbars();
    }

    /**
     * The content that is represented by this ContentFrame.
     */
    private final ContentFrame contentFrame;
}


