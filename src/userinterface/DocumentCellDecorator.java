package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * A class of decorators that decorate a visualization of a {@link DocumentCell} in 
 * browsr with a scroll bar.
 */
public abstract class DocumentCellDecorator extends DocumentCell {

    /**
     * Initialize this decorator with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public DocumentCellDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell.getxPos(), cell.getyPos(), cell.getWidth(), cell.getHeight());
        this.cellToBeDecorated = cell;
    }

    /**
     * Sets the length  of this {@code DocumentCellDecorator} to the given value
     * 
     * @param newLength : The new length of this {@code DocumentCellDecorator}
     */
    public void setLength(int newLength) {
        length = newLength;
//        innerBarLength = (int) Math.round(length/getRatio());
    }

    /**
     * Get the fraction of this  {@code DocumentCellDecorator}.
     * 
     * @return fraction : fraction of type {@link Double} of this {@code DocumentCellDecorator}.
     */
    public double getFraction() {
        return fraction;
    }

    /**
     * Get the ratio of this  {@code DocumentCellDecorator}.
     * 
     * @return ratio : the ratio of type {@link Double} of this {@code DocumentCellDecorator}.
     */
    public double getRatio() {
        return 1.0;
    }

    /**
     * Sets the fraction of this {@code DocumentCellDecorator} to the given value. 
     * 
     * @param fraction : a {@link Double} reaching from 0 to 1
     * @post assumes that the scroll bar has moved afterwards.
     */
    public void setFraction(double fraction) {
        if (Double.isNaN(fraction)) return;
        if (fraction > 1.0)
            fraction = 1.0;
        if (fraction < 0.0)
            fraction = 0.0;
        this.fraction = fraction;
        moved();
    }

    /**
     * Calculates the ratio of this {@code DocumentCellDecorator} to the {@code length}
     * of this {@code DocumentCellDecorator} to the given value. 
     * 
     * @param ratio : a {@link Double} smaller than or equal to 1
     */
    public void ratioChanged(double newRatio) {
        if (newRatio < 1.0)
            newRatio = 1.0;
        innerBarLength = (int) Math.round(length/newRatio);
    }


    /**
     * render the graphics {@code g} of this DocumentCell.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        cellToBeDecorated.render(g);
    }

    /**
     * Handle mouseEvents. Determine if this Frame was pressed and do the right actions.
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
        this.cellToBeDecorated.handleMouse(id, x, y, clickCount, button, modifiersEx);
        init();
        int[] currentMouse;
        if (id == MouseEvent.MOUSE_PRESSED && (wasClicked(x, y))) {
            prevMouse = new int[] {x, y};
            currentColor = innerColorDragging;
        }
        else if (id == MouseEvent.MOUSE_DRAGGED) {
            if (prevMouse == null) return;
            currentMouse = new int[] {x, y};
            dragged(currentMouse[0]-prevMouse[0], currentMouse[1]-prevMouse[1]);
            prevMouse = new int[] {x, y};
        }
        else if (id == MouseEvent.MOUSE_RELEASED) {
            prevMouse = null;
            currentColor = innerColorNormal;
        }
    }

    /**
     * stub method (needs to be overridden) to initialize a scroll bar
     */
    void init() {

    }

    /**
     * stub method (needs to be overridden) to move a scroll bar
     */
    void moved() {

    }
    
    /**
     * stub method (needs to be overridden) to drag a scroll bar
     */
    void dragged(int dx, int dy) {

    }

