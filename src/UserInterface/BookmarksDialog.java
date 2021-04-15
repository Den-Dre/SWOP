package UserInterface;

import java.awt.*;
import java.util.ArrayList;

public class BookmarksDialog extends GenericDialogScreen {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param width      : The width of this Frame.
     * @param height     : The height of this Frame.
     * @param currentUrl : The URL of the document currently loaded.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public BookmarksDialog(int width, int height, String currentUrl, BookmarksBar bookmarksBar, Browsr browsr) throws IllegalDimensionException {
        // Must cover the entire screen:
        super(0, 0, width, height, browsr, currentUrl);
        int offset = getOffset();
        int textSize = getTextSize();

        this.bookmarksBar = bookmarksBar;
        this.nameInput = new UITextInputField(offset, offset,100,textSize, "Name");
        this.urlInput = new UITextInputField(offset, offset,100,textSize, "URL");
        this.content = getForm();
    }

    /**
     * Get the {@link UIForm} contents of this {@code BookmarksDialog}.
     *
     * @return form: the {@link UIForm} linked to this {@code BookmarksDialog}.
     */
    public UIForm getForm() {
        return constructForm(super.getCurrentUrl());
    }

    /**
     * Construct the hardcoded layout of this {@code BookmarksDialog}.
     *
     * @param currentUrl:
     *           The {@link String} representation of the URL currently entered in the {@link AddressBar}.
     * @return new UIForm: a {@link UIForm} object that represents the form of this {@code BookmarksDialog}.
     */
    UIForm constructForm(String currentUrl) {

        int offset = getOffset();
        int textSize = getTextSize();

        // Hard coded bookmarks dialog screen
        UITextField nameText = new UITextField(offset, offset,0,textSize, "Name");
        UITextField urlText = new UITextField(offset, offset,0,textSize, "URL");

        // URL input must be prefilled:
        urlInput.setText(currentUrl);


        UITextField formTitle = new UITextField(offset, offset, 0,textSize, "Add Bookmark");

        ArrayList<ArrayList<DocumentCell>> innerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> innerTableRow1 = new ArrayList<>();
        innerTableRow1.add(nameText);
        innerTableRow1.add(nameInput);
        ArrayList<DocumentCell> innerTableRow2 = new ArrayList<>();
        innerTableRow2.add(urlText);
        innerTableRow2.add(urlInput);
        innerTableRows.add(innerTableRow1);
        innerTableRows.add(innerTableRow2);
        UITable innerTable = new UITable(offset, offset,0,0, innerTableRows);

        UIButton addButton = new UIButton(offset, offset,50,15, "Add Bookmark", "Add Bookmark");
        UIButton cancelButton = new UIButton(offset, offset,50,15, "Cancel", "Cancel");

        ArrayList<ArrayList<DocumentCell>> outerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> outerTableRow1 = new ArrayList<>();
        outerTableRow1.add(formTitle);
        ArrayList<DocumentCell> outerTableRow2 = new ArrayList<>();
        outerTableRow2.add(innerTable);
        ArrayList<DocumentCell> outerTableRow3 = new ArrayList<>();
        outerTableRow3.add(addButton);
        outerTableRow3.add(cancelButton);
        outerTableRows.add(outerTableRow1);
        outerTableRows.add(outerTableRow2);
        outerTableRows.add(outerTableRow3);

        UITable outerTable = new UITable(offset, offset,0,0, outerTableRows);

        return new UIForm(offset, offset, "bookmarksDialog", outerTable);
    }

    /**
     * Render this {@code BookmarksDialog}.
     *
     * @param g: The graphics used to render this {@code BookmarksDialog}.
     */
    @Override
    public void Render(Graphics g) {
        this.content.Render(g);
    }

     /**
     * Handle a mouseclick on this {@code BookmarksDialog}.
     *
     * @param id: The type of mouse activity.
     * @param x: The x coordinate of the mouse activity.
     * @param y: The y coordinate of the mouse activity.
     * @param clickCount: The number of clicks.
     * @param button: The mouse button that was clicked.
     * @param modifiersEx: The control keys that were held on the click.
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        String result = this.content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        try {
			handleClickResult(result);
		} catch (IllegalArgumentException e) {
			System.out.println("bad arguments!!");
		}
    }

    /**
     * Help method to handle mouse clicks on this {@code BookmarksDialog}.
     *
     * @param result:
     *              the string returned by the clicked element of the content of this {@code BookmarksDialog}.
     */
    private void handleClickResult(String result) {
        if (result.equals(""))
            return;
        switch (result) {
            case "Add Bookmark" ->
            		bookmarksBar.addBookmark(nameInput.getText(), urlInput.getText());
            case "Cancel" ->  // nothing needs to be done here
                    System.out.println("Cancel pressed.");
            case "Save" ->
                    System.out.println("Save document not implemented yet."); // TODO also needs to check whether the input differs from ""
        }

        // Set the BrowsrLayout of the linked Browsr back to the regular Layout.
        Browsr browsr = getBrowsr();
        browsr.setBrowsrLayout(browsr.new RegularLayout());
    }


    /**
     * Handle key presses on this {@code SaveDialogLayout}.
     * This method does the right action when a key is pressed.
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent).
     * @param keyCode: The KeyEvent code (Determines the involved key).
     * @param keyChar: The character representation of the involved key.
     * @param modifiersEx: Specifies other keys that were involved in the event.
     */
    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        this.content.handleKey(id, keyCode, keyChar, modifiersEx);
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
        this.content.handleResize(newWindowWidth, newWindowHeight);
    }

    public String getNameInput() {
        return this.nameInput.getText();
    }

    public String getURLInput() {
        return this.urlInput.getText();
    }

    /**
     * A variable to represent the content
     * that this {@code BookmarksDialog}
     * displays. The content is contained
     * in a {@link DocumentCell}.
     */
    private final UIForm content;

    /**
     * The {@link BookmarksBar} associated
     * to this {@code BookmarksBarDialog}.
     */
    private final BookmarksBar bookmarksBar;

    /**
     * The input field which takes a name for
     * the to be created bookmark.
     */
    private final UITextInputField nameInput;

    /**
     * The input field which takes an URL in
     * string form for the to be created bookmark.
     */
    private final UITextInputField urlInput;
}
