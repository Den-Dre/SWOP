package userinterface;

import java.awt.*;

public class HorizontalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public HorizontalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
    }

    /**
     * render the contents of this Frame.
     *
     * @param g : The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.RED);
        // Simulate a scrollbar. TODO: Replace this with a working scroll bar
        g.fillRect(getxPos(), getyPos() + getHeight() - SCROLLBAR_HEIGHT, cellToBeDecorated.getWidth(), SCROLLBAR_HEIGHT);
        System.out.println("Created HSB of: " + cellToBeDecorated.getClass());
    }
}
