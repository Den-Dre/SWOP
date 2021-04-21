package UserInterface;

import java.awt.*;
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
     * Initialise this Frame with the given parameters.
     *
     * @param width        : The width of this Frame.
     * @param height       : The height of this Frame.
     * @param currentUrl   : The URL of the document currently loaded.
     * @param bookmarksBar : The {@link BookmarksBar} associated to this {@code BoomkarksDialog}.
     * @param browsr       : The {@link Browsr} associated to this {@code BoomkarksDialog}.
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

        // URL input must be prefilled:
        urlInput.changeTextTo(currentUrl);
        this.form = getForm(currentUrl);
    }

    /**
     * Construct the hardcoded layout of this {@code BookmarksDialog}.
     *
     * @param currentUrl:
     *           The {@link String} representation of the URL currently entered in the {@link AddressBar}.
     * @return new UIForm: a {@link UIForm} object that represents the form of this {@code BookmarksDialog}.
     */
//    UIForm constructForm(String currentUrl) {
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

        return new UIForm(offset, offset, "bookmarksDialog", outerTable);
    }

    /**
     * Render this {@code BookmarksDialog}.
     *
     * @param g: The graphics used to render this {@code BookmarksDialog}.
     */
    @Override
    public void Render(Graphics g) {
        this.form.Render(g);
    }


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
            		bookmarksBar.addBookmark(nameInput.getText(), urlInput.getText());
            case "Cancel" ->  // nothing needs to be done here
                    System.out.println("Cancel pressed.");
        }

        // Set the BrowsrLayout of the linked Browsr back to the regular Layout.
        Browsr browsr = getBrowsr();
        browsr.setBrowsrLayout(browsr.new RegularLayout());
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
    private final UIForm form;

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
