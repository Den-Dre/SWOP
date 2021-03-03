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
        textWidth = width;
    }

    @Override
    /*
    Render this UITextField.
    => Get information about the width of the text in the textFieldFont
    => Set the color, font and then draw the string on the window.
     */
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(textFieldFont);
        updateSizes();
        g.setColor(textFieldColor);
        g.setFont(textFieldFont);
        g.drawString(textField, getxPos(), getyPos()+getHeight());
        // Draw a rectangle around the text for debugging purposes
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /*
    Update the textWidth of this UITextField in the textFieldFont.
     */
    private void updateSizes() {
        if (metrics == null) return;
        textWidth = metrics.stringWidth(textField);
    }

    @Override
    /*
    The max width of a UITextField is the width of the string
     */
    public int getMaxWidth() {
        return textWidth;
    }

    @Override
    /*
    the max height of a UITextField is the height of the string
     */
    public int getMaxHeight() {
        return textHeight;
    }

    // Content of the textField
    private String textField = "";

    // Dimension parameters
    private int textHeight;
    private int textWidth;

    // Font variables
    private Font textFieldFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, getHeight());
    private Color textFieldColor = Color.BLACK;
    private FontMetrics metrics;


}
