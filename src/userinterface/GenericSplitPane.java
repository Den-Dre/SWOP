package userinterface;

import java.awt.*;

public abstract class GenericSplitPane extends Pane {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param x      : The x coordinate of this AbstractFrame.
     * @param y      : The y coordinate of this AbstractFrame.
     * @param width  : The width of this AbstractFrame
     * @param height : The height of this AbstractFrame
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public GenericSplitPane(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
    }

    public GenericSplitPane(GenericSplitPane p) {
        super(p.getxPos(), p.getyPos(), p.getWidth(), p.getHeight());
    }

    abstract void drawSeparator(Graphics g);

    /**
     * Set the {@link ContentFrame} object that currently has focus.
     *
     * @param pane : The {@code ContentFrame} object to be set.
     */
    @Override
    public void setFocusedPane(Pane pane) {
        this.focusedPane = pane;
    }

    /**
     * A constant that denotes the thickness
     * of the separator drawn between split windows.
     */
    protected final int SEPARATOR_THICKNESS = 5;
}
