package userinterface;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class to represent individual UI
 * elements that need to be rendered.
 */
public class DocumentCell extends Frame {

	/**
     * Initialise this DocumentCell with the given parameters.
     *
     * @param x: The x coordinate of this DocumentCell
     * @param y: The y coordinate of this DocumentCell
     * @param width: The width of this DoucmentCell
     * @param height: The height of this DoucmentCell
     * @throws IllegalDimensionException: When the dimensions of {@code super} are illegal.
     */
    public DocumentCell(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
    }

    /**
     * render the graphics {@code g} of this DocumentCell.
     *
     * @param g: The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) { }

    /**
     * Handle a mouse click on this DocumentCell.
     *
     * @param id: The id of the click
     * @param x: The x coordinate of the click
     * @param y: The y coordinate of the click
     * @param clickCount: The number of clicks that occured.
     * @param button: Which mouse button was clicked.
     * @param modifiersEx: Extra control keys that were held during the click.
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) { }

    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) { }

    /**
     * Handle a resize of this DocumentCell.
     *
     * @param newWindowWidth: The width the DocumentCell will be resized to.
     * @param newWindowHeight: The height the DocumentCell will be resized to.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
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
        return (x >= this.getxPos()+getxOffset()) && x <= (this.getxPos() + this.getWidth()+getxOffset()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    /**
     * The output generated by a mouse click on this DocumentCell.
     * This is always a {@link ReturnMessage} that contains the empty string "".
     *
     * @param id: The id of the click
     * @param x: The x coordinate of the click
     * @param y: The y coordinate of the click
     * @param clickCount: The number of clicks that occured.
     * @param button: Which mouse button was clicked.
     * @param modifier: Extra control key that was held during the click.
     * @return ReturnMessage(ReturnMessage.Type.Empty): a {@link ReturnMessage} which contains the empty string {@code ""}.
     */
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        return new ReturnMessage(ReturnMessage.Type.Empty);
    }

    /**
     * Retrieve the name and value of this DocumentCell.
     *
     * Only applies if the DocumentCell has a name and value (e.g UITextInputField)
     * or can contain elements that do (e.g. UITable).
     *
     * @return An ArrayList with the name and value of the DocumentCell separated by a '=' sign.
     */
    public ArrayList<String> getNamesAndValues() {
        return new ArrayList<>();
    }

    /**
     * Return the maximum height of this DocumentCell.
     *
     * @return 1: this is always equal to 1.
     */
    public int getMaxHeight() {
        return getHeight();
    }

    /**
     * Return the maximum width of this DocumentCell.
     *
     * @return 1: this is always equal to 1.
     */
    public int getMaxWidth() {
        return getWidth();
    }

    /**
     * The height-to-width-ratio is the ratio between the height of a character to its width.
     * This is used for estimating the width of a string given its height
     * @return the height-to-width-ratio to use for strings in this DocumentCell
     */
    public double getHeightToWidthRatio() {
        return this.heightToWidthRatio;
    }
    
    /**
     * @return result of {@code calculateActualWidth}, for testing/debug purposes 
     */
    public boolean isCalculateActualWidth() {
		return this.calculateActualWidth;
	}
    
    /**
     * If calculateActualWidth is false, the estimation of the width is done as follows:
     * -> textHeight*(length of the text)*heightToWidthRatio
     */
    private final boolean calculateActualWidth = false; // set to true if the actual width has to be calculated, otherwise an estimation is made

    /**
     * The height to width ratio of this DocumentCell,
     * this is always equal to 2/3.
     */
    protected final double heightToWidthRatio = 2.0/3.0;
}
