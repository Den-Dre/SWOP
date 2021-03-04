import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class UIHyperlink extends DocumentCell{
    /*
    (int x, int y) -> position on the window
    link_size -> the height of the text
    String href -> The href text of the HyperLink
    */
    public UIHyperlink(int x, int y, int width, int link_size, String href) {
        super(x, y, width, link_size);
        this.href = href;
        textHeight = link_size;
        textWidth = width;
    }

    @Override
    /*
    Renders the link onto the window.
    -> Update information about the dimensions of the string
    -> Set the color
    -> Underline the text
    -> Draw the href on the window
     */
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(hyperlinkFont);
        updateSizes();
        g.setColor(hyperlinkColor);

        // Underling the hyperlink
        AttributedString link = new AttributedString(href);
        link.addAttribute(TextAttribute.FONT, hyperlinkFont);
        link.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        g.drawString(link.getIterator(), getxPos(), getyPos()+textHeight);

        // Draw a rectangle around the text for debugging purposes
        // g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    private void updateSizes() {
        if (metrics == null) return;
        textWidth = metrics.stringWidth(href);
    }

    @Override
    /*
    Input: a mouse click
    Output: String
    -> The method only checks if the click was on itself, other checks have been done upstream
    -> Returns the href if this UIHyperlink was pressed, else "".
     */
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (wasClicked(x,y)) {
            return href;
        }
        return "";
    }

    @Override
    /*
    The max height of a UIHyperlink is the width of the string
     */
    public int getMaxHeight() {
        return textHeight;
    }

    @Override
    /*
    the max width of a UIHyperlink is the height of the string
     */
    public int getMaxWidth() {
        return textWidth;
    }

    // Content of the Hyperlink
    private String href = "";


    // Dimension variables
    private int textHeight;
    private int textWidth;

    // Font variables
    private Font hyperlinkFont = new Font(Font.DIALOG_INPUT, Font.ITALIC, getHeight());
    private Color hyperlinkColor = Color.BLUE;
    private FontMetrics metrics;


}
