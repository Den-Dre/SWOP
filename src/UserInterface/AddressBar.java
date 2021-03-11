package UserInterface;

import domainmodel.DocumentListener;
import domainmodel.UIController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * A class to represent an address bar in the browsr,
 * as an extension of a Frame, in the UI layer.
 */
public class AddressBar extends Frame implements DocumentListener {
    /**
     * Creates an AddressBar object.
     *
     * @param x: x-position on the window
     * @param y: y-position on the window
     * @param width: the width of this AddressBar
     * @param height: the height of this AddressBar
     * @param offset: distance between this AddressBar and left, right and top window edge
     */
    public AddressBar(int x, int y, int width, int height, int offset) throws Exception {
        super(x, y, width, height);
        this.offset = offset;
    }

    /**
     * Renders this {@code AddressBar} using the given {@code Graphics} parameter
     * - Renders the url
     * - Renders the selected text
     * - Renders the cursor
     * @param g: The graphics object that is used to draw this {@code AddressBar}
     */
    @Override
    public void Render(Graphics g) {
        setFontMetrics(g);
        updateSelectStart();
        drawSelection(g);
        drawAddressBar(g);
        printURL(g);
        printCursor(g);
    }

    /**
     * Prints the url into the addressBar.
     * - The color is textcolor. - The font is font.
     * - The last term in the calculation is to draw it slightly higher than the underside of the addressBar.
     *
     * @param g: The graphics that contain the information to be printed
     */
    private void printURL(Graphics g){
        g.setColor(textColor);
        g.setFont(font);
        g.drawString(this.getURL(), this.URLStart, this.getyPos()+this.getHeight()-(this.getHeight()/5));
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
        this.cursorPos = new int[] {metrics.stringWidth(this.getURL().substring(0,this.cursor))+this.getxPos()+(URLStart/2), getCursorYPos()};
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
        this.selectStartPos = new int[] {metrics.stringWidth(this.getURL().substring(0,this.selectStart))+this.getxPos()+(URLStart/2), this.getyPos()};
    }

