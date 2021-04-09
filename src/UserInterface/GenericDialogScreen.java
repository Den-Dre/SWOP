package UserInterface;

import jdk.jshell.spi.ExecutionControl;

import java.awt.*;

public class GenericDialogScreen extends Frame {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x      : The x coordinate of this Frame.
     * @param y      : The y coordinate of this Frame.
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public GenericDialogScreen(int x, int y, int width, int height, Browsr browsr) throws IllegalDimensionException {
        super(x, y, width, height);
        this.browsr = browsr;
    }

    public void Render(Graphics g) { }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) { }

    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) { }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) { }

    /**
     * Get the {@link Browsr} associated to
     * this {@code GenericDialogScreen}.
     *
     * @return browsr:
     *      The {@link Browsr} object associated to this GenericDialogScreen.
     */
    Browsr getBrowsr() {
        return this.browsr;
    }

    private final Browsr browsr;
}
