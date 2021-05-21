package userinterface;

import domainlayer.DocumentListener;
import domainlayer.UIController;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A class to represent the portion of Browsr that renders the document extending from {@link Pane}
 * (as defined in the assignment)
 */
public class LeafPane extends Pane {
    /**
     * Construct a {@code ContentFrame} with the given parameters.
     *
     * @param contentFrame: The contents of this {@code LeafPane} in a {@link ContentFrame} object.
     * @param controller: The {@link UIController} associated to this {@code LeafPane}.
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
     * Render the contents of this LeafPane.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        contentFrame.render(g);
        if (hasFocus())
            drawFocusedBorder(g);
    }
    
    /**
     * Render the border of this {@code LeafPane} with a blue line when focused 
     * 
     * @param g : The graphics to be rendered.
     */
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
     * If the new window dimensions are legal, the {@link LeafPane} gets resized.
     * It also resizes its content.
     * 
     * @param newWindowWidth	: The new window width of this {@link LeafPane}
     * @param newWindowHeight	: The new window height of this {@link LeafPane}
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
     * Handle a horizontal split of the contents of this {@code LeafPane}.
     */
    @Override
    public Pane getHorizontalSplit() {
        return new HorizontalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this, parentPane);
    }

    /**
     * Handle a vertical split of the contents of this {@code LeafPane}.
     */
    @Override
    public Pane getVerticalSplit() {
        return new VerticalSplitPane(getxPos(), getyPos(), getWidth(), getHeight(), this, parentPane);
    }

    /**
     * Closes the current {@link LeafPane} and returns its sibling or the welcome document
     * (when this {@link LeafPane} is the root)
     * 
     * @return pane : the sibling of this (closed) {@link LeafPane} or the welcome document
     */
    public Pane closeLeafPane() {
        if (this == getRootPane()) {
            contentFrame.setWelcomeDocument();
            toggleFocus(true);
            setFocusedPane(this);
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
        setFocusedPane(sibling);
        sibling.toggleFocus(true);
        return sibling;
    }

    /**
     * Update the parent of the parent of the given {@link LeafPane}.
     * 
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
     * Handle mouseEvents. Determine if this LeafPane was pressed and do the right actions.
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
    }
    
    /**
     * Retrieve the {@link UIController} object associated to the contentFrame of 
     * this {@link LeafPane}.
     *
     * @return controller: the {@link UIController} object associated to the 
     *  contentFrame of this {@link LeafPane}.
     */
    public UIController getController() {
        return contentFrame.getController();
    }

    /**
     * Set the {@code LeafPane}'s controller to a given {@link domainlayer.UIController} object
     *
     * @param controller :
     *        The new {@link domainlayer.UIController} object of this {@code LeafPane}
     */
    public void setController(UIController controller) {
        this.contentFrame.setController(controller);
    }
    
    /**
     * Get the {@link ContentFrame} of this {@code LeafPane}.
     * 
     * @return contentFrame : the {@link ContentFrame} of this {@code LeafPane}
     */
    public ContentFrame getContentFrame() {
        return contentFrame;
    }

    /**
     * Set the x position of this {@code LeafPane} and its contents to the given value
     *
     * @param xPos :
     *             The value this {@code LeafPane}'s x position should be set to.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        contentFrame.setxPos(xPos);
    }

    /**
     * Set the y position of this {@code LeafPane} and its contents to the given value
     *
     * @param yPos :
     *             The value this {@code LeafPane} and its contents' y position should be set to.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        contentFrame.setyPos(yPos);
    }

    /**
     * Set the width of this {@code LeafPane} and its contents to the given value.
     *
     * @param newWidth :
     *                 The new value to which this {@code LeafPane} and its content's width should be set to.
     */
    @Override
    public void setWidth(int newWidth) {
        super.setWidth(newWidth);
        contentFrame.setWidth(newWidth);
    }

    /**
     * Set the height of this {@code LeafPane} and its contents to the given value.
     *
     * @param newHeight :
     *                  The new value of this {@code LeafPane} and its contents' height should be set to.
     */
    @Override
    public void setHeight(int newHeight) {
        super.setHeight(newHeight);
        contentFrame.setHeight(newHeight);
    }

    /**
     * Sets the width of the parent {@link Pane} of this {@code LeafPane} and the parent of 
     * the currentFrame of this {@code LeafPane} to the given value.
     *
     * @param parentWidth :
     *                 The new value to which the parent {@link Pane} of this {@code LeafPane}'s
     *                 and the parent of the currentFrame of this {@code LeafPane}'s width should be set to.
     */
    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        contentFrame.setParentWidth(parentWidth);
    }

    /**
     * Sets the height of the parent {@link Pane} of this {@code LeafPane} and the parent of 
     * the currentFrame of this {@code LeafPane} to the given value.
     *
     * @param parentHeight :
     * 					The new value to which the parent {@link Pane} of this {@code LeafPane}'s
     *                 	and the parent of the currentFrame of this {@code LeafPane}'s height should be set to.
     */
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        contentFrame.setParentHeight(parentHeight);
    }

    /**
     * Sets the x reference position this {@code LeafPane} and its contentFrame to the 
     * given value.
     * 
     * @param xReference : 
     * 					The new value to which the x reference position of this {@code LeafPane} 
     * 					and its contentFrame gets set.
     */
    @Override
    public void setxReference(int xReference) {
        super.setxReference(xReference);
        contentFrame.setxReference(xReference);
    }

    /**
     * Sets the y reference position this {@code LeafPane} and its contentFrame to the 
     * given value.
     * 
     * @param yReference : 
     * 					The new value to which the y reference position of this {@code LeafPane} 
     * 					and its contentFrame gets set.
     */
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
    // TODO: delete note?
    // Note: if this method is not overridden and instead
    // taken from Pane.java, incorrect behavior occurs due
    // to the id of the parentPane being returned.
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Implementation of getFirstChild declared in {@link Pane}. But a {@code LeafPane} has
     * no child {@code Pane} by design.
     * 
     * @throws UnsupportedOperationException : 
     * 											a {@code LeafPane} has no child {@code Pane} 
     * 											to return.
     */
    @Override
    public Pane getFirstChild() {
        throw new UnsupportedOperationException("Can't request child of a LeafPane.");
    }

    /**
     * Implementation of getSecondChild declared in {@link Pane}. But a {@code LeafPane} has
     * no child {@code Pane} by design.
     * 
     * @throws UnsupportedOperationException : 
     * 											a {@code LeafPane} has no child {@code Pane}
     * 											to return.
     */
    @Override
    public Pane getSecondChild() {
        throw new UnsupportedOperationException("Can't request child of a LeafPane.");
    }

    /**
     * Implementation of setFirstChild declared in {@link Pane}. But a {@code LeafPane} has
     * no child {@code Pane} by design.
     * 
     * @throws UnsupportedOperationException : 
     * 											a {@code LeafPane} has no child {@code Pane}
     * 											to be set.
     */
    @Override
    public void setFirstChild(Pane pane) {
        throw new UnsupportedOperationException("Can't set child Pane of a LeafPane.");
    }

    /**
     * Implementation of setSecondChild declared in {@link Pane}. But a {@code LeafPane} has
     * no child {@code Pane} by design.
     * 
     * @throws UnsupportedOperationException : 
     * 											a {@code LeafPane} has no child {@code Pane}
     * 											to be set.
     */
    @Override
    public void setSecondChild(Pane pane) {
        throw new UnsupportedOperationException("Can't set child Pane of a LeafPane.");
    }
    
    /**
     * a stub implementation of replacePaneWith declared in {@link Pane}.
     * 
     * @param oldPane : the old {@code Pane} to replace 
     * @param newPane : the new {@code Pane} to replace the old {@code Pane} with
     */
    @Override
    public void replacePaneWith(Pane oldPane, Pane newPane) { }

    /**
     * Get the content of this {@code LeafPane} without the
     * surrounding scrollbar decorators.
     * @return content: the content of this {@code LeafPane} without the
     *      surrounding scrollbar decorators.
     */
    public DocumentCell getContentWithoutScrollbars() {
        return ((DocumentCellDecorator) contentFrame.getContent()).getContentWithoutScrollbars();
    }

    /**
     * A final variable of type {@link ContentFrame} containing the content of the contentFrame
     * of this {@code LeafPane}.
     */
    private final ContentFrame contentFrame;
}


