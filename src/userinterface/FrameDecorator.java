package userinterface;

public abstract class FrameDecorator extends Frame {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x      : The x coordinate of this Frame.
     * @param y      : The y coordinate of this Frame.
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public FrameDecorator(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
    }
}
