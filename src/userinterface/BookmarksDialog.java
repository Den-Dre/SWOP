package userinterface;

import java.awt.*;
import java.awt.print.Book;
import java.util.ArrayList;

/**
 * A class to represent a dialog screen
 * that's displayed when the user wants to
 * save a bookmark.
 *
 * <p>
 *     The dialog asks for a Name and URL value
 *     of the bookmark to be saved, along with providing
 *     two {@link UIButton}'s: one to save the bookmark
 *     and one to cancel the current action of saving
 *     a bookmark.
 * </p>
 */
public class BookmarksDialog extends GenericDialogScreen {
    /**
     * Initialize this {@code BookmarksDialog} with the given parameters.
     *
     * @param width        : The width of this {@code BookmarksDialog}.
     * @param height       : The height of this {@code BookmarksDialog}.
     * @param currentUrl   : The URL of the document currently loaded.
     * @param bookmarksBar : The {@link BookmarksBar} associated to this {@code BoomkarksDialog}.
     * @param browsr       : The {@link Browsr} associated to this {@code BoomkarksDialog}.
     * @throws IllegalDimensionException: When one of the dimensions of this {@code BookmarksDialog} is negative
     */
    public BookmarksDialog(int width, int height, String currentUrl, BookmarksBar bookmarksBar, Browsr browsr) throws IllegalDimensionException {
        // Must cover the entire screen:
        super(0, 0, width, height, browsr, currentUrl);
        int offset = getOffset();
        int textSize = getTextSize();

        this.bookmarksBar = bookmarksBar;
        this.nameInput = new UITextInputField(offset, offset,100,textSize, "Name");
        this.urlInput = new UITextInputField(offset, offset,100,textSize, "URL");

        this.nameInputContents = nameInput;
        this.urlInputContents = urlInput;

        // URL input must be prefilled:
        this.urlInput.changeTextTo(currentUrl);
        this.form = getForm(currentUrl);
    }
    
    /**
     * Construct the hardcoded layout of this {@code BookmarksDialog}.
     *
     * @param currentUrl:
     *           The {@link String} representation of the URL currently entered in the {@link AddressBar}.
     * @return new UIForm: a {@link UIActionForm} object that represents the form of this {@code BookmarksDialog}.
     */
    public UIForm getForm(String currentUrl) {
        // Hard coded bookmarks dialog screen
        int offset = getOffset();
        int textSize = getTextSize();

        UITextField nameText = new UITextField(offset, offset,0,textSize, "Name");
        UITextField urlText = new UITextField(offset, offset,0,textSize, "URL");

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
        outerTable.handleResize(getBrowsr().getWidth(),getBrowsr().getHeight());

        return new UIActionForm(offset, offset, "bookmarksDialog", outerTable);
    }


    /**
     * render this {@code BookmarksDialog}.
     *
     * @param g: The graphics used to render this {@code BookmarksDialog}.
     */
    @Override
    public void render(Graphics g) {
        this.form.render(g);
    }

    /**
     * Handle mouseEvents. Determine if this LeafPane was pressed and do the right actions.
     *
     * @param id          : The type of mouse activity
     * @param x           : The x coordinate of the mouse activity
     * @param y           : The y coordinate of the mouse activity
     * @param clickCount  : The number of clicks
     * @param button      : The mouse button that was clicked
     * @param modifiersEx : The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        ReturnMessage result = form.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        try {
			handleClickResult(result);
		} catch (IllegalArgumentException e) {
			System.out.println("Can't save bookmark with empty name or url.");
		}
    }

    /**
     * Help method to handle mouse clicks on this {@code BookmarksDialog}.
     *
     * @param result:
     *              the string returned by the clicked element of the content of this {@code BookmarksDialog}.
     */
    protected void handleClickResult(ReturnMessage result) {
        if (result.getType() != ReturnMessage.Type.Button)
            return;
        switch (result.getContent()) {
            case "Add Bookmark" ->
            		bookmarksBar.addBookmark(nameInputContents.getText(), urlInputContents.getText());
            case "Cancel" ->  // nothing needs to be done here
                    System.out.println("Cancel pressed.");
        }

        // Set the BrowsrLayout of the linked Browsr back to the regular Layout.
        Browsr browsr = getBrowsr();
        browsr.setBrowsrLayout(browsr.new RegularLayout());
    }

    /**
     * Get the text contained in the 'Name' {@link UITextField} of the content of this {@code BookmarksDialog}.
     *
     * @return text:
     *          The text contained in the "Name" {@link UITextInputField} of this {@code BookmarksDialog}.
     */
    public String getNameInput() {
        return this.nameInputContents.getText();
    }

    /**
     * Get the text contained in the 'URL' {@link UITextField} of the content of this {@code BookmarksDialog}.
     *
     * @return text:
     *          The text contained in the "Name" {@link UITextInputField} of this {@code BookmarksDialog}.
     */
    public String getURLInput() {
        return this.urlInputContents.getText();
    }

    /**
     * A variable to represent the content
     * that this {@code BookmarksDialog}
     * displays. The content is contained
     * in a {@link UIForm}.
     */
    private final UIForm form;

    /**
     * The {@link BookmarksBar} associated
     * to this {@code BookmarksBarDialog}.
     */
    private final BookmarksBar bookmarksBar;

    /**
     * The input field which takes a name for
     * the to be created bookmark. 
     * Denoted by a {@link UITextInputField}.
     */
    private final UITextInputField nameInput;

    /**
     * The {@link UITextInputField} which takes an URL in
     * string form for the to be created bookmark.
     */
    private final UITextInputField urlInput;

    /**
     * The {@link UITextInputField} denoting the name input contents
     * of this {@code BookmarksDialog}.
     */
    private final UITextInputField nameInputContents;

    /**
     * The {@link UITextInputField} denoting the url input contents
     * of this {@code BookmarksDialog}.
     */
    private final UITextInputField urlInputContents;
}
