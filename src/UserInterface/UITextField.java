package UserInterface;

import java.awt.*;

public class UITextField extends DocumentCell{
    /*
    (int x, int y) -> position on the window
    text_size -> the height of the text
    String text -> The text of the TextField
     */
    public UITextField(int x, int y, int width, int text_size, String text) {
        super(x, y, width, text_size);
        textField = text;
        textHeight = text_size;
        updateSizes();
        setWidth(getMaxWidth());
    }

    @Override
    /*
    Render this UserInterface.UITextField.
    => Get information about the width of the text in the textFieldFont
    => Set the color, font and then draw the string on the window.
     */
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(textFieldFont);
        updateSizes();
        g.setColor(textFieldColor);
        g.setFont(textFieldFont);
        g.drawString(textField, getxPos(), getyPos()+textHeight);
        // Draw a rectangle around the text for debugging purposes
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
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

    @Override
    /*
    The max width of a UserInterface.UITextField is the width of the string
     */
    public int getMaxWidth() {
        return textWidth;
    }

    @Override
    /*
    the max height of a UserInterface.UITextField is the height of the string
     */
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
