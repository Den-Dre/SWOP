package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A class to represent a graphical
 * button that can be clicked and is
 * contained within a {@link UIForm}.
 */
public class UIButton extends DocumentCell{

    /**
     * Initialise this {@code UIButton} with the given parameters.
     *
     * @param x          : The x coordinate of this {@code UIButton}
     * @param y          : The y coordinate of this {@code UIButton}
     * @param width      : The width of this {@code UIButton}
     * @param height     : The height of this {@code UIButton}
     * @param displayText: The text to be displayed on this {@code UIButton}.
     * @param returnText : The text to be returned when this {@code UIButton} is clicked.
     * @throws IllegalDimensionException: When the dimensions of {@code super} are illegal.
     */
    public UIButton(int x, int y, int width, int height, String displayText, String returnText) throws IllegalDimensionException {
        super(x, y, width, height);
        this.displayText = displayText;
        this.returnText = returnText;
    }

    public UIButton(UIButton button) {
        super(button.getxPos(), button.getyPos(), button.getWidth(), button.getHeight());
        this.displayText = button.getDisplayText();
        this.returnText = button.getReturnText();
    }

    /**
     * A {@link State} variable that represents the state of a
     * button that is not pressed in.
     */
    private State state = new NotPressed();

    /**
     * A string variable that contains the
     * text to be displayed on a button.
     */
    private final String displayText;

    /**
     * A string variable that contains the
     * text to be returned by a button
     * when it's clicked.
     */
    private final String returnText;

    /**
     * An integer variable that represents
     * the constant heigt of the text contained
     * in a {@code UIButton}.
     */
    private final int textHeight = getHeight()*4/5;

    /**
     * A variable that contains the
     * {@link Color} of the inside area
     * of a {@code UIButton}.
     */
    private Color buttonColor = Color.LIGHT_GRAY;

    /**
     * A variable that contains the
     * {@link Color} of the edges
     * of a {@code UIButton}.
     */
    private Color contourColor = Color.BLACK;

    /**
     * A variable that contains the
     * {@link Color} of the text
     * of a {@code UIButton}.
     */
    private Color textColor = Color.BLACK;

    /**
     * A variable that contains the
     * font of the text to be displayed
     * in this {@code UIButton}.
     */
    private Font font = new Font(Font.SERIF, Font.PLAIN, textHeight);

    /**
     * An abstract class to represent the states
     * that a {@code UIButton} can be in.
     *
     * <p>This can either be a {@code Pressed} or
     * {@code NotPressed} state.</p>
     */
    private static abstract class State {

        /**
         * render the button given its current state
         *
         * @param g: the {@link Graphics} to be rendered.
         */
        abstract void Render(Graphics g);

