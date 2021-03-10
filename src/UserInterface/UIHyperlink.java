package UserInterface;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class UIHyperlink extends DocumentCell{
    /**
     * Construct a new DocumentCell
     *
     * @param x: x position on the window
     * @param y: y position of the window
     * @param width: the width of this {@code UIHyperlink}
     * @param link_size: The height of this {@code UIHyperlink}
     * @param href: The value of the href attribute of this {@code UIHyperlink}
     * @throws Exception 
     */
    public UIHyperlink(int x, int y, int width, int link_size, String href, String text) throws Exception {
        super(x, y, width, link_size);
        this.href = href;
        this.text = text;
        textHeight = link_size;
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

        g.drawString(link.getIterator(), getxPos(), getyPos()+textHeight);

        // Draw a rectangle around the text for debugging purposes
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * Update the {@code textWidth} based on this {@code UIHyperlink} {@code href} attribute.
     */
    private void updateSizes() {
        if (!calculateActualWidth) textWidth =  (int) (textHeight*text.length()*heightToWidthRatio);
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
     * @return string: {@code href} attribute of this {@code UIHyperlink} object iff this {@code UIHyperlink} object was clicked
     */
    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (wasClicked(x,y)) {
            return href;
        }
        return "";
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

    public String getText() {
        return text;
    }

    // Contents of this UIHyperlink
    private String href = "";
    private final String text;

    // Dimension variables
    private int textHeight;
    private int textWidth;

    // Font variables
    private Font hyperlinkFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    private Color hyperlinkColor = Color.BLUE;
    private FontMetrics metrics;
}
