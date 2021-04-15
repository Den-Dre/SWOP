package UserInterface;

import java.awt.*;

/**
 * A class to represent an abstract dialog screen.
 * Examples of implementations are {@link BookmarksDialog} and {@code SaveDialog}.
 */
public abstract class GenericDialogScreen extends Frame {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x      : The x coordinate of this Frame.
     * @param y      : The y coordinate of this Frame.
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public GenericDialogScreen(int x, int y, int width, int height, Browsr browsr, String currentUrl) throws IllegalDimensionException {
        super(x, y, width, height);
        this.browsr = browsr;
        this.currentUrl = currentUrl;
    }

    /**
     * Stub method to render this {@code GenericDialogScreen}.
     *
     * @param g: The graphics to be rendered.
     */
    public void Render(Graphics g) { }

    /**
     * A stub method to handle mouse clicks on this {@code GenericDialogScreen}.
     *
     * @param id: The type of mouse activity
     * @param x: The x coordinate of the mouse activity
     * @param y: The y coordinate of the mouse activity
     * @param clickCount: The number of clicks
     * @param button: The mouse button that was clicked
     * @param modifiersEx: The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) { }

    /**
     * A stub method to handle key presses on this {@code GenericDialogScreen}.
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode: The KeyEvent code (Determines the involved key)
     * @param keyChar: The character representation of the involved key
     * @param modifiersEx: Specifies other keys that were involved in the event
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) { }

    /**
     * A stub method to handle resizes of this {@code GenericDialogScreen}.
     *
     * @param newWindowWidth: parameter containing the new window-width of this Frame.
     * @param newWindowHeight: parameter containing the new window-height of this Frame.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) { }

    /**
     * Get the {@link Browsr} associated to
     * this {@code GenericDialogScreen}.
     *
     * @return browsr:
     *      The {@link Browsr} object associated to this GenericDialogScreen.
     */
    Browsr getBrowsr() {
        return this.browsr;
    }

    public UIForm getForm() {
        return constructForm(currentUrl);
    }

    /**
     * Return the form that should be displayed in the dialog screen.
     *
     * @param currentUrl: The url associated to the currently loaded webpage
     *                  in the associated {@link Browsr} object.
     * @return form: The UIForm that should be displayed in this dialog screen.
     */
    abstract UIForm constructForm(String currentUrl);

    /**
     * Return the url of the webpage associated
     * to * this {@code GeneralDialogScreen}.
     *
     * @return url: The url of the webpage associated
     *              to this {@code GeneralDialogScreen}.
     */
    public String getCurrentUrl() {
        return this.currentUrl;
    }

    /**
     * Get the offset for the input, text fields and buttons
     * displayed in this {@code BookmarksDialog}.
     */
    public int getOffset() {
        return 5;
    }

    /**
     * Get the text size of the text displayed
     * in this {@code BookmarksDialog}.
     */
    public int getTextSize() {
        return 14;
    }

    /**
     * A variable to represent the {@link Browsr}
     * object linked to this {@code GenericDialogScreen}.
     */
    private final Browsr browsr;

    /**
     * The url of the currently loaded document
     * in the linked {@link Browsr} object.
     */
    private final String currentUrl;

}