    /**
     * The output generated by a mouse click on this DocumentCell.
     * This is always a {@link ReturnMessage} that contains the empty string "".
     *
     * @param id         : The id of the click
     * @param x          : The x coordinate of the click
     * @param y          : The y coordinate of the click
     * @param clickCount : The number of clicks that occured.
     * @param button     : Which mouse button was clicked.
     * @param modifier   : Extra control key that was held during the click.
     * @return ReturnMessage(ReturnMessage.Type.Empty): a {@link ReturnMessage} which contains the empty string {@code ""}.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        handleMouse(id, x, y, clickCount, button, modifier);
        return cellToBeDecorated.getHandleMouse(id, x, y, clickCount, button, modifier);
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
        this.cellToBeDecorated.handleKey(id, keyCode, keyChar, modifiersEx);
    }

    /**
     * This method handles resizes.
     * It makes sure the Frame is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmarksBar} would be rendered with
     * the given absolute width, and thus one would need to guess the
     * correct initial size of the window. Using this method, widths are
     * automatically adjusted: both at initialization and at runtime.</p>
     *
     * @param newWindowWidth  : parameter containing the new window-width of this Frame.
     * @param newWindowHeight : parameter containing the new window-height of this Frame.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        //setParentHeight(newWindowHeight);
        this.cellToBeDecorated.handleResize(newWindowWidth, newWindowHeight);
        moved();
    }

    // These setters are overridden to fix the issue
    // where `this` would contain the correct coordinates
    // after adding a `DocumentCellDecorator` to a UITable,
    // but the coordinates of `cellToBeDecorated` weren't updated yet.
    /**
     * Set the x position of this {@code DocumentCellDecorator} to the given value. Does the 
     * same for the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param xPos :
     *             The value this {@code DocumentCellDecorator}'s x position should be set to.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        this.cellToBeDecorated.setxPos(xPos);
    }

    /**
     * Set the y position of this {@code DocumentCellDecorator} to the given value. Does the 
     * same for the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param yPos :
     *             The value this {@code DocumentCellDecorator}'s y position should be set to.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        this.cellToBeDecorated.setyPos(yPos);
    }

    /**
     * Set the x offset of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param xOffset :
     *             The value the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}'s x offset should be set to.
     */
    @Override
    public void setxOffset(int xOffset) {
        //super.setxOffset(xOffset);
        cellToBeDecorated.setxOffset(xOffset);
    }

    /**
     * Set the y offset of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param yOffset :
     *             The value the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}'s y offset should be set to.
     */
    @Override
    public void setyOffset(int yOffset) {
        //super.setyOffset(yOffset);
        cellToBeDecorated.setyOffset(yOffset);
    }

    /**
     * Set the width of this {@code DocumentCellDecorator} to the given value. Does the 
     * same for the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param newWidth :
     *             The value this {@code DocumentCellDecorator}'s width should be set to.
     */
    @Override
    public void setWidth(int newWidth) {
        super.setWidth(newWidth);
        cellToBeDecorated.setWidth(newWidth);
    }

    /**
     * Set the height of this {@code DocumentCellDecorator} to the given value. Does the 
     * same for the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param newHeight:
     *             The value this {@code DocumentCellDecorator}'s height should be set to.
     */
    @Override
    public void setHeight(int newHeight) {
        super.setHeight(newHeight);
        cellToBeDecorated.setHeight(newHeight);
    }

