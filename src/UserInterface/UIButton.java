package UserInterface;

import java.awt.*;
import java.awt.event.MouseEvent;

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

    private State state = new NotPressed();

    private final String displayText;
    private final String returnText;

    private final int textHeight = getHeight()*4/5;

    private Color buttonColor = Color.LIGHT_GRAY;
    private Color contourColor = Color.BLACK;
    private Color textColor = Color.BLACK;
    private Font font = new Font(Font.SERIF, Font.PLAIN, textHeight);

    private static abstract class State {
        abstract void Render(Graphics g);
        abstract ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier);
    }

    private class NotPressed extends State {
        private NotPressed() {
            buttonColor = Color.LIGHT_GRAY;
            contourColor = Color.BLACK;
            textColor = Color.BLACK;
            font = new Font(Font.SERIF, Font.PLAIN, textHeight);
        }

        @Override
        void Render(Graphics g) {
            drawButton(g);
            drawText(g);
        }

        private void drawButton(Graphics g) {
            g.setColor(buttonColor);
            g.fillRoundRect(getxPos(), getyPos(), getWidth(), getHeight(), 3,3);
            g.setColor(contourColor);
            g.drawRoundRect(getxPos(), getyPos(), getWidth(), getHeight(), 3,3);
        }

        @Override
        public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
            if (!wasClicked(x, y)) return new ReturnMessage(ReturnMessage.Type.Empty);
            if (id != MouseEvent.MOUSE_PRESSED) return new ReturnMessage(ReturnMessage.Type.Empty);
            state = new Pressed();
            return new ReturnMessage(ReturnMessage.Type.Empty);
        }
    }

    private class Pressed extends State {

        private Pressed() {
            buttonColor = Color.GRAY;
            contourColor = Color.BLACK;
            textColor = Color.BLACK;
            font = new Font(Font.SERIF, Font.ITALIC, textHeight);
        }

        @Override
        void Render(Graphics g) {
            drawButton(g);
            drawText(g);
        }

        void drawButton(Graphics g) {
            g.setColor(buttonColor);
            g.fillRoundRect(getxPos(), getyPos(), getWidth(), getHeight(), 3,3);
            g.setColor(contourColor);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g.drawRoundRect(getxPos(), getyPos(), getWidth(), getHeight(), 3,3);
            g2.setStroke(new BasicStroke(1));
        }

        @Override
        public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
            if (id != MouseEvent.MOUSE_RELEASED) return new ReturnMessage(ReturnMessage.Type.Empty);
            state = new NotPressed();
            if (!wasClicked(x, y)) return new ReturnMessage(ReturnMessage.Type.Empty);
            return new ReturnMessage(ReturnMessage.Type.Button, returnText);
        }
    }

    @Override
    public void Render(Graphics g) {
        setWidth(g.getFontMetrics().stringWidth(displayText)*3/2);
        state.Render(g);
    }

    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        return state.getHandleMouse(id, x, y, clickCount, button, modifier);
    }

    /**
     * Draw this {@code UIButton's} displayText on the screen.
     *
     * @param g The graphics to use.
     */
    void drawText(Graphics g) {
        g.setColor(textColor);
        g.setFont(font);
        int centerY = getyPos()+textHeight;
        int textWidth = g.getFontMetrics().stringWidth(displayText);
        int centerX = getxPos() + (getWidth()-textWidth)/2;
        g.drawString(displayText, centerX, centerY);
    }

    @Override
    public void setHeight(int newHeight) { }

    public String getDisplayText() {
        return displayText;
    }
}
