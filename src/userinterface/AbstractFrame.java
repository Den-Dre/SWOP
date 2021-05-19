package userinterface;

import java.awt.*;

/**
 * An abstract class to denote the concept of a graphical
 * window within which content can be displayed.
 */
public abstract class AbstractFrame {

    /**
     * Initialize this {@code AbstractFrame} with the given parameters.
     *
     * @param x: The x coordinate of this {@code AbstractFrame}.
     * @param y: The y coordinate of this {@code AbstractFrame}.
     * @param width: The width of this {@code AbstractFrame}
     * @param height: The height of this {@code AbstractFrame}
     * @throws IllegalDimensionException: When one of the dimensions of this {@code AbstractFrame} is negative
     */
    public AbstractFrame(int x, int y, int width, int height) throws IllegalDimensionException {
        // defensively throw exception to caller
        if (x < 0 || y < 0 || width < 0 || height < 0)
        	throw new IllegalDimensionException();

        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        this.parentWidth = width;
        this.parentHeight = height;
        this.hasFocus = false;
    }

    /**
     * render the contents of this {@code AbstractFrame}.
     *
     * @param g: The graphics to be rendered.
     */
    public abstract void render(Graphics g);

     /**
     * Handle mouseEvents. Determine if this {@code AbstractFrame} was pressed and do the right actions.
     *
     * @param id: The type of mouse activity
     * @param x: The x coordinate of the mouse activity
     * @param y: The y coordinate of the mouse activity
     * @param clickCount: The number of clicks
     * @param button: The mouse button that was clicked
     * @param modifiersEx: The control keys that were held on the click
     */
    public abstract void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx);

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode: The KeyEvent code (Determines the involved key)
     * @param keyChar: The character representation of the involved key
     * @param modifiersEx: Specifies other keys that were involved in the event
     */
    public abstract void handleKey(int id, int keyCode, char keyChar, int modifiersEx);


    /**
     * This method handles resizes.
     * It makes sure the {@code AbstractFrame} is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmakrBar} would be rendered with
     *          the given absolute width, and thus one would need to guess the
     *          correct initial size of the window. Using this method, widths are
     *          automatically adjusted: both at initialization and at runtime.</p>
     *
     * @param newWindowHeight: parameter containing the new window-height of this {@code AbstractFrame}.
     * @param newWindowWidth: parameter containing the new window-width of this {@code AbstractFrame}.
     */
    public abstract void handleResize(int newWindowWidth, int newWindowHeight);

    /**
     * Toggle whether this {@code AbstractFrame} has focus right now.
     *
     * @param newState:
     *                The new boolean value to denote whether this {@code AbstractFrame} has focus right now.
     */
    public void toggleFocus(boolean newState) {
        this.hasFocus = newState;
    }

    /**
     * Determines whether this {@code AbstractFrame} falls outside the vertical bounds of this 
     * {@code AbstractFrame}'s reference.
     * 
     * @return True iff this {@code AbstractFrame} falls outside the vertical bounds
     */
    public boolean outOfVerticalBounds() {
        return ((getyPos()+getyOffset() < getyReference()) |
                (getyPos()+getyOffset()+getHeight() > getyReference()+parentHeight));
    }

    /**
     * Determines whether this {@code AbstractFrame} falls outside the horizontal bounds of this 
     * {@code AbstractFrame}'s reference.
     * 
     * @return True iff this {@code AbstractFrame} falls outside the horizontal bounds
     */
    public boolean outOfHorizontalBounds() {
        return (getxPos()+getxOffset() < getxReference()); // ||
//                (getxPos()+getWidth()+getxOffset()-5 > getxReference()+parentWidth));
    }

    /**
     * Retrieve the x position of this {@code AbstractFrame}.
     *
     * @return xPos:
     *          The x position of this {@code AbstractFrame}.
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Retrieve the y position of this {@code AbstractFrame}.
     *
     * @return yPos:
     *          The y position of this {@code AbstractFrame}.
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Retrieve the x offset of this {@code AbstractFrame}.
     *
     * @return xOffset:
     *          The x offset of this {@code AbstractFrame}.
     */
    public int getxOffset() {
        return xOffset;
    }

    /**
     * Retrieve the y offset of this {@code AbstractFrame}.
     *
     * @return yOffset:
     *          The y offset of this {@code AbstractFrame}.
     */
    public int getyOffset() {
        return yOffset;
    }

    /**
     * Set the x offset of this {@code AbstractFrame} to the given value
     *
     * @param xOffset:
     *             The value this {@code AbstractFrame}'s x offset should be set to.
     */
    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * Set the x offset of this {@code AbstractFrame} to the given value
     *
     * @param xOffset:
     *             The value this {@code AbstractFrame}'s x offset should be set to.
     */
    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * Retrieve the x reference of this {@code AbstractFrame}.
     *
     * @return xReference:
     *          The x reference of this {@code AbstractFrame}.
     */
    public int getxReference() {
        return xReference;
    }

    /**
     * Retrieve the y reference of this {@code AbstractFrame}.
     *
     * @return yReference:
     *          The y reference of this {@code AbstractFrame}.
     */
    public int getyReference() {
        return yReference;
    }

    /**
     * Set the x reference of this {@code AbstractFrame} to the given value
     *
     * @param xReference:
     *             The value this {@code AbstractFrame}'s x reference should be set to.
     */
    public void setxReference(int xReference) {
        this.xReference = xReference;
    }

    /**
     * Set the y reference of this {@code AbstractFrame} to the given value
     *
     * @param yReference:
     *             The value this {@code AbstractFrame}'s y reference should be set to.
     */
    public void setyReference(int yReference) {
        this.yReference = yReference;
    }

    /**
     * Set the parent height of this {@code AbstractFrame} to the given value
     *
     * @param parentHeight:
     *             The value this {@code AbstractFrame}'s parent's height should be set to.
     */
    public void setParentHeight(int parentHeight) {
        this.parentHeight = parentHeight;
    }

    /**
     * Set the parent width of this {@code AbstractFrame} to the given value
     *
     * @param parentWidth:
     *             The value this {@code AbstractFrame}'s parent's width should be set to.
     */
    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    /**
     * Set the x position of this {@code AbstractFrame} to the given value
     *
     * @param xPos:
     *             The value this {@code AbstractFrame}'s x position should be set to.
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Set the y position of this {@code AbstractFrame} to the given value
     *
     * @param yPos:
     *             The value this {@code AbstractFrame}'s y position should be set to.
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Retrieve the width of this {@code AbstractFrame}.
     *
     * @return width:
     *              The width of this {@code AbstractFrame}.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width of this {@code AbstractFrame} to the given value.
     *
     * @param newWidth:
     *                The new value of this {@code AbstractFrame}'s width should be set to.
     */
    public void setWidth(int newWidth){
        this.width = newWidth;
        if (newWidth > parentWidth) {
            parentWidth = newWidth;
        }
    }

     /**
     * Retrieve the height of this {@code AbstractFrame}.
     *
     * @return height:
     *              The height of this {@code AbstractFrame}.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height of this {@code AbstractFrame} to the given value.
     *
     * @param newHeight:
     *                The new value of this {@code AbstractFrame}'s height should be set to.
     */
    public void setHeight(int newHeight){
        this.height = newHeight;
        if (newHeight > parentHeight) {
            parentHeight = newHeight;
        }
    }

    /**
     * Retrieve the background color of this frame
     *
     * @return color:
     *              The background {@link Color} of this {@code AbstractFrame}.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Create a deep copy of this {@code AbstractFrame} object.
     *
     * @return copy: a deep copied version of this {@code AbstractFrame}
     *               object which thus does not point to the original object.
     */
    protected abstract AbstractFrame deepCopy();

    /**
     * Method to determine if the click was in this {@code AbstractFrame}'s area
     * @param x: the x-position of the click
     * @param y: the y-position of the click
     * @return True iff the given point lies in this {@code AbstractFrame}'s area including the edges
     */
    public boolean wasClicked(int x, int y) {
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }
    
    /**
     * An integer variable to denote the x coordinate of this {@code AbstractFrame}.
     */
    private int xPos;

    /**
     * An integer variable to denote the y coordinate of this {@code AbstractFrame}.
     */
    private int yPos;

    /**
     * An integer variable to denote the x offset of this {@code AbstractFrame}.
     */
    private int xOffset;

    /**
     * An integer variable to denote the y offset of this {@code AbstractFrame}.
     */
    private int yOffset;

    /**
     * An integer variable to denote the x reference of this {@code AbstractFrame}.
     */
    private int xReference = xPos;

    /**
     * An integer variable to denote the y reference of this {@code AbstractFrame}.
     */
    private int yReference = yPos;

    /**
     * A boolean variable to denote whether this {@code AbstractFrame} has focus right now.
     */
    public boolean hasFocus;

    /**
     * An integer variable to denote the width of this {@code AbstractFrame}.
     */
    private int width;

    /**
     * An integer variable to denote the height of this {@code AbstractFrame}.
     */
    private int height;

    /**
     * An integer variable to denote the width of the parent of this {@code AbstractFrame}.
     */
    int parentWidth;
    
    /**
     * An integer variable to denote the height of the parent of this {@code AbstractFrame}.
     */
    int parentHeight;

    /**
     * A variable to denote the background {@link Color} of this {@code AbstractFrame}.
     */
    private final Color backgroundColor = Color.WHITE;
}
