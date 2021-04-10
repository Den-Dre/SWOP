package UserInterface;

import UserInterface.DocumentCell;
import UserInterface.IllegalDimensionException;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

/**
 * A class to represent a hyperlink in the UI layer.
 */
public class UITextHyperlink extends DocumentCell {
    /**
     * Construct a new {@code UITextHyperlink}.
     *
     * @param x        : x position on the window
     * @param y        : y position of the window
     * @param width    : the width of this {@code UIHyperlink}
     * @param link_size: The height of this {@code UIHyperlink}
     * @param text     : The value of the href attribute of this {@code UIHyperlink}
     * @throws IllegalDimensionException : When negative dimensions are provided.
     */
    public UITextHyperlink(int x, int y, int width, int link_size, String text) throws IllegalDimensionException {
        super(x, y, width, link_size);
        this.text = text;
        textHeight = link_size;

        // TODO: Decide whether the following two calls can be omitted in this class's constructor:
        updateSizes();
        setWidth(getMaxWidth());
    }

    /**
     * Renders the link onto the window.
     * -> Update information about the dimensions of the string
     * -> Set the color
     * -> Underline the text
     *
     * @param g: The graphics that will be updated
     */
    @Override
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(hyperlinkFont);
        updateSizes();
        AttributedString link = new AttributedString(text);
        g.drawString(link.getIterator(), getxPos(), getyPos()+textHeight);

        // Draw a rectangle around the text for debugging purposes
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * Update the {@code textWidth} based on this {@code UIHyperlink} {@code href} attribute.
     */
    private void updateSizes() {
        if (!isCalculateActualWidth()) textWidth =  (int) (textHeight*text.length()*heightToWidthRatio);
        else {
            if (metrics == null) return;
            textWidth = metrics.stringWidth(text);
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
     * @return text: the text attribute of this {@code UITextHyperlink}.
     */
    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        return this.text;
    }

    /**
     * Returns the max height of this {@code UIHyperlink}, which is the width of the string.
     */
    @Override
    public int getMaxHeight() {
        return textHeight; // the +3 can be deleted but what it does is account for the extra height from the underlining.
    }

    /**
     * Returns the max width of this {@code UIHyperlink}, which is the height of the string
     */
    @Override
    public int getMaxWidth() {
        return textWidth;
    }

    /**
     * Retrieve the text displayed in this UIHyperlink
     *
     * @return text:
     *              The text displayed in this UIHyperlink.
     */
    public String getText() {
        return text;
    }

    // =========== Contents of this UIHyperlink =============
    /**
     * A string variable to denote the text value of this UIHyperlink.
     */
    private final String text;

    // ============== Dimension variables ====================
    /**
     * An integer variable to denote the height of the text of this UIHyperlink.
     */
    private final int textHeight;
    /**
     * An integer variable to denote the width of the text of this UIHyperlink.
     */
    private int textWidth;

    // ============== Font variables ===========================
    /**
     * A variable to denote the {@link Font} of the text of this UIHyperlink
     */
    private final Font hyperlinkFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    /**
     * A variable to denote the {@link FontMetrics} of the text of this UIHyperlink.
     */
    private FontMetrics metrics;
}