    /**
     * Set the height of the parent of this {@code DocumentCellDecorator} to the given value. Does the 
     * same for the parent of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param parentHeight:
     *             The value the height of the parent of this {@code DocumentCellDecorator} should be set to.
     */
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        cellToBeDecorated.setParentHeight(parentHeight);
    }

    /**
     * Set the width of the parent of this {@code DocumentCellDecorator} to the given value. Does the 
     * same for the parent of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param parentWidth:
     *             The value the width of the parent of this {@code DocumentCellDecorator} should be set to.
     */
    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        cellToBeDecorated.setParentWidth(parentWidth);
    }

    /**
     * Get the max width of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     * 
     * @return ratio : 
     * 					the width of type {@link Double} of the {@code cellToBeDecorated} of 
     * 					this {@code DocumentCellDecorator}.
     */
    @Override
    public int getMaxWidth() {
        return cellToBeDecorated.getMaxWidth();
    }

    /**
     * Get the max height of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     * 
     * @return height : 
     * 					the height of type {@link Double} of the {@code cellToBeDecorated} of 
     * 					this {@code DocumentCellDecorator}.
     */
    @Override
    public int getMaxHeight() {
        return cellToBeDecorated.getMaxHeight();
    }

    /**
     * Set the y reference of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator}.
     *
     * @param yReference:
     *             The value the y reference of the {@code cellToBeDecorated} of this {@code DocumentCellDecorator} should be set to.
     */
    @Override
    public void setyReference(int yReference) {
        cellToBeDecorated.setyReference(yReference);
    }
    
    /**
     * Get the vertical x offset of the scroll bar of this {@code DocumentCellDecorator}.
     * 
     * @return xOffset : 
     * 					the vertical x offset of the scroll bar of this {@code DocumentCellDecorator}.
     */
    public int getVerticalBarXOffset() {
        return 0;
    }

    /**
     * Get the vertical y offset of the scroll bar of this {@code DocumentCellDecorator}.
     * 
     * @return yOffset : 
     * 					the vertical y offset of the scroll bar of this {@code DocumentCellDecorator}.
     */
    public int getHorizontalBarYOffset() {
        return 0;
    }

    /**
     * Method to determine if the click was in this DocumentCells area
     * @param x: the x-position of the click
     * @param y: the y-position of the click
     * @return True iff the given point lies in this DocumentCells area including the edges
     */
    @Override
    public boolean wasClicked(int x, int y) {
        return (x >= this.getxPos()+getxOffset()+getVerticalBarXOffset()) &&
                x <= (this.getxPos() + this.getWidth()+getxOffset()+getVerticalBarXOffset()) &&
                y >= (this.getyPos()+getyOffset()+getHorizontalBarYOffset()) &&
                y <= (this.getyPos() + this.getHeight()+getyOffset()+getHorizontalBarYOffset());
    }

    /**
     * Get the contents that are being decorated.
     *
     * @return cell: The {@link DocumentCell} object that is being decorated.
     */
    public DocumentCell getContent() {
        return this.cellToBeDecorated;
    }

    /**
     * Get the contents that are being decorated,
     * with all added scrollbar decorations omitted.
     *
     * @return cellToBeDecorated:
     *          the contents that are being decorated without any decorations
     */
    public DocumentCell getContentWithoutScrollbars() {
        if (this.cellToBeDecorated instanceof VerticalScrollBarDecorator)
            return ((VerticalScrollBarDecorator) this.cellToBeDecorated).getContentWithoutScrollbars();
        else if (this.cellToBeDecorated instanceof HorizontalScrollBarDecorator)
            return ((HorizontalScrollBarDecorator) this.cellToBeDecorated).getContentWithoutScrollbars();
        // In this case we have peeled off both the potentially added scroll bars
        // and are left with the wrapper `UITable` from which we extract the encapsulated element:
        if (cellToBeDecorated instanceof UITextField)
            return cellToBeDecorated;
        return ((UITable) cellToBeDecorated).getContent().get(0).get(0);
    }

    /**
     * A variable to denote the {@link DocumentCell} that will be decorated.
     */
    protected final DocumentCell cellToBeDecorated;
    
    /**
     * A variable to denote the thickness of the border around the scroll bar.
     */
    final static int thicknessOuterBar = 8;
    
    /**
     * A variable to denote the thickness of the scroll bar.
     */
    final static int thicknessInnerBar = 4;
    
    /**
     * A variable to denote the fraction of this {@code DocumentCellDecorator}.
     */
    double fraction = 0.0;
    
    /**
     * A variable to denote the length of the scroll bar of this {@code DocumentCellDecorator}.
     */
    int innerBarLength;
    
    /**
     * A variable to denote the length of this {@code DocumentCellDecorator}.
     */
    int length;
    
    /**
     * A variable to denote the offset of this {@code DocumentCellDecorator}.
     */
    final int offset = 2;
    
    /**
     * A variable of type {@link Array} to denote the previous mouse event
     * of this {@code DocumentCellDecorator}.
     */
    int[] prevMouse;

    /**
     * A variable of type {@link Color} to denote the standard color of the scrollbar
     * of this {@code DocumentCellDecorator}.
     */
    final Color innerColorNormal = Color.gray;
    
    /**
     * A variable of type {@link Color} to denote the color of the scrollbar when selected
     * of this {@code DocumentCellDecorator}.
     */
    final Color innerColorDragging = Color.blue;
    
    /** 
     * A variable of type {@link Color} to denote the currentColor of this {@code DocumentCellDecorator}.
     */
    Color currentColor = innerColorNormal;
}
