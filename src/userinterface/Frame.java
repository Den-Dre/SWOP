package userinterface;

import java.awt.*;

/**
 * A class to denote the concept of a graphical
 * window within which content can be displayed.
 */
public abstract class Frame {

    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x: The x coordinate of this Frame.
     * @param y: The y coordinate of this Frame.
     * @param width: The width of this Frame
     * @param height: The height of this Frame
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public Frame(int x, int y, int width, int height) throws IllegalDimensionException {
        // defensively throw exception to caller
        if (x < 0 || y < 0 || width < 0 || height < 0)
        	throw new IllegalDimensionException();

        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        this.hasFocus = false;
    }

    /**
     * render the contents of this Frame.
     *
     * @param g: The graphics to be rendered.
     */
    public abstract void render(Graphics g);

     /**
     * Handle mouseEvents. Determine if this Frame was pressed and do the right actions.
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
     * It makes sure the Frame is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmakrBar} would be rendered with
     *          the given absolute width, and thus one would need to guess the
     *          correct initial size of the window. Using this method, widths are
     *          automatically adjusted: both at initialization and at runtime.</p>
     *
     * @param newWindowHeight: parameter containing the new window-height of this Frame.
     * @param newWindowWidth: parameter containing the new window-width of this Frame.
     */
    public abstract void handleResize(int newWindowWidth, int newWindowHeight);

    /**
     * Toggle whether this Frame has focus right now.
     *
     * @param newState:
     *                The new boolean value to denote whether this Frame has focus right now.
     */
    public void toggleFocus(boolean newState) {
        this.hasFocus = newState;
    }

    public boolean outOfArea() {
        return (getyPos() < getyReference()); //(getxPos() - Math.abs(getxOffset())) < getxReference() |
    }

    /**
     * An integer variable to denote the x coordinate of this Frame.
     */
    private int xPos;

    /**
     * An integer variable to denote the y coordinate of this Frame.
     */
    private int yPos;

    private int xOffset;

    private int yOffset;

    private int xReference = xPos;

    private int yReference = yPos;
    /**
     * A boolean variable to denote whether this Frame has focus right now.
     */
    public boolean hasFocus;

    /**
     * An integer variable to denote the width of this Frame.
     */
    private int width;

    /**
     * An integer variable to denote the height of this Frame.
     */
    private int height;

    /**
     * A variable to denote the background {@link Color} of this Frame.
     */
    private final Color backgroundColor = Color.WHITE;

    /**
     * Retrieve the x position of this Frame.
     *
     * @return xPos:
     *          The x position of this Frame.
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Retrieve the y position of this Frame.
     *
     * @return yPos:
     *          The y position of this Frame.
     */
    public int getyPos() {
        return yPos;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getxReference() {
        return xReference;
    }

    public int getyReference() {
        return yReference;
    }

    public void setxReference(int xReference) {
        this.xReference = xReference;
    }

    public void setyReference(int yReference) {
        this.yReference = yReference;
    }

    /**
     * Set the x position of this Frame to the given value
     *
     * @param xPos:
     *             The value this Frame's x position should be set to.
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Set the y position of this Frame to the given value
     *
     * @param yPos:
     *             The value this Frame's y position should be set to.
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Retrieve the width of this Frame.
     *
     * @return width:
     *              The width of this Frame.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width of this Frame to the given value.
     *
     * @param newWidth:
     *                The new value of this Frame's width should be set to.
     */
    public void setWidth(int newWidth){
        this.width = newWidth;
    }

     /**
     * Retrieve the height of this Frame.
     *
     * @return height:
     *              The height of this Frame.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height of this Frame to the given value.
     *
     * @param newHeight:
     *                The new value of this Frame's height should be set to.
     */
    public void setHeight(int newHeight){
        this.height = newHeight;
    }

    /**
     * Retrieve the background color of this frame
     *
     * @return color:
     *              The background {@link Color} of this Frame.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Method to determine if the click was in this DocumentCells area
     * @param x: the x-position of the click
     * @param y: the y-position of the click
     * @return True iff the given point lies in this DocumentCells area including the edges
     */
    public boolean wasClicked(int x, int y) {
//    	System.out.println("docCell: on: "+x+","+y);
//    	System.out.println("getX: "+this.getxPos()+", getY: "+this.getyPos());
//    	System.out.println("width: "+this.getWidth()+", height: "+this.getHeight());
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }
}
