package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
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
        this.textField = new UITextField(x, y, width, link_size, text);
        textHeight = link_size;
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
    public void render(Graphics g) {
        metrics = g.getFontMetrics(hyperlinkFont);
        textField.setMetrics(metrics);
        updateSizes();
        if (outOfVerticalBounds()) return;
        String visibleText = textField.visibleText();
        if (visibleText.length() == 0) return;
        AttributedString link = new AttributedString(visibleText);
        g.setColor(Color.BLUE);
        link.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (getxPos() <= getxReference()+5)
            g.drawString(link.getIterator(), getxPos(), getyPos()+textHeight+getyOffset());
        else
            g.drawString(link.getIterator(), Math.max(getxPos() + getxOffset(), getxReference()), getyPos() + textHeight + getyOffset());
    }

    /**
     * Update the {@code textWidth} based on this {@code UIHyperlink} {@code href} attribute.
     */
    private void updateSizes() {
        if (!isCalculateActualWidth()) textWidth =  (int) (textHeight*getText().length()*heightToWidthRatio);
        else {
            if (metrics == null) return;
            textWidth = metrics.stringWidth(getText());
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
     * @return returnMessage: a {@link ReturnMessage} of the {@link ReturnMessage.Type} {@code text} based on
     *                        the text attribute of this {@code UITextHyperlink} if this {@code UITextHyperlink}
     *                        was clicked, a {@link ReturnMessage} of the {@link ReturnMessage.Type} {@code Empty} otherwise.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (id == MouseEvent.MOUSE_RELEASED && wasClicked(x, y))
                return new ReturnMessage(ReturnMessage.Type.Hyperlink, getText());
        return new ReturnMessage(ReturnMessage.Type.Empty);
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
        return textField.getText();
    }

    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        textField.setxPos(xPos);
    }

    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        textField.setyPos(yPos);
    }

    @Override
    public void setxOffset(int xOffset) {
        super.setxOffset(xOffset);
        textField.setxOffset(xOffset);
    }

    @Override
    public void setxReference(int xReference) {
        super.setxReference(xReference);
        textField.setxReference(xReference);
    }

    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        textField.setParentWidth(parentWidth);
    }

    /**
     * A textField to keep the text of this {@link UITextHyperlink}.
     */
    private final UITextField textField;

    // ============== Dimension variables ====================
    /**
     * An integer variable to denote the height of the text of this {@link UITextHyperlink}.
     */
    private final int textHeight;
    /**
     * An integer variable to denote the width of the text of this {@link UITextHyperlink}.
     */
    private int textWidth;

    // ============== Font variables ===========================
    /**
     * A variable to denote the {@link Font} of the text of this {@link UITextHyperlink}
     */
    private final Font hyperlinkFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    /**
     * A variable to denote the {@link FontMetrics} of the text of this {@link UITextHyperlink}.
     */
    private FontMetrics metrics;
}

