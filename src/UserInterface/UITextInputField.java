package UserInterface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UITextInputField extends DocumentCell{
    /**
     * Initialise this UITextInputField with the given parameters.
     *
     * @param x      : The x coordinate of this UITextInputField
     * @param y      : The y coordinate of this UITextInputField
     * @param width  : The width of this UITextInputField
     * @param height : The height of this UITextInputField
     * @throws IllegalDimensionException: When the dimensions of {@code super} are illegal.
     */
    public UITextInputField(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
        //int inputFieldWidth = 50;
        //setWidth(inputFieldWidth);
    }

    /**
     * Renders this {@code AddressBar} using the given {@code Graphics} parameter
     * <ul>
     *       <li>  Renders the url </li>
     *       <li>  Renders the selected text </li>
     *       <li>  Renders the cursor </li>
     * </ul>
     * @param g: The graphics object that is used to draw this {@code AddressBar}
     */
    @Override
    public void Render(Graphics g) {
        setFontMetrics(g);
        updateSelectStart();
        drawSelection(g);
        drawBox(g);
        printText(g);
        printCursor(g);
    }

    /**
     * Prints the url into the addressBar.
     * <ul>
     *      <li>  The color is textcolor. </li>
     *      <li>  The font is font.</li>
     *      <li>  The last term in the calculation is to draw it slightly higher than the underside of the addressBar.</li>
     * </ul>
     * @param g: The graphics that contain the information to be printed
     */
    private void printText(Graphics g){
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(this.getText(), this.textStart+getxPos(), this.getyPos()+this.getHeight()-(this.getHeight()/5));
    }

    /**
     * Draws the cursor onto the right location on screen, only if the addressBar has focus.
     * @param g: The graphics that contain the information to be printed
     */
    private void printCursor(Graphics g) {
        if (!this.hasFocus) return;
        g.setColor(cursorColor);
        g.fillRect(this.cursorPos[0], this.cursorPos[1], this.cursorDimensions[0], this.cursorDimensions[1]);
    }

    /**
     * This gets the metrics of the font and uses it to determine the position of the cursor on screen.
     * The metrics includes information about the width of the characters in that font.
     * The idea here is to ask the distance from the beginning of the url to the cursorindex (=this.cursor)
     *
     * @param g: The graphics that contain the information to be printed
     */
    private void setFontMetrics(Graphics g) {
        this.metrics = g.getFontMetrics(font);
        int offset = 0;
        if (getText().length() == 0) offset = cursorOffset;
        this.cursorPos = new int[] {metrics.stringWidth(this.getText().substring(0,this.cursor))+this.getxPos()+(textStart)+offset, getCursorYPos()};
        this.textHeight = metrics.getHeight();
    }

    /**
     * Updates the selectStart variable. This variable moves together with the cursor when the user is not selecting
     * anything. When the user does, it stays in place to determine the characters that are selected.
     */
    private void updateSelectStart() {
        if (this.doSelect) return;
        this.selectStart = this.cursor;
        if (metrics == null) return;
        int offset = 0;
        if (getText().length() == 0) offset = cursorOffset;
        this.selectStartPos = new int[] {metrics.stringWidth(this.getText().substring(0,this.selectStart))+this.getxPos()+(textStart), this.getyPos()};
    }

    /**
     * Draw a rectangle of color highlight color behind the selected characters.
     *
     * @param g: The graphics that will be updated.
     */
    private void drawSelection(Graphics g) {
        System.out.println(doSelect);
        if (!doSelect) return;
        if (this.hasFocus) {
            g.setColor(this.highlightColor);
            int start = Math.min(this.cursorPos[0], this.selectStartPos[0]);
            int stop = Math.max(this.cursorPos[0], this.selectStartPos[0])-start;
            g.fillRect(start, this.getyPos(), stop, textHeight);
        }
    }

    /**
     * Draw a black frame where the addressBar is.
     *
     * @param g: The graphics that will be pained.
     */
    private void drawBox(Graphics g) {
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        if (hasFocus) {
            g.setColor(focusColor);
            g2.setStroke(new BasicStroke(2));
        }
        g.drawRoundRect(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight(), 3,3);
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * Handle mouseEvents. Determine if the addressBar was pressed and do the right actions.
     *
     * @param id: The type of mouse activity
     * @param x: The x coordinate of the mouse activity
     * @param y: The y coordinate of the mouse activity
     * @param clickCount: The number of clicks
     * @param button: The mouse button that was clicked
     * @param modifiersEx: The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (button != MouseEvent.BUTTON1) return; // Button1 is left mouse button
        if (id != MouseEvent.MOUSE_CLICKED) return;
        if (this.wasClicked(x,y)) {
            if (!this.hasFocus) {
                doSelect = true;
                resetSelectStart();
            }
            this.toggleFocus(true);
        }
        else {
            // clicking out is the same as pressing enter.
            // Only react if the addressbar has focus
            if (hasFocus) handleEnter();
            toggleFocus(false);
        }
    }

    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        handleMouse(id, x, y, clickCount, button, modifier);
        return "";
    }

    /**
     * Reset the selection cursor back to the start of this {@code AddressBar}.
     */
    private void resetSelectStart() {
        selectStart = 0;
        int offset = 0;
        if (getText().length() == 0) offset = cursorOffset;
        selectStartPos = new int[] {this.getxPos() + textStart + offset, this.getyPos()};
    }

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     * <ul>
     *     <li>If the addressBar has no focus, it does nothing.</li>
     *     <li>Else, if a key is clicked, it looks at what key it is and does the corresponding action.</li>
     * </ul>
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode: The KeyEvent code (Determines the involved key)
     * @param keyChar: The character representation of the involved key
     * @param modifiersEx: Specifies other keys that were involved in the event
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        //System.out.println(Arrays.toString(new int[]{id, keyCode, modifiersEx}));
        if (!this.hasFocus) return;
        if (id != KeyEvent.KEY_PRESSED) return;

        switch (keyCode) {
            case 27 -> handleEscape();
            case 10 -> handleEnter();
            case 8 -> handleRemoveCharacters(0); //act as backspace
            case 127 -> handleRemoveCharacters(1); //act as delete
            case 35 -> moveCursor(this.getText().length());
            case 36 -> moveCursor(-this.getText().length());
            case 37 -> moveCursor(-1);
            case 39 -> moveCursor(1);
            default -> handleNoSpecialKey(id, keyCode, keyChar, modifiersEx);
        }
        this.doSelect = (keyCode == 39 || keyCode == 37 || keyCode == 35 || keyCode == 36) && modifiersEx == 64;
        updateSelectStart();
    }

    /**
     * Contains the actions needed for the escape key
     */
    void handleEscape() { }

    /**
     * Contains the actions needed for the enter key
     */
    void handleEnter() { }

    /**
     * Remove characters in the following manner:
     * <ul>
     *     <li>If backspace is pressed, zero, one or more characters need to be removed.</li>
     *     <li>If no characters are selected. The character before the cursor is removed.
     *         If there are (one or more) selected characters, these are removed.</li>
     *     <li>The cursor position is then adjusted accordingly.</li>
     * </ul>
     * @param mode: if 0, function acts as backspace, else as delete
     */
    private void handleRemoveCharacters(int mode) {
        if (getText().length() == 0) return;
        StringBuilder newText = new StringBuilder(this.getText());
        int start = Math.min(this.cursor, this.selectStart);
        int stop = Math.max(this.cursor, this.selectStart);
        if (doSelect) {
            //if (start != 0) start += 1;
            newText.delete(start, stop);
            if (cursor < selectStart) moveCursor(0);
            else moveCursor(start-stop);
        }
        else {
            if (mode == 0) {
                int deleteindex = cursor-1;
                if (deleteindex < 0) return;
                newText.deleteCharAt(deleteindex);
                moveCursor(-1);
            }
            else if (mode == 1) {
                int deleteindex = cursor;
                if (deleteindex == getText().length()) return;
                newText.deleteCharAt(deleteindex);
                moveCursor(0);
            }
        }
        this.setText(newText.toString());
    }

    /**
     * <ul>
     *     <li>This method handles all the normal keys on the keyboard with keyCodes in these ranges: [44,111], [512,523]
     *     These contain normal letters, numbers and some more.</li>
     *     <li>!! Not all characters are supported yet.</li>
     *     <li>(You need to look at a keyCode table and look which characters can be typed, e.g. some special keys that need shift can't be pressed yet)</li>
     *     <li>The characters are inserted at the cursor location.</li>
     *     <li>If text is selected, it will be replaced with the typed character.</li>
     * </ul>
     *
     * Based on: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
     *
     * @param id: the KeyEvent id associated with the type of KeyEvent
     * @param keyCode: the KeyEvent code associated with the key
     * @param keyChar: the character representation of the pressed key
     */
    private void handleNoSpecialKey(int id, int keyCode, char keyChar, int modifier) {
        if ((keyCode >= 44 && keyCode <= 111) || (keyCode >= 512 && keyCode <= 523) ||
                (keyCode == KeyEvent.VK_SPACE) || (keyCode >= 128 && keyCode <= 143) ||
                (keyCode >= 150 && keyCode <= 153) || (keyCode >= 160 && keyCode <= 162) ||
                (keyCode == 192) || (keyCode == 222)) {
            if (doSelect) handleRemoveCharacters(0);
            StringBuilder newText = new StringBuilder(this.getText());
            newText.insert(this.cursor, keyChar);
            this.setText(newText.toString());
            this.moveCursor(1);
        }
    }

    /**
     * Get the current url of the AddressBar.
     *
     * @return the url string of this AddressBar.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the addressBar to a given text
     * @param text
     *        The new text for this Document
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Externally change the url, this moves the cursor to the right and toggles focus off.
     * @param text
     *        The String representation of the url to be set
     */
    public void changeTextTo(String text) {
        this.text = text;
        moveCursor(this.getText().length());
        updateSelectStart();
        toggleFocus(false);
    }

    /**
     * Move the cursor delta index positions in the text.
     * It cannot move further than the length of the text (both left and right)
     *
     * @param delta: The amount that the cursor should be moved.
     */
    void moveCursor(int delta) {
        int textLength = this.getText().length();
        int newCursor = this.cursor + delta;
        if (newCursor > textLength) newCursor = textLength;
        if (newCursor < 0) newCursor = 0;
        this.cursor = newCursor;
    }

    /**
     * Returns the y-position of the cursor, in a way that the cursor is vertically centered in the addressbar.
     */
    private int getCursorYPos() {
        return getyPos()+(getHeight()-cursorDimensions[1])/2;
    }

    /**
     * A string variable to hold the
     * current contents of this AddressBar
     */
    private String text = ""; // The url starts off empty

    /**
     * An integer variable to denote the position
     * where the cursor should return to if it
     * is placed at the start of this AddressBar.
     */
    private final int textStart = 2;

    /**
     * An integer variable to denote the current
     * position of the cursor in this AddressBar
     */
    private int cursor = 0;

    private int cursorOffset = 2;

    /**
     * An integer variable to denote the graphical
     * dimensions of the cursor displayed this AddressBar.
     */
    private final int[] cursorDimensions = new int[] {3, this.getHeight()*3/4};

    /**
     * An integer array that contains two values:
     * <ul>
     *     <li>The current x coordinate of the cursor </li>
     *     <li>The current y coordinate of the cursor</li>
     * </ul>
     */
    private int[] cursorPos = new int[] {this.getxPos() + (textStart) + cursorOffset, getCursorYPos()};

    /**
     * A variable that holds the {@link Color}
     * of the cursor in this AddressBar.
     */
    private final Color cursorColor = Color.BLACK;

    // =============== Selection Variables ===================
    /**
     * A boolean variable to denote whether the user
     * is currently selecting content of this AddressBar.
     */
    private boolean doSelect = false;

    /**
     * An integer variable to denote the start
     * of a selection action in this AddressBar.
     */
    private int selectStart = 0;

    /**
     * An integer array that contains two values:
     * <ul>
     *     <li>The current x coordinate of the content selection action </li>
     *     <li>The current y coordinate of the content selection action</li>
     * </ul>
     */
    private int[] selectStartPos = new int[] {this.getxPos() + (textStart), this.getyPos()};

    /**
     * A variable that denotes the {@link Color}
     * that should be displayed when selecting
     * content of this AddressBar.
     */
    private final Color highlightColor = Color.BLUE;

    // ================ Font variables =======================
    /**
     * A variable to denote the {@link Font}
     * the text displayed in this AddressBar
     * should be typed in.
     */
    Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, this.getHeight()*3/4);

    /**
     * A variable to hold the {@link FontMetrics}
     * associated to the text in this AddressBar
     */
    FontMetrics metrics;

    /**
     * An integer variable to denote the
     * height of the text displayed in this
     * AddressBar.
     */
    int textHeight;

    /**
     * A variable to denote the
     * {@link Color} of the text
     * displayed in this AddressBar.
     */
    private final Color textColor = Color.BLACK;

    /**
     * A variable to denote the
     * {@link Color} of the box
     * surrounding this UITextInputField
     */
    private final Color focusColor = Color.BLUE;

}