    /**
     * Draw a rectangle of color highlightcolor behind the selected characters.
     *
     * @param g: The graphics that will be updated.
     */
    private void drawSelection(Graphics g) {
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
    private void drawAddressBar(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight());
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
            // Something else to do when clicked on addressbar?
        }
        else {
            // clicking out is the same as pressing enter
             handleEnter();

//            this.toggleFocus(false);
//            this.moveCursor(this.getURL().length()); // Put cursor at the end of AddressBar
//            // go to this.getURL()
        }
    }

    /**
     * Returns true if and oly if the given x and y coordinates are in the addressBar area.
     * @param x: x-coordinate
     * @param y: y-coordinate
     */
    private boolean wasClicked(int x, int y){
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    private void resetSelectStart() {
        selectStart = 0;
        selectStartPos = new int[] {this.getxPos() + (URLStart/2), this.getyPos()};
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
            case 35 -> moveCursor(this.getURL().length());
            case 36 -> moveCursor(-this.getURL().length());
            case 37 -> moveCursor(-1);
            case 39 -> moveCursor(1);
            default -> handleNoSpecialKey(id, keyCode, keyChar, modifiersEx);
        }
        this.doSelect = (keyCode == 39 || keyCode == 37 || keyCode == 35 || keyCode == 36) && modifiersEx == 64;
        updateSelectStart();
    }

    /**
     * Contains the actions needed for the escape key
     * <ul>
     *     <li>This AddressBars focus is set to false</li>
     *     <li>This url is reset to before editing</li>
     *     <li>The cursor is moved to the end of the url</li>
     * </ul>
     */
    private void handleEscape() {
        this.toggleFocus(false);
        this.setURL(this.getOldUrl());
        this.moveCursor(this.getURL().length());
    }

    /**
     * Contains the actions needed for the enter key
     * <ul>
     *     <li>This AddressBars focus is set to false</li>
     *     <li>The editing of the url is finalized</li>
     *     <li>The cursor is moved to the end of the url</li>
     * </ul>
     */
    private void handleEnter() {
        this.toggleFocus(false);
        this.updateCopyUrl();
        this.moveCursor(this.getURL().length());
        System.out.println("Enter pressed");
        System.out.println(this.getURL());
        if (uiController != null)
            this.uiController.loadDocument(this.getURL());
    }

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
        if (getURL().length() == 0) return;
        StringBuilder newUrl = new StringBuilder(this.getURL());
        int start = Math.min(this.cursor, this.selectStart);
        int stop = Math.max(this.cursor, this.selectStart);
        if (doSelect) {
            //if (start != 0) start += 1;
            newUrl.delete(start, stop);
            if (cursor < selectStart) moveCursor(0);
            else moveCursor(start-stop);
        }
        else {
            if (mode == 0) {
                int deleteindex = cursor-1;
                if (deleteindex < 0) return;
                newUrl.deleteCharAt(deleteindex);
                moveCursor(-1);
            }
            else if (mode == 1) {
                int deleteindex = cursor;
                if (deleteindex == getURL().length()) return;
                newUrl.deleteCharAt(deleteindex);
                moveCursor(0);
            }
        }
        this.setURL(newUrl.toString());
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
     * @param id: the KeyEvent id associated with the type of KeyEvent
     * @param keyCode: the KeyEvent code associated with the key
     * @param keyChar: the character representation of the pressed key
     */
    private void handleNoSpecialKey(int id, int keyCode, char keyChar, int modifier) {
        if (keyCode == 131) {
            keyChar = '~';
            System.out.println("tilde");
        }
        if ((keyCode >= 44 && keyCode <= 111) || (keyCode >= 512 && keyCode <= 523)) {
            if (doSelect) handleRemoveCharacters(0);
            StringBuilder newUrl = new StringBuilder(this.getURL());
            newUrl.insert(this.cursor, keyChar);
            this.setURL(newUrl.toString());
            this.moveCursor(1);
        }
    }

    /**
     * This method handles resizes.
     * It makes sure the addressBar is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     * @param newWindowHeight: parameter containing the new window-height
     * @param newWindowWidth: parameter containing the new window-width
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.setWidth(newWindowWidth-2*offset);
    }

    // Distance between addressBar and the window edges
    int offset;

    //Two URL's to be able to 'rollback' the old url if editing is cancelled.
    // URL is the variable to be edited. URLCopy gets updated after it is certain the edited url is final.
    private String URL = ""; // The url starts empty
    private String URLCopy = URL;
    private final int URLStart = this.getxPos()+5;

    // The UIController for the addressbar
    private UIController uiController;

    // Cursor variables
    private int cursor = 0;
    private final int[] cursorDimensions = new int[] {3, this.getHeight()*3/4};
    private int[] cursorPos = new int[] {this.getxPos() + (URLStart/2), getCursorYPos()};
    private final Color cursorColor = Color.BLACK;

    // Selection variables
    private boolean doSelect = false;
    private int selectStart = 0;
    private int[] selectStartPos = new int[] {this.getxPos() + (URLStart/2), this.getyPos()};
    private final Color highlightColor = Color.BLUE;

    // Font variables
    Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, this.getHeight()*3/4);
    FontMetrics metrics;
    int textHeight;
    private final Color textColor = Color.BLACK;

    /**
     * Get the current url of the AddressBar.
     *
     * @return the url string of this AddressBar.
     */
    public String getURL() {
        return URL;
    }

    /**
     * Set the URL of the addressBar to a given URL
     * @param URL
     *        The new URL for this Document
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * Externally change the url, this moves the cursor to the right and toggles focus off.
     * @param URL
     *        The String representation of the url to be set
     */
    public void changeURLto(String URL) {
        this.URL = URL;
        moveCursor(this.getURL().length());
        updateSelectStart();
        toggleFocus(false);
    }

    /**
     * Set the uiController of the AddressBar to the given uiController
     *
     * @param uiController
     *        The uiController to be set
     */
    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    /**
     * Retrurn the old URL of the adressBar
     */
    public String getOldUrl() {
        return this.URLCopy;
    }

    /**
     * Updates the URLCopy to the new URL
     */
    private void updateCopyUrl() {
        this.URLCopy = this.URL;
    }

    /**
     * Move the cursor delta index positions in the url.
     * It cannot move further than the length of the url (both left and right)
     *
     * @param delta: The amount that the cursor should be moved.
     */
    private void moveCursor(int delta) {
        int urlLength = this.getURL().length();
        int newCursor = this.cursor + delta;
        if (newCursor > urlLength) newCursor = urlLength;
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
     *  Signals that the url has been changed to a given url
     */
    @Override
    public void contentChanged() {
        String newUrl = uiController.getUrlString();
        this.changeURLto(newUrl);
    }
}
