package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public abstract class DocumentCellDecorator extends DocumentCell {

    final static int thicknessOuterBar = 8;
    final static int thicknessInnerBar = 4;
    double fraction = 0.0;
    int innerBarLength;
    int length;
    final int offset = 2;
    int[] prevMouse;

    final Color innerColorNormal = Color.gray;
    final Color innerColorDragging = Color.blue;
    Color currentColor = innerColorNormal;

    /**
     * Initialise this decorator with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public DocumentCellDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell.getxPos(), cell.getyPos(), cell.getWidth(), cell.getHeight());
        this.cellToBeDecorated = cell;
    }


    public void setLength(int newLength) {
        length = newLength;
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        if (Double.isNaN(fraction)) return;
        if (fraction > 1.0)
            fraction = 1.0;
        if (fraction < 0.0)
            fraction = 0.0;
        this.fraction = fraction;
        moved();
    }

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
            System.out.println("pressed");
        }
        else if (id == MouseEvent.MOUSE_DRAGGED) {
            if (prevMouse == null) return;
            System.out.println("dragged");
            currentMouse = new int[] {x, y};
            dragged(currentMouse[0]-prevMouse[0], currentMouse[1]-prevMouse[1]);
            prevMouse = new int[] {x, y};
        }
        else if (id == MouseEvent.MOUSE_RELEASED) {
            prevMouse = null;
            currentColor = innerColorNormal;
        }
    }

    void init() {

    }

    void moved() {

    }
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
        ReturnMessage message = cellToBeDecorated.getHandleMouse(id, x, y, clickCount, button, modifier);
        return message;
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
        this.cellToBeDecorated.handleResize(newWindowWidth, newWindowHeight);
        moved();
    }

    // These setters are overridden to fix the issue
    // where `this` would contain the correct coordinates
    // after adding a `DocumentCellDecorator` to a UITable,
    // but the coordinates of `cellToBeDecorated` weren't updated yet.
    /**
     * Set the x position of this Frame to the given value
     *
     * @param xPos :
     *             The value this Frame's x position should be set to.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        this.cellToBeDecorated.setxPos(xPos);
    }

    /**
     * Set the y position of this Frame to the given value
     *
     * @param yPos :
     *             The value this Frame's y position should be set to.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        this.cellToBeDecorated.setyPos(yPos);
    }

    @Override
    public void setWidth(int newWidth) {
        super.setWidth(newWidth);
        cellToBeDecorated.setWidth(newWidth);
    }

    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        cellToBeDecorated.setParentHeight(parentHeight);
    }

    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        cellToBeDecorated.setParentWidth(parentWidth);
    }

    @Override
    public void setxOffset(int xOffset) {
        super.setxOffset(xOffset);
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
     * The height of the added horizontal scrolling bar.
     */
    protected final int SCROLLBAR_HEIGHT = 5;

    /**
     * The width of the added vertical scrolling bar.
     */
    protected final int SCROLLBAR_WIDTH = 5;
}
