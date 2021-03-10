package UserInterface;

import java.awt.*;

public class DocumentCell extends Frame {
    public DocumentCell(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void Render(Graphics g) {
        super.Render(g);
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        super.handleMouse(id, x, y, clickCount, button, modifiersEx);
    }

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
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        return "";
    }

    public int getMaxHeight() {
        return 1;
    }

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

    /*
    If calculateActualWidth is false, the estimation of the width is done as follows:
    -> textHeight*(length of the text)*heightToWidthRatio
     */
    protected final boolean calculateActualWidth = false; // set to true if the actual width has to be calculated, otherwise an estimation is made
    protected final double heightToWidthRatio = 2.0/3.0;



}
