import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class UIHyperlink extends DocumentCell{
    public UIHyperlink(int x, int y, int width, int height, String link) {
        super(x, y, width, height);
        hyperLink = link;
    }

    @Override
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(hyperlinkFont);
        g.setColor(hyperlinkColor);
        AttributedString link = new AttributedString(hyperLink);
        link.addAttribute(TextAttribute.FONT, hyperlinkFont);
        link.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        g.drawString(link.getIterator(), getxPos(), getyPos()+getHeight());
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if (metrics == null) return;
        textWidth = metrics.stringWidth(hyperLink);
        textHeight = metrics.getHeight();
        setHeight(textHeight);
        setWidth(textWidth);
    }

    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (wasClicked(x,y)) {
            return hyperLink;
        }
        return "";
    }

    @Override
    public int getMaxHeight() {
        return getHeight();
    }

    @Override
    public int getMaxWidth() {
        return getWidth();
    }

    private int textHeight = getHeight();
    private String hyperLink = "";
    private Font hyperlinkFont = new Font(Font.DIALOG_INPUT, Font.ITALIC, textHeight);
    private Color hyperlinkColor = Color.BLUE;
    private FontMetrics metrics;
    private int textWidth;

}
