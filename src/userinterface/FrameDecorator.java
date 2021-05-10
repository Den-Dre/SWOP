package userinterface;

public abstract class FrameDecorator extends Frame {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param frame: The {@link Frame} to be decorated
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public FrameDecorator(Frame frame) throws IllegalDimensionException {
        super(frame.getxPos(), frame.getyPos(), frame.getWidth(), frame.getHeight());
    }
}
