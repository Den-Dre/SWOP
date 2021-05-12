package userinterface;

import java.awt.*;

public class VerticalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public VerticalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
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
        g.setColor(Color.BLUE);
        // Simulate a scroll bar. TODO: Replace this with a working scroll bar
        // The drawn scroll bar overlaps with the `Add Bookmark` button in the
        // `BoomkarksDialog` screen, due to the `render()` method of `UIButton`:
        // in this method, the width of the button is re-estimated, but only when it's rendered.
        // Commenting out that `setWidth()` call will result in correctly placed scrollbars.
        g.fillRect(getxPos() + getWidth() - SCROLLBAR_WIDTH, getyPos(), SCROLLBAR_WIDTH, getHeight());
        System.out.println("Created VSB of: " + cellToBeDecorated.getClass().getCanonicalName());
    }
}
