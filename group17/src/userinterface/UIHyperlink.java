package userinterface;

import java.awt.event.MouseEvent;

/**
 * A class to represent a hyperlink in the UI layer.
 */
public class UIHyperlink extends UITextHyperlink{
    /**
     * Construct a new DocumentCell
     *
     * @param x: x position on the window
     * @param y: y position of the window
     * @param width: the width of this {@code UIHyperlink}
     * @param link_size: The height of this {@code UIHyperlink}
     * @param href: The value of the href attribute of this {@code UIHyperlink}
     * @param text: the text to be shown on this {@code UIHyperlink}, meant for {@link UITextHyperlink} being it's superclass
     */
    public UIHyperlink(int x, int y, int width, int link_size, String href, String text) throws IllegalDimensionException {
        super(x, y, width, link_size, text);
        this.href = href;
        setWidth(getMaxWidth());
    }

    /**
     * Input: a mouse click
     * Output: String
     * -> The method only checks if the click was on itself, other checks have been done upstream
     * -> Returns the href if this {@code UIHyperlink} was pressed, else "".
     *
     * @param id: The type of mouse action
     * @param x: The x coordinate of the mouse action.
     * @param y: The y coordinate of the mouse action.
     * @param clickCount: The number of times the mouse has clicked.
     * @param button: The mouse button that was clicked.
     * @param modifier: Possible other keys that were pressed during this mouse action.
     * @return string: {@code href} attribute of this {@code UIHyperlink} object iff this {@code UIHyperlink} object was clicked.
     *              The result is encapsulated in a {@link ReturnMessage} object of the corresponding type:
     *              {@link ReturnMessage.Type} {@code Hyperlink} or {@code Empty}.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (id == MouseEvent.MOUSE_RELEASED) {
            if (wasClicked(x, y))
                return new ReturnMessage(ReturnMessage.Type.Hyperlink, this.href);
        }
        return new ReturnMessage(ReturnMessage.Type.Empty);
    }

    /**
     * Get the {@code href} value of this {@code UITextHyperlink}.
     *
     * @return href: the href value of this {@code UITextHyperlink}.
     */
    public String getHref() {
        return this.href;
    }


    // =========== Contents of this UIHyperlink =============
    /**
     * A string variable to denote the href value of this UIHyperlink.
     */
    private final String href;
}