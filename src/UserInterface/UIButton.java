package UserInterface;

import java.awt.*;

public class UIButton extends DocumentCell{

    /**
     * Initialise this {@code UIButton} with the given parameters.
     *
     * @param x      : The x coordinate of this {@code UIButton}
     * @param y      : The y coordinate of this {@code UIButton}
     * @param width  : The width of this {@code UIButton}
     * @param height : The height of this {@code UIButton}
     * @throws IllegalDimensionException: When the dimensions of {@code super} are illegal.
     */
    public UIButton(int x, int y, int width, int height, String displayText, String returnText) throws IllegalDimensionException {
        super(x, y, width, height);
        this.displayText = displayText;
        this.returnText = returnText;
    }

    /**
     * Renders this {@code UIButton} on the display.
     *
     * @param g: The graphics to be rendered with.
     */
    @Override
    public void Render(Graphics g) {
        // hangt af van staat: not-pressed of pressed -> design pattern gebruiken hiervoor? (zie pac-man voorbeeld)
        drawButton(g);
        drawText(g);
    }

    /**
     * Draw this {@code UIButton} visual aspects on the screen.
     *
     * @param g The graphics object to use.
     */
    private void drawButton(Graphics g) {
        setWidth(g.getFontMetrics().stringWidth(displayText)*3/2);
        g.setColor(normalButtonColor);
        g.fillRoundRect(getxPos(), getyPos(), getWidth(), getHeight(), 3,3);
        g.setColor(buttonContourColor);
        g.drawRoundRect(getxPos(), getyPos(), getWidth(), getHeight(), 3,3);
    }

    /**
     * Draw this {@code UIButton's} displayText on the screen.
     *
     * @param g The graphics to use.
     */
    private void drawText(Graphics g) {
        g.setColor(normalTextColor);
        g.setFont(font);
        int centerY = getyPos()+textHeight;
        int textWidth = g.getFontMetrics().stringWidth(displayText);
        int centerX = getxPos() + (getWidth()-textWidth)/2;
        g.drawString(displayText, centerX, centerY);
    }

    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        if (!wasClicked(x, y)) return "";
        System.out.println("Button was pressed!");
        return returnText;
    }

//    @Override
//    public void setWidth(int newWidth) { }

    @Override
    public void setHeight(int newHeight) { }

    public String getDisplayText() {
        return displayText;
    }

    private final String displayText;
    private final String returnText;

    private final int textHeight = getHeight()*4/5;
    Font font = new Font(Font.SERIF, Font.PLAIN, textHeight);

    private final Color normalButtonColor = Color.LIGHT_GRAY;
    private final Color buttonContourColor = Color.BLACK;
    private final Color normalTextColor = Color.BLACK;
}
