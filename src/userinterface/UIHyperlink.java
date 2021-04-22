package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

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
        this.textHeight = link_size;
        this.textWidth = width;
        this.href = href;
        updateSizes();
        setWidth(getMaxWidth());
    }

    /**
     * Renders the link onto the window.
     * -> Update information about the dimensions of the string
     * -> Set the color
     * -> Underline the text
     * -> Draw the href on the window
     *
     * @param g: The graphics that will be updated
     */
    @Override
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(hyperlinkFont);
        updateSizes();
        g.setColor(hyperlinkColor);

        // Underling the hyperlink
        AttributedString link = new AttributedString(text);
        link.addAttribute(TextAttribute.FONT, hyperlinkFont);
        link.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        g.drawString(link.getIterator(), getxPos(), getyPos()+super.getMaxHeight());

        // Draw a rectangle around the text for debugging purposes
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * Update the {@code textWidth} based on this {@code UIHyperlink} {@code href} attribute.
     */
    private void updateSizes() {
        if (!isCalculateActualWidth()) textWidth =  (int) (super.getMaxHeight()*text.length()*heightToWidthRatio);
        else {
            if (metrics == null) return;
            textWidth = metrics.stringWidth(href);
        }
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
     * Return the text width of this {@code UIHyperlink}
     *
     * @return textWidth: the width of this {@code UIHyperlink}.
     */
    @Override
    public int getMaxWidth() {
        return this.textWidth;
    }

    /**
     * Return the text height of this {@code UIHyperlink}
     *
     * @return textHeight: the height of this {@code UIHyperlink}.
     */
    @Override
    public int getMaxHeight() {
        return this.textHeight;
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
    /**
     * A string variable to denote the text value of this UIHyperlink.
     */
    private final String text = super.getText();

    // ============== Dimension variables ====================

    /**
     * An integer variable to denote the text height of this {@code UIHyperlink}.
     */
    private int textWidth;

    /**
     * An integer variable to denote the text height of this {@code UIHyperlink}.
     */
    private int textHeight;

    // ============== Font variables ===========================
    /**
     * A variable to denote the {@link Font} of the text of this UIHyperlink
     */
    private final Font hyperlinkFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    /**
     * A variable to denote the {@link Color} of the text of this UIHyperlink
     */
    private final Color hyperlinkColor = Color.BLUE;
    /**
     * A variable to denote the {@link FontMetrics} of the text of this UIHyperlink.
     */
    private FontMetrics metrics;
}
