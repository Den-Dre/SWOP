import java.awt.*;

public class UITextField extends DocumentCell{
    public UITextField(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        textField = text;
    }

    @Override
    public void Render(Graphics g) {
        metrics = g.getFontMetrics(textFieldFont);
        g.setColor(textFieldColor);
        g.setFont(textFieldFont);
        g.drawString(textField, getxPos(), getyPos()+getHeight());
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if (metrics == null) return;
        textWidth = metrics.stringWidth(textField);
        textHeight = metrics.getHeight();
        setHeight(textHeight);
        setWidth(textWidth);
    }

    @Override
    public int getMaxWidth() {
        return textWidth;
    }

    @Override
    public int getMaxHeight() {
        return textHeight;
    }

    private int textHeight = 15;
    private String textField = "";
    private Font textFieldFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, textHeight);
    private Color textFieldColor = Color.BLACK;
    private FontMetrics metrics;
    private int textWidth;

}