        /**
         * The output generated by a mouse click on this
         * {@code UIButton}. The return result is always given
         * in the form of a {@link ReturnMessage} object.
         *
         * @param id: The id of the click
         * @param x: The x coordinate of the click
         * @param y: The y coordinate of the click
         * @param clickCount: The number of clicks that occured.
         * @param button: Which mouse button was clicked.
         * @param modifier: Extra control key that was held during the click.
         * @return returnMessage: a {@link ReturnMessage} which contains the string to be returned.
         */
        abstract ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier);
    }

    /**
     * A nested class to represent the state of a
     * button when it's not pressed.
     */
    private class NotPressed extends State {

        /**
         * Creates a state of this {@code UIButton}
         * to represent it's not pressed.
         */
        private NotPressed() {
            buttonColor = Color.LIGHT_GRAY;
            contourColor = Color.BLACK;
            textColor = Color.BLACK;
            font = new Font(Font.SERIF, Font.PLAIN, textHeight);
        }

        /**
         * render the button given its current {@code notPressed} state.
         *
         * @param g: the {@link Graphics} to be rendered.
         */
        @Override
        void Render(Graphics g) {
            drawButton(g);
            drawText(g);
        }

        /**
         * A helper method to render the {@code notPressed}
         * state of this button.
         *
         * @param g: the {@link Graphics} to be rendered.
         */
        private void drawButton(Graphics g) {
            g.setColor(buttonColor);
            g.fillRoundRect(getxPos()+getxOffset(), getyPos()+getyOffset(), getWidth(), getHeight(), 3,3);
            g.setColor(contourColor);
            g.drawRoundRect(getxPos()+getxOffset(), getyPos()+getyOffset(), getWidth(), getHeight(), 3,3);
        }

        /**
         * The output generated by a mouse click on this
         * {@code UIButton}. The return result is always given
         * in the form of a {@link ReturnMessage} object.
         *
         * @param id: The id of the click
         * @param x: The x coordinate of the click
         * @param y: The y coordinate of the click
         * @param clickCount: The number of clicks that occured.
         * @param button: Which mouse button was clicked.
         * @param modifier: Extra control key that was held during the click.
         * @return  {@code ReturnMessage(ReturnMessage.Type.Empty)}:
         *                  a {@link ReturnMessage} which contains the empty string {@code ""}.
         */
        @Override
        public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
            if (wasClicked(x, y) && id == MouseEvent.MOUSE_PRESSED)
                state = new Pressed();
            return new ReturnMessage(ReturnMessage.Type.Empty);
        }
    }

    /**
     * A nested class to represent the state of a
     * button when it's not pressed.
     */
    private class Pressed extends State {

        /**
         * Creates a state of this {@code UIButton}
         * to represent it is pressed.
         */
        private Pressed() {
            buttonColor = Color.GRAY;
            contourColor = Color.BLACK;
            textColor = Color.BLACK;
            font = new Font(Font.SERIF, Font.ITALIC, textHeight);
        }

        /**
         * render the button given its current {@code Pressed} state.
         *
         * @param g: the {@link Graphics} to be rendered.
         */
        @Override
        void Render(Graphics g) {
            drawButton(g);
            drawText(g);
        }

        /**
         * A helper method to render the {@code Pressed}
         * state of this button.
         *
         * @param g: the {@link Graphics} to be rendered.
         */
        void drawButton(Graphics g) {
            g.setColor(buttonColor);
            g.fillRoundRect(getxPos()+getxOffset(), getyPos()+getyOffset(), getWidth(), getHeight(), 3,3);
            g.setColor(contourColor);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g.drawRoundRect(getxPos()+getxOffset(), getyPos()+getyOffset(), getWidth(), getHeight(), 3,3);
            g2.setStroke(new BasicStroke(1));
        }

        /**
         * The output generated by a mouse click on this
         * {@code UIButton}. The return result is always given
         * in the form of a {@link ReturnMessage} object.
         *
         * @param id: The id of the click
         * @param x: The x coordinate of the click
         * @param y: The y coordinate of the click
         * @param clickCount: The number of clicks that occured.
         * @param button: Which mouse button was clicked.
         * @param modifier: Extra control key that was held during the click.
         * @return  ReturnMessage: a {@link ReturnMessage} which contains the appropriate return string.
         */
        @Override
        public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
            if (id != MouseEvent.MOUSE_RELEASED) return new ReturnMessage(ReturnMessage.Type.Empty);
            state = new NotPressed();
            if (!wasClicked(x, y)) return new ReturnMessage(ReturnMessage.Type.Empty);
            return new ReturnMessage(ReturnMessage.Type.Button, returnText);
        }
    }

    /**
     * render this button depending on which {@link State} it's in.
     *
     * @param g: The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        if (outOfHorizontalBounds() | outOfVerticalBounds()) return;
        setWidth(g.getFontMetrics().stringWidth(displayText)*3/2);
        state.Render(g);
    }

    /**
     * Get the appropriate {@link ReturnMessage} when a click occurs on this {@code UIButton}.
     *
     * @param id: The id of the click
     * @param x: The x coordinate of the click
     * @param y: The y coordinate of the click
     * @param clickCount: The number of clicks that occured.
     * @param button: Which mouse button was clicked.
     * @param modifier: Extra control key that was held during the click.
     * @return returnMessage: a {@link ReturnMessage} containing the correct string depending on
     *                          the current {@link State} this {@code UIButton} is in.
     */
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
        int centerY = getyPos()+textHeight+getyOffset();
        int textWidth = g.getFontMetrics().stringWidth(displayText);
        int centerX = getxPos() + (getWidth()-textWidth)/2 +getxOffset();
        g.drawString(displayText, centerX, centerY);
    }

    /**
     * Set the heigh of this buttong
     * to the given height.
     *
     * @param newHeight: the new height to be set.
     */
    @Override
    public void setHeight(int newHeight) { }

    /**
     * Get the text that is displayed on the
     * body of this button.
     *
     * @return displayText: the text that is displayed
     *          on the body of this {@code UIButton}.
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * Get the text that is returned when
     * this {@code UIButton} is clicked.
     *
     * @return returnText: the text that is
     *                     returned when this {@code UIButton} is clicked.
     *
     */
    public String getReturnText() {
        return this.returnText;
    }
}
