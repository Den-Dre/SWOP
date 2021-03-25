package UserInterface;

import java.awt.*;

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
     * Render the graphics {@code g} of this DocumentCell.
     *
     * @param g: The graphics to be rendered.
     */
    @Override
    public void Render(Graphics g) {
        super.Render(g);
    }

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
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        super.handleMouse(id, x, y, clickCount, button, modifiersEx);
    }

    /**
     * Handle a resize of this DocumentCell.
     *
     * @param newWindowWidth: The width the DocumentCell will be resized to.
     * @param newWindowHeight: The height the DocumentCell will be resized to.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        super.handleResize(newWindowWidth, newWindowHeight);
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

    /**
     * The output generated by a mouse click on this DocumentCell.
     * This is always the empty string "".
     *
     * @param id: The id of the click
     * @param x: The x coordinate of the click
     * @param y: The y coordinate of the click
     * @param clickCount: The number of clicks that occured.
     * @param button: Which mouse button was clicked.
     * @param modifier: Extra control key that was held during the click.
     * @return "": the empty string.
     */
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        return "";
    }

    /**
     * Return the maximum height of this DocumentCell.
     *
     * @return 1: this is always equal to 1.
     */
    public int getMaxHeight() {
        return 1;
    }

    /**
     * Return the maximum width of this DocumentCell.
     *
     * @return 1: this is always equal to 1.
     */
    public int getMaxWidth() {
        return 1;
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
