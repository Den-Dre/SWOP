package userinterface;

import java.awt.*;

public class VerticalScrollBarDecorator extends DocumentCellDecorator {
    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param cell: The {@link DocumentCell} to be decorated.
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public VerticalScrollBarDecorator(DocumentCell cell) throws IllegalDimensionException {
        super(cell);
    }

    public VerticalScrollBarDecorator(VerticalScrollBarDecorator decorator) {
        super(decorator.getContent().deepCopy());
    }

    /**
     * Create a deep copy of this {@code VerticalScrollBarDecorator} object.
     *
     * @return copy: a deep copied version of this {@code VerticalScrollBarDecorator}
     * object which thus does not point to the original object.
     */
    @Override
    protected VerticalScrollBarDecorator deepCopy() {
        return new VerticalScrollBarDecorator(this);
    }

    /**
     * render the contents of this AbstractFrame.
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
//        System.out.println("Created VSB of: " + cellToBeDecorated.getClass().getCanonicalName());
    }
}
