package userinterface;

import domainlayer.DocumentListener;
import domainlayer.UIController;

/**
 * A class to represent an address bar in the browsr,
 * as an extension of a AbstractFrame, in the UI layer.
 */
public class AddressBar extends UITextInputField implements DocumentListener {

    /**
     * Creates an AddressBar object.
     *
     * @param x: x-position on the window
     * @param y: y-position on the window
     * @param width: the width of this AddressBar
     * @param height: the height of this AddressBar
     * @param offset: distance between this AddressBar and left, right and top window edge
     * @throws IllegalDimensionException: When one of the dimensions of the {@link AbstractFrame} of this AddressBar is negative
     */
    public AddressBar(int x, int y, int width, int height, int offset) throws IllegalDimensionException{
        super(x, y, width, height);
        this.offset = offset;
        
    }

    /**
     * Contains the actions needed for the escape key
     * <ul>
     *     <li>This AddressBars focus is set to false</li>
     *     <li>This url is reset to before editing</li>
     *     <li>The cursor is moved to the end of the url</li>
     * </ul>
     */
    @Override
    void handleEscape() {
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
    @Override
    void handleEnter() {
        this.toggleFocus(false);
        this.updateCopyUrl();
        this.moveCursor(this.getURL().length());
        if (uiController != null)
            this.uiController.loadDocument(this.getURL());
    }

    /**
     * Method that specifies what this AddressBar should do when
     * it gets notified of a content-change
     */
    @Override
    public void contentChanged() {
        String newUrl = uiController.getUrlString();
        this.changeURLto(newUrl);
    }

    /**
     * This method handles resizes.
     * It makes sure the addressBar is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmakrBar} would be rendered with
     *          the given absolute width, and thus one would need to guess the
     *          correct initial size of the window. Using this mehtod, widths are
     *          automatically adjusted: both at initialisation and at runtime.</p>
     *
     * @param newWindowHeight: parameter containing the new window-height
     * @param newWindowWidth: parameter containing the new window-width
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.setWidth(newWindowWidth-2*offset);
    }

    /**
     * Get the current url of the AddressBar.
     *
     * @return the url string of this AddressBar.
     */
    public String getURL() {
        return getText();
    }

    /**
     * Set the URL of the addressBar to a given URL
     * @param URL
     *        The new URL for this Document
     */
    public void setURL(String URL) { setText(URL); }

    /**
     * Externally change the url, this moves the cursor to the right and toggles focus off.
     * @param URL
     *        The String representation of the url to be set
     */
    public void changeURLto(String URL) {
        changeTextTo(URL);
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
     *
     * @return url:
     *          The old URL linked to this AddressBar.
     */
    public String getOldUrl() {
        return urlCopy;
    }

    /**
     * Updates the URLCopy to the new URL
     */
    private void updateCopyUrl() {
        this.urlCopy = getText();
    }

    /**
     * The {@link UIController} object that is
     * linked to this AddressBar.
     */
    private UIController uiController;

    /**
     * Two text's should be used to be able to 'rollback'
     * the old url if editing is cancelled.
     * textCopy gets updated after it is certain the edited url is final.
     */
    private String urlCopy = getText();

    /**
     * Integer defining the distance between this AddressBar and the window edges
     */
    private final int offset;
}
