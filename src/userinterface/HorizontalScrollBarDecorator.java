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
        // Simulate a scrollbar. TODO: Replace this with a working scroll bar
        g.fillRect(getxPos(), getyPos() + getHeight() - SCROLLBAR_HEIGHT, cellToBeDecorated.getWidth(), SCROLLBAR_HEIGHT);
//        System.out.println("Created HSB of: " + cellToBeDecorated.getClass());
    }
}
