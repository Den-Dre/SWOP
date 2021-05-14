package userinterface;

import java.awt.*;

/**
 * A class to represent text fields in the UI layer.
 */
public class UITextField extends DocumentCell{
    /**
     * Create a {@code UITextField} based on the given parameters.
     *
     * @param x: x position on this UITextField in the window.
     * @param y: y position of this UITextField in the window.
     * @param width: the width of this {@code UITextField}.
     * @param text_size: The height of this {@code UITextField}.
     * @param text: The text attribute of this {@code UITextField}.
     * @throws IllegalDimensionException: When one of the dimensions of the associated {@link Frame} is negative.
     */
    public UITextField(int x, int y, int width, int text_size, String text) throws IllegalDimensionException {
        super(x, y, width, text_size);
        textField = text;
        textHeight = text_size;
        updateSizes();
        setWidth(getMaxWidth());
    }

    /**
     * Render this UITextField.
     * => Get information about the width of the text in the textFieldFont.
     * => Set the color, normalFont and then draw the string on the window.
     *
     * @param g: The graphics to be updated.
     */
    @Override
    public void Render(Graphics g) {
        if (outOfArea()) return;
        metrics = g.getFontMetrics(textFieldFont);
        updateSizes();
        g.setColor(textFieldColor);
        g.setFont(textFieldFont);
        g.drawString(textField, getxPos()+getxOffset(), getyPos()+textHeight+getyOffset());
        // Draw a rectangle around the text for debugging purposes
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * Update the textWidth of this UserInterface.UITextField in the textFieldFont.
     */
    private void updateSizes() {
        if (!isCalculateActualWidth()) textWidth =  (int) (textHeight*textField.length()*heightToWidthRatio);
        else {
            if (metrics == null) return;
            textWidth = metrics.stringWidth(textField);
        }
    }

    /**
     * The max width of a {@code UITextField} is the width of the string.
     */
    @Override
    public int getMaxWidth() {
        return textWidth;
    }

    /**
     * The max height of a {@code UITextField} is the height of the string.
     */
    @Override
    public int getMaxHeight() {
        return textHeight;
    }

    /**
     * Retrieve a String representation of the text that this UITextField contains
     *
     * @return text:
     *              The text that this UITextField contains.
     */
    public String getText() {
        return this.textField;
    }

    /**
     * A string variable to represent the contents of this UITextField.
     */
    private String textField = "";

    // ============== Dimension parameters ====================
    /**
     * An integer variable to represent the height
     * of the text contained in this UITextField.
     */
    private final int textHeight;
    /**
     * An integer variable to represent the width
     * of the text contained in this UITextField.
     */
    private int textWidth;

    // ================ Font variables ==============
    /**
     * A variable to denote the {@link Font} used for the text of this UITextField.
     */
    private final Font textFieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    /**
     * A variable to denote the {@link Color} used for the text of this UITextField.
     */
    private final Color textFieldColor = Color.BLACK;
    
    /**
     * A variable to denote the {@link FontMetrics} used for the text of this UITextField.
     */
    private FontMetrics metrics;
}
