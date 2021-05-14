package userinterface;

import java.awt.*;

public class HorizontalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public HorizontalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
    }

    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param frame: The {@link ContentFrame} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public HorizontalScrollBarDecorator(ContentFrame frame) throws IllegalDimensionException {
        super(frame);
    }


    public HorizontalScrollBarDecorator(HorizontalScrollBarDecorator decorator) {
        super(decorator.getContent().deepCopy());
    }

    /**
     * Create a deep copy of this {@code HorizontalScrollBarDecorator} object.
     *
     * @return copy: a deep copied version of this {@code HorizontalScrollBarDecorator}
     * object which thus does not point to the original object.
     */
    @Override
    protected HorizontalScrollBarDecorator deepCopy() {
        return new HorizontalScrollBarDecorator(this);
    }

    /**
     * render the contents of this AbstractFrame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.RED);
        DocumentCell contents = getContentWithoutScrollbars();
        // Simulate a scrollbar. TODO: Replace this with a working scroll bar
        g.fillRect(contents.getxPos(), contents.getyPos() + contents.getHeight() - SCROLLBAR_HEIGHT, contents.getWidth(), SCROLLBAR_HEIGHT);
//        System.out.println("Created HSB of: " + cellToBeDecorated.getClass());
    }
}
