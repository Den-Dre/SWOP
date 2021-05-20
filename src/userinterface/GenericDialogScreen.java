package userinterface;

/**
 * A class to represent an abstract dialog screen.
 * Examples of implementations are {@link BookmarksDialog} and {@code SaveDialog}.
 */
public abstract class GenericDialogScreen extends AbstractFrame {
    /**
     * Initialize this {@code GenericDialogScreen} with the given parameters.
     *
     * @param x          : The x coordinate of this {@code GenericDialogScreen}.
     * @param y          : The y coordinate of this {@code GenericDialogScreen}.
     * @param width      : The width of this {@code GenericDialogScreen}
     * @param height     : The height of this {@code GenericDialogScreen}
     * @param browsr     : The {@link Browsr} object associated to this {@code GenericDialogScreen}.
     * @param currentUrl : The URL of the doucment currently displayed in the associated {@link Browsr} object.
     * @throws IllegalDimensionException: When one of the dimensions of this {@code GenericDialogScreen} is negative
     */
    public GenericDialogScreen(int x, int y, int width, int height, Browsr browsr, String currentUrl) throws IllegalDimensionException {
        super(x, y, width, height);
        this.browsr = browsr;
        this.currentUrl = currentUrl;
    }

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode: The KeyEvent code (Determines the involved key)
     * @param keyChar: The character representation of the involved key
     * @param modifiersEx: Specifies other keys that were involved in the event
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        this.getForm(currentUrl).handleKey(id, keyCode, keyChar, modifiersEx);
    }

    /**
     * This method handles resizes of this {@code BookmarksDialog}.
     * It makes sure the {@code BookmarksDialog} is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * @param newWindowHeight: parameter containing the new window-height
     * @param newWindowWidth: parameter containing the new window-width
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.getForm(currentUrl).handleResize(newWindowWidth, newWindowHeight);
    }

    /**
     * Get the {@link Browsr} associated to
     * this {@code GenericDialogScreen}.
     *
     * @return browsr:
     *      The {@link Browsr} object associated to this GenericDialogScreen.
     */
    public Browsr getBrowsr() {
        return this.browsr;
    }

//    /**
//     * Get the URL associated to the currently
//     * loaded page.
//     *
//     * @return url: The URL associated to the currently loaded page.
//     */
//    public String getCurrentUrl() {
//        return this.currentUrl;
//    }

    /**
     * Get the contents of this dialog screen.
     *
     * @param currentUrl: The URL of the current document displayed in the associated {@link Browsr} object.
     * @return form: The {@link UIForm} that contains the contents of this dialog screen.
     */
    public abstract UIForm getForm(String currentUrl);

//    /**
//     * Return the form that should be displayed in the dialog screen.
//     *
//     * @param currentUrl: The url associated to the currently loaded webpage
//     *                  in the associated {@link Browsr} object.
//     * @return form: The UIForm that should be displayed in this dialog screen.
//     */
//    abstract UIForm constructForm(String currentUrl);

    /**
     * Get the offset for the input, text fields and buttons
     * displayed in this {@code BookmarksDialog}.
     *
     * @return 5: a fixed value of 5.
     */
    public int getOffset() {
        return 5;
    }

    /**
     * Get the text size of the text displayed
     * in this {@code BookmarksDialog}.
     *
     * @return 14: a fixed value of 14.
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
