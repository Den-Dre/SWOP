package userinterface;

import java.awt.*;

/**
 * A class to represent text fields in the UI layer.
 */
public class UITextField extends DocumentCell{
    /**
     * Create a {@code UITextField} based on the given parameters.
     *
     * @param x: x position on this {@code UITextField} in the window.
     * @param y: y position of this {@code UITextField} in the window.
     * @param width: the width of this {@code UITextField}.
     * @param text_size: The height of this {@code UITextField}.
     * @param text: The text attribute of this {@code UITextField}.
     * @throws IllegalDimensionException: When one of the dimensions of the associated {@link AbstractFrame} is negative.
     */
    public UITextField(int x, int y, int width, int text_size, String text) throws IllegalDimensionException {
        super(x, y, width, text_size);
        textField = text;
        textHeight = text_size;
        updateSizes();
        setWidth(getMaxWidth());
    }
    
    /**
     * Create a {@code UITextField} based on the given {@code UITextField}. Used to achieve 
     * a deep copy of a given {@code UITextField}.
     * 
     * @param textField : the {@code UITextField} that needs to be copied.
     */
    public UITextField(UITextField textField) {
        super(textField.getxPos(), textField.getyPos(), textField.getMaxWidth(), textField.getMaxHeight());
        this.textField = textField.getText();
        this.textHeight = textField.getMaxHeight();
        updateSizes();
        setWidth(getMaxWidth());
    }

    /**
     * render this {@code UITextField}.
     * => Get information about the width of the text in the {@code textFieldFont}.
     * => Set the color, normalFont and then draw the string on the window.
     *
     * @param g: The graphics to be updated.
     */
    @Override
    public void render(Graphics g) {
        metrics = g.getFontMetrics(textFieldFont);
        updateSizes();
        if (outOfVerticalBounds()) return;
        g.setColor(textFieldColor);
        g.setFont(textFieldFont);
        g.drawString(visibleText(), getxPos(), getyPos()+textHeight+getyOffset());
        // Draw a rectangle around the text for debugging purposes
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * Update the textWidth of this {@code UITextField} in the {@code textFieldFont}.
     */
    private void updateSizes() {
//        if (!isCalculateActualWidth()) textWidth =  (int) (textHeight*textField.length()*heightToWidthRatio);
//        else {
//            if (metrics == null) return;
//            textWidth = metrics.stringWidth(textField);
//        }
        if (metrics == null) {
            textWidth = (int) (textHeight * textField.length() * heightToWidthRatio);
            return;
        }
        textWidth = metrics.stringWidth(textField);
    }

    /**
     * Return the visible part of the text of this {@code UITextField} based on 
     * the size of the {@link LeafPane} where this {@code UITextField} belongs.
     * Only the text that fits into the {@link LeafPane} will be returned.
     * 
     * @return text : 
     * 				the visible part of the text of type {@link String}. A substring
     * 				of the text of this {@code UITextField}.
     */
    public String visibleText() {
        String text = getText();
        if (metrics == null) return text;
        //if (metrics.stringWidth(text) < parentWidth) return text;
        int frontCut = Math.abs(getxOffset());
        if (metrics.stringWidth(text) <= frontCut) return "";
        for (int i = 0; i <= text.length(); i++) {
            if (metrics.stringWidth(text.substring(0,i)) >= frontCut){
                text = text.substring(i);
                break;
            }
        }
        int width = parentWidth;
        for (int j = 0; j < text.length(); j++) {
            if (metrics.stringWidth(text.substring(0,j)) >= width){
                text = text.substring(0, j);
                break;
            }
        }
        return text;
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
     * Retrieve a String representation of the text that this {@code UITextField} contains
     *
     * @return text:
     *              The text that this {@code UITextField} contains.
     */
    public String getText() {
        return this.textField;
    }

    /**
     * Set the text of this {@code UITextField} to the given value
     * 
     * @param text : the new {@code textField} of this {@code UITextField} 
     */
    public void setText(String text) {
        textField = text;
    }

    /**
     * A string variable to represent the contents of this {@code UITextField}.
     */
    private String textField = "";

    // ============== Dimension parameters ====================
    /**
     * An integer variable to represent the height
     * of the text contained in this {@code UITextField}.
     */
    private final int textHeight;
    /**
     * An integer variable to represent the width
     * of the text contained in this {@code UITextField}.
     */
    private int textWidth;

    // ================ Font variables ==============
    /**
     * A variable to denote the {@link Font} used for the text of this {@code UITextField}.
     */
    private final Font textFieldFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, getHeight());
    /**
     * A variable to denote the {@link Color} used for the text of this {@code UITextField}.
     */
    private final Color textFieldColor = Color.BLACK;
    
    /**
     * A variable to denote the {@link FontMetrics} used for the text of this {@code UITextField}.
     */
    private FontMetrics metrics;
}
