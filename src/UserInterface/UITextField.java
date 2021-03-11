package UserInterface;

import java.awt.*;

public class UITextField extends DocumentCell{
    /**
     * Create a {@code UITextField} based on the given parameters.
     *
     * @param x: x position on this UITextField in the window.
     * @param y: y position of this UITextField in the window.
     * @param width: the width of this {@code UITextField}.
     * @param text_size: The height of this {@code UITextField}.
     * @param text: The text attribute of this {@code UITextField}.
     * @throws Exception 
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
     * => Set the color, font and then draw the string on the window.
     *
     * @param g: The graphics to be updated.
     */
    @Override
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(textFieldFont);
        updateSizes();
        g.setColor(textFieldColor);
        g.setFont(textFieldFont);
        g.drawString(textField, getxPos(), getyPos()+textHeight);
        // Draw a rectangle around the text for debugging purposes
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /*
    Update the textWidth of this UserInterface.UITextField in the textFieldFont.
     */
    private void updateSizes() {
        if (!calculateActualWidth) textWidth =  (int) (textHeight*textField.length()*heightToWidthRatio);
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

    public String getText() {
        return this.textField;
    }

    // Content of the textField
    private String textField = "";

    // Dimension parameters
    private final int textHeight;
    private int textWidth;

    // Font variables
    private final Font textFieldFont = new Font(Font.SANS_SERIF, Font.PLAIN, getHeight());
    private final Color textFieldColor = Color.BLACK;
    private FontMetrics metrics;
}
