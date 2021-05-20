package userinterface;

import domainlayer.UIController;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A class extending {@link Pane} to handle the splitting of {@link LeafPane}'s when adding a new 
 * LeafPane to it by transforming it into a {@link Pane} with two {@link LeafPane}'s as attributes
 */
public abstract class GenericSplitPane extends Pane {
	
    /**
     * Initialize this {@code GenericSplitPane} with the given parameters.
     *
     * @param x      : The x coordinate of this GenericSplitPane.
     * @param y      : The y coordinate of this GenericSplitPane.
     * @param width  : The width of this GenericSplitPane
     * @param height : The height of this GenericSplitPane
     * @param childPane : the {@link LeafPane} child of this GenericSplitPane
     * @param parentPane : the {@link Pane} parent of this GenericSplitPane
     * @throws IllegalDimensionException: When one of the dimensions of this GenericSplitPane is negative
     */
    public GenericSplitPane(int x, int y, int width, int height, LeafPane childPane, Pane parentPane) throws IllegalDimensionException {
        super(x, y, width, height);
        setParentPane(parentPane);
        setFirstChild(childPane); // horizontal split -> = lower pane; vertical split -> = left pane
        ContentFrame cf = new ContentFrame(x, y, width, height);
        setSecondChild(new LeafPane(cf, getFirstChild().getController())); // horizontal split -> = upper pane; vertical split -> = right pane
    }

    /**
     * Initialize this {@code GenericSplitPane} with a given GenericSplitPane. Used for deep copy.
     * 
     * @param pane	: The {@code GenericSplitPane} 
     */
    public GenericSplitPane(GenericSplitPane pane) {
        super(pane.getxPos(), pane.getyPos(), pane.getWidth(), pane.getHeight());
    }
    
    /**
     * Accesses the domain-layer through an observer design pattern linking the children ({@link LeafPane}'s)
     * of this {@code GenericSplitPane} (UI-layer) to the right {@link domainlayer.Document} (Domain-layer).
     * This results in children of this {@code GenericSplitPane} listening to changes in their 
     * respective {@link domainlayer.Document}.
     */
    protected void updateListeners() {
        // Update domain layer knowledge
        UIController controller = getController();
        controller.addDocumentListener(getFirstChild().getId(), getFirstChild());
        controller.addDocumentListener(getSecondChild().getId(), getSecondChild(), getFirstChild().getId());
    }

    /**
     * render the contents of this {@code GenericSplitPane}.
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
     * Handle a horizontal split of the contents of this {@code GenericSplitPane}.
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
     * Handle a vertical split of the contents of this {@code GenericSplitPane}.
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
     * Handle mouseEvents. Determine if this GenericSplitPane was pressed and do the right actions.
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
     * This method handles resizes of this {@code GenericSplitPane}.
     * It makes sure the GenericSplitPane is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowWidth  : parameter containing the new window-width of this GenericSplitPane.
     * @param newWindowHeight : parameter containing the new window-height of this GenericSplitPane.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setWidth(getFirstChild().getWidth() + getSecondChild().getWidth());
        setHeight(getFirstChild().getHeight() + getSecondChild().getHeight());
    }

    /**
     * Sets the {@link domainlayer.UIController} for this {@code GenericSplitPane} which relays 
     * calls from this GenericSplitPane to the Domain-Layer.
     */
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
    protected void handleMouseNotDragged(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (id == MouseEvent.MOUSE_RELEASED && dragging) {
            getFocusedPane().handleMouse(id, x, y, clickCount, button, modifiersEx);
        } else {
            getFirstChild().handleMouse(id, x, y, clickCount, button, modifiersEx);
            getSecondChild().handleMouse(id, x, y, clickCount, button, modifiersEx);
        }
        dragging = false;
    }

    /**
     * Check whether this {@code GenericSplitPane} currently has focus.
     *
     * @return focus: True iff. this {@code GenericSplitPane} currently has focus.
     */
    @Override
    public boolean hasFocus() {
        return (getSecondChild().hasFocus() || getFirstChild().hasFocus());
    }

    /**
     * Sets the width of the parentPane ({@link Pane}) of this {@code GenericSplitPane} and its children Panes
     * ({@code upperPane} and {@code lowerPane})
     *
     * @param parentWidth : the new width of the parentPane of this {@code HorizontalSplitPane}.
     */
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        getFirstChild().setParentWidth(parentWidth);
        getSecondChild().setParentWidth(parentWidth);
    }

     /**
     * Sets the height of the parentPane ({@link Pane}) of this {@code GenericSplitPane} and its children Panes
     * ({@code upperPane} and {@code lowerPane})
     *
     * @param parentHeight : the new height of the parentPane of this {@code GenericSplitPane}.
     */
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        getFirstChild().setParentHeight(parentHeight);
        getSecondChild().setParentHeight(parentHeight);
    }

    /**
     * Get the {@link Pane} object that currently has focus.
     */
    @Override
    public Pane getFocusedPane() {
        return parentPane != null ? parentPane.getFocusedPane() : focusedPane;
    }

    /**
     * Abstract method returning the first child (of type {@link Pane} of this {@code GenericSplitPane}
     */
    public abstract Pane getFirstChild();

    /**
     * Abstract method returning the second child (of type {@link Pane} of this {@code GenericSplitPane}
     */
    public abstract Pane getSecondChild();

    /**
     * Abstract method to draw the separator (as defined in the assignment)
     * 
     * @param g : The graphics to be rendered.
     */
    abstract void drawSeparator(Graphics g);

    /**
     * A constant of type int that denotes the thickness
     * of the separator drawn between split windows.
     */
    protected final int SEPARATOR_THICKNESS = 10;

    public boolean dragging = false;
}
