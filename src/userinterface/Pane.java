package userinterface;

import domainlayer.Document;
import domainlayer.DocumentListener;
import domainlayer.UIController;

/**
 * A class of panes (as defined in the assignment) extending {@link AbstractFrame} 
 * forming the internal nodes of a tree where the leaf nodes are {@link LeafPane}s
 * containing the contents to be visualized
 */
public abstract class Pane extends AbstractFrame implements DocumentListener {
	
    /**
     * Initialize this Pane with the given parameters.
     *
     * @param x      : The x coordinate of this Pane.
     * @param y      : The y coordinate of this Pane.
     * @param width  : The width of this Pane
     * @param height : The height of this Pane
     * @throws IllegalDimensionException: When one of the dimensions of this Pane is negative
     */
    public Pane(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
    }

    /**
     * Set the {@link ContentFrame} object that currently has focus.
     *
     * @param pane: The {@code Pane} object to be set.
     */
    public void setFocusedPane(Pane pane) {
        if (getParentPane() != null)
            getParentPane().setFocusedPane(pane);
        else
            focusedPane = pane;
    }
    
    /**
     * Replaces an old Pane (either this {@code Pane} or one of 
     * its child panes of type {@link LeafPane}) with a new given Pane
     * 
     * @param oldPane : the old {@code Pane} to replace 
     * @param newPane : the new {@code Pane} to replace the old {@code Pane} with
     */
    public void replacePaneWith(Pane oldPane, Pane newPane) {
        if (oldPane == getFirstChild())
            setFirstChild(newPane);
        else if (oldPane == getSecondChild())
            setSecondChild(newPane);
        else {
            //getFocusedPane().replacePaneWith(oldPane, newPane);
            getFirstChild().replacePaneWith(oldPane, newPane);
            getSecondChild().replacePaneWith(oldPane, newPane);
        }

    }
    
    /**
     * Gets the {@link domainlayer.UIController} of the first child of this {@code Pane}
     * 
     * @return controller : the {@link domainlayer.UIController} of the first child of this {@code Pane}
     */
    public UIController getController() {
        return getFirstChild().getController();
    }

    /**
     * Gets the content of the sub {@code Pane} of this {@code Pane}
     *
     * @return content : the content of the sub pane of this {@code Pane}
     */
    public ContentFrame getContent() {
    	if (this instanceof LeafPane)
    		return ((LeafPane) this).getContentFrame();
        return this.getFirstChild().getContent();
    }

    /**
     * Gets the root {@code Pane} of this {@code Pane}. Also returns when this pane is the root.
     *
     * @return pane : the root pane of this {@code Pane}
     */
    public Pane getRootPane() {
        if (this.parentPane == null)
            return this;
        return parentPane.getRootPane();
    }

    /**
     * Abstract method Defining what the class that implements
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

    /**
     * Handle mouseEvents. Determine if this Pane was pressed and do the right actions.
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
     * This abstract method handles resizes.
     * It makes sure the Pane is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmarksBar} would be rendered with
     * the given absolute width, and thus one would need to guess the
     * correct initial size of the window. Using this method, widths are
     * automatically adjusted: both at initialization and at runtime.</p>
     *
     * @param newWindowWidth  : parameter containing the new window-width of this Pane.
     * @param newWindowHeight : parameter containing the new window-height of this Pane.
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

    /**
     * Abstract method to set the {@link domainlayer.UIController} of this {@code Pane}
     * 
     * @param controller : the new {@link domainlayer.UIController} of this {@code Pane}
     */
    public abstract void setController(UIController controller);

    /**
     * Sets the parent pane of this {@code Pane}
     * 
     * @param pane : the new parent {@code Pane} of this {@code Pane}
     */
    protected void setParentPane(Pane pane) {
        this.parentPane = pane;
    }

    /**
     * Gets the parent pane of this {@code Pane}
     * 
     * @return pane : the parent {@code Pane} of this {@code Pane}
     */
    public Pane getParentPane() {
        return parentPane;
    }

    /**
     * Gets the current focused {@code Pane}, either from the parent {@code Pane}
     * or from this {@code Pane}
     * 
     * @return focusedPane : the current focused {@code Pane}
     */
    public Pane getFocusedPane() {
        if (parentPane != null)
            return parentPane.getFocusedPane();
        return focusedPane;
    };

    /**
     * Abstract method returning the first child (of type {@code Pane}) of this {@code Pane}
     * 
     * @return pane : the first child (of type {@code Pane}) of this {@code Pane}
     */
    public abstract Pane getFirstChild();
    
    /**
     * Abstract method returning the second child (of type {@code Pane}) of this {@code Pane}
     * 
     * @return pane : the second child (of type {@code Pane}) of this {@code Pane}
     */
    public abstract Pane getSecondChild();

    /**
     * Abstract method setting the first child (of type {@code Pane}) of this {@code Pane}
     * 
     * @param pane : the new first child (of type {@code Pane}) of this {@code Pane}
     */
    public abstract void setFirstChild(Pane pane);

    /**
     * Abstract method setting the second child (of type {@code Pane}) of this {@code Pane}
     * 
     * @param pane : the new second child (of type {@code Pane}) of this {@code Pane}
     */
    public abstract void setSecondChild(Pane pane);

    /**
     * Gets the id of this {@code Pane}
     * 
     * @return id : the id of this {@code Pane}
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the x-position of the root {@code Pane}
     * 
     * @return xpos : the x-position of the root {@code Pane}
     */
    protected int getBasexPos() {
        return getRootPane().getxPos();
    }

    /**
     * Gets the y-position of the root {@code Pane}
     * 
     * @return ypos : the y-position of the root {@code Pane}
     */
    protected int getBaseyPos() {
        return getRootPane().getyPos();
    }
    
    /**
     * A variable of type {@code Pane} containing the sub pane of this {@code Pane}
     */
    private Pane subPane;

    /**
     * A variable of type {@code Pane} containing the focused pane of this {@code Pane}
     */
    protected Pane focusedPane;

    /**
     * A variable of type {@code Pane} containing the parent pane of this {@code Pane}
     */
    protected Pane parentPane;

    /**
     * A variable containing the id of this {@code Pane}
     */
    protected int id;
}
